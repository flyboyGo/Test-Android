package com.example.chapter06_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.chapter06_client.entity.Image;
import com.example.chapter06_client.util.FileUtil;
import com.example.chapter06_client.util.PermissionUtil;
import com.example.chapter06_client.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProviderMmsActivity extends AppCompatActivity {

    private EditText et_phone;
    private EditText et_title;
    private EditText et_message;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    // 图片列表
    private List<Image> imageList = new ArrayList<>();
    private GridLayout gl_appendix;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_mms);

        et_phone = findViewById(R.id.et_phone);
        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);

        gl_appendix = findViewById(R.id.gl_appendix);


        //手动让MediaStore扫描入库,将新添加的文件,扫描到
        MediaScannerConnection.scanFile(this,
                new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
                null, null);

        if(PermissionUtil.checkPermission(this,PERMISSIONS,PERMISSION_REQUEST_CODE)){
            // 加载图片列表
            loadImageList();
            // 显示图像网格
            showImageGrid();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && PermissionUtil.checkGrant(grantResults)) {
            // 加载图片列表
            loadImageList();
            // 显示图像网格
            showImageGrid();
        }
    }

    // 显示图像网格
    private void showImageGrid() {
        gl_appendix.removeAllViews();
        for (Image image : imageList) {
            // image -> imageView
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            imageView.setImageBitmap(bitmap);
            // 设置图片的缩放类型
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // 设置图像的布局参数
            int px = Utils.dipToPx(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px,px);
            imageView.setLayoutParams(params);
            // 设置图像视图的内部间距
            int padding = Utils.dipToPx(this, 5);
            imageView.setPadding(padding,padding,padding,padding);
            // 图像视图添加点击事件
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // 发送带图片的彩信
                    sendMms(et_phone.getText().toString(),
                            et_title.getText().toString(),
                            et_message.getText().toString(),
                            image.path);
                }
            });
            //把图像视图添加至网格布局
            gl_appendix.addView(imageView);
        }
    }

    // 加载图片列表
    @SuppressLint("Range")
    private void loadImageList() {
        // MediaStore
        String [] columns = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA, // 文件路径
        };

        // 图片大小在300KB以内
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 307200",
                null,
                "_size DESC");

        int count = 0;
        if(cursor != null)
        {
            while (cursor.moveToNext() && count < 6)
            {
                Image image = new Image();
                image.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if(FileUtil.checkFileUri(this,image.path))
                {
                    count++;
                    imageList.add(image);
                }
                Log.d("test","image:" + image.toString());
            }
        }
    }

    // 发送彩信
    private void sendMms(String phone, String title, String message,String path) {
        // 根据指定路径构建Uri对象
        Uri uri =Uri.parse(path);
        // 兼容Android7.0，把访问文件的Uri方式改为FileProvider
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            // 通过FileProvider获得文件的Uri的访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d("test",String.format("new uri : %s",uri.toString()));
        }
        // 隐式意图
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Intent的接受者将被准许读取Intent 携带的URI数据
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 彩信的内容
        intent.putExtra("address",phone);
        intent.putExtra("subject",title);
        intent.putExtra("sms_body",message);
        // 图片以流的形式存放,彩信的图片附件
        // image:Image{id=49, name='vivo', size=138366, path='/storage/emulated/0/Download/vivo.jpg'}
        // picUri : content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F50/ORIGINAL/NONE/image%2Fjpeg/357677118
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        // 彩信的附件为图片
        intent.setType("image/*");
        // 因为未指定要打开哪个页面，所以系统会在底部弹出选择窗口
        startActivity(intent);
    }

    /*
       注意到以上代码获得了字符串格式的文件路径，而彩信发送应用却要求Uri类型的路径对象，原本可以通
       过代码“Uri.parse(path)”将字符串转换为Uri对象，但是从Android 7.0开始，系统不允许其他应用直接访
       问老格式的路径，必须使用文件提供器FileProvider才能获取合法的Uri路径，相当于A应用申明了共享某
       个文件，然后B应用方可访问该文件
     */
}