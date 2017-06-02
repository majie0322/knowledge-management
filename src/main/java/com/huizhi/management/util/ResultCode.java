package com.huizhi.management.util;

import java.util.ResourceBundle;

public class ResultCode {

    private static ResourceBundle rb;

    private String code;

    private String message;

    static {
        rb = ResourceBundle.getBundle("error");
    }

    public ResultCode(String code) {
        this.code = code;
        this.message = rb.getString(code);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 未知错误
     */
    public static final ResultCode UNKNOWN = new ResultCode("-1");

    /**
     * 正常操作
     */
    public static final ResultCode SUCCESS = new ResultCode("0");
    
    /**
     * 用户名密码错误
     */
    public static final ResultCode PASSWORD_INCORRECT = new ResultCode("10000");

    /**
     * 登录超时
     */
    public static final ResultCode SESSION_TIME_OUT = new ResultCode("10001");

    /**
     * 未登录
     */
    public static final ResultCode UNLOGIN = new ResultCode("10002");
    
    /**
     * 会员不存在
     */
    public static final ResultCode MEMBER_NOT_EXIST = new ResultCode("10004");
    
    /**
     * 用户被限制登录
     */
    public static final ResultCode MEMBER_LIMIT_LOGIN = new ResultCode("10005");
    
    /**
     * 数据更新异常
     */
    public static final ResultCode DATA_UPDATE_ERROR = new ResultCode("20000");
    
    /**
     * 用户名为空
     */
    public static final ResultCode USERNAME_IS_NULL = new ResultCode("20009");
    
    /**
     * 密码为空
     */
    public static final ResultCode PASSWORD_IS_NULL = new ResultCode("20010");
    
    /**
     * IO异常
     */
    public static final ResultCode IO_ERROR = new ResultCode("20003");
    
    /**
     * key错误无效
     */
    public static final ResultCode INVALID_KEY_ERROR = new ResultCode("20004");
    
    /**
     * 算法错误
     */
    public static final ResultCode NO_SUCH_ALGORITHM_ERROR = new ResultCode("20005");
    
    /**
     * 非法的状态
     */
    public static final ResultCode ILLEGAL_STATE_ERROR = new ResultCode("20006");
    
    /**
     * 签名错误
     */
    public static final ResultCode SIGNATURE_ERROR = new ResultCode("20007");
    
    /**
     * 无效的token
     */
    public static final ResultCode INVALIDE_TOKEN_ERROR = new ResultCode("20001");
    
    /**
     * 无权限错误
     */
    public static final ResultCode NO_PERMISSIONS_ERROR = new ResultCode("20002");
    
    /**
     * 访问的资源不存在
     */
    public static final ResultCode ARTICLE_NOT_EXIST = new ResultCode("30001");
    
    /**
     * 缺少关联课程ID
     */
    public static final ResultCode MISSING_ASSOCIATED_COURSE_ID = new ResultCode("30002");
    
    /**
     * 只允许一条关系
     */
    public static final ResultCode ONLY_ONE_RELATIONSHIP_IS_ALLOWED = new ResultCode("30003");
    
    /**
     * 不允许的操作
     */
    public static final ResultCode NOT_ALLOWED_TO_OPERATE = new ResultCode("30004");
    
    /**
     * 缺少关联课程名称
     */
    public static final ResultCode MISSING_ASSOCIATED_COURSE_NAME = new ResultCode("30005");

    /**
     * 操作失败
     */
    public static final  ResultCode FAIL = new ResultCode("30006");
    
    /**
     * 缺失字段
     */
    public static final ResultCode MISSING_FIELD = new ResultCode("30007");


}
