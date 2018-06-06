package cn.itcast.nsfw.home.action;

import cn.itcast.core.action.BaseAction;

public class HomeAction extends BaseAction {
	
	//跳转到纳税服务系统首页
	public String frame(){
		
		return "frame";
	}
	//跳转至系统顶部
	public String top(){
		return "top";
	}
	
	//跳转至系统左侧菜单
	public String left(){
		return "left";
	}
	
}
