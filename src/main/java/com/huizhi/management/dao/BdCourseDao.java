package com.huizhi.management.dao;

import com.huizhi.management.annotation.MapperInfo;
import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.BdCourse;

/**
 * 科目
 * @author onlyo
 */
@MapperInfo(dbType = DBEnum.MYSQL, env = EnvEnum.TEST, refTable = "bd_course")
public interface BdCourseDao{

    /**
     * 通过courseId查看课程
     *
     * @param courseId
     * @return
     */
    BdCourse findCourseByCourseId(Long courseId);
}
