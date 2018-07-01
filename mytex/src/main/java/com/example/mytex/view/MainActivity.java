package com.example.mytex.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mytex.R;
import com.example.mytex.adapter.XrlvAdapter;
import com.example.mytex.adapter.XrlvItemClicked;
import com.example.mytex.bean.NewBean;
import com.example.mytex.comnnom.Contanx;
import com.example.mytex.db.DbHelper;
import com.example.mytex.presenter.MainPresenter;
import com.example.mytex.view.interfaces.IMainView;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainView{

    private XRecyclerView mXrlv;
    private XrlvAdapter xrlvAdapter;
    private int type=5010;
    private MainPresenter mainPresenter;
    private List<NewBean.DataBean> data;
    private boolean isRefresh = true;
    private int i;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String id = getIntent().getStringExtra("id");
        Integer integer = Integer.valueOf(id);
        i = integer + type;
        Log.e("string0","string-----"+i);
        initView();
        initData();

    }

    private void initData() {
        SharedPreferences sharedPreferences = getSharedPreferences("loginUser", this.MODE_PRIVATE);
        dbHelper = new DbHelper(this);
        mainPresenter = new MainPresenter(this);
        data = new ArrayList<>();
        request();
    }
    public void request(){
        mainPresenter.getDataFrom(Contanx.url+i);
    }

    private void initView() {
        mXrlv = (XRecyclerView) findViewById(R.id.xrlv);
        mXrlv.setLayoutManager(new LinearLayoutManager(this));
        mXrlv.setLoadingMoreEnabled(true);
        mXrlv.setPullRefreshEnabled(true);
        mXrlv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                type++;
                isRefresh = true;
                request();
            }

            @Override
            public void onLoadMore() {
                type++;
                isRefresh = false;
                request();
            }
        });
    }

    @Override
    public void onSuccess(NewBean newBean) {
//        String s = new Gson().toJson(newBean);
        data = newBean.getData();
            if (isRefresh){
                xrlvAdapter = new XrlvAdapter(this, data);
                mXrlv.setAdapter(xrlvAdapter);
                mXrlv.refreshComplete();
            }else{
                if (xrlvAdapter != null){
                    xrlvAdapter.loadMore(data);
                }
                mXrlv.loadMoreComplete();
            }


            xrlvAdapter.setXrlvItemClicked(new XrlvItemClicked() {
                @Override
                public void onItemLongLicked(View view, int proision) {

                    data.remove(proision);
                    xrlvAdapter.notifyItemRemoved(proision);
                }
            });

    }
}
