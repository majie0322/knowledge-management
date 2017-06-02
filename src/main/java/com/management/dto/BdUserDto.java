package com.management.dto;

import com.huizhi.bd.common.entity.User;
import com.management.entity.BdUser;

import java.sql.Timestamp;

public class BdUserDto {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private int type;
	
	private Timestamp createTime;
	
	private Long linkId;

	public BdUserDto() {
	}

	public BdUserDto(Long id, String username, String password, int type, Timestamp createTime, Long linkId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.createTime = createTime;
		this.linkId = linkId;
	}

	@Override public String toString() {
		return "BdUserDto{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", type=" + type + ", createTime=" + createTime + ", linkId=" + linkId + '}';
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public static BdUser from (BdUserDto dto){
		BdUser user = null;
		if(dto != null){
			user = new BdUser();
			user.setId(dto.getId());
			user.setUsername(dto.getUsername());
			user.setPassword(dto.getPassword());
			user.setType(dto.getType());
			user.setCreateTime(dto.getCreateTime());
			user.setLinkId(dto.getLinkId());
		}
		return user;
	}
	
	
}
