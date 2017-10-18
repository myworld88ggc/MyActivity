import static org.junit.Assert.*;
import java.util.List;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import sun.management.counter.Variability;

public class SayHelloLeaveTest {

	@Test
	public void test() {
		ProcessEngine _processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();

		// Get Repository Services
		RepositoryService _repositoryService = _processEngine.getRepositoryService();
		// 部署流程
		Deployment _deployment = _repositoryService.createDeployment()
				.addClasspathResource("diagrams/SayHelloLeave.bpmn").deploy();

		System.out.println("部署ID：" + _deployment.getId());// 部署ID:1
		System.out.println("部署时间：" + _deployment.getDeploymentTime());// 部署时间
		System.out.println("TenantId：" + _deployment.getTenantId());// 部署时间

		// System.out.println(_processEngine.getRepositoryService().createDeploymentQuery().count());
		System.out.println("***************启动请假流程******************");

		ProcessInstance pi = _processEngine.getRuntimeService().startProcessInstanceByKey("SayHelloLeaveProcess");

		System.out.println("***************Task数量******************");
		List<Task> listTask = _processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).list();
		System.out.println(listTask.size());
		

	}

	private void ShowTaskInfo(Task task) {
		System.out.println("#####################################");
		System.out.println("任务ID:" + task.getId());
		System.out.println("任务的办理人:" + task.getAssignee());
		System.out.println("任务名称:" + task.getName());
		System.out.println("任务的创建时间:" + task.getCreateTime());
		System.out.println("任务ID:" + task.getId());
		System.out.println("流程实例ID:" + task.getProcessInstanceId());
		System.out.println("#####################################");
	}
}
