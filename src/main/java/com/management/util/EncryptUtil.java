package com.management.util;

public class EncryptUtil {
	
	/**
     * 盐值
     */
    private static final String SALT="huizhixiangshang";
    /**
     * 密码加盐后MD5加密
     * @param password
     * @return 加密后的密码
     */
    public static String getPWd(String password){
        return new MD5Code().getMD5ofStr(password+"({"+SALT+"})");
    }

//    public static void main(String[] args) {
//        System.out.println(EncryptUtil.getPWd("123456"));
//    }
}
