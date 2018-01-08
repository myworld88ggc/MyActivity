package com.oa.will.mclistener.signtaskListener;

import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Will on 2018/01/06.
 */
@Service
public class McSignTaskService {


    public static String getSignTaskApprovalResultValue(DelegateExecution execution) {
        Object objResult = execution.getVariable(OaBPMSetting.SignNodeSetting.TASK_APPROVAL_RESULT);
        if (objResult == null) {
            return "";
        } else {
            return objResult.toString();
        }
    }

    /**
     * 获取会签节点完成变量名称
     *
     * @param execution
     * @return
     */
    public static String getCompletionVariableName(DelegateExecution execution) {
        //获取当前节点Id
        String currentTaskId = execution.getCurrentActivityId();
        //设定提前结束变量名称
        String completionVariableName = currentTaskId.concat(OaBPMSetting.SignNodeSetting.COMPLETION_SUFFIX);
        return completionVariableName;
    }

    /**
     * 获取会签节点同意总数变量名称
     *
     * @param execution
     * @return
     */
    public static String getApprovalOkCountVariableName(DelegateExecution execution) {
        //获取当前节点Id
        String currentTaskId = execution.getCurrentActivityId();
        //设定同意总数变量名称
        String okAccount = currentTaskId.concat(OaBPMSetting.SignNodeSetting.SIGN_OK_ACCOUNT_SUFFIX);
        return okAccount;
    }
    /**
     * 获取会签节点不同意总数变量名称
     *
     * @param execution
     * @return
     */
    public static String getApprovalRejectCountVariableName(DelegateExecution execution) {
        //获取当前节点Id
        String currentTaskId = execution.getCurrentActivityId();
        //拒绝总数变量名称
        String rejectAccount = currentTaskId.concat(OaBPMSetting.SignNodeSetting.SIGN_REJECT_ACCOUNT_SUFFIX);
        return rejectAccount;
    }

    /**
     * 获取通过变量值
     *
     * @param execution
     */
    public static boolean getCompletionVariableValue(DelegateExecution execution) {
        Object objResult = execution.getVariable(getCompletionVariableName(execution));
        if (objResult == null) {
//            setCompletionVariableValue(execution,false);
            return false;
        } else {
            return Boolean.parseBoolean(objResult.toString());
        }
    }

    /**
     * 设置通过变量值
     *
     * @param execution
     */
    public static void setCompletionVariableValue(DelegateExecution execution, boolean IsCompletion) {
        execution.setVariable(getCompletionVariableName(execution), IsCompletion);
    }

    /**
     * 获取通过总数变量值
     *
     * @param execution
     */
    public  static int getApprovalOKCount(DelegateExecution execution) {
        Object objResult = execution.getVariable(getApprovalOkCountVariableName(execution));
        if (objResult == null) {
//            setApprovalOKCountValue(execution,0);
            return 0;
        } else {
            return Integer.parseInt(objResult.toString());
        }
    }

    /**
     * 设置通过变量总数值
     * @param execution
     * @param approvalOkAccount
     */
    public static void setApprovalOKCountValue(DelegateExecution execution, int approvalOkAccount) {
        execution.setVariable(getApprovalOkCountVariableName(execution), approvalOkAccount);
    }

    /**
     * 会签结果 驳回 变量值
     * @param execution
     * @return
     */
    public static String getSignResultIsTurnBackVariableName(DelegateExecution execution) {
        //获取当前节点Id
        String currentTaskId = execution.getCurrentActivityId();
        //拒绝总数变量名称
        String rejectAccount = currentTaskId.concat(OaBPMSetting.SignNodeSetting.SIGN_RESULT_TURNBACK_SUFFIX);
        return rejectAccount;
    }

    /**
     * 获取签结果驳回
     *
     * @param execution
     */
    public static boolean getSignResultIsTurnBackVariableValue(DelegateExecution execution) {
        Object objResult = execution.getVariable(getSignResultIsTurnBackVariableName(execution));
        if (objResult == null) {
//            execution.setVariable(getSignResultIsTurnBackVariableName(execution), false);
            return false;
        } else {
            return Boolean.parseBoolean(objResult.toString());
        }
    }

    /**
     * 设置会签结果驳回变量值
     * @param execution
     * @param isTurnback
     */
    public static void setSignResultIsTurnBackVariableValue(DelegateExecution execution, boolean isTurnback) {
        execution.setVariable(getSignResultIsTurnBackVariableName(execution), isTurnback);
    }

    /**
     * 获取拒绝总数变量值
     *
     * @param execution
     */
    public static int getApprovalRejectCount(DelegateExecution execution) {
        Object objResult = execution.getVariable(getApprovalRejectCountVariableName(execution));
        if (objResult == null) {
//            setApprovalRejectCountValue(execution,0);
            return 0;
        } else {
            return Integer.parseInt(objResult.toString());
        }
    }

    /**
     * 设置拒绝总数变量值
     * @param execution
     * @param approvalRejectAccount
     */
    public static void setApprovalRejectCountValue(DelegateExecution execution, int approvalRejectAccount) {
        execution.setVariable(getApprovalRejectCountVariableName(execution), approvalRejectAccount);
    }

    /**
     * 会签---当前有效实例个数
     *
     * @param execution
     * @return
     */
    public static int getNrOfActiveInstances(DelegateExecution execution) {
        int result = 0;
        Object objVariable = execution.getVariable(OaBPMSetting.SignNodeSetting.NR_OF_ACTIVE_INSTANCES);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---循环计数
     *
     * @param execution
     * @return
     */
    public static int getLoopCounter(DelegateExecution execution) {
        int result = 0;
        Object objVariable = execution.getVariable(OaBPMSetting.SignNodeSetting.LOOP_COUNTER);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---循环计数是否为null
     *
     * @param execution
     * @return
     */
    public static boolean isNullLoopCounter(DelegateExecution execution) {
        Object objVariable = execution.getVariable(OaBPMSetting.SignNodeSetting.LOOP_COUNTER);
        return objVariable == null;
    }

    /**
     * 会签---实例总数
     *
     * @param execution
     * @return
     */
    public static int getNrOfInstances(DelegateExecution execution) {
        int result = 0;
        Object objVariable = execution.getVariable(OaBPMSetting.SignNodeSetting.NR_OF_INSTANCES);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---已完成实例数
     *
     * @param execution
     * @return
     */
    public static int getNrOfCompletedInstances(DelegateExecution execution) {
        int result = 0;
        Object objVariable = execution.getVariable(OaBPMSetting.SignNodeSetting.NR_OF_COMPLETED_INSTANCES);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 删除流程实例
     * @param execution
     */
    public static void deleteProcessInstance(DelegateExecution execution) {
        RuntimeService runtimeService = execution.getEngineServices().getRuntimeService();
        runtimeService.deleteProcessInstance(execution.getProcessInstanceId(), "会签拒绝");
    }
}
