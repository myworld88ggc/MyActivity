package com.mc.m01;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.mc.AbstractTest;


public class ACT_TEST_MultiTaskTest extends AbstractTest {

//	@Test
	public void deploy(){

		repositoryService.createDeployment().addClasspathResource("diagrams/201801/ACT_TEST_MultiTask.bpmn").deploy();		
		
	}
	
	@Test
//	@org.activiti.engine.test.Deployment(resources="diagrams/201801/ACT_TEST_MultiTask.bpmn")
	public void test() {

        Map<String, Object> startVariable = new HashMap<String, Object>();
        List<String>listMultUserId=new ArrayList<String>();
        listMultUserId.add("51034200");
        listMultUserId.add("51034600");
        startVariable.put("taskLeadersAudit", listMultUserId);
        String procInstId=  runtimeService.startProcessInstanceByKey("ACT_TEST_MultiTask", startVariable).getId();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        
        //第一个审批
        String leaderTaskID = tasks.get(0).getId();
        Map<String, Object> leaderVariables = new HashMap<String, Object>();
        leaderVariables.put("approvalResult", "1");
        taskService.complete(leaderTaskID,leaderVariables);


       //第二个审批
        List<Task> hrTasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : hrTasks) {
            showTaskInfo(task);
        }
     /*    String hrTaskID = hrTasks.get(0).getId();
        Map<String, Object> hrVariables = new HashMap<String, Object>();
        hrVariables.put("approvalResult", "1");
        taskService.complete(hrTaskID,hrVariables);*/
		
	}
	
	
	@Test
	@org.activiti.engine.test.Deployment(resources="diagrams/201801/ACT_TEST_MultiTask.bpmn")
	public void testThenDelete() {

        Map<String, Object> startVariable = new HashMap<String, Object>();
        List<String>listMultUserId=new ArrayList<String>();
        listMultUserId.add("51034200");
        listMultUserId.add("51034600");
        startVariable.put("taskLeadersAudit", listMultUserId);
        String procInstId=  runtimeService.startProcessInstanceByKey("ACT_TEST_MultiTask", startVariable).getId();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        
        //第一个审批
        String leaderTaskID = tasks.get(0).getId();
        Map<String, Object> leaderVariables = new HashMap<String, Object>();
        leaderVariables.put("approvalResult", "0");
        taskService.complete(leaderTaskID,leaderVariables);

        System.out.println("\n\n第1个审批完成\n\n");
       //第二个审批
        List<Task> hrTasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : hrTasks) {
            showTaskInfo(task);
        }

         String hrTaskID = hrTasks.get(0).getId();
        Map<String, Object> hrVariables = new HashMap<String, Object>();
        hrVariables.put("approvalResult", "1");
        taskService.complete(hrTaskID,hrVariables);
        System.out.println("\n\n第2个审批完成\n\n");
	}
	
	

}
