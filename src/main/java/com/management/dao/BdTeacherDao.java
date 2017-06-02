package com.management.dao;

import com.management.annotation.MapperInfo;
import com.management.constant.DBEnum;
import com.management.constant.EnvEnum;
import com.management.entity.BdTeacher;

/**
 * 教师
 * @author onlyo
 */
@MapperInfo(dbType = DBEnum.MYSQL,env = EnvEnum.TEST,refTable = "bd_teacher")
public interface BdTeacherDao {
	
	BdTeacher findTeacherByLinkId(Long linkId);

}
