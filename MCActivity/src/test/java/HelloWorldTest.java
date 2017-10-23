import static org.junit.Assert.*;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class HelloWorldTest {

	@Test
	public void test() {
		// Create ProcessEngine
		ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
				.buildProcessEngine();

		// Deploy Process
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("diagrams/helloworld.bpmn").deploy();
		
		//Verify Deployed Process Info
		ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().singleResult();
		
		System.out.println(processDefinition.getKey());
		
		//启动流程，并返回流程实例		
		RuntimeService runtimeService=processEngine.getRuntimeService();
		ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("helloworld");
		
		assertNotNull(processInstance);		
		System.out.println("PID="+processInstance.getId() +", pdid="+processInstance.getProcessDefinitionId());			
		
	}

}
