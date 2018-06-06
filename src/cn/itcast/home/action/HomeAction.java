package cn.itcast.home.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.constant.Constant;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;
import cn.itcast.nsfw.user.entity.User;
import cn.itcast.nsfw.user.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomeAction extends ActionSupport {

	@Resource
	private UserService userService;
	@Resource
	private InfoService infoService;
	@Resource
	private ComplainService complainService;

	private Map<String, Object> map;
	private Complain comp;

	private Info info;

	// 跳转到首页
	public String execute() {
		// 加载信息列表
		// 加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",
				Info.INFO_TYPE_MAP);
		QueryHelper queryHelper1 = new QueryHelper(Info.class, "i");
		queryHelper1.addOrderByProperty(" i.createTime  ",
				QueryHelper.ORDER_BY_DESC);
		List<Info> infoList = infoService.getPageResult(queryHelper1, 1, 5)
				.getItems();
		ActionContext.getContext().getContextMap().put("infoList", infoList);

		User user = (User) ServletActionContext.getRequest().getSession()
				.getAttribute(Constant.USER);
		// 加载投诉信息列表
		// 加载状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap",
				Complain.COMPLAIN_STATE);
		QueryHelper queryHelper2 = new QueryHelper(Complain.class, "c");
		queryHelper2.addCondition("c.compName =?", user.getName());
		// 按时间进行升序排序
		//queryHelper2.addOrderByProperty("c.compTime",QueryHelper.ORDER_BY_ASC);
		// 按状态进行降序排序
		queryHelper2.addOrderByProperty(" c.state ", QueryHelper.ORDER_BY_DESC);
		List<Complain> complainList = complainService.getPageResult(
				queryHelper2, 1, 9).getItems();
		ActionContext.getContext().getContextMap().put("complainList",
				complainList);
		return "home";
	}

	// 跳转到投诉界面
	public String complainAddUI() {
		return "complainAddUI";
	}

	public void getUserJson() {
		try {
			// 获取部门
			String dept = ServletActionContext.getRequest()
					.getParameter("dept");
			if (StringUtils.isNotBlank(dept)) {
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", "%" + dept);
				// 根据部门查询用户列表
				List<User> userList = userService.findObjects(queryHelper);
				// 创建JSON对象
				JSONObject json = new JSONObject();
				json.put("msg", "success");
				json.accumulate("userList", userList);

				// 输出用户列表以json格式输出
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html,utf-8");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write(json.toString().getBytes("utf-8"));
				outputStream.close();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getUserJson2() {
		try {
			// 获取部门
			String dept = ServletActionContext.getRequest()
					.getParameter("dept");
			if (StringUtils.isNotBlank(dept)) {
				QueryHelper queryHelper = new QueryHelper(User.class, "u");
				queryHelper.addCondition("u.dept like ?", "%" + dept);
				// 根据部门查询用户列表
				map = new HashMap<String, Object>();
				map.put("msg", "success");
				map.put("userList", userService.findObjects(queryHelper));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 保存投诉
	public void complainAdd() {
		try {
			if (comp != null) {
				// 设置默写投诉状态为 待受理
				comp.setState(Complain.COMPLAIN_STATE_UNDONE);
				comp.setCompTime(new Timestamp(new Date().getTime()));
				complainService.save(comp);

				// 输出投诉结果
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html");
				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("success".getBytes("utf-8"));
				outputStream.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查看信息
	public String infoViewUI() {
		// 加载集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",
				Info.INFO_TYPE_MAP);
		if (info != null) {
			info = infoService.findObjectById(info.getInfoId());
		}
		return "infoViewUI";
	}

	// 查看投诉信息
	public String complainViewUI() {
		// 加载状态集合
		ActionContext.getContext().getContextMap().put("complainStateMap",
				Complain.COMPLAIN_STATE);
		if (comp != null) {
			comp = complainService.findObjectById(comp.getCompId());
		}
		return "complainViewUI";
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Complain getComp() {
		return comp;
	}

	public void setComp(Complain comp) {
		this.comp = comp;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

}
