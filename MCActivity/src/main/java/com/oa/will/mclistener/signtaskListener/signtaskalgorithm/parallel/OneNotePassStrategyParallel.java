package com.oa.will.mclistener.signtaskListener.signtaskalgorithm.parallel;

import com.oa.will.service.McSignTaskService;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

/**
 * 会签--->一票通过
 * Created by Will on 2018/01/06.
 */
public class OneNotePassStrategyParallel extends SignParallelResultCalBaseStrategy {


    @Override
    public void signResultCalculate(DelegateExecution execution, Expression completeCondition) {

        //获取审批结果
        String strApprovalResult = McSignTaskService.getSignTaskApprovalResultValue(execution);

//        //计算会签结果----获取循环计数器的声明与获取
//        //活动的实例数量
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
        }

        //判断当前会签节点是否通过
        boolean isCompletion = McSignTaskService.getCompletionVariableValue(execution);
        if (isCompletion) {
            return;
        }

        //会签没有结束
        if (nrOfInstances > nrOfCompletedInstances) {
            if (strApprovalResult.equalsIgnoreCase(OaBPMSetting.ApprovalResultSetting.OK)) {
                //设置通过变量为true
                McSignTaskService.setCompletionVariableValue(execution, true);
            } else {
                McSignTaskService.setCompletionVariableValue(execution, false);
                if (nrOfInstances==nrOfCompletedInstances+1){
                    McSignTaskService.deleteProcessInstance(execution);
                }
            }
        }


    }

}
