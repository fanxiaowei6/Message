package cn.itcast.nsfw.user.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserRole implements Serializable {

	private UserRoleId id;

	public UserRoleId getId() {
		return id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}

	public UserRole(UserRoleId id) {
		super();
		this.id = id;
	}

	public UserRole() {
		super();
	}
	
}
