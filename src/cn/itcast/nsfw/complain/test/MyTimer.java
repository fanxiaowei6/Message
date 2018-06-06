package cn.itcast.nsfw.complain.test;

import java.util.Timer;

public class MyTimer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.schedule(new MyTimeTask(), 1000,2000);
	}

}
