package com.huizhi.management.dao;

import com.huizhi.management.annotation.MapperInfo;
import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.Knowledge;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * 通过父节点ID查询子节点集合
     * @param fatherId 父节点ID
     * @return 返回子节点集合
     */
    @Query("MATCH (father:Knowledge)-[n]->(son:Knowledge) WHERE ID(father) = {fatherId} RETURN son")
    List<Knowledge> findSonNode(@Param("fatherId")Long fatherId);

    /**
     *  查询某课程下第一层知识点
     * @param courseId 课程ID
     * @param rzGradations 知识点层级
     * @param teacherIds 教师ID
     * @return 知识点集合
     */
    @Query("MATCH(c:Course)-[n]-(k:Knowledge) WHERE ID(c) = {courseId} AND k.rzGradation IN {rzGradations} RETURN k")
    List<Knowledge> findKnowledgeByCourseIdAndRzGradation(@Param("courseId")Long courseId, @Param("rzGradations")List<Integer> rzGradations, @Param("teacherIds")List<Long>teacherIds);

    /**
     *通过传入的ID集合查询对应的知识点集合
     * @param knowledgeIds 知识点ID集合
     * @return 知识点集合
     */
    @Query("MATCH(k:Knowledge) WHERE ID(k) IN {knowledgeIds} RETURN k")
    List<Knowledge> findKnowledgeByIds(@Param("knowledgeIds")List<Long> knowledgeIds);

    /**
     * 通过传入的父节点ID查询子节点集合
     * @param fatherId 父节点ID
     * @return 子节点集合
     */
    @Query("MATCH(fatherKnowledge:Knowledge)-[n]->(sonKnowledge:Knowledge) WHERE ID(fatherKnowledge) = {fatherId} RETURN sonKnowledge")
    public List<Knowledge> findSonNodeByFatherNodeId(@Param("fatherId")Long fatherId);

    /**
     * 通过传入的父节点ID集合查询子节点集合
     * @param fatherIds 父节点ID集合
     * @return 子节点集合
     */
    @Query("MATCH(fatherKnowledge:Knowledge)-[n]->(sonKnowledge:Knowledge) WHERE ID(fatherKnowledge) in {fatherIds} RETURN sonKnowledge")
    public List<Knowledge> findSonNodeByFatherNodeIds(@Param("fatherIds")List<Long> fatherIds);

    /**
     *  查询子节点的关系
     * @param fatherId 父节点ID
     * @return 父节点与子节点的关系
     */
    @Query("MATCH(fatherKnowledge:Knowledge)-[n]->(sonKnowledge:Knowledge) WHERE ID(fatherKnowledge) = {fatherId} RETURN ID(fatherKnowledge), ID(sonKnowledge), n.type")
    public List<Map<String, Object>> findSonNodeRelationshipByFatherNodeId(@Param("fatherId")Long fatherId);

    /**
     * 查询父节点的关系
     * @param sonId 子节点ID
     * @return 父节点与子节点的关系
     */
    @Query("MATCH(fatherKnowledge:Knowledge)-[n]->(sonKnowledge:Knowledge) WHERE ID(sonKnowledge) = {sonId} RETURN ID(fatherKnowledge), ID(sonKnowledge), n.type")
    public List<Map<String, Object>> findFatherNodeRelationshipByFatherNodeId(@Param("sonId")Long sonId);

    /**
     * 通过传入的ID查询对应的父节点和子节点
     * @param id 知识点ID
     * @return 传入ID的父节点和子节点
     */
    @Query("MATCH(sonKnowledge:Knowledge)<-[n]-(startKnowledge:Knowledge)<-[r]-(fatherKnowledge:Knowledge) WHERE ID(startKnowledge) = {id} RETURN startKnowledge, sonKnowledge, fatherKnowledge")
    public List<Knowledge> findFatherAndSonNodeByRzKid(@Param("id")Long id);

    /**
     * 将某知识点与某科目建立关系
     * @param courseId
     * @param knowledgeId
     */
    @Query("MATCH (k:Knowledge),(c:Course) WHERE id(c) = {courseId} and id(k) = {knowledgeId} CREATE (c)<-[b:BELONG{type:'BELONG'}]-(k)")
    void addRelation(@Param("courseId")Long courseId,@Param("knowledgeId")  Long knowledgeId);
}
