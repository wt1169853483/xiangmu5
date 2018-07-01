package com.example.mytex.presenter;

import android.text.TextUtils;

import com.example.mytex.bean.NewBean;
import com.example.mytex.model.MainModel;
import com.example.mytex.view.interfaces.IMainView;
import com.google.gson.Gson;

/**
 * Created by BoyLucky on 2018/5/29.
 */

public class MainPresenter {
    private IMainView iMainView;
    private MainModel mainModel;
    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
        mainModel = new MainModel();
    }

    public void getDataFrom(String url) {
        mainModel.getData(url, new MainModel.GetModel() {
            @Override
            public void getSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    String replace = result.replace("null(", "").replace(")", "");
                    NewBean newBean = new Gson().fromJson(replace, NewBean.class);
                    iMainView.onSuccess(newBean);
                }
            }

            @Override
            public void getErr() {

            }
        });
    }
}
