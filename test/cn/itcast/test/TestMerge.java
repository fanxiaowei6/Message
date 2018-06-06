package cn.itcast.test;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;


public class TestMerge {	
	
	ClassPathXmlApplicationContext	ctx;
	@Before
	public void LoadCtx(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	
	@Test
	public void testSpring(){
	TestService	ts=(TestService)ctx.getBean("testService");
	ts.say();
	}
	
	
	@Test
	public void testHibernate() {
		SessionFactory sf = (SessionFactory)ctx.getBean("sessionFactory");
		
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		//保存人员
		session.save(new Person("人员1"));
		transaction.commit();
		session.close();
	}
	
	@Test
	public void testServiceAndDao(){
	TestService	ts=(TestService)ctx.getBean("testService");
	ts.save(new Person("人员2"));
	}
	
	
	@Test
	public void testTransationReadOnly(){//只读事务，如果在只读事务中出现事务更新操作则回滚
	TestService	ts=(TestService)ctx.getBean("testService");
	System.out.println(ts.findPerson("402881ed54906d5d0154906d5e260000").getName());
	}
	//控制事务回滚
	@Test
	public void testTransationRollback(){
	TestService	ts=(TestService)ctx.getBean("testService");//回滚事务，如果在操作中出现异常则回滚。
	ts.save(new Person("事务回滚"));
	}
	
	
	
	
	
	
	
}
