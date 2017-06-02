package com.huizhi.management.dao;

import com.huizhi.management.constant.DBEnum;
import com.huizhi.management.constant.EnvEnum;
import com.huizhi.management.entity.BdUser;
import com.huizhi.management.annotation.MapperInfo;

/**
 * 用户权限表
 */
@MapperInfo(dbType = DBEnum.MYSQL,env = EnvEnum.TEST,refTable = "bd_user")
public interface BdUserDao {
    BdUser findUserById(Long id);

    BdUser findUserByName(String username);
}
