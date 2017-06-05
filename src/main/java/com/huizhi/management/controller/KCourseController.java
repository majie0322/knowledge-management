package com.huizhi.management.controller;

import com.huizhi.management.dao.KCourseDao;
import com.huizhi.management.dto.CourseDto;
import com.huizhi.management.dto.ResultDto;
import com.huizhi.management.entity.Course;
import com.huizhi.management.service.KCourseService;
import com.huizhi.management.util.ResultCode;
import com.huizhi.management.util.ResultDtoFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/findAllCourse", method = RequestMethod.POST)
    @ApiOperation(value="查看所有科目")
    public ResultDto findAllCourse(){
        List<Course> courseList = kCourseService.findAllCourse();
        return ResultDtoFactory.toSuccess(courseList);
    }

    @RequestMapping(value = "/findCourseBySubjectId", method = RequestMethod.POST)
    @ApiOperation(value = "根据科目ID查询对应的课程")
    public ResultDto findCourseBySubjectId(@RequestBody CourseDto dto){
        if (dto.getCourseId() != null){
            return ResultDtoFactory.toSuccess(kCourseService.findCourseBySubjectId(dto.getCourseId()));
        } else {
            return ResultDtoFactory.toError(ResultCode.MISSING_FIELD);
        }
    }

    @RequestMapping(value = "/addCourse", method = RequestMethod.POST)
    @ApiOperation(value = "添加课程")
    public ResultDto addCourse(@RequestBody CourseDto dto){
        if(dto != null){
            Course course  = new Course();
            course.setName(dto.getName());
            course.setCourseId(dto.getCourseId());
            course.setRemark(dto.getRemark());
            Course c = kCourseService.addCourse(course);
            return ResultDtoFactory.toSuccess(c);
        } else {
            return ResultDtoFactory.toError(ResultCode.MISSING_FIELD);
        }
    }

    @RequestMapping(value = "/findRelatedKnowledge", method = RequestMethod.POST)
    @ApiOperation(value = "查找课程下相关知识点")
    public ResultDto findRelatedKnowledge(@RequestBody CourseDto dto){
        if(dto.getTeacherId() != null && dto.getId() != null){
            List<Long> kIds = kCourseService.recordedId(dto.getTeacherId(), dto.getKnowledgeId(), dto.getId());
            String key = "Kedit:teacher"+dto.getTeacherId()+dto.getId();
            Map<String, Object> map = kCourseService.showKnowledge(dto.getId(), dto.getTeacherId(), kIds, dto.getGradeTypes(), dto.getMaxValue(), key);
            return ResultDtoFactory.toSuccess(map);
        } else {
            return ResultDtoFactory.toError(ResultCode.MISSING_FIELD);
        }
    }

}
