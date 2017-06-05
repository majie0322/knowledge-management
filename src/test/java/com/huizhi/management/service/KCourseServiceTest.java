package com.huizhi.management.service;

import com.huizhi.management.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KCourseServiceTest {

    @Autowired
    private KCourseService kCourseService;
    @Test
    public void findAllCourse() throws Exception {

        System.out.println(kCourseService.findAllCourse());
    }

    @Test
    public void findCourseById() throws Exception {
        System.out.println(kCourseService.findCourseById(15L));
    }

    @Test
    public void addCourse() throws Exception {
        Course course = new Course();
        course.setName("测试");
        course.setCourseId(99L);
        course.setRemark("");
        kCourseService.addCourse(course);
    }

}
