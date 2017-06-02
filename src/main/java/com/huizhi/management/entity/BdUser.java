package com.huizhi.management.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户表
 */
public class BdUser implements Serializable{

    private static final long serialVersionUID = 430787859981904825L;

    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型(1是学生，2是教师，3是管理员)
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 对应实际老师学生表的id
     */
    private Long linkId;

    public BdUser() {
    }

    public BdUser(Long id, String username, String password, Integer type, Timestamp createTime, Long linkId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.createTime = createTime;
        this.linkId = linkId;
    }

    @Override public String toString() {
        return "BdUser{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", type="
                + type + ", createTime=" + createTime + ", linkId=" + linkId + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }
}
