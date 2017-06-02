package com.huizhi.management.service.impl;

import com.alibaba.fastjson.JSON;
import com.huizhi.management.constant.Constants;
import com.huizhi.management.util.TokenUtil;
import com.huizhi.management.constant.Consts;
import com.huizhi.management.dao.BdUserDao;
import com.huizhi.management.entity.BdUser;
import com.huizhi.management.entity.RuiZhiLoginResult;
import com.huizhi.management.service.LoginService;
import com.huizhi.management.util.HttpClientUtil;
import com.huizhi.management.util.RedisUtilsTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 登入实现
 * @author onlyo
 */
@Service
public class LoginServiceImpl implements LoginService{
	
	private Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired(required = false)
	private BdUserDao bdUserDao;

	@Autowired
	private RedisUtilsTemplate redisUtilsTemplate;

	/**
	 * 用户登入验证
	 * @param username 账号
	 * @param password 密码
	 * @return 验证信息
	 */
	public String login(String username, String password){

		//查询本地数据库中的用户表
		BdUser user = bdUserDao.findUserByName(username);
		Map<String,String> parameters = new HashMap<>();
	    parameters.put("username", username);
	    parameters.put("password",password);

	    RuiZhiLoginResult ruiZhiLoginResult = null;
	    String result;
	    String token ;
	    try {

	    	//调用睿智的登陆接口
	    	result = HttpClientUtil.httpRequestToString(Consts.getRuiZhiLoginUrl(),Consts.POST,parameters);
	        ruiZhiLoginResult = JSON.parseObject(result,RuiZhiLoginResult.class);
		} catch (Exception e) {
			logger.error("调用睿智的登录接口出现异常");
	        e.printStackTrace();
		}

	    //如果返回结果为1则登陆失败，进一步判断是否为管理员
	    if(ruiZhiLoginResult.getResult() == Constants.ONE){

			if(user == null){
				return Constants.USER_NOT_EXIST;
			}
	    	if(user != null && password.equals(user.getPassword())){
	    		if(user.getType() == Constants.THREE){
	    			long id = user.getId();
					Integer type = user.getType();
					token = TokenUtil.createToken(username,id,type);
					String key = Constants.USER_lOGIN_KEY+id;
					redisUtilsTemplate.set(key, token);
					return token;
	    		}
				return Constants.USER_NOT_EXIST;
			} else {
				return Constants.PASSWORD_ERROR;
			}
	    } else {
	    	String key = ruiZhiLoginResult.getUser_id();
	    	token = ruiZhiLoginResult.getToken();
	    	String arr = token+","+key;
			redisUtilsTemplate.set(key, token);
			return arr;
	    }
	}
	
}
