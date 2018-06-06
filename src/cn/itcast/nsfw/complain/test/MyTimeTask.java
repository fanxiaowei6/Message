package cn.itcast.nsfw.complain.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MyTimeTask extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("时间："+sim.format(new Date()));
	}

}
