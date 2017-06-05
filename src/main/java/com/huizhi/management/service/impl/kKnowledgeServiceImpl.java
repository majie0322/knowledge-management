package com.huizhi.management.service.impl;

import com.huizhi.management.dao.IdRemarkInShowDao;
import com.huizhi.management.dao.KKnowledgeDao;
import com.huizhi.management.dto.KnowledgeDto;
import com.huizhi.management.entity.IdRemarkInShow;
import com.huizhi.management.entity.Knowledge;
import com.huizhi.management.service.KKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class kKnowledgeServiceImpl implements KKnowledgeService{

    @Autowired
    private KKnowledgeDao kKnowledgeDao;

    @Autowired
    private IdRemarkInShowDao idRemarkInShowDao;

    /**
     * 通过id查找知识点
     * @param id 知识点唯一标识
     * @return 知识点
     */
   public Knowledge findKnowledgeById(Long id){
        return kKnowledgeDao.findKnowledgeById(id);
    }

    /**
     * 新增子节点，并与课程建立关系
     * @param knowledgeDto
     * @return
     */
    @Override
    public Knowledge addKnowledge(KnowledgeDto knowledgeDto) {

        if (knowledgeDto.getId() == null) {

            //获取临时睿智的id，由mongodb临时生成
            IdRemarkInShow idRemarkInShow = idRemarkInShowDao.findIdRemarkInShow();
            if (idRemarkInShow != null) {
                knowledgeDto.setRzKid(idRemarkInShow.getRuiZhiId() + 1);
                idRemarkInShow.setRuiZhiId(knowledgeDto.getRzKid());
                idRemarkInShowDao.updeteIdRemarkInShow(idRemarkInShow);
            } else {
                idRemarkInShow = new IdRemarkInShow();
                idRemarkInShow.setCode("rzKid");
                idRemarkInShow.setRuiZhiId(10000001L);
                idRemarkInShowDao.add(idRemarkInShow);
                knowledgeDto.setRzKid(idRemarkInShow.getRuiZhiId());
            }
        }

        Knowledge knowledge = KnowledgeDto.from(knowledgeDto);
        Date newTime = new Date();
        knowledge.setCreateTime(newTime.getTime());
        knowledge.setModifyTime(newTime.getTime());

        //设置原始知识点状态
        if(knowledge.getTeacherId() != -1){
            knowledge.setIsBase(-1);
        }

        //初始化这个三个数据
        knowledge.setRzGradation(1);
        knowledge.setRzParentId(0L);
        knowledge.setRzEnd(1);

        Knowledge know = kKnowledgeDao.save(knowledge);

        Long courseId = knowledgeDto.getCourseId();
        Long knowledgeId = know.getId();

        kKnowledgeDao.addRelation(courseId,knowledgeId);

        return know;
    }
}
