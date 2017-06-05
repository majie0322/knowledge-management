package com.huizhi.management.dao;

import com.huizhi.management.annotation.MapperInfo;
import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.Knowledge;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 关于知识点的（knowledge）
 */
@Repository
@MapperInfo(dbType = DBEnum.NEO4J, env = EnvEnum.TEST, refTable = "Knowledge")
public interface KKnowledgeDao extends GraphRepository<Knowledge> {

    /**
     * 通过id查找知识点
     * @param id 知识点唯一标识
     * @return 知识点
     */
    @Query("MATCH (k:Knowledge) WHERE id(k) = {id} RETURN k")
    Knowledge findKnowledgeById(@Param("id")Long id);

    /**
     * 将某知识点与某科目建立关系
     * @param courseId
     * @param knowledgeId
     */
    @Query("MATCH (k:Knowledge),(c:Course) WHERE id(c) = {courseId} and id(k) = {knowledgeId} CREATE (c)<-[b:BELONG{type:'BELONG'}]-(k)")
    void addRelation(@Param("courseId")Long courseId,@Param("knowledgeId")  Long knowledgeId);
}
