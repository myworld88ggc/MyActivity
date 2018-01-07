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
        double completionConditionValue = 0d;
        try {
            completionConditionValue = Double.parseDouble(completeCondition.getValue(execution).toString());
            if (completionConditionValue < 0 || completionConditionValue > 100) {
                throw new Exception("完成条件，设置有误。 取值范围（0到100）之间");
            }
        } catch (Exception ex) {
            throw ex;
        }

        //计算会签结果----获取循环计数器的声明与获取
        //活动的实例数量
        int nrOfActiveInstances = McSignTaskService.getNrOfActiveInstances(execution);
        //循环计数器
        int loopCounter = McSignTaskService.getLoopCounter(execution);
        boolean isNullLoopCounter = McSignTaskService.isNullLoopCounter(execution);
        //实例总数
        int nrOfInstances = McSignTaskService.getNrOfInstances(execution);
        //已完成实例总数
        int nrOfCompletedInstances = McSignTaskService.getNrOfCompletedInstances(execution);

        int approvalOkAccount = 0;
        int approvalRejectAccount = 0;
        //会签第一人审批
        if (loopCounter == 0 && nrOfActiveInstances > 0 && !isNullLoopCounter) {
            approvalOkAccount = 0;
            approvalRejectAccount = 0;
            //设置通过变量为false
            McSignTaskService.setCompletionVariableValue(execution, false);
            McSignTaskService.setApprovalOKCountValue(execution, approvalOkAccount);
            McSignTaskService.setApprovalRejectCountValue(execution, approvalRejectAccount);
        } else {
            approvalOkAccount = McSignTaskService.getApprovalOKCount(execution);
            approvalRejectAccount = McSignTaskService.getApprovalRejectCount(execution);
        }

        //会签没有结束
        if (nrOfInstances > nrOfCompletedInstances) {
            //会签结果累加
            if (strApprovalResult.equalsIgnoreCase(OaBPMSetting.ApprovalResultSetting.OK)) {
                ++approvalOkAccount;
            } else {
                ++approvalRejectAccount;
            }

            //计算是否满足通过条件
            //通过
            if ((approvalOkAccount * 100 / nrOfInstances) >= completionConditionValue) {
                McSignTaskService.setCompletionVariableValue(execution, true);
                McSignTaskService.setApprovalOKCountValue(execution, approvalOkAccount);
                McSignTaskService.setApprovalRejectCountValue(execution, approvalRejectAccount);
            } else if ((approvalRejectAccount * 100 / nrOfInstances) > (100 - completionConditionValue)) {//不通过
                McSignTaskService.deleteProcessInstance(execution);
            } else//继续会签
            {
                McSignTaskService.setApprovalOKCountValue(execution, approvalOkAccount);
                McSignTaskService.setApprovalRejectCountValue(execution, approvalRejectAccount);
            }

        }
    }

}
