package com.wcj.myblend.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wcj.myblend.R;
import com.wcj.myblend.bean.Account;
import com.wcj.myblend.bean.Collection;
import com.wcj.myblend.common.BaseActivity;
import com.wcj.myblend.common.BaseApplication;
import com.wcj.myblend.ui.adapter.CollectionAdapter;
import com.wcj.myblend.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by jayli on 2017/5/16 0016.
 */

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private View loadFailed;
    //声明并初始化数据
    private List<Collection> datas = new ArrayList<>();
    //声明适配器
    private CollectionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recyclerview);
        ButterKnife.bind(this);
        setTitle(getString(R.string.collection));
        setRightVisibility(View.GONE);
        initViews();
    }

    private void initViews() {
        adapter = new CollectionAdapter(CollectionActivity.this,null,false);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_blue_dark,
                android.R.color.holo_green_light, android.R.color.holo_green_dark,
                android.R.color.holo_orange_light, android.R.color.holo_orange_dark,
                android.R.color.holo_purple, android.R.color.holo_red_light
        );
        //初始化EmptyView
        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, (ViewGroup) recyclerview.getParent(), false);

        loadFailed = LayoutInflater.from(this).inflate(R.layout.layout_failed, (ViewGroup) recyclerview.getParent(), false);

        adapter.setEmptyView(emptyView);
        //初始化 开始加载更多的loading View
        adapter.setLoadingView(R.layout.layout_loading);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ayncTask();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ayncTask();
    }

    private void ayncTask(){
        BmobQuery<Collection> query = new BmobQuery<Collection>();
        Account account = BmobUser.getCurrentUser(BaseApplication.getInstance(),Account.class);
        query.addWhereEqualTo("uId",account.getObjectId());
        query.setLimit(30);
        query.findObjects(this, new FindListener<Collection>() {
            @Override
            public void onSuccess(List<Collection> list) {
                if(swipeRefreshLayout != null){
                    if(swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                adapter.setNewData(list);
                adapter.removeEmptyView();
                LogUtils.d("收藏数据:" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                adapter.setLoadFailedView(loadFailed);
            }
        });
    }

}
