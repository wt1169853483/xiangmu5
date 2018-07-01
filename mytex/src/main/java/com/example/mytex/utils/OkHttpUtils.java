package com.example.mytex.utils;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by BoyLucky on 2018/5/29.
 */

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    private Handler handler;

    private OkHttpUtils() {
        okHttpClient = new OkHttpClient.Builder()
                //拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        handler = new Handler();

    }
    public static OkHttpUtils getInstance(){
        if (okHttpUtils == null){
            okHttpUtils = new OkHttpUtils();
        }
        return okHttpUtils;
    }

    /*
    get
     */
    public  void getData(String url, final ICallBack iCallBack){
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (iCallBack != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallBack.onErr("请求失败");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (iCallBack != null){
                    if (response.isSuccessful()&&response.code() == 200) {
                        final String string = response.body().string();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                iCallBack.onSuccess(string);
                            }
                        });
                    }
                }
            }
        });
    }
    /*
    post
     */
    public void postData(String url, Map<String,String> map, final ICallBack iCallBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> bean : map.entrySet()) {
            builder.add(bean.getKey(),bean.getValue());
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (iCallBack != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iCallBack.onErr("请求失败");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (iCallBack != null){
                    if (response.isSuccessful()&&response.code() == 200){
                        final String string = response.body().string();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                iCallBack.onSuccess(string);
                            }
                        });
                    }

                }
            }
        });
    }

    public interface ICallBack{
        void onSuccess(String result);
        void onErr(String fail);
    }
}
