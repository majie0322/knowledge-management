package com.management.dao;

import com.management.annotation.MapperInfo;
import com.management.constant.DBEnum;
import com.management.constant.EnvEnum;
import com.management.entity.BdCourse;
import org.springframework.stereotype.Repository;

/**
 * 科目
 *
 * @author onlyo
 */
@MapperInfo(dbType = DBEnum.MYSQL, env = EnvEnum.TEST, refTable = "bd_course")
public interface BdCourseDao {

    /**
     * 通过courseId查看课程
     *
     * @param courseId
     * @return
     */
    BdCourse findCourseByCourseId(Long courseId);
}
