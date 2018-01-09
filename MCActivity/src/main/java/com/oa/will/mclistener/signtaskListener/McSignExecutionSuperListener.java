package com.oa.will.mclistener.signtaskListener;

import com.oa.will.service.McSignTaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import java.util.HashMap;

/**
 * 会签结果统计监听类
 */
public class McSignExecutionSuperListener implements ExecutionListener {

    private Expression completeType;
    private Expression completeCondition;



    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("========        会签执行结果Super监听器   ========");

        //若是删除触发的End事件则返回
        if (execution instanceof  ExecutionEntity){
            ExecutionEntity executionEntity=(ExecutionEntity)execution;
            if (executionEntity.isDeleteRoot()){
                return;
            }
        }
        //如果是最后一次SIGNTask最后一次触发，则直接返回
        boolean isNullLoopCounter = McSignTaskService.isNullLoopCounter(execution);
        if (isNullLoopCounter) {
            return;
        }

        //若流程删除则返回
        ExecutionEntity processInstance = (ExecutionEntity) execution.getEngineServices().getRuntimeService().createProcessInstanceQuery().processInstanceId(execution.getProcessInstanceId()).singleResult();
        if (processInstance.isDeleteRoot()) {
            return;
        }

        //输出所有流程变量，方便分析，会签数据
        HashMap<String, Object> listVar = (HashMap<String, Object>) execution.getVariables();
        for (java.util.Map.Entry<String, Object> entry : listVar.entrySet()) {
            System.out.println("Key:   " + entry.getKey() + "    Value:   " + entry.getValue());
        }

        //根据不同的会签策略，调用不同的会签算法
        SignResultCalContext signResultCalContext=new SignResultCalContext(completeType.getValue(execution).toString(), execution,completeCondition);
        signResultCalContext.calSignResult();
    }


    public Expression getCompleteType() {
        return completeType;
    }

    public void setCompleteType(Expression completeType) {
        this.completeType = completeType;
    }

    public Expression getCompleteCondition() {
        return completeCondition;
    }

    public void setCompleteCondition(Expression completeCondition) {
        this.completeCondition = completeCondition;
    }
}
