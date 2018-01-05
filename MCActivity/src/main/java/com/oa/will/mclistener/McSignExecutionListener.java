package com.oa.will.mclistener;

import com.oa.will.oaconst.SignNodeSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import java.util.HashMap;

/**
 * 会签结果统计监听类
 */
public class McSignExecutionListener implements ExecutionListener {

     private Expression completeType;
    private Expression completeCondition;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println("========#############################========");
        //获取当前节点Id
        String currentTaskId = execution.getCurrentActivityId();
        //设定提前结束变量名称
        String completionVariableName = currentTaskId.concat(SignNodeSetting.COMPLETION_SUFFIX);
        System.out.println(currentTaskId);
        HashMap<String, Object> listVar = (HashMap<String, Object>) execution.getVariables();
        for (java.util.Map.Entry<String, Object> entry : listVar.entrySet()) {
            System.out.println("Key:   " + entry.getKey() + "    Value:   " + entry.getValue());
        }


        //获取变量
        String procInstId = execution.getProcessInstanceId();
        HashMap<String, Object> listVariable = (HashMap<String, Object>) execution.getVariables();

        //计算会签结果
        //获取循环计数器的声明与获取
        int nrOfActiveInstances = 0;//活动的实例数量
        int loopCounter = 0;//循环计数器
        int nrOfInstances = 0;//实例总数
        int nrOfCompletedInstances = 0;//已完成实例总数


        if (listVariable.get(SignNodeSetting.NR_OF_ACTIVE_INSTANCES) != null) {
            nrOfActiveInstances = Integer.parseInt(listVariable.get(SignNodeSetting.NR_OF_ACTIVE_INSTANCES).toString());
        }
        if (listVariable.get(SignNodeSetting.LOOP_COUNTER) != null) {
            loopCounter = Integer.parseInt(listVariable.get(SignNodeSetting.LOOP_COUNTER).toString());
        }
        if (listVariable.get(SignNodeSetting.NR_OF_INSTANCES) != null) {
            nrOfInstances = Integer.parseInt(listVariable.get(SignNodeSetting.NR_OF_INSTANCES).toString());
        }
        if (listVariable.get(SignNodeSetting.NR_OF_COMPLETED_INSTANCES) != null) {
            nrOfCompletedInstances = Integer.parseInt(listVariable.get(SignNodeSetting.NR_OF_COMPLETED_INSTANCES).toString());
        }

        //第一个人审批，初始化统计数据
        if (loopCounter == 0 && nrOfActiveInstances > 0) {
            //设置提前结束变量
            execution.setVariable(completionVariableName, false);


        } else if (nrOfActiveInstances > 0) {//还有剩余审批任务

        } else {//所有人都已经审批

        }


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
