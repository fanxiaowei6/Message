package cn.itcast.nsfw.user.service;

import java.io.File;
import java.util.List;

import javax.servlet.ServletOutputStream;

import cn.itcast.core.service.BaseService;

import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;

public interface UserService extends BaseService<User> {


	//导出用户列表信息
	public void exportExcel(List<User> userList,ServletOutputStream outputStream);
	//导入用户信息
	public void importExcel(File userExcel,String userExcelFileName);
	
    //根据用户账号号和用户id查询用户信息
	public List<User> findUserByAccountAndId(String id, String account);
	//保存用户对应的角色
	public void saveUserAndRole(User user, String... roleIds);
	public void updateUserAndRole(User user, String... roleIds);
	//根据用户id获取该用户的的角色
	public List<UserRole> getUserRolesByUserId(String id);
	//根据账号和密码查询用户列表
	public List<User> findUserByAccAndPass(String account, String password);

	

}
