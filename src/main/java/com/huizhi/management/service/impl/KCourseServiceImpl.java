package com.huizhi.management.service.impl;

import com.huizhi.management.dao.KCourseDao;
import com.huizhi.management.entity.Course;
import com.huizhi.management.service.KCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KCourseServiceImpl implements KCourseService {

    @Autowired
    private KCourseDao kCourseDao;

    /**
     * 查找所有课目
     *
     * @return 课目集合
     */
    @Override
    public List<Course> findAllCourse() {
        return kCourseDao.findAllCourse();
    }

    /**
     * 基础基础库中的科目ID查询课程
     *
     * @param courseId 基础库科目ID
     * @return
     */
    @Override
    public Course findCourseById(Long courseId) {
        if (courseId != null) {
            return kCourseDao.findCourseById(courseId);
        } else {
            return null;
        }
    }

    @Override
    public Course addCourse(Course course) {
        kCourseDao.save(course);
        Course c = kCourseDao.findCourseById(course.getCourseId());
        return c;
    }

}