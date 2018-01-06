package com.huizhi.management.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.huizhi.management.util.RedisUtilsTemplate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BdUserDaoTest {

    @Autowired
    private RedisUtilsTemplate redisUtilsTemplate;

    @Test
    public void findUserByName() throws Exception {
        //redisUtilsTemplate.set("a", "111");
        System.err.println(redisUtilsTemplate.get("a"));
    }

    @Test
    public void findUserById() throws Exception {

    }

}
