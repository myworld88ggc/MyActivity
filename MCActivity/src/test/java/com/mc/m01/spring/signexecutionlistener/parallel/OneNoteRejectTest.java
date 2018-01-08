package com.mc.m01.spring.signexecutionlistener.parallel;

import com.mc.SpringAbstractTestBase;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class OneNoteRejectTest extends SpringAbstractTestBase {


    @Test
    public void baseTest() {
        // TODO Auto-generated method stub
        // long count=repositoryService.createProcessDefinitionQuery().count();
        // System.out.println(count);
		repositoryService.createDeployment().addClasspathResource("diagrams/201801/parallel/ACT_TEST_OneNoteReject.bpmn").deploy();
    }

    @Test
//    @org.activiti.engine.test.Deployment(resources = "diagrams/201801/ACT_TEST_OneNoteReject.bpmn")
    public void testOneNoteReject() throws InterruptedException {

        Map<String, Object> startVariable = new HashMap<String, Object>();
        List<String> listMultUserId = new ArrayList<String>();
        listMultUserId.add("51034200");
        listMultUserId.add("51034600");
        startVariable.put("taskLeadersAudit", listMultUserId);
        startVariable.put("ceo", "51034200");
        String procInstId = runtimeService.startProcessInstanceByKey("ACT_TEST_OneNoteReject", startVariable).getId();
        System.out.println(procInstId);
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : tasks) {
            showTaskInfo(task);
        }


        //模拟并发审批
        if (false) {
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
                            leaderVariables.put("approvalResult", "2");
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
            // exec.shutdown();
            return;
        }else {
            // 第一个审批
            String leaderTaskID = tasks.get(0).getId();
            Map<String, Object> leaderVariables = new HashMap<String, Object>();
            leaderVariables.put("approvalResult", "1");
            taskService.complete(leaderTaskID, leaderVariables);

            System.out.println("\n\n第一个审批完成\n\n");
            // 第二个审批
            List<Task> hrTasks = taskService.createTaskQuery().processInstanceId(procInstId).list();
            for (Task task : hrTasks) {
                showTaskInfo(task);
            }

            if (hrTasks.size() > 0) {
                String hrTaskID = hrTasks.get(0).getId();
                Map<String, Object> hrVariables = new HashMap<String, Object>();
                hrVariables.put("approvalResult", "2");
                taskService.complete(hrTaskID, hrVariables);
                System.out.println("\n\n第2个审批完成\n\n");
            }
        }

        // 第3个审批
        List<Task> ceoTask = taskService.createTaskQuery().processInstanceId(procInstId).list();
        for (Task task : ceoTask) {
            showTaskInfo(task);
        }

        if (ceoTask.size() > 0) {
            String hrTaskID = ceoTask.get(0).getId();
            Map<String, Object> hrVariables = new HashMap<String, Object>();
            hrVariables.put("approvalResult", "1");
            taskService.complete(hrTaskID, hrVariables);
            System.out.println("\n\n第3个审批完成\n\n");
        }

        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        System.out.println(processInstance.getDeleteReason());
    }


}
