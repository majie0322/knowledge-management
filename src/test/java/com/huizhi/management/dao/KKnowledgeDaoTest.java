package com.huizhi.management.dao;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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
public class KKnowledgeDaoTest {

    @Autowired
    private KKnowledgeDao kKnowledgeDao;

    @Test
    public void findKnowledgeById() throws Exception {
        System.out.println(kKnowledgeDao.findKnowledgeById(83L));
    }

    @Test
    public void addRelation() throws Exception {
        kKnowledgeDao.addRelation(6L,5733L);
    }

    @Test
    public void findSonNode() throws Exception {
        System.out.println(kKnowledgeDao.findSonNode(78L));
    }

    @Test
    public void findKnowledgeByCourseIdAndRzGradation() throws Exception {
        List<Integer> rzGradation = new ArrayList<Integer>();
        rzGradation.add(1);
        rzGradation.add(2);
        rzGradation.add(3);
        List<Long> teacherIds = new ArrayList<Long>();
        teacherIds.add(-1L);
        System.out.println(kKnowledgeDao.findKnowledgeByCourseIdAndRzGradation(2L, rzGradation, teacherIds));
    }

    @Test
    public void findKnowledgeByIds() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(78L);
        ids.add(79L);
        System.out.println(kKnowledgeDao.findKnowledgeByIds(ids));
    }

    @Test
    public void findSonNodeByFatherNodeId() throws Exception {
        System.out.println(kKnowledgeDao.findSonNodeByFatherNodeId(78L));
    }

    @Test
    public void findSonNodeByFatherNodeIds() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(78L);
        System.out.println(kKnowledgeDao.findSonNodeByFatherNodeIds(ids));
    }

    @Test
    public void findSonNodeRelationshipByFatherNodeId() throws Exception {
        System.out.println(kKnowledgeDao.findSonNodeRelationshipByFatherNodeId(78L));
    }

    @Test
    public void findFatherNodeRelationshipByFatherNodeId() throws Exception {
        System.out.println(kKnowledgeDao.findFatherNodeRelationshipByFatherNodeId(79L));
    }

    @Test
    public void findFatherAndSonNodeByRzKid() throws Exception {
        System.out.println(kKnowledgeDao.findFatherAndSonNodeByRzKid(78L));
    }

}
