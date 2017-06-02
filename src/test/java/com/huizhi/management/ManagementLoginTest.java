package com.huizhi.management;

import com.huizhi.management.dao.BdTeacherDao;
import com.huizhi.management.dao.BdUserDao;
import com.huizhi.management.dao.BdCourseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManagementLoginTest {

    @Autowired
    private BdCourseDao bdCourseDao;

    @Autowired
    private BdUserDao bdUserDao;

    @Autowired
    private BdTeacherDao bdTeacherDao;

    @Test
    public void test(){
        System.out.println(111);
    }

    @Test
    public void courseTest(){
        System.out.println(bdCourseDao.findCourseByCourseId(1L));
    }

    /**
     * 用户通过id查找
     */
    @Test
    public void UserTest(){
        System.out.println(bdUserDao.findUserById(1L));
    }

    /**
     *
     */
    @Test
    public  void Usertest1(){
        System.out.println(bdUserDao.findUserByName("ruizhi"));
    }

    /**
     * 通过关联id查找相应的教师
     */
    @Test
    public void findTeacherByLinkId(){
        System.out.println(bdTeacherDao.findTeacherByLinkId(1L));

    }


}
