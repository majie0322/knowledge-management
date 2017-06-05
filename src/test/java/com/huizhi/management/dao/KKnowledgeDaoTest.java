package com.huizhi.management.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KKnowledgeDaoTest {

    @Autowired
    private KKnowledgeDao kKnowledgeDao;

    @Test
    public void findKnowledgeById() throws Exception {
        System.out.println(kKnowledgeDao.findKnowledgeById(83L));
    }

}
