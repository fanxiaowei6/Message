package cn.itcast.test;




import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


public class TestLog {

	@Test
	public void test(){
	Log	log=LogFactory.getLog(getClass());
	try {
		int i=1/0;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		log.error(e.getMessage());
		e.printStackTrace();
	}
	log.debug("测试级别日志");
	log.info("info 级别日志");
	log.warn("警告级别日志");
	log.error("错误级别日志");
	log.fatal("致命级别日志");
	}
}
