package com.huizhi.management.dao;

import com.huizhi.management.annotation.MapperInfo;
import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.BdTeacher;

/**
 * 教师
 * @author onlyo
 */
@MapperInfo(dbType = DBEnum.MYSQL,env = EnvEnum.TEST,refTable = "bd_teacher")
public interface BdTeacherDao {
	
	BdTeacher findTeacherByLinkId(Long linkId);

}
