package kft.ch04;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class MyLeaveProcessTest {

	@Test
	public void test() {
//		fail("Not yet implemented");
		
		// Create ProcessEngine
				ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
						.buildProcessEngine();

				// Deploy Process
				RepositoryService repositoryService = processEngine.getRepositoryService();
				repositoryService.createDeployment().addClasspathResource("diagrams/kft/ch04/MyLeaveProcess.bpmn").deploy();
				
				//Verify Deployed Process Info
				ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().singleResult();
				
				System.out.println(processDefinition.getKey());
				
				//启动流程，并返回流程实例		
				RuntimeService runtimeService=processEngine.getRuntimeService();
				Map<String, Object> varibles=new HashMap<String,Object>();
				varibles.put("applyUser", "Andy");
				varibles.put("days", 3);				
				ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myLeaveProcess",varibles);
				System.out.println("PID="+processInstance.getId() +", pdid="+processInstance.getProcessDefinitionId());			
				
				//获取待办
				TaskService taskService=processEngine.getTaskService();
				Task task=taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();				
				System.out.println("节点名称:"+task.getName());
				
				//审批
				taskService.claim(task.getId(), "leaderUser");
				varibles=new HashMap<String, Object>();
				varibles.put("approval", true);
				taskService.complete(task.getId(),varibles);
				
				//历史记录
				HistoryService historyService=processEngine.getHistoryService();
				long count=historyService.createHistoricProcessInstanceQuery().finished().count();
				
				System.out.println("已完成流程数量:"+count);
	}

}
