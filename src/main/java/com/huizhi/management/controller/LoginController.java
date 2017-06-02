package com.huizhi.management.controller;

import com.huizhi.management.constant.Constants;
import com.huizhi.management.dao.BdTeacherDao;
import com.huizhi.management.dto.ResultDto;
import com.huizhi.management.dao.BdCourseDao;
import com.huizhi.management.dao.BdUserDao;
import com.huizhi.management.dto.BdUserDto;
import com.huizhi.management.util.ResultCode;
import com.huizhi.management.util.ResultDtoFactory;
import com.huizhi.management.entity.BdCourse;
import com.huizhi.management.entity.BdTeacher;
import com.huizhi.management.entity.BdUser;
import com.huizhi.management.service.LoginService;
import com.huizhi.management.util.RedisUtilsTemplate;
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
		if(Constants.PASSWORD_ERROR.equals(token)){
			return ResultDtoFactory.toError(ResultCode.PASSWORD_INCORRECT);
		} else if(Constants.USER_NOT_EXIST.equals(token)){
			return ResultDtoFactory.toError(ResultCode.MEMBER_NOT_EXIST);
		} else {
			Map<String, Object> map = new HashMap<>(14);

			//用户存在，判断用户的type类型是管理员还是教师
			BdUser user = bdUserDao.findUserByName(dto.getUsername());
			BdTeacher teacher;
			if(user.getType() == Constants.TWO){
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
