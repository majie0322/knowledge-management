package com.management.controller;

import com.management.dao.BdCourseDao;
import com.management.dao.BdTeacherDao;
import com.management.dao.BdUserDao;
import com.management.dto.BdUserDto;
import com.management.dto.ResultCode;
import com.management.dto.ResultDto;
import com.management.dto.ResultDtoFactory;
import com.management.entity.BdCourse;
import com.management.entity.BdTeacher;
import com.management.entity.BdUser;
import com.management.service.LoginService;
import com.management.util.RedisUtilsTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登入验证
 * @author onlyo
 */
@RestController
@Api(value = "登入验证", description = "登入验证", position = 2)
public class LoginController {
	
	private static final int ONE = 1;
	
	private static final int TWO = 2;
	
	private static final int THREE = 3;

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	@Autowired(required = false)
	private BdUserDao bdUserDao;

	@Autowired(required = false)
	private BdTeacherDao bdTeacherDao;

	@Autowired(required = false)
	private BdCourseDao bdCourseDao;

	@Autowired
	private RedisUtilsTemplate redisUtilsTemplate;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(value="权限判定并登入成功")
	public ResultDto findUserByName(@RequestBody BdUserDto dto) throws Exception{

		//生成token，成功返回token，失败返回原因
		String token = loginService.login(dto.getUsername(), dto.getPassword());
		if("wrong_password".equals(token)){
			return ResultDtoFactory.toError(ResultCode.PASSWORD_INCORRECT);
		} else if("User_does_not_exist".equals(token)){
			return ResultDtoFactory.toError(ResultCode.MEMBER_NOT_EXIST);
		} else {
			Map<String, Object> map = new HashMap<String, Object>(14);
			//用户存在，判断用户的type类型是管理员还是教师
			BdUser user = bdUserDao.findUserByName(dto.getUsername());
			BdTeacher teacher = null;
			if(user.getType() == TWO){
				String [] arr = token.split(",");
				teacher = bdTeacherDao.findTeacherByLinkId(user.getLinkId());
				BdCourse course = bdCourseDao.findCourseByCourseId(teacher.getCourseId());
				map.put("courseId", teacher.getCourseId());
				map.put("teacherId", teacher.getId());
				map.put("schoolId", teacher.getSchoolId());
				map.put("courseName", course.getCourseName());
				map.put("token", arr[0]);
				map.put("key", arr[1]);
				map.put("type", user.getType());
			} else {
				map.put("teacherId",-1L);
				map.put("schoolId", -1L);
				map.put("token", token);
				map.put("type", user.getType());
				map.put("key", user.getId());
				
			}
		    return ResultDtoFactory.toSuccess(map);
		}
	}

}
