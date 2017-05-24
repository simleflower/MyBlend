package com.wcj.myblend.ui.widget.recyclerview.interfaces;

import com.wcj.myblend.ui.widget.recyclerview.ViewHolder;



public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
