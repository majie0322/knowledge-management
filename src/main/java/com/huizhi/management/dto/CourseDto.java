package com.huizhi.management.dto;

import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;
import java.util.List;

public class CourseDto implements Serializable{

    private static final long serialVersionUID = 1910290138403920446L;

    @GraphId
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对应基础库中的科目ID
     */
    private Long courseId;

    /**
     * 图谱编辑器显示的点的最大值范围
     */
    private Integer maxValue;

    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 知识点ID
     */
    private Long knowledgeId;

    /**
     * 年级段集合
     */
    private List<Long> gradeTypes;

    public CourseDto() {
    }

    public CourseDto(Long id, String name, String remark, Long courseId, Integer maxValue, Long teacherId, Long knowledgeId, List<Long> gradeTypes) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.courseId = courseId;
        this.maxValue = maxValue;
        this.teacherId = teacherId;
        this.knowledgeId = knowledgeId;
        this.gradeTypes = gradeTypes;
    }

    @Override
    public String toString() {
        return "Course [id=" + id
                + ", name=" + name
                + ", remark=" + remark
                + ", courseId=" + courseId
                + ", maxValue=" + maxValue
                + ", teacherId=" + teacherId
                + ", knowledgeId=" + knowledgeId
                + ", gradeTypes=" + gradeTypes + "]";
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

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Long knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public List<Long> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<Long> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }
}
