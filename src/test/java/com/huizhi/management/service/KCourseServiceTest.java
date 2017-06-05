package com.huizhi.management.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(kCourseService.findCourseBySubjectId(15L));
    }

    @Test
    public void recordedId() throws Exception {
        System.out.println(kCourseService.recordedId(-1L, 78L, 3L));
    }

    @Test
    public void showKnowledge() throws Exception {
       List<Long> ids = new ArrayList<Long>();
       ids.add(81L);
       List<Long> gradeTyprs = new ArrayList<Long>();
       gradeTyprs.add(1L);
       gradeTyprs.add(2L);
       gradeTyprs.add(3L);
       gradeTyprs.add(4L);
        System.out.println(kCourseService.showKnowledge(2L,-1L,ids,gradeTyprs,50,"teacherId-13"));
    }

}
