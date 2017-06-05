package com.huizhi.management.service.impl;

import com.huizhi.management.dao.KKnowledgeDao;
import com.huizhi.management.entity.Knowledge;
import com.huizhi.management.service.KKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class kKnowledgeServiceImpl implements KKnowledgeService{

    @Autowired
    private KKnowledgeDao kKnowledgeDao;

    /**
     * 通过id查找知识点
     * @param id 知识点唯一标识
     * @return 知识点
     */
   public Knowledge findKnowledgeById(Long id){
        return kKnowledgeDao.findKnowledgeById(id);
    }
}
