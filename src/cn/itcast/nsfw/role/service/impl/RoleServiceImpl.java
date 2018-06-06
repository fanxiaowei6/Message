package cn.itcast.nsfw.role.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.nsfw.role.dao.RoleDao;
import cn.itcast.nsfw.role.entity.Role;
import cn.itcast.nsfw.role.service.RoleService;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role>  implements RoleService {


	private RoleDao roleDao;

	@Resource
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}

	@Override
	public void delete(Serializable id) {
		// TODO Auto-generated method stub
		roleDao.delete(id);
	}


	@Override
	public Role findObjectById(Serializable id) {
		// TODO Auto-generated method stub
		return roleDao.findObjectById(id);
	}

	@Override
	public List<Role> findObjects() {
		// TODO Auto-generated method stub
		return roleDao.findObjects();
	}

	@Override
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}


	@Override
	public void update(Role role) {
		// TODO Auto-generated method stub
		// 删除该角色之前所有权限
		roleDao.deleteRolePrivilegeByRoleId(role.getRoleId());
		// 更新角色及其权限
		roleDao.update(role);
	}

}
