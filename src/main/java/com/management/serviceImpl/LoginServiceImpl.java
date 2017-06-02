package com.management.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.huizhi.bd.common.entity.RuiZhiLoginResult;
import com.management.constant.Constants;
import com.management.constant.Consts;
import com.management.dao.BdUserDao;
import com.management.entity.BdUser;
import com.management.service.LoginService;
import com.management.util.HttpClientUtil;
import com.management.util.RedisUtilsTemplate;
import com.management.util.TokenUtil;
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

	private static final int ONE = 1;
	
	private static final int THREE = 3;
	
	private Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	@Autowired(required = false)
	private BdUserDao bdUserDao;

	@Autowired
	private RedisUtilsTemplate redisUtilsTemplate;
	

	public String login(String username, String password){
		//查询本地数据库中的用户表
		BdUser user = bdUserDao.findUserByName(username);
		Map<String,String> parameters = new HashMap<>();
	    parameters.put("username", username);
	    parameters.put("password",password);
	    RuiZhiLoginResult ruiZhiLoginResult = null;
	    String result = null;
	    String token = null;
	    try {
	    	//调用睿智的登陆接口
	    	result = HttpClientUtil.httpRequestToString(Consts.getRuiZhiLoginUrl(),Consts.POST,parameters);
	        ruiZhiLoginResult = JSON.parseObject(result,RuiZhiLoginResult.class);
		} catch (Exception e) {
			logger.error("调用睿智的登录接口出现异常");
	        e.printStackTrace();
		}
	    //如果返回结果为1则登陆失败，进一步判断是否为管理员
	    if(ruiZhiLoginResult.getResult() == ONE){
	    	if(user != null && password.equals(user.getPassword())){
	    		if(user.getType() == THREE){
	    			long id = user.getId();
					Integer type = user.getType();
					token = TokenUtil.createToken(username,id,type);
					String key = Constants.USER_lOGIN_KEY+id;
					redisUtilsTemplate.set(key, token);
					return token;
	    		}
				return "User_does_not_exist";
			} else if(user == null){
				return "User_does_not_exist";
			} else {
				return "wrong_password";
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
