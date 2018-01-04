package com.oa.will.mclistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class MyExecutionListener implements ExecutionListener {

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getSimpleName());
		System.out.println(execution.getEventName());
	
	}

}
