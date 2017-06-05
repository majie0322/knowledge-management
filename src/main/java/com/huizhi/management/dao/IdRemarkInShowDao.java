package com.huizhi.management.dao;


import com.huizhi.management.conf.MongodbConfig;
import com.huizhi.management.entity.IdRemarkInShow;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 主要是用于插入知识点，获取rzkid
 * @author onlyo
 */
@Service
public class IdRemarkInShowDao extends MongodbConfig {
	
	/**
	 * 插入IdRemarkInShow对象
	 * @param idRemarkInShow
	 */
	public void add(IdRemarkInShow idRemarkInShow){
		mongoTemplate.insert(idRemarkInShow);
	}
	
	/**
	 * 查询IdRemarkInShow对象
	 * @return
	 */
	public IdRemarkInShow findIdRemarkInShow(){
		Query query = new Query();
		query.addCriteria(Criteria.where("code").is("rzKid"));
		return mongoTemplate.findOne(query, IdRemarkInShow.class);
	}

	public void updeteIdRemarkInShow(IdRemarkInShow idRemarkInShow){
		Update update = new Update();
		update.set("ruiZhiId", idRemarkInShow.getRuiZhiId());
		mongoTemplate.updateFirst(new Query(Criteria.where("code").is(idRemarkInShow.getCode())), update, IdRemarkInShow.class);
		
	}

}
