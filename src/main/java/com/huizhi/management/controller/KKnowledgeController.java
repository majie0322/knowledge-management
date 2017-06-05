package com.huizhi.management.controller;

import com.huizhi.management.dto.KnowledgeDto;
import com.huizhi.management.dto.ResultDto;
import com.huizhi.management.entity.Knowledge;
import com.huizhi.management.service.KKnowledgeService;
import com.huizhi.management.util.ResultCode;
import com.huizhi.management.util.ResultDtoFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识图谱（knowledge相关控制层）
 */
@RestController
@RequestMapping("/management/knowledge")
@Api(value = "知识点", description = "图谱相关知识点", position = 2)
public class KKnowledgeController {

    @Autowired(required = false)
    private KKnowledgeService kKnowledgeService;

    @RequestMapping(value = "findKnowledgeById", method = RequestMethod.POST)
    @ApiOperation(value = "通过id查找知识点的信息")
    public ResultDto findKnowledgeById(@RequestBody KnowledgeDto dto){
        Long id = dto.getId();
        Knowledge knowledge = kKnowledgeService.findKnowledgeById(id);
        return ResultDtoFactory.toSuccess(knowledge);
    }

    @RequestMapping(value = "addKnowledge", method = RequestMethod.POST)
    @ApiOperation(value = "添加子节点,并与课程建立关系")
    public ResultDto addKnowledge(@RequestBody KnowledgeDto dto){

        String name = dto.getName();
        Integer gradeSection = dto.getGradeSection();
        Long teacherId = dto.getTeacherId();
        Long courseId = dto.getCourseId();

        if(name == null || gradeSection == null || teacherId == null || courseId == null){
            return ResultDtoFactory.toError(ResultCode.MISSING_FIELD);
        } else {
            kKnowledgeService.addKnowledge(dto);
            return ResultDtoFactory.toSuccess(ResultCode.SUCCESS);
        }
    }

}
