package com.huizhi.management.dto;

import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;

public class CourseDto implements Serializable{

    private static final long serialVersionUID = 1910290138403920446L;

    @GraphId
    private Long id;

    private String name;

    private String remark;

    private Long courseId;

    public CourseDto() {
    }

    public CourseDto(Long id, String name, String remark, Long courseId) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.courseId = courseId;
    }


    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", remark=" + remark + ", courseId=" + courseId + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}
