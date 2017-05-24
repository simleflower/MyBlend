package com.wcj.myblend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.wcj.myblend.common.BaseActivity;
import com.wcj.myblend.ui.fragment.JokeFragment;
import com.wcj.myblend.ui.fragment.NewsFragment;
import com.wcj.myblend.ui.fragment.PersonFragment;
import com.wcj.myblend.ui.fragment.PictureFragment;
import com.wcj.myblend.ui.widget.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * BottomNavigationViewEx:
 * https://github.com/ittianyu/BottomNavigationViewEx
 *
 * */
public class MainActivity extends BaseActivity {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.container)
    LinearLayout container;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private NewsFragment newsFragment;
    private JokeFragment jokeFragment;
    private PictureFragment pictureFragment;
    private PersonFragment personFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        setLeftVisibility(View.GONE);
        setRightVisibility(View.GONE);

        initFragment();
    }

    private void initFragment() {
        //设置默认标题为新闻
        setTitle(R.string.title_news);
        //设置默认显示新闻的fragment
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        newsFragment = new NewsFragment();
        transaction.replace(R.id.content,newsFragment);
        transaction.commit();

    }

    private void initView() {
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    if(newsFragment == null){
                        newsFragment = new NewsFragment();
                    }
                    setTitle(R.string.title_news);
                    replaceFragment(newsFragment);
                    return true;
                case R.id.navigation_joke:
                    if(jokeFragment == null){
                        jokeFragment = new JokeFragment();
                    }
                    setTitle(R.string.title_joke);
                    replaceFragment(jokeFragment);
                    return true;
                case R.id.navigation_picture:
                    if(pictureFragment == null){
                        pictureFragment = new PictureFragment();
                    }
                    setTitle(R.string.title_picture);
                    replaceFragment(pictureFragment);
                    return true;
                case R.id.navigation_person:
                    if(personFragment == null){
                        personFragment = new PersonFragment();
                    }
                    setTitle(R.string.title_person);
                    replaceFragment(personFragment);
                    return true;
            }
            return false;
        }

    };

    private void replaceFragment(Fragment fragment){
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

}
