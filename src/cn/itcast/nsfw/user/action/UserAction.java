package cn.itcast.nsfw.user.action;

import java.io.File;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.role.service.RoleService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.entity.UserRole;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;

public class UserAction extends BaseAction {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	private List<User> userList;
	private User user;

	private File headImg;
	private String headImgContentType;
	private String headImgFileName;

	private File userExcel;
	private String userExcelContentType;
	private String userExcelFileName;
	private Serializable id;

	private String[] userRoleIds;

	private PageResult pageResult;
	private int pageNo;
	private int pageSize;

	//日期转化 spring mvc  中需要自己进行日期转化，内部没有日期转化机制
	/*@InitBinder
	protected void initBinder(HttpServletRequest request,ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}*/


	// 列表页面
	public String listUI() throws Exception {
		QueryHelper queryHelper = new QueryHelper(User.class, "u");
		try {
			if (user != null) {
				if (StringUtils.isNotBlank(user.getName())) {
					user.setName(URLDecoder.decode(user.getName(), "utf-8"));
					queryHelper.addCondition("u.name like ? ", "%" + user.getName() + "%");
				}
			}
			pageResult = userService.getPageResult(queryHelper,getPageNo(),getPageSize());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return "listUI";
	}

	// 上传方法
	public void upload() {
		try {
			if (headImg != null) {
				// 1、保存头像到upload/user
				// 获取保存路径的绝对地址
				String filePath = ServletActionContext.getServletContext().getRealPath("upload/user");
			
				String fileName = UUID.randomUUID().toString().replaceAll("-",
						"")
						+ headImgFileName.substring(headImgFileName
								.lastIndexOf("."));
				// 复制文件
				FileUtils.copyFile(headImg, new File(filePath, fileName));

				// 2、设置用户头像路径
				user.setHeadImg("user/" + fileName);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 跳转新增
	public String addUI() {
		// 加载角色列表
		ActionContext.getContext().getContextMap().put("roleList",
				roleService.findObjects());
		return "addUI";
	}

	// 保存新增
	public String add() {
		if (user != null) {
			this.upload();
			userService.saveUserAndRole(user, userRoleIds);
		}
		return "list";
	}

	// 跳转编辑页面
	public String editUI() {
		// 加载角色列表
		ActionContext.getContext().getContextMap().put("roleList",
				roleService.findObjects());
		if (user != null && user.getId() != null) {
			user = userService.findObjectById(user.getId());
			// 处理角色回显
			List<UserRole> list = userService
					.getUserRolesByUserId(user.getId());
			if (list != null && list.size() > 0) {
				userRoleIds = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					userRoleIds[i] = list.get(i).getId().getRole().getRoleId();
				}
			}

		}
		return "editUI";
	}

	// 保存编辑
	public String edit() {
		if (user != null) {
			this.upload();
			userService.updateUserAndRole(user, userRoleIds);
			System.out.println("保存修改");
		}
		return "list";
	}

	// 删除
	public String delete() {
		if (user != null && user.getId() != null) {
			userService.delete(user.getId());
		}
		return "list";
	}

	// 批量删除
	public String deleteSelected() {
		if (selectedRow != null) {
			System.out.println("开始批量删除！");
			for (String id : selectedRow) {
				userService.delete(id);
			}
		}
		return "list";
	}

	// 导出excel
	public void exportExcel() {
		try {
			// 1、查找用户列表
			userList = userService.findObjects();
			// 2、导出用户列表信息
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-excel");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String("员工信息.xls".getBytes(), "ISO-8859-1"));

			ServletOutputStream outputStream = response.getOutputStream();
			userService.exportExcel(userList, outputStream);
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 导入excel
	public String importExcel() {
		// 获取excel文件,判断是否为空
		if (userExcel != null) {
			// 判断是否是excel文件
			if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
				userService.importExcel(userExcel, userExcelFileName);
			}
		}

		return "list";
	}

	// 校验账号的唯一性
	public void verifyAccount() {
		try {
			if (user != null && StringUtils.isNotBlank(user.getAccount())) {
				List<User> list = userService.findUserByAccountAndId(user
						.getId(), user.getAccount());
				String strResult = "true";
				if (list != null && list.size() > 0) {
					// 说明该账号已经存在
					strResult = "false";
				}
				// 输出
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(strResult.getBytes());
				outputStream.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String[] getUserRoleIds() {
		return userRoleIds;
	}

	public void setUserRoleIds(String[] userRoleIds) {
		this.userRoleIds = userRoleIds;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		pageSize=5;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	
}
