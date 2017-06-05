package com.huizhi.management.service.impl;

import com.huizhi.management.dao.KCourseDao;
import com.huizhi.management.dao.KKnowledgeDao;
import com.huizhi.management.entity.Course;
import com.huizhi.management.entity.Knowledge;
import com.huizhi.management.service.KCourseService;
import com.huizhi.management.util.RedisUtilsTemplate;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KCourseServiceImpl implements KCourseService {

    @Autowired
    private KCourseDao kCourseDao;

    @Autowired
    private KKnowledgeDao kKnowledgeDao;

    @Autowired
    private RedisUtilsTemplate redisUtilsTemplate;

    /**
     * 查找所有课目
     *
     * @return 课目集合
     */
    @Override
    public List<Course> findAllCourse() {
        return kCourseDao.findAllCourse();
    }

    /**
     * 基础基础库中的科目ID查询课程
     *
     * @param courseId 基础库科目ID
     * @return
     */
    @Override
    public Course findCourseBySubjectId(Long courseId) {
        if (courseId != null) {
            return kCourseDao.findCourseBySubjectId(courseId);
        } else {
            return null;
        }
    }

    /**
     * 添加课程
     * @param course 课程
     * @return 课程对象
     */
    @Override
    public Course addCourse(Course course) {
        kCourseDao.save(course);
        Course c = kCourseDao.findCourseById(course.getCourseId());
        return c;
    }

    /**
     * 记录用户操作，知识点展开收起功能
     * @param teacherId 教师ID
     * @param knowledgeId
     * @param id 课程ID
     * @return 用户上一次操作过的知识点ID集合
     */
    @Override
    public List<Long> recordedId(Long teacherId, Long knowledgeId, Long id) {
        //存入redis中的key
        String redisKey = "Kedit:teacher"+teacherId+id;
        //根据key从redis中取出ID集合
        String kids = redisUtilsTemplate.get(redisKey);

        if(knowledgeId == null){
            if(kids == null){
                return new ArrayList<Long>();
            }
            List<String> karr = new ArrayList<String>(Arrays.asList(kids.split(",")));
            List<Long> kIds = new ArrayList<Long>();
            for (int i = 0; i < karr.size(); i++) {
                if(karr.get(i) == ""){
                    return new ArrayList<Long>();
                } else {
                    kIds.add(Long.valueOf(karr.get(i)));
                }
            }
            return kIds;
        }
        if(knowledgeId == -1L){
            redisUtilsTemplate.delete(redisKey);
            return new ArrayList<Long>();
        }

        if(kids == null){
            kids = String.valueOf(knowledgeId);
            redisUtilsTemplate.set(redisKey, kids);
        } else {
            String [] arr = kids.split(",");
            List<String> karr = new ArrayList<String>(Arrays.asList(arr));
            boolean bl = isExist(karr, knowledgeId);
            if(bl == true){
                List<String> list = findByIdToChildNode(knowledgeId);
                karr.removeAll(list);
            } else {
                karr.add(String.valueOf(knowledgeId));
            }

            if(karr.isEmpty()){
                redisUtilsTemplate.delete(redisKey);
            } else {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < karr.size(); i++) {
                    if(i == 0){
                        sb.append(karr.get(i));
                    } else {
                        sb.append(",");
                        sb.append(karr.get(i));
                    }
                }
                kids = sb.toString();
                redisUtilsTemplate.set(redisKey, kids);
            }
        }

        kids = redisUtilsTemplate.get(redisKey);
        if(kids == null){
            return new ArrayList<Long>();
        } else {
            String [] arr = kids.split(",");
            List<String> karr = new ArrayList<String>(Arrays.asList(arr));
            List<Long> kIds = new ArrayList<Long>();

            for (int i = 0; i < karr.size(); i++) {
                if(karr.get(i) == ""){
                    return new ArrayList<Long>();
                } else {
                    kIds.add(Long.valueOf(karr.get(i)));
                }
            }

            return kIds;
        }
    }

    /**
     * 判断redis中是否已存在知识点ID，返回true表示存在，返回false表示不存在
     * @param arr
     * @param knowledgeId
     * @return  true或false
     */
    private boolean isExist(List<String> arr, Long knowledgeId){
        for (int i = 0; i < arr.size(); i++) {
            if(arr.get(i).equals(String.valueOf(knowledgeId))){
                return true;
            }
        }
        return false;
    }

    private List<String> findByIdToChildNode(Long knowledgeId) {
        if(knowledgeId != null && knowledgeId != 0){
            Iterator<Knowledge> knowledges = kKnowledgeDao.findSonNode(knowledgeId).iterator();
            List<String> list = new ArrayList<String>();
            while(knowledges.hasNext()){
                Knowledge k = knowledges.next();
                list.add(String.valueOf(k.getId()));
                list.add(String.valueOf(knowledgeId));
            }
            return list;
        } else {
            return null;
        }
    }

    private Object map(String key1, Object value1, String key2, Object value2, String key3, Object value3, String key4, Object value4) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key1, value1);
        map.put(key2, value2);
        map.put(key3, value3);
        map.put(key4, value4);
        return map;
    }

    @Override
    public Map<String, Object> showKnowledge(Long id, Long teacherId, List<Long> KnowledgeIds, List<Long> gradeTypes, Integer maxValue, String redisKey) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Long> teacherIds = new ArrayList<Long>();
        teacherIds.add(-1L);
        teacherIds.add(teacherId);
        //通过传入的课程ID查找该课程
        Course course = kCourseDao.findCourseById(id);
        //虚拟一个课程节点
        Knowledge know = new Knowledge(-1L, -1L, -1L, 0, course.getName(), "", 0, 0, 0L, 0L, 0L, 0, "", 0L, -1L, 0, 0, course.getCourseId());

        //如果knowledgeIds为空，只显示第一层
        if(KnowledgeIds.isEmpty()){
            List<Integer> rzGradations = null;

            List<Knowledge> nodes = new ArrayList<Knowledge>();
            List<Object> rels = new ArrayList<Object>();

            if(nodes.size() == 0){
                rzGradations = new ArrayList<Integer>();
                rzGradations.add(1);

                //查询第一层知识点
                Iterator<Knowledge> levelOneKnowledge = kKnowledgeDao.findKnowledgeByCourseIdAndRzGradation(id, rzGradations, teacherIds).iterator();
                while(levelOneKnowledge.hasNext()){
                    Knowledge k = levelOneKnowledge.next();
                    nodes.add(k);
                }

            }
            nodes.add(know);
            map.put("nodes", nodes);
            map.put("rels", rels);
            return map;
        } else {
            List<Knowledge> nodes = new ArrayList<Knowledge>();
            List<Object> rels = new ArrayList<Object>();

            List<Knowledge> knowledges = kKnowledgeDao.findKnowledgeByIds(KnowledgeIds);
            //根据传入的knowledgeIds的顺序将Knowledge排序
            List<Knowledge> knowledge = new ArrayList<Knowledge>();
            //传入ID节点的父节点ID
            List<Long> fatherId = new ArrayList<Long>();
            for (int i = 0; i < KnowledgeIds.size(); i++) {
                for (int j = 0; j < knowledges.size(); j++) {
                    fatherId.add(knowledges.get(j).getRzParentId());
                    if(KnowledgeIds.get(i).equals(knowledges.get(j).getId())){
                        knowledge.add(knowledges.get(j));
                    }
                }
            }

            //去除重复的父节点ID
            for (int y = 0; y < fatherId.size() - 1; y++) {
                for (int j = fatherId.size() - 1; j > y; j--) {
                    if (fatherId.get(j).equals(fatherId.get(y))) {
                        fatherId.remove(j);
                    }
                }
            }

            for (int i = 0; i < knowledge.size(); i++) {
                //判断传入的已学知识点层级是否为第一层
                if (knowledge.get(i).getRzGradation() == 1) {
                    //查询第一层知识点的子节点
                    Iterator<Knowledge> sonNode = kKnowledgeDao.findSonNodeByFatherNodeId(knowledge.get(i).getId()).iterator();
                    while (sonNode.hasNext()) {
                        Knowledge k = sonNode.next();
                        nodes.add(k);
                    }
                    //查询第一层节点的子节点的关系
                    Iterator<Map<String, Object>> sonNodeRelationship = kKnowledgeDao.findSonNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                    while(sonNodeRelationship.hasNext()){
                        Map<String, Object> row = sonNodeRelationship.next();
                        rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                    }

                    List<Integer> rzGradationList = new ArrayList<Integer>();
                    rzGradationList.add(1);
                    //查询第一层节点
                    Iterator<Knowledge> levelOneKnowledge = kKnowledgeDao.findKnowledgeByCourseIdAndRzGradation(id, rzGradationList, teacherIds).iterator();
                    while (levelOneKnowledge.hasNext()) {
                        Knowledge k = levelOneKnowledge.next();
                        nodes.add(k);
                    }
                    nodes.removeAll(knowledge);
                    nodes.addAll(knowledge);
                } else {
                    if(i == 0){
                        List<Integer> rzGradationList = new ArrayList<Integer>();
                        rzGradationList.add(1);
                        //查询层级为1的节点
                        Iterator<Knowledge> levelOneKnowledge = kKnowledgeDao.findKnowledgeByCourseIdAndRzGradation(id, rzGradationList, teacherIds).iterator();
                        while (levelOneKnowledge.hasNext()) {
                            Knowledge k = levelOneKnowledge.next();
                            nodes.add(k);
                        }

                        //通过传入一个ID查询其子节点和父节点，包含该ID对应的对象
                        Iterator<Knowledge> fatherandsonNode = kKnowledgeDao.findFatherAndSonNodeByRzKid(knowledge.get(i).getId()).iterator();
                        while (fatherandsonNode.hasNext()) {
                            Knowledge k = fatherandsonNode.next();
                            nodes.add(k);
                        }
                        Iterator<Map<String, Object>> sonNodeRelationship = kKnowledgeDao.findSonNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                        while(sonNodeRelationship.hasNext()){
                            Map<String, Object> row = sonNodeRelationship.next();
                            rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                        }
                        Iterator<Map<String, Object>> FatherNodeRelationship = kKnowledgeDao.findFatherNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                        while(FatherNodeRelationship.hasNext()){
                            Map<String, Object> row = FatherNodeRelationship.next();
                            rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                        }

                        //通过父节点ID查询
                        Iterator<Knowledge> sonNode = kKnowledgeDao.findSonNodeByFatherNodeIds(fatherId).iterator();
                        while (sonNode.hasNext()) {
                            Knowledge k = sonNode.next();
                            nodes.add(k);
                        }
                        Iterator<Map<String, Object>> levelTwosonNodeRelationship = kKnowledgeDao.findSonNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                        while(levelTwosonNodeRelationship.hasNext()){
                            Map<String, Object> row = levelTwosonNodeRelationship.next();
                            rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                        }



                    } else {
                        Iterator<Knowledge> fatherandsonNode = kKnowledgeDao.findFatherAndSonNodeByRzKid(knowledge.get(i).getId()).iterator();
                        while (fatherandsonNode.hasNext()) {
                            Knowledge k = fatherandsonNode.next();
                            nodes.add(k);
                        }
                        Iterator<Map<String, Object>> sonNodeRelationship = kKnowledgeDao.findSonNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                        while(sonNodeRelationship.hasNext()){
                            Map<String, Object> row = sonNodeRelationship.next();
                            rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                        }
                        Iterator<Map<String, Object>> FatherNodeRelationship = kKnowledgeDao.findFatherNodeRelationshipByFatherNodeId(knowledge.get(i).getId()).iterator();
                        while(FatherNodeRelationship.hasNext()){
                            Map<String, Object> row = FatherNodeRelationship.next();
                            rels.add(map("source", row.get("id(fatherKnowledge)"), "target", row.get("id(sonKnowledge)"), "type", row.get("n.type"), "lineStyle",""));
                        }

                    }
                    nodes.removeAll(knowledge);
                    nodes.removeAll(knowledge);
                    nodes.addAll(knowledge);
                }

            }

            //进一步去重操作
            for (int y = 0; y < nodes.size() - 1; y++) {
                for (int j = nodes.size() - 1; j > y; j--) {
                    if (nodes.get(j).equals(nodes.get(y))) {
                        nodes.remove(j);
                    }
                }
            }

            List<Integer> gradations = new ArrayList<Integer>();
            for (int j = 0; j < knowledge.size(); j++) {
                //取出除最后一个元素外的层级
                if(!knowledge.get(j).getRzKid().equals(KnowledgeIds.get(KnowledgeIds.size()-1))){
                    gradations.add(knowledge.get(j).getRzGradation());
                }
            }
            //确定传入知识点中层级最远的一层
            int  maxGradation = 0;
            if(gradations.isEmpty()){
                maxGradation = 1;
            } else {
                maxGradation = Collections.max(gradations);
            }

            if (!gradeTypes.isEmpty()) {
                List<Knowledge> filterKnowledge = new ArrayList<Knowledge>();
                for (int y = 0; y < nodes.size(); y++) {
                    for (int j = 0; j < gradeTypes.size(); j++) {
                        if (nodes.get(y).getGradeSection() == gradeTypes.get(j)) {
                            filterKnowledge.add(nodes.get(y));
                        }
                    }
                }
                if(filterKnowledge.size() > maxValue){
                    isMax(maxValue, filterKnowledge, knowledge, maxGradation, redisKey);
                }
                filterKnowledge.add(know);

                map.put("nodes", filterKnowledge);
                map.put("rels", rels);
                return map;
            }
            if(nodes.size() > maxValue){
                isMax(maxValue, nodes, knowledge, maxGradation, redisKey);
            }

            nodes.add(know);
            map.put("nodes", nodes);
            map.put("rels", rels);
            return map;
        }
    }

    private List<Knowledge> isMax(int maxValue, List<Knowledge> nodes, List<Knowledge> knowledge, int maxGradation, String redisKey){
        for (int j = 0; j < knowledge.size(); j++) {
            if(knowledge.get(j).getRzGradation() == maxGradation){
                deleteRecord(redisKey, knowledge.get(j).getRzKid());
                List<Knowledge> list = kKnowledgeDao.findSonNodeByFatherNodeId(knowledge.get(j).getRzKid());
                knowledge.remove(knowledge.get(j));
                nodes.removeAll(list);
                break;
            }
        }
        List<Integer> gradations = new ArrayList<Integer>();
        for (int j = 0; j < knowledge.size(); j++) {
            //取出除最后一个元素外的层级
            if(!knowledge.get(j).getRzKid().equals(knowledge.get(knowledge.size()-1).getRzKid())){
                gradations.add(knowledge.get(j).getRzGradation());
            }
        }
        maxGradation = Collections.max(gradations);
        if(nodes.size() > maxValue){
            isMax(maxValue, nodes, knowledge, maxGradation, redisKey);
        }
        return nodes;
    }

    private void deleteRecord(String key, Long fatherId){
        System.out.println(fatherId);
        String record = redisUtilsTemplate.get(key);
        System.err.println(record);
        if(record!=null){
            String[] records = record.split(",");
            List<String> recordsList = new ArrayList<String>(Arrays.asList(records));
            List<String> StringIds  = findByIdToChildNode(fatherId);

            recordsList.removeAll(StringIds);

            if(recordsList.size() > 0){
                String recordNew = null;
                for(int i=0; i<recordsList.size(); i++){
                    if(i==0){
                        recordNew = recordsList.get(i);
                    }else{
                        recordNew += ","+recordsList.get(i);
                    }
                }
                redisUtilsTemplate.set(key, recordNew);
            }else{
                redisUtilsTemplate.delete(key);
            }
        }
    }

}