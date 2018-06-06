package cn.itcast.nsfw.complain.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sun.security.jca.GetInstance;

import cn.itcast.core.service.impl.BaseServiceImpl;
import cn.itcast.core.util.QueryHelper;
import cn.itcast.nsfw.complain.dao.ComplainDao;
import cn.itcast.nsfw.complain.entity.Complain;
import cn.itcast.nsfw.complain.service.ComplainService;

@Service("complainService")
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements
		ComplainService {

	private ComplainDao complainDao;

	@Resource
	public void setComplainDao(ComplainDao complainDao) {
		super.setBaseDao(complainDao);
		this.complainDao = complainDao;
	}

	public ComplainDao getComplainDao() {
		return complainDao;
	}

	// 自动受理投诉
	@Override
	public void autoDeal() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// 利用查询助手查询上个月未受理的数据
		QueryHelper queryHelper = new QueryHelper(Complain.class, "c");
		queryHelper.addCondition("c.state like ?",
				Complain.COMPLAIN_STATE_UNDONE);
		// 查询本月之前所有未受理的投诉
		queryHelper.addCondition("c.compTime < ?", cal.getTime());
		List<Complain> list = findObjects(queryHelper);
		if (list != null && list.size() > 0) {
			for (Complain comp : list) {
				// 设置状态已失效
				comp.setState(Complain.COMPLAIN_STATE_INVALID);
				update(comp);
			}
		}
	}

	@Override
	public List<Map> getAnnualStatisticDataByYear(int year) {
		List<Map> resList = new ArrayList<Map>();
		//1、获取统计数据
		List<Object[]> list = complainDao.getAnnualStatisticDataByYear(year);
		if(list != null && list.size()>0){
			Calendar cal = Calendar.getInstance();
			//是否当前年份
			boolean isCurYear = (cal.get(Calendar.YEAR) == year);
			int curMonth = cal.get(Calendar.MONTH)+1;//当前月份
			//2、格式化统计结果
			int temMonth = 0;
			Map<String, Object> map = null;
			for(Object[] obj: list){
				temMonth = Integer.valueOf((obj[0])+"");
				map = new HashMap<String, Object>();
				map.put("label", temMonth+ " 月");
				if(isCurYear){//当前年份
					//当前年份：如果月份已过的则直接取投诉数并且值为空或null时则设为0；如果月份未过的则全部投诉数置空
					if(temMonth > curMonth){//未到月份，则投诉数为空
						map.put("value", "");
					} else {//已过月份
						map.put("value", obj[1]==null?"0":obj[1]);
					}
				} else {//非当前年份则直接取投诉数并且值为空或null时则设为0
					map.put("value", obj[1]==null?"0":obj[1]);
				}
				resList.add(map);
			}
		}
		
		return resList;
	}
}
