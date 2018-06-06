package cn.itcast.nsfw.role.dao;



import cn.itcast.core.dao.BaseDao;
import cn.itcast.nsfw.role.entity.Role;

public interface RoleDao extends BaseDao<Role> {


	//删除所有角色之前的权限
	public void deleteRolePrivilegeByRoleId(String roleId);

	
}
