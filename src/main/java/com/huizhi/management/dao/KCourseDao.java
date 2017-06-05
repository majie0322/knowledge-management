package com.huizhi.management.dao;

import com.huizhi.management.annotation.MapperInfo;
import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.Course;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author onlyo
 * 科目表
 */
@Repository
@MapperInfo(dbType = DBEnum.NEO4J,env = EnvEnum.TEST,refTable = "course")
public interface KCourseDao extends GraphRepository<Course> {

    /**
     * 查找所有课目
     * @return 课目集合
     */
    @Query("MATCH(c:Course) RETURN c ORDER BY c.name DESC")
    List<Course> findAllCourse();

    /**
     * 通过基础库中的课程ID查询neo4j中对应的课程
     * @param courseId 基础库中课程ID
     * @return 课程
     */
    @Query("MATCH(c:Course) WHERE c.courseId = {courseId} RETURN c")
    Course findCourseById(@Param("courseId")Long courseId);
}
