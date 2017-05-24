package com.wcj.myblend.ui.widget.recyclerview.interfaces;


import com.wcj.myblend.ui.widget.recyclerview.ViewHolder;

/**
 * Created by  on 2017/5/5 0005.
 */

public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
