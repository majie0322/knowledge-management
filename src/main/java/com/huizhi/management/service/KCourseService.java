package com.huizhi.management.service;

import com.huizhi.management.entity.Course;

import java.util.List;

/**
 * @author onlyo
 */
public interface KCourseService {

    /**
     * 查找所有课目
     * @return 课目集合
     */
    List<Course> findAllCourse();

    /**
     * 基础基础库中的科目ID查询课程
     * @param courseId 基础库科目ID
     * @return
     */
    Course findCourseById(Long courseId);

    /**
     * 添加课程
     * @param course 课程
     */
    Course addCourse(Course course);
}
