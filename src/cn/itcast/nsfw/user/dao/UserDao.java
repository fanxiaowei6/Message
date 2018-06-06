package cn.itcast.nsfw.user.dao;

import java.io.Serializable;
import java.util.List;

import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserDao extends BaseDao<User> {

	//根据用户账号号和用户id查询用户信息
	public List<User> findUserByAccountAndId(String id, String account);
	//保存用户角色
	public void saveUserRole(UserRole userRole);
	//根据用户id删除用户角色
	public void deleteUserRoleByUserId(Serializable id);
	//根据用户id获取用户角色
	public List<UserRole> UserRolesByUserId(String id);
	//根据账号和密码查询用户列表
	public List<User> findUsersByAccountAndPass(String account, String password);
		
}
