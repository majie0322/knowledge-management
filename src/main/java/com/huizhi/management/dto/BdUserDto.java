package com.huizhi.management.dto;

import com.huizhi.management.entity.BdUser;

public class BdUserDto {
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private Integer type;

	private Long linkId;

	public BdUserDto() {
	}

	public BdUserDto(Long id, String username, String password, Integer type, Long linkId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.linkId = linkId;
	}

	@Override public String toString() {
		return "BdUserDto{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", type=" + type + ", linkId=" + linkId + '}';
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
			user.setLinkId(dto.getLinkId());
		}
		return user;
	}
	
	
}
