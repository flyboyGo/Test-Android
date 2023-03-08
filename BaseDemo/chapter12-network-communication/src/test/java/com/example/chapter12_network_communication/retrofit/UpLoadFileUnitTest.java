package com.example.chapter12_network_communication.retrofit;


import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpLoadFileUnitTest {

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
    UpLoadService upLoadService = retrofit.create(UpLoadService.class);

    @Test
    public void uploadFileTest() throws IOException {
        File file = new File("C:\\Users\\flyboy\\Desktop\\Android\\info.txt");
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "info.txt",
                RequestBody.create(file, MediaType.parse("text/plain")));

        Call<ResponseBody> call = upLoadService.upload(part);
        System.out.println(call.execute().body().string());
    }


    @Test
    public void downloadTest() throws IOException {
        Response<ResponseBody> response = upLoadService.download("https://www.bejson.com/0bd8fb52-0a21-4a22-b713-cd6c46bad2cd")
                .execute();

            if(response.isSuccessful())
            {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fos = new FileOutputStream("C:\\Users\\flyboy\\Desktop\\Android\\download.apk");
                int len;
                byte[] buffer = new byte[4096];

                while((len = inputStream.read(buffer)) != -1)
                {
                    fos.write(buffer,0,len);
                }

                fos.close();
                inputStream.close();
            }
    }

    @Test
    public void downloadRxJava()
    {
        upLoadService.downloadRxJava("https://www.bejson.com/0bd8fb52-0a21-4a22-b713-cd6c46bad2cd")
        .map(new Function<ResponseBody, Object>() {
            @Override
            public Object apply(ResponseBody responseBody) throws IOException {
                File file = new File("C:\\Users\\flyboy\\Desktop\\Android\\download.apk");
                InputStream inputStream = responseBody.byteStream();
                FileOutputStream fos = new FileOutputStream("C:\\Users\\flyboy\\Desktop\\Android\\download.apk");
                int len;
                byte[] buffer = new byte[4096];

                while((len = inputStream.read(buffer)) != -1)
                {
                    fos.write(buffer,0,len);
                }

                fos.close();
                inputStream.close();
                return file;
            }
        });

//            .subscribe(new Consumer<File>() {
//            @Override
//            public void accept(File file) throws Throwable {
//                System.out.println("文件操作");
//            }
//        })

        while (true){}
    }

}
