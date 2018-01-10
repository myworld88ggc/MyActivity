package com.oa.will.mclistener.global;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

/**
 * Created by gao.guangcai on 2017-12-11.
 */

public class GlobalActEventListener implements ActivitiEventListener {


    @Override
    public void onEvent(ActivitiEvent event) {

        System.out.println("Event received: " + event.getType() + "\t " + event.getClass().toString());

    }

    @Override
    public boolean isFailOnException() {
// onEvent方法中的逻辑并不重要，日志失败异常可以被忽略……
        return false;
    }
}