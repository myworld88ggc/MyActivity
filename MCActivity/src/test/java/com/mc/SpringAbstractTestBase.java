package com.mc;

import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by gao.guangcai on 2017-11-23.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-test.xml", "classpath:activiti.cfg.spring.xml"})
public abstract class SpringAbstractTestBase {

//    @Test
//    public abstract void baseTest();


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
