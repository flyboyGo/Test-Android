package com.example.chapter05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chapter05.dao.BookDao;
import com.example.chapter05.enity.Book;

import java.util.List;

public class RoomWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_author;
    private EditText et_press;
    private EditText et_price;
    private BookDao bookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_write);

        et_name = findViewById(R.id.et_name);
        et_author = findViewById(R.id.et_author);
        et_press = findViewById(R.id.et_press);
        et_price = findViewById(R.id.et_price);

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        // 从App实例中获取唯一的书籍持久化对象
        bookDao = MyApplication.getInstance().getBookDataBase().bookDao();
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String author = et_author.getText().toString();
        String press = et_press.getText().toString();
        String price = et_price.getText().toString();

        switch (v.getId())
        {
            case R.id.btn_save:
                // 声明一个书籍对象，并填写它的各个字段
                Book book = new Book();
                book.setName(name);
                book.setAuthor(author);
                book.setPress(press);
                book.setPrice(Double.parseDouble(price));

                bookDao.insert(book);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query:
                List<Book> bookList = bookDao.queryAll();
                for(Book b : bookList)
                {
                    Log.d("test",b.toString());
                }
                break;
            case R.id.btn_delete:
                Book book2 = new Book();
                book2.setId(1);
                bookDao.delete(book2);
                break;
            case R.id.btn_update:
                Book book3 = new Book();
                Book book4 = bookDao.queryByName(name).get(0);

                book3.setId(book4.getId());
                book3.setName(name);
                book3.setAuthor(author);
                book3.setPress(press);
                book3.setPrice(Double.parseDouble(price));

                bookDao.update(book3);
                break;
        }
    }
}