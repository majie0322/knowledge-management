package com.huizhi.management.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 基本库的课目表
 *
 */
public class BdCourse implements Serializable{

    private static final long serialVersionUID = -106260845022496368L;

    private long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程的最后修改时间
     */
    private Timestamp createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    public BdCourse() {
    }

    public BdCourse(long id, String courseName, Timestamp createTime, String remark, Integer isDelete) {
        this.id = id;
        this.courseName = courseName;
        this.createTime = createTime;
        this.remark = remark;
        this.isDelete = isDelete;
    }

    @Override public String toString() {
        return "BdCourse{" + "id=" + id + ", courseName='" + courseName + '\'' + ", createTime=" + createTime
                + ", remark='" + remark + '\'' + ", isDelete=" + isDelete + '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
