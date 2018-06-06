package cn.itcast.test.service;

import java.io.Serializable;

import cn.itcast.test.entity.Person;

public interface TestService {

	//输出
	public void say();
	//保存
	public void save(Person person);
	//根据ID查询人员
	public Person findPerson(Serializable id);
}
