package com.huizhi.management.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 主要是为了新增知识点的同时，并不知道睿智的id，先从mongodb中从1000000开始自增获取id(budong ask yinlong)
 * @author onlyo
 */
public class IdRemarkInShow implements Serializable{

	private static final long serialVersionUID = 8523614471165042226L;

	private String code;

	/**
	 * 自动生成睿智id
	 */
	private long ruiZhiId;

	private List<Long> knowledgeIds;

	public IdRemarkInShow() {

	}
	public IdRemarkInShow(String code, long ruiZhiId, List<Long> knowledgeIds) {
		super();
		this.code = code;
		this.ruiZhiId = ruiZhiId;
		this.knowledgeIds = knowledgeIds;
	}

	@Override
	public String toString() {
		return "IdRemarkInShow [code=" + code + ", ruiZhiId=" + ruiZhiId + ", knowledgeIds=" + knowledgeIds + "]";
	}

	public List<Long> getKnowledgeIds() {
		return knowledgeIds;
	}
	public void setKnowledgeIds(List<Long> knowledgeIds) {
		this.knowledgeIds = knowledgeIds;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getRuiZhiId() {
		return ruiZhiId;
	}
	public void setRuiZhiId(long ruiZhiId) {
		this.ruiZhiId = ruiZhiId;
	}

}
