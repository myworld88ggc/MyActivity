package com.oa.will.mclistener.signtaskListener.signtaskresultcalalgorithm;

import com.oa.will.mclistener.signtaskListener.McSignTaskService;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

/**
 * 会签---> 百分比通过
 * Created by Will on 2018/01/06.
 */
public class PercentPassIfNotRejectStrategy extends SignResultCalBaseStrategy {


    @Override
    public void signResultCalculate(DelegateExecution execution, Expression completeCondition) throws Exception {

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

        //判断通过百分比是否符合要求
        double completionConditionValue = 0d;
        try {
            completionConditionValue = Double.parseDouble(completeCondition.getValue(execution).toString());
            if (completionConditionValue < 0 || completionConditionValue > 100) {
                throw new Exception("完成条件，设置有误。 取值范围（0到100）之间");
            }
        } catch (Exception ex) {
            throw ex;
        }

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
                ++approvalOkAccount;
                McSignTaskService.setApprovalOKCountValue(execution, approvalOkAccount);
            } else {
                ++approvalRejectAccount;
                McSignTaskService.setApprovalRejectCountValue(execution, approvalRejectAccount);
            }

            //计算是否满足通过条件
            System.out.println("agree: " + (approvalOkAccount * 100.0 / nrOfInstances));
            System.out.println("reject:" + (approvalRejectAccount * 100.0 / nrOfInstances));
            System.out.println("max reject percent" + (100.0 - completionConditionValue));
            //通过
            if ((approvalOkAccount * 100.0 / nrOfInstances) >= completionConditionValue) {
                McSignTaskService.setCompletionVariableValue(execution, true);
            } else if ((approvalRejectAccount * 100.0 / nrOfInstances) > (100.0 - completionConditionValue)) {//不通过
                McSignTaskService.deleteProcessInstance(execution);
            }

        }
    }

}
