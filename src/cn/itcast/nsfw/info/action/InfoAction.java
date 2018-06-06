package cn.itcast.nsfw.info.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.page.PageResult;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.info.entity.Info;
import cn.itcast.nsfw.info.service.InfoService;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class InfoAction extends BaseAction {

	@Resource
	private InfoService infoService;
	private Info info;
	private List<Info> infoList;
	private String strTitle;

	private PageResult pageResult;

	// 列表页面
	public String listUI() throws Exception {
		// 加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",
				Info.INFO_TYPE_MAP);
		QueryHelper queryHelper = new QueryHelper(Info.class, "i");
		try {
			if (info != null) {
				System.out.println("----------显示前台获取的title-----------"
						+ info.getTitle());
				if (StringUtils.isNotBlank(info.getTitle())) {
					info.setTitle(URLDecoder.decode(info.getTitle(), "UTF-8"));
					queryHelper.addCondition("i.title like ?", "%"
							+ info.getTitle() + "%");
				}
			}
			// 根据时间进行排序
			queryHelper.addOrderByProperty("i.createTime",
					queryHelper.ORDER_BY_DESC);

			pageResult = infoService.getPageResult(queryHelper, getPageNo(),
					getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

		return "listUI";
	}

	// 跳转到新增界面
	public String addUI() {
		ActionContext.getContext().getContextMap().put("infoTypeMap",
				Info.INFO_TYPE_MAP);
		info = new Info();
		info.setCreateTime(new Timestamp(new Date().getTime()));
		return "addUI";
	}

	// 保存新增
	public String add() {
		try {
			if (info != null) {
				infoService.save(info);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}

	// 跳转到编辑页面
	public String editUI() {
		// 加载分类集合
		ActionContext.getContext().getContextMap().put("infoTypeMap",
				Info.INFO_TYPE_MAP);
		if (info != null && info.getInfoId() != null) {
			// 解决条件查询覆盖问题
			strTitle = info.getTitle();
			info = infoService.findObjectById(info.getInfoId());
		}
		return "editUI";
	}

	// 保存编辑
	public String edit() {
		try {
			if (info != null) {
				infoService.update(info);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "list";
	}

	// 删除
	public String delete() {
		if (info != null && info.getInfoId() != null) {
			strTitle = info.getTitle();
			infoService.delete(info.getInfoId());
		}
		return "list";
	}

	// 批量删除
	public String deleteSelected() {
		if (selectedRow != null) {
			strTitle = info.getTitle();
			for (String id : selectedRow) {
				infoService.delete(id);
			}
		}
		return "list";
	}

	// 发布信息
	public void publicInfo() {
		if (info != null) {
			try {
				Info inf = infoService.findObjectById(info.getInfoId());
				inf.setState(info.getState());
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.setContentType("text/html,utf-8");

				ServletOutputStream outputStream = response.getOutputStream();
				outputStream.write("更新状态成功".getBytes("utf-8"));
				outputStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}

	public InfoService getInfoService() {
		return infoService;
	}

	public void setInfoService(InfoService infoService) {
		this.infoService = infoService;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public String getStrTitle() {
		return strTitle;
	}

	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}

	public PageResult getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult pageResult) {
		this.pageResult = pageResult;
	}

}
