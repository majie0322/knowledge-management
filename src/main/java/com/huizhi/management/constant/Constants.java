package com.huizhi.management.constant;

public interface Constants {
	
	/**
	 * 默认每页数据条数
	 */
	int PAGE_DEFAULT_ROWS = 10;

	/**
	 * 管理员id 保存到redis中生成的key的规则
	 */
	String USER_lOGIN_KEY = "MANAGER_ID_IS";

	String SHOW_lOGIN_KEY = "SHOW_LOGIN_KEY";

	/**
	 * 常量定义
	 */
	int ONE = 1;

	int TWO = 2;

	int THREE = 3;

	/**
	 *账号不存在
	 */
	String USER_NOT_EXIST = "User_does_not_exist";

	/**
	 * 密码错误
	 */
	String PASSWORD_ERROR = "wrong_password";
}
