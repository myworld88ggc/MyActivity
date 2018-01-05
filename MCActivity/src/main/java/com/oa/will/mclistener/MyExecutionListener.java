package com.oa.will.mclistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.HashMap;

public class MyExecutionListener implements ExecutionListener {
	private String taskEntityId;
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.getClass().getSimpleName());
		System.out.println(execution.getEventName());
		HashMap<String, Object> listVariable= (HashMap<String, Object>) execution.getVariables();
		for (java.util.Map.Entry<String, Object> entry : listVariable.entrySet()) {
			System.out.println("Key:   " +entry.getKey()+"    Value:   "+entry.getValue());
		}

		//计算会签结果

		execution.getProcessInstanceId();

	}

	public String getTaskEntityId() {
		return taskEntityId;
	}

	public void setTaskEntityId(String taskEntityId) {
		this.taskEntityId = taskEntityId;
	}
}
