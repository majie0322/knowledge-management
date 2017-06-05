package com.huizhi.management.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

/**
 * @author onlyo
 * 科目表
 */
@NodeEntity
public class Course implements Serializable{

	private static final long serialVersionUID = -9076856285981024123L;

	@GraphId
	private Long id;

	private String name;

	private String remark;
	
	private Long courseId;

	public Course() {
	}

	public Course(Long id, String name, String remark, Long courseId) {
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
