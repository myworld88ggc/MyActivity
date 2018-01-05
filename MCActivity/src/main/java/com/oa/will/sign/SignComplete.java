package com.oa.will.sign;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.javafx.collections.MappingChange.Map;

import java.util.HashMap;

public class SignComplete {
	
	Logger logger= LoggerFactory.getLogger(this.getClass());

	public boolean isComplete(DelegateExecution execution) {  
	      
	    logger.debug("entert the SignComplete isComplete method...");  
	    //完成会签的次数  
	    Integer completeCounter=(Integer)execution.getVariable("nrOfCompletedInstances");  
	    //总循环次数  
	    Integer instanceOfNumbers=(Integer)execution.getVariable("nrOfInstances");  
	    HashMap<String, Object> listVariable= (HashMap<String, Object>) execution.getVariables();
		for (java.util.Map.Entry<String, Object> entry : listVariable.entrySet()) {
			System.out.println("Key:" +entry.getKey()+"Value: "+entry.getValue());
		}
	    
	    System.out.println("流程完成条件判断");
	    return false;
	}
}
