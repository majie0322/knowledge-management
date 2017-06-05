package com.huizhi.management.controller;

import com.huizhi.management.dto.ResultDto;
import com.huizhi.management.entity.Course;
import com.huizhi.management.service.KCourseService;
import com.huizhi.management.util.ResultDtoFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author onlyo
 * 科目控制层
 */
@RestController
@RequestMapping("/management/course")
@Api(value = "科目", description = "图谱相关科目",position = 1)
public class KCourseController {

    @Autowired
    private KCourseService kCourseService;

    @RequestMapping(value = "/findAllCourse",method = RequestMethod.POST)
    @ApiOperation(value="查看所有科目")
    public ResultDto findAllCourse(){

        List<Course> courseList = kCourseService.findAllCourse();

        return ResultDtoFactory.toSuccess(courseList);
    }


}
