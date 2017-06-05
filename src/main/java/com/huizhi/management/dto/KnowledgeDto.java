package com.huizhi.management.dto;

import com.huizhi.management.entity.Knowledge;
import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;

public class KnowledgeDto implements Serializable{

    private static final long serialVersionUID = 6570510477096981154L;

    @GraphId
    private Long id;

    /**
     * 老师id
     */
    private Long teacherId;

    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 是否为基础表
     */
    private Integer isBase;

    /**
     * 知识点名称
     */
    private String name;

    /**
     * 知识点描述
     */
    private String description;

    /**
     * 难度
     */
    private Integer level;

    /**
     * 学期
     */
    private int termNumber;

    /**
     * 年级ID
     */
    private Long gradeId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 年级段
     */
    private int gradeSection;

    /**
     * 唯一表示--睿智
     */
    private String rzNo;

    /**
     * 知识点ID--睿智
     */
    private Long rzKid;

    /**
     * 父节点ID--睿智
     */
    private Long rzParentId;

    /**
     * 知识点层级--睿智
     */
    private int rzGradation;

    /**
     * 是否为末级--睿智
     */
    private int rzEnd;

    /**
     * 课程ID
     */
    private Long courseId;


    public KnowledgeDto() {
    }

    public KnowledgeDto(Long id, Long teacherId, Long schoolId, Integer isBase, String name, String description,
            Integer level, int termNumber, Long gradeId, Long createTime, Long modifyTime, int gradeSection,
            String rzNo, Long rzKid, Long rzParentId, int rzGradation, int rzEnd, Long courseId) {
        this.id = id;
        this.teacherId = teacherId;
        this.schoolId = schoolId;
        this.isBase = isBase;
        this.name = name;
        this.description = description;
        this.level = level;
        this.termNumber = termNumber;
        this.gradeId = gradeId;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.gradeSection = gradeSection;
        this.rzNo = rzNo;
        this.rzKid = rzKid;
        this.rzParentId = rzParentId;
        this.rzGradation = rzGradation;
        this.rzEnd = rzEnd;
        this.courseId = courseId;
    }

    @Override public String toString() {
        return "Knowledge{" + "id=" + id + ", teacherId=" + teacherId + ", schoolId=" + schoolId + ", isBase=" + isBase
                + ", name='" + name + '\'' + ", description='" + description + '\'' + ", level=" + level
                + ", termNumber=" + termNumber + ", gradeId=" + gradeId + ", createTime=" + createTime + ", modifyTime="
                + modifyTime + ", gradeSection=" + gradeSection + ", rzNo='" + rzNo + '\'' + ", rzKid=" + rzKid
                + ", rzParentId=" + rzParentId + ", rzGradation=" + rzGradation + ", rzEnd=" + rzEnd + ", courseId="
                + courseId + ", isSelected=" + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public Integer getIsBase() {
        return isBase;
    }

    public void setIsBase(Integer isBase) {
        this.isBase = isBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(int termNumber) {
        this.termNumber = termNumber;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getGradeSection() {
        return gradeSection;
    }

    public void setGradeSection(int gradeSection) {
        this.gradeSection = gradeSection;
    }

    public String getRzNo() {
        return rzNo;
    }

    public void setRzNo(String rzNo) {
        this.rzNo = rzNo;
    }

    public Long getRzKid() {
        return rzKid;
    }

    public void setRzKid(Long rzKid) {
        this.rzKid = rzKid;
    }

    public Long getRzParentId() {
        return rzParentId;
    }

    public void setRzParentId(Long rzParentId) {
        this.rzParentId = rzParentId;
    }

    public int getRzGradation() {
        return rzGradation;
    }

    public void setRzGradation(int rzGradation) {
        this.rzGradation = rzGradation;
    }

    public int getRzEnd() {
        return rzEnd;
    }

    public void setRzEnd(int rzEnd) {
        this.rzEnd = rzEnd;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public static Knowledge from(KnowledgeDto dto){
        Knowledge knowledge = null;
        if(dto != null){
            knowledge = new Knowledge();
            knowledge.setId(dto.getId());
            knowledge.setTeacherId(dto.getTeacherId());
            knowledge.setSchoolId(dto.getSchoolId());
            knowledge.setIsBase(dto.getIsBase());
            knowledge.setName(dto.getName());
            knowledge.setDescription(dto.getDescription());
            knowledge.setLevel(dto.getLevel());
            knowledge.setTermNumber(dto.getTermNumber());
            knowledge.setGradeId(dto.getGradeId());
            knowledge.setGradeSection(dto.getGradeSection());
            knowledge.setRzNo(dto.getRzNo());
            knowledge.setRzKid(dto.getRzKid());
            knowledge.setRzParentId(dto.getRzParentId());
            knowledge.setRzGradation(dto.getRzGradation());
            knowledge.setRzEnd(dto.getRzEnd());
            knowledge.setCourseId(dto.getCourseId());
        }
        return knowledge;
    }
}
