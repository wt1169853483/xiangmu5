package com.example.mytex.model;

import com.example.mytex.utils.OkHttpUtils;

/**
 * Created by BoyLucky on 2018/5/29.
 */

public class MainModel {
    public void getData(String url, final GetModel getModel) {
        OkHttpUtils.getInstance().getData(url, new OkHttpUtils.ICallBack() {
            @Override
            public void onSuccess(String result) {
            getModel.getSuccess(result);
            }

            @Override
            public void onErr(String fail) {

            }
        });
    }

    public interface GetModel{
        void getSuccess(String result);
        void getErr();
    }
}
