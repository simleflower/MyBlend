package com.wcj.myblend.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wcj.myblend.R;
import com.wcj.myblend.bean.NewsChanel;
import com.wcj.myblend.common.BaseFragment;
import com.wcj.myblend.common.parse.JsonMananger;
import com.wcj.myblend.ui.adapter.TabNewsAdapter;
import com.wcj.myblend.utils.FileUtils;
import com.wcj.myblend.utils.HttpException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.vp_news)
    ViewPager vpNews;
    Unbinder unbinder;
    private View rootView;
    private List<Fragment> list_fragment = new ArrayList<>();//定义要装fragment的列表
    private List<NewsChanel> list_title = new ArrayList<>();//定义要装tab名称的列表
    private TabNewsAdapter tabNewsAdapter;//定义Tab的Adapter


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, null);
        setInflateView(rootView);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        list_title.clear();
        String chenelJson = FileUtils.getJson(getActivity(),"newsChanel.json");
        try {
            list_title.addAll(JsonMananger.jsonToList(chenelJson,NewsChanel.class));
        } catch (HttpException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {
        tabTitle.removeAllTabs();
        for (int i = 0; i < list_title.size(); i++) {
            //初始化fragment
            TabNewsFragment tabNewsFragment = new TabNewsFragment();
            //为fragment传递参数：参数为新闻类型
            tabNewsFragment.setArgument("channelId",list_title.get(i).getChannelId());
            list_fragment.add(tabNewsFragment);
            tabTitle.addTab(tabTitle.newTab().setText(list_title.get(i).getName()));
        }
        tabNewsAdapter = new TabNewsAdapter(getChildFragmentManager(),list_fragment,list_title);
        vpNews.setAdapter(tabNewsAdapter);
        //为TabLayout添加adapter
        tabTitle.setupWithViewPager(vpNews);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
