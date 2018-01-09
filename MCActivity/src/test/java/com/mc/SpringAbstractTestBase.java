package com.mc;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by gao.guangcai on 2017-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-test.xml", "classpath:activiti.cfg.spring.xml" })
public abstract class SpringAbstractTestBase {

	@Autowired
	protected ProcessEngine processEngine;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected TaskService taskService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected IdentityService identityService;
	@Autowired
	protected ManagementService managementService;
	@Autowired
	protected FormService formService;

	public void showTaskInfo(Task task) {
		System.out.println("#####################################");
		// task.setAssignee("Kermit");
		System.out.println("任务ID:" + task.getId());
		System.out.println("任务的办理人:" + task.getAssignee());
		System.out.println("任务名称:" + task.getName());
		System.out.println("任务的创建时间:" + task.getCreateTime());
		System.out.println("任务ID:" + task.getId());
		System.out.println("流程实例ID:" + task.getProcessInstanceId());
		System.out.println("#####################################");
	}


	public void asyncApprovalListTask(List<Task> tasks, String approvalResult) throws InterruptedException {
		int thread_num = tasks.size();

		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semp = new Semaphore(thread_num);
		for (int index = 0; index < thread_num; index++) {
			final int NO = index;
			final String taskId = tasks.get(index).getId();
			Runnable run = new Runnable() {
				public void run() {
					try {
						semp.acquire();
						//HttpClientTest.postLogin();
						System.out.println("Thread:" + NO);

						Map<String, Object> leaderVariables = new HashMap<String, Object>();
						leaderVariables.put("approvalResult", approvalResult);
						taskService.complete(taskId, leaderVariables);

						semp.release();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			exec.execute(run);
		}
		Thread.sleep(5 * 1000L);
	}
}
