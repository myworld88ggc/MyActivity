package com.oa.will.mclistener.signtaskListener.signtaskalgorithm.sequence;

import com.oa.will.oaconst.OaBPMSetting;
import com.oa.will.service.McSignTaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;

/**
 * Created by gao.guangcai on 2018-01-10.
 */
public class OneNoteTurnBackStrategy extends  SignSequenceResultCalBaseStrategy {

    @Override
    public void signResultCalculate(DelegateTask delegateTask, Expression completeCondition) throws Exception {

        DelegateExecution execution=delegateTask.getExecution();

        //获取审批结果
        String strApprovalResult = McSignTaskService.getSignTaskApprovalResultValue(execution);

        //计算会签结果----获取循环计数器的声明与获取
        //活动的实例数量
//        int nrOfActiveInstances = McSignTaskService.getNrOfActiveInstances(execution);
//        //循环计数器
//        int loopCounter = McSignTaskService.getLoopCounter(execution);

        //实例总数
        int nrOfInstances = McSignTaskService.getNrOfInstances(execution);
        //已完成实例总数
        int nrOfCompletedInstances = McSignTaskService.getNrOfCompletedInstances(execution);

        //第一个审批，初始化节点变量
        if (nrOfCompletedInstances==0){
            McSignTaskService.setCompletionVariableValue(execution,false);
            McSignTaskService.setSignResultIsTurnBackVariableValue(execution,false);
        }
        //判断当前会签节点是否通过
        boolean isCompletion = McSignTaskService.getCompletionVariableValue(execution);
//        boolean isTurnBack=McSignTaskService.getSignResultIsTurnBackVariableValue(execution);
        if (isCompletion) {
            return;
        }

        //会签没有结束
        if (nrOfInstances > nrOfCompletedInstances) {
            if (strApprovalResult.equals(OaBPMSetting.ApprovalResultSetting.TRUNBACK)) {
                //设置通过变量为true
                McSignTaskService.setCompletionVariableValue(execution, true);
                McSignTaskService.setSignResultIsTurnBackVariableValue(execution,true);
            } else {
                McSignTaskService.setCompletionVariableValue(execution, false);
            }
        }
    }
}
