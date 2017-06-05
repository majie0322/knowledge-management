package com.huizhi.management.service;

import com.huizhi.management.entity.Knowledge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

/**
 * 知识图谱管理后台（Knowledge相关接口）
 * @author onlyo
 */
public interface KKnowledgeService {

    /**
     * 通过id查找知识点
     * @param id 知识点唯一标识
     * @return 知识点
     */
    Knowledge findKnowledgeById(Long id);
}
