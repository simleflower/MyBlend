package com.wcj.myblend.ui.adapter;

import android.content.Context;


import com.wcj.myblend.R;
import com.wcj.myblend.bean.ResultJoke.ResultBean.Joke;
import com.wcj.myblend.ui.widget.recyclerview.ViewHolder;
import com.wcj.myblend.ui.widget.recyclerview.base.SingleBaseAdapter;

import java.util.List;

/**
 * Created by jayli on 2017/5/8 0008.
 */

public class JokeAdapter extends SingleBaseAdapter<Joke> {

    public JokeAdapter(Context context, List<Joke> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, Joke data) {
        holder.setText(R.id.tv_joke,data.getContent());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_joke;
    }
}
