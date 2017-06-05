package com.huizhi.management.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KKnowledgeServiceTest {

    @Autowired
    private KKnowledgeService kKnowledgeService;

    @Test
    public void findKnowledgeById() throws Exception {
        System.out.println(kKnowledgeService.findKnowledgeById(83L));
    }

}
