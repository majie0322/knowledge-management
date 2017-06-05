package com.huizhi.management.service;

import com.huizhi.management.entity.Course;

import java.util.List;
import java.util.Map;

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
    Course findCourseBySubjectId(Long courseId);

    /**
     * 添加课程
     * @param course 课程
     */
    Course addCourse(Course course);

    /**
     * 记录用户操作，知识点展开收起功能
     * @param teacherId 教师ID
     * @param knoweldgeId 知识点ID
     * @param id 课程ID
     * @return redis中存的知识点ID集合
     */
    List<Long> recordedId(Long teacherId, Long knoweldgeId, Long id);

    /**
     * 查询展示页面需要的知识点和相关关系
     * @return 知识点和关系
     */
    Map<String, Object> showKnowledge(Long id, Long teacherId, List<Long> knowledgeId, List<Long> gradeTypes, Integer maxValue, String redisKey);
}
