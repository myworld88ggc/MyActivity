package com.mc.m01.spring.signexecutionlistener.parallel;

import com.mc.SpringAbstractTestBase;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class OneNoteTurnBackTest extends SpringAbstractTestBase {


    @Test
    public void baseTest() {
        // TODO Auto-generated method stub
        // long count=repositoryService.createProcessDefinitionQuery().count();
        // System.out.println(count);
		repositoryService.createDeployment().addClasspathResource("diagrams/201801/parallel/ACT_TEST_OneNoteTurnBack.bpmn").deploy();
    }

    @Test
//    @org.activiti.engine.test.Deployment(resources = "diagrams/201801/ACT_TEST_OneNoteTurnBack.bpmn")
    //第一次驳回，第二次同意
    public void testOneNoteTurnBack_FirstTrunBack_SencodAgree() throws InterruptedException {

        Map<String, Object> startVariable = new HashMap<String, Object>();
        List<String> listMultUserId = new ArrayList<String>();
        listMultUserId.add("51034200");
        listMultUserId.add("51034600");
        startVariable.put("taskLeadersAudit", listMultUserId);
        startVariable.put("starter", "51034100");
        startVariable.put("ceo", "51034200");
        String procInstId = runtimeService.startProcessInstanceByKey("ACT_TEST_OneNoteTurnBack", startVariable).getId();
        System.out.println(procInstId);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }
        //模拟并发审批
        if (false) {
            asyncApprovalListTask(tasks,"3");
            return;
        }else {
        // 会签第一个审批
        System.out.println("会签审批");
        String leaderTaskID = tasks.get(0).getId();
        Map<String, Object> leaderVariables = new HashMap<String, Object>();
        leaderVariables.put("approvalResult", "3");
        taskService.complete(leaderTaskID, leaderVariables);

        System.out.println("\n\n第一个会签审批完成\n\n");
//        // 会签第二个审批
//        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
//        for (Task task : tasks) {
//            showTaskInfo(task);
//        }
//
//        if (tasks.size() > 0) {
//            String hrTaskID = tasks.get(0).getId();
//            Map<String, Object> hrVariables = new HashMap<String, Object>();
//            hrVariables.put("approvalResult", "1");
//            taskService.complete(hrTaskID, hrVariables);
//            System.out.println("\n\n第2个会签审批完成\n\n");
//        }
    }
        System.out.println("驳回到发起人审批");
       tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            String hrTaskID = tasks.get(0).getId();
            Map<String, Object> hrVariables = new HashMap<String, Object>();
            hrVariables.put("approvalResult", "1");
            taskService.complete(hrTaskID, hrVariables);
            System.out.println("\n\n发起人审批完成\n\n");
        }



        System.out.println("领导 会签审批");
        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            for(Task task :tasks) {
                String hrTaskID = task.getId();
                Map<String, Object> hrVariables = new HashMap<String, Object>();
                hrVariables.put("approvalResult", "1");
                taskService.complete(hrTaskID, hrVariables);
                System.out.println("\n\n领导审批完成\n\n");
            }
        }


        System.out.println("CEO审批");
        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            String hrTaskID = tasks.get(0).getId();
            Map<String, Object> hrVariables = new HashMap<String, Object>();
            hrVariables.put("approvalResult", "1");
            taskService.complete(hrTaskID, hrVariables);
            System.out.println("\n\nCEO审批完成\n\n");
        }

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        System.out.println(processInstance.getDeleteReason());
    }



    @Test
//    @org.activiti.engine.test.Deployment(resources = "diagrams/201801/ACT_TEST_OneNoteTurnBack.bpmn")
    //第一次驳回，第二次同意
    public void testOneNoteTurnBack_FirstAgree() throws InterruptedException {

        Map<String, Object> startVariable = new HashMap<String, Object>();
        List<String> listMultUserId = new ArrayList<String>();
        listMultUserId.add("51034200");
        listMultUserId.add("51034600");
        startVariable.put("taskLeadersAudit", listMultUserId);
        startVariable.put("starter", "51034100");
        startVariable.put("ceo", "51034200");
        String procInstId = runtimeService.startProcessInstanceByKey("ACT_TEST_OneNoteTurnBack", startVariable).getId();
        System.out.println(procInstId);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }
        //模拟并发审批
        if (true) {
            asyncApprovalListTask(tasks,"3");
            return;
        }else {
            // 会签第一个审批
            System.out.println("会签审批");
            String leaderTaskID = tasks.get(0).getId();
            Map<String, Object> leaderVariables = new HashMap<String, Object>();
            leaderVariables.put("approvalResult", "1");
            taskService.complete(leaderTaskID, leaderVariables);

            System.out.println("\n\n第一个会签审批完成\n\n");
//        // 会签第二个审批
            tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
            for (Task task : tasks) {
                showTaskInfo(task);
            }

            if (tasks.size() > 0) {
                String hrTaskID = tasks.get(0).getId();
                Map<String, Object> hrVariables = new HashMap<String, Object>();
                hrVariables.put("approvalResult", "3");
                taskService.complete(hrTaskID, hrVariables);
                System.out.println("\n\n第2个会签审批完成\n\n");
            }
        }
        System.out.println("驳回到发起人审批");
        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            String hrTaskID = tasks.get(0).getId();
            Map<String, Object> hrVariables = new HashMap<String, Object>();
            hrVariables.put("approvalResult", "1");
            taskService.complete(hrTaskID, hrVariables);
            System.out.println("\n\n发起人审批完成\n\n");
        }



        System.out.println("领导 会签审批");
        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            for(Task task :tasks) {
                String hrTaskID = task.getId();
                Map<String, Object> hrVariables = new HashMap<String, Object>();
                hrVariables.put("approvalResult", "1");
                taskService.complete(hrTaskID, hrVariables);
            }
            System.out.println("\n\n领导审批完成\n\n");
        }


        System.out.println("CEO审批");
        tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }

        if (tasks.size() > 0) {
            String hrTaskID = tasks.get(0).getId();
            Map<String, Object> hrVariables = new HashMap<String, Object>();
            hrVariables.put("approvalResult", "1");
            taskService.complete(hrTaskID, hrVariables);
            System.out.println("\n\nCEO审批完成\n\n");
        }

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        System.out.println(processInstance.getDeleteReason());
    }


}
