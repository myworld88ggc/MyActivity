package com.oa.will.mclistener.signtaskListener.signtaskresultcalalgorithm;

import com.oa.will.mclistener.signtaskListener.McSignTaskService;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import java.util.HashMap;

/**
 * 会签--->一票否决
 * Created by Will on 2018/01/06.
 */
public class OneNoteRejectStrategy extends SignResultCalBaseStrategy {


    @Override
    public void signResultCalculate(DelegateExecution execution, Expression completeCondition) {

        //获取审批结果
        String strApprovalResult = McSignTaskService.getSignTaskApprovalResultValue(execution);

//        //计算会签结果----获取循环计数器的声明与获取
//        //活动的实例数量
//        int nrOfActiveInstances = McSignTaskService.getNrOfActiveInstances(execution);
//        //循环计数器
//        int loopCounter = McSignTaskService.getLoopCounter(execution);
        boolean isNullLoopCounter = McSignTaskService.isNullLoopCounter(execution);
        if (isNullLoopCounter) {
            return;
        }
        //实例总数
        int nrOfInstances = McSignTaskService.getNrOfInstances(execution);
        //已完成实例总数
        int nrOfCompletedInstances = McSignTaskService.getNrOfCompletedInstances(execution);

        //第一个审批，初始化节点变量
        if (nrOfCompletedInstances == 0) {
            McSignTaskService.setCompletionVariableValue(execution, false);
        }
        //判断当前会签节点是否通过
        boolean isCompletion = McSignTaskService.getCompletionVariableValue(execution);
        if (isCompletion) {
            return;
        }

        //会签没有结束
        if (nrOfInstances > nrOfCompletedInstances) {
            if (strApprovalResult.equals(OaBPMSetting.ApprovalResultSetting.REJECT)) {
                McSignTaskService.deleteProcessInstance(execution);
            } else {
                McSignTaskService.setCompletionVariableValue(execution, false);
            }
        }
    }
}
