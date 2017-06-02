package com.management.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 教师实体类
 * @author onlyo
 */
public class BdTeacher implements Serializable{

    private static final long serialVersionUID = -4484861850188847994L;

    private Long id;

    private Long schoolId;

    /**
     * 科目id
     */
    private Long courseId;

    private Long teacherNo;

    private String teacherName;

    private Integer sex;

    private Integer age;

    /**
     * 该教师的职称
     */
    private String level;

    private Timestamp createTime;

    private String remark;

    private String userId;

    public BdTeacher() {
    }

    public BdTeacher(Long id, Long schoolId, Long courseId, Long teacherNo, String teacherName, Integer sex,
            Integer age, String level, Timestamp createTime, String remark, String userId) {
        this.id = id;
        this.schoolId = schoolId;
        this.courseId = courseId;
        this.teacherNo = teacherNo;
        this.teacherName = teacherName;
        this.sex = sex;
        this.age = age;
        this.level = level;
        this.createTime = createTime;
        this.remark = remark;
        this.userId = userId;
    }

    @Override public String toString() {
        return "BdTeacher{" + "id=" + id + ", schoolId=" + schoolId + ", courseId=" + courseId + ", teacherNo="
                + teacherNo + ", teacherName='" + teacherName + '\'' + ", sex=" + sex + ", age=" + age + ", level='"
                + level + '\'' + ", createTime=" + createTime + ", remark='" + remark + '\'' + ", userId='" + userId
                + '\'' + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(Long teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
