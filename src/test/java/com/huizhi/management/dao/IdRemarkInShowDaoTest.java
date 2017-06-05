package com.huizhi.management.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IdRemarkInShowDaoTest {

    @Autowired
    private IdRemarkInShowDao idRemarkInShowDao;

    @Test
    public void add() throws Exception {

    }

    @Test public void findIdRemarkInShow() throws Exception {
        System.out.println(idRemarkInShowDao.findIdRemarkInShow());
    }

    @Test public void updeteIdRemarkInShow() throws Exception {
    }

}
