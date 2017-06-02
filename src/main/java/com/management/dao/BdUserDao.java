package com.management.dao;

import com.management.annotation.MapperInfo;
import com.management.constant.DBEnum;
import com.management.constant.EnvEnum;
import com.management.entity.BdUser;

/**
 * 用户权限表
 */
@MapperInfo(dbType = DBEnum.MYSQL,env = EnvEnum.TEST,refTable = "bd_user")
public interface BdUserDao {
    public BdUser findUserById(Long id);

    public BdUser findUserByName(String username);
}
