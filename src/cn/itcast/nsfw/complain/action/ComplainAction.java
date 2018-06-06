package cn.itcast.nsfw.complain.action;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.ServletActionContext;

import cn.itcast.core.action.BaseAction;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.entity.ComplainReply;
import cn.itcast.nsfw.complain.service.ComplainService;

import com.opensymphony.xwork2.ActionContext;

public class ComplainAction extends BaseAction {

	@Resource
	public ComplainService complainService;
	private Complain complain;
	private ComplainReply reply;

	private String startTime;
	private String endTime;
	private String strState;
	private Map<String, Object> statisticMap;
	// 列表
	public String listUI() {

		// 加载集合
		ActionContext.getContext().getContextMap().put("complainStateMap",
				Complain.COMPLAIN_STATE);
		try {
			// 查询助手
			QueryHelper queryHelper = new QueryHelper(Complain.class, "c");

			if (StringUtils.isNotBlank(startTime)) {
				startTime = URLDecoder.decode(startTime, "utf-8");
				queryHelper.addCondition("c.compTime >= ?", DateUtils
						.parseDate(startTime + ":00", "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(endTime)) {
				endTime = URLDecoder.decode(endTime, "utf-8");
				queryHelper.addCondition("c.compTime <= ?", DateUtils
						.parseDate(endTime + ":59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (complain != null) {
				// state
				if (StringUtils.isNotBlank(complain.getState())) {
					complain.setState(URLDecoder.decode(complain.getState(),
							"UTF-8"));
					queryHelper.addCondition("c.state like ?", "%"
							+ complain.getState() + "%");
					System.out.println("状态：" + complain.getState());
				}
				// compTitle
				if (StringUtils.isNotBlank(complain.getCompTitle())) {
					complain.setCompTitle(URLDecoder.decode(complain
							.getCompTitle(), "UTF-8"));
					queryHelper.addCondition("c.compTitle like ?", "%"
							+ complain.getCompTitle() + "%");
					System.out.println("当前值是：" + complain);
				}
			}
			// 按照状态进行排序
			queryHelper.addOrderByProperty("c.state", queryHelper.ORDER_BY_ASC);
			// 根据时间进行排序
			queryHelper.addOrderByProperty("c.compTime",
					queryHelper.ORDER_BY_ASC);
			pageResult = complainService.getPageResult(queryHelper,
					getPageNo(), getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "listUI";
	}

	// 跳转到受理界面
	public String dealUI() {
		// 加载集合
		ActionContext.getContext().getContextMap().put("complainStateMap",
				Complain.COMPLAIN_STATE);
		if (complain != null) {
			complain = complainService.findObjectById(complain.getCompId());
		}
		return "dealUI";
	}

	// 受理
	@SuppressWarnings( { "static-access", "unchecked" })
	public String deal() {
		if (complain != null) {
			// 更新投诉信息为已受理
			Complain tmp = complainService.findObjectById(complain.getCompId());
			if (!complain.COMPLAIN_STATE_DONE.equals(tmp.getState())) {
				// 受理投诉
				tmp.setState(complain.COMPLAIN_STATE_DONE);
			}
			// 保存回复
			if (reply != null) {
				reply.setComplain(tmp);
				reply.setReplyTime(new Timestamp(new Date().getTime()));
				tmp.getComplainReplies().add(reply);
			}
			complainService.update(tmp);
		}
		return "list";
	}

	//跳转到统计页面
	public String  annualStatisticChartUI(){
		return "annualStatisticChartUI";
	}
	
	//根据年度获取该年度下的统计数
	public String getAnnualStatisticData(){
		//1、获取年份
		HttpServletRequest request = ServletActionContext.getRequest();
		int year = 0;
		if(request.getParameter("year") != null){
			year = Integer.valueOf(request.getParameter("year"));
		} else {
			//默认 当前年份
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		//2、获取统计年度的每个月的投诉数
		statisticMap = new HashMap<String, Object>();
		statisticMap.put("msg", "success");
		statisticMap.put("chartData", complainService.getAnnualStatisticDataByYear(year));
		return "annualStatisticData";
	}

	public Complain getComplain() {
		return complain;
	}

	public void setComplain(Complain complain) {
		this.complain = complain;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public ComplainReply getReply() {
		return reply;
	}

	public void setReply(ComplainReply reply) {
		this.reply = reply;
	}

	public Map<String, Object> getStatisticMap() {
		return statisticMap;
	}

	public void setStatisticMap(Map<String, Object> statisticMap) {
		this.statisticMap = statisticMap;
	}

}
