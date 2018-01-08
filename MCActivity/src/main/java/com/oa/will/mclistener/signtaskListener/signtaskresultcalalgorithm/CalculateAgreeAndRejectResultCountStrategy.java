package com.oa.will.mclistener.signtaskListener.signtaskresultcalalgorithm;

import com.oa.will.mclistener.signtaskListener.McSignTaskService;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

/**
 * 会签--->计算 同意和拒绝 数量
 * Created by Will on 2018/01/06.
 */
public class CalculateAgreeAndRejectResultCountStrategy extends SignResultCalBaseStrategy {


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
        if (nrOfCompletedInstances == 0) {
            McSignTaskService.setCompletionVariableValue(execution, false);
            McSignTaskService.setApprovalOKCountValue(execution, 0);
            McSignTaskService.setApprovalRejectCountValue(execution, 0);
        }


        int approvalOkAccount = McSignTaskService.getApprovalOKCount(execution);
        int approvalRejectAccount = McSignTaskService.getApprovalRejectCount(execution);
        //会签没有结束
        if (nrOfInstances > nrOfCompletedInstances) {
            //会签结果累加
            if (strApprovalResult.equalsIgnoreCase(OaBPMSetting.ApprovalResultSetting.OK)) {
                McSignTaskService.setApprovalOKCountValue(execution, ++approvalOkAccount);
            } else {
                McSignTaskService.setApprovalRejectCountValue(execution, ++approvalRejectAccount);
            }
        }

    }
}
