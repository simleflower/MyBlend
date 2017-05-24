package com.wcj.myblend.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;


import com.wcj.myblend.R;

import com.wcj.myblend.bean.ResultJoke;
import com.wcj.myblend.ui.widget.recyclerview.ViewHolder;
import com.wcj.myblend.bean.ResultNews.ResBodyBean.PageBean.News;
import com.wcj.myblend.ui.widget.recyclerview.base.SingleBaseAdapter;
import com.wcj.myblend.utils.picture.ImageLoader;
import com.wcj.myblend.utils.picture.LruCacheUtils;

import java.util.List;

/**
 * Created by  on 2017/5/5 0005.
 */

public abstract class NewsRefreshAdapter extends SingleBaseAdapter<News> {

    public NewsRefreshAdapter(Context context, List<News> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(final ViewHolder holder, final News data) {
        holder.setText(R.id.tv_title,data.getTitle());
        if(data.isHavePic()){
            String imgUrl = data.getImageurls().get(0).getUrl();
            //判断缓存中是否已经缓存过该图片，有则直接拿Bitmap，没有则直接调用Glide加载并缓存Bitmap
            Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemCache(imgUrl);
            if (bitmap != null) {
                holder.setImageBitmap(R.id.img_news,bitmap);
            } else {
                holder.setImageUrl(R.id.img_news, new ViewHolder.ImageLoder(imgUrl) {
                    @Override
                    public void loadImage(ImageView imageView, String path) {
                        //封装的图片缓存加载类
                        ImageLoader.getInstance().displayImageTarget(imageView,path);
                    }
                });
            }
        }else{
            holder.setVisbilty(R.id.layout_news,View.GONE);
        }

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_news;
    }

    protected abstract void convert(ViewHolder holder, ResultJoke.ResultBean.Joke data);
}
