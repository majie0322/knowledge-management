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
}
