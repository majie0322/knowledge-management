package com.huizhi.management.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.transform.Source;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KCourseDaoTest {

    @Autowired
    private KCourseDao kCourseDao;

    /**
     * 查找所有课目
     * @return 课目集合
     */
    @Test
    public void findAllCourse() throws Exception {
        System.out.println( kCourseDao.findAllCourse());
    }

    @Test
    public void findCourseById() throws Exception {
        System.out.println(kCourseDao.findCourseById(3L));
    }

}
