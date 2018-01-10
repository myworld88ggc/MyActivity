package com.oa.will.service;

import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Will on 2018/01/06.
 */
@Service
public class McSignTaskServiceByHashMap {

    /**
     * 获取审批结果
     * @param variableHash
     * @return
     */
    public static String getSignTaskApprovalResultValue(HashMap<String, Object> variableHash) {
        //获取审批结果
        Object approvalResult = variableHash.get(OaBPMSetting.SignNodeSetting.TASK_APPROVAL_RESULT);
        String strApprovalResult = approvalResult != null ? String.valueOf(approvalResult.toString()) : "";
        return strApprovalResult;
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
     * 获取通过变量值
     *
     * @param variableHash
     */
    public static boolean getCompletionVariableValue(HashMap<String, Object> variableHash,String completionVariableName) {
        Object objResult =  variableHash.get(completionVariableName);
        if (objResult == null) {
            return false;
        } else {
            return Boolean.parseBoolean(objResult.toString());
        }
    }

    /**
     * 设置通过变量值为True
     *
     * @param execution
     */
    public static void setCompletionVariableValue(DelegateExecution execution, boolean IsCompletion) {
        execution.setVariable(getCompletionVariableName(execution), IsCompletion);
    }

    /**
     * 会签---当前有效实例个数
     *
     * @param variableHash
     * @return
     */
    public static int getNrOfActiveInstances(HashMap<String, Object> variableHash) {
        int result = 0;
        Object objVariable =  variableHash.get(OaBPMSetting.SignNodeSetting.NR_OF_ACTIVE_INSTANCES);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---循环计数
     *
     * @param variableHash
     * @return
     */
    public static int getLoopCounter(HashMap<String, Object> variableHash) {
        int result = 0;
        Object objVariable =  variableHash.get(OaBPMSetting.SignNodeSetting.LOOP_COUNTER);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---循环计数是否为null
     *
     * @param variableHash
     * @return
     */
    public static boolean isNullLoopCounter(HashMap<String, Object> variableHash) {
        Object objVariable =  variableHash.get(OaBPMSetting.SignNodeSetting.LOOP_COUNTER);
        return objVariable == null;
    }

    /**
     * 会签---实例总数
     *
     * @param variableHash
     * @return
     */
    public static int getNrOfInstances(HashMap<String, Object> variableHash) {
        int result = 0;
        Object objVariable =  variableHash.get(OaBPMSetting.SignNodeSetting.NR_OF_INSTANCES);
        if (objVariable != null) {
            result = Integer.parseInt(objVariable.toString());
        }
        return result;
    }

    /**
     * 会签---已完成实例数
     *
     * @param variableHash
     * @return
     */
    public static int getNrOfCompletedInstances(HashMap<String, Object> variableHash) {
        int result = 0;
        Object objVariable =  variableHash.get(OaBPMSetting.SignNodeSetting.NR_OF_COMPLETED_INSTANCES);
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
