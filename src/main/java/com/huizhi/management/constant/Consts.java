package com.huizhi.management.constant;

import java.util.ResourceBundle;

/**
 * Created by Janita on 2017-03-30 15:23
 */
public class Consts implements Constants {

    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("application");
    }

    /**
     * 请求头中的token所对应的键
     */
    public static final String  HEADER_TOKEN = "x-access-token";

    /**
     * 默认字符编码
     */
    public static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * post请求
     */
    public static final String POST = "post";
    
    /**
     * get请求
     */
    public static final String GET = "get";

    /**
     * 获取睿智登陆接口地址
     * @return  
     */
    public static String getRuiZhiLoginUrl(){
        return bundle.getString("ruiZhiLoginUrl");
    }
    

}
