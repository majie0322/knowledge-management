package com.huizhi.management.entity;

import java.io.Serializable;

/**
 * @author onlyo
 * 使用HttpClient哦远程调用睿智的登录接口
 * 返回的JSON的数据格式
 */
public class RuiZhiLoginResult implements Serializable {

    /**
     * 登录成功之后返回的token
     */
    private String token;

    /**
     * 登录成功之后返回的用户id
     */
    private String user_id;

    /**
     *
     */
    private String server_rootdir;

    /**
     * 成功：0，失败：1
     */
    private Integer result;

    /**
     * 登录成功之后返回的角色，如：teacher,student
     */
    private String role;

    /**
     * 登录成功之后返回的学校code
     */
    private String school_code;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getServer_rootdir() {
        return server_rootdir;
    }

    public void setServer_rootdir(String server_rootdir) {
        this.server_rootdir = server_rootdir;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchool_code() {
        return school_code;
    }

    public void setSchool_code(String school_code) {
        this.school_code = school_code;
    }

    @Override
    public String toString() {
        return "RuiZhiLoginResult{" +
                "token='" + token + '\'' +
                ", user_id='" + user_id + '\'' +
                ", server_rootdir='" + server_rootdir + '\'' +
                ", result=" + result +
                ", role='" + role + '\'' +
                ", school_code='" + school_code + '\'' +
                '}';
    }
}
