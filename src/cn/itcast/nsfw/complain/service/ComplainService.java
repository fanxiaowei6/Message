package cn.itcast.nsfw.complain.service;

import java.util.List;
import java.util.Map;

import cn.itcast.core.service.BaseService;
import cn.itcast.nsfw.complain.entity.Complain;

public interface ComplainService extends BaseService<Complain> {
	
	//自动受理投诉
	public void autoDeal();

	public List<Map> getAnnualStatisticDataByYear(int year);
}
