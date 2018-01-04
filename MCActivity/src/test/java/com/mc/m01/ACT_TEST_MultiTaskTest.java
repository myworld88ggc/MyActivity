package com.mc.m01;

import static org.junit.Assert.*;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import com.mc.AbstractTest;


public class ACT_TEST_MultiTaskTest extends AbstractTest {

	@Test
	public void test() {

		// Deploy Process	
	Deployment deployment=	repositoryService.createDeployment().addClasspathResource("diagrams/201801/ACT_TEST_MultiTask.bpmn").deploy();
	System.out.println(deployment.getId());
		
	}

}
