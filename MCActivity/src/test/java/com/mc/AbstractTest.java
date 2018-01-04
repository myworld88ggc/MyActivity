package com.mc;

import org.activiti.engine.*;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;

/**
 * 抽象测试基类
 *
 * @author henryyan
 */
public abstract class AbstractTest {

    /**
     * 专门用于测试套件
     */
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");

    protected ProcessEngine processEngine;
    protected RepositoryService repositoryService;
    protected RuntimeService runtimeService;
    protected TaskService taskService;
    protected HistoryService historyService;
    protected IdentityService identityService;
    protected ManagementService managementService;
    protected FormService formService;

    /**
     * 开始测试
     */
    @BeforeClass
    public static void setUpForClass() throws Exception {
        System.out.println("++++++++ 开始测试 ++++++++");
    }

    /**
     * 结束测试
     */
    @AfterClass
    public static void testOverForClass() throws Exception {
        System.out.println("-------- 结束测试 --------");
    }

    /**
     * 初始化变量
     */
    @Before
    public void setUp() throws Exception {
        processEngine = activitiRule.getProcessEngine();
        repositoryService = activitiRule.getRepositoryService();
        runtimeService = activitiRule.getRuntimeService();
        taskService = activitiRule.getTaskService();
        historyService = activitiRule.getHistoryService();
        identityService = activitiRule.getIdentityService();
        managementService = activitiRule.getManagementService();
        formService = activitiRule.getFormService();
    }


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
}
