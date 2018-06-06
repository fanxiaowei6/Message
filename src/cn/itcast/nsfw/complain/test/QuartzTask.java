package cn.itcast.nsfw.complain.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuartzTask {

	public void doSimpleTriggerTask(){
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("时间"+sim.format(new Date()));
	}
	
	public void doCronTriggerTask(){
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("时间"+sim.format(new Date()));
	}
}
