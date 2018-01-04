package com.oa.will.mclistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskLisener implements TaskListener{

	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
//		System.out.println(this.getClass().getSimpleName());
//		System.out.println(delegateTask.getEventName());
	}

}
