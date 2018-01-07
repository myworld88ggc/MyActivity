package com.oa.will.mclistener.signtaskListener.signtaskresultcalalgorithm;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

/**
 * 会签结果计算抽象类
 * Created by Will on 2018/01/06.
 */
public abstract class SignResultCalBaseStrategy {


    /**
     * 会签结果统计
     * @param execution
     * @param completeCondition
     */
    public abstract void signResultCalculate(DelegateExecution execution,Expression completeCondition) throws Exception;
}
