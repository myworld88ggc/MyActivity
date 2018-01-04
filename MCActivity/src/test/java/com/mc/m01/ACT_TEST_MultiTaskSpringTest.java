package com.mc.m01;

import org.activiti.engine.RepositoryService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mc.SpringAbstractTestBase;

public class ACT_TEST_MultiTaskSpringTest  extends SpringAbstractTestBase{
	
	@Autowired
	RepositoryService repositoryService;
	
	@Test
	public void baseTest() {
		// TODO Auto-generated method stub
		long count=repositoryService.createProcessDefinitionQuery().count();
		System.out.println(count);
	}

}
