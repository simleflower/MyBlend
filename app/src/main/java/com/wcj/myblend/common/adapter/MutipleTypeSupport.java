package com.wcj.myblend.common.adapter;

/**
 * Created by jayli on 2017/5/4 0004.
 */

public interface MutipleTypeSupport<T> {
    //根据当前条目获取布局
    int getLayoutId(T t);
}
