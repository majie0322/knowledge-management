package com.huizhi.management.service.impl;

import com.huizhi.management.dao.KCourseDao;
import com.huizhi.management.entity.Course;
import com.huizhi.management.service.KCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KCourseServiceImpl implements KCourseService{

    @Autowired
    private KCourseDao kCourseDao;
    /**
     * 查找所有课目
     * @return 课目集合
     */
    @Override
    public List<Course> findAllCourse() {

        return kCourseDao.findAllCourse();
    }
}
