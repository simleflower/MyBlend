package com.wcj.myblend.common;

/**
 * Created by S01 on 2017/4/28.
 */

public class ServerConfig {
    //public static String BASE_URL="http://v.juhe.cn/toutiao/index";
    public static final String SHOW_API_URL = "http://route.showapi.com/";
    public static final String JUHE_JOKE_URL ="http://japi.juhe.cn/";
    public static final String SHOWAP_PIC_URL = "http://route.showapi.com/852-2";
    public static final String DEFAULT_IMG = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494239910571&di=d37c902a947dd59d0a0c78639a6e069b&imgtype=0&src=http%3A%2F%2Fs16.sinaimg.cn%2Fmw690%2F003gRgrCzy73OGZAV434f%26690";



    public static class API {
        public static final String SHOW_API_NEWS_QUERY = SHOW_API_URL + "109-35";
        public static final String SHOW_API_NEWS_CHANEL_QUERY = SHOW_API_URL + "109-34";
       // public static final String SHOW_API_JOKE_PIC_QUERY = SHOWAP_PIC_URL + "107-33";
        public static final String JUHE_JOKE_QUERY =JUHE_JOKE_URL+"joke/content/list.from";
        public static final String BAIDU_API_IMG = "http://image.baidu.com/data/imgs";
    }

}
