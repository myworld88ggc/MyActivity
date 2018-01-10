package com.oa.will.mclistener.signtaskListener.signtaskalgorithm.sequence;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;

/**
 * 串行会签---算法计算抽象类
 * Created by gao.guangcai on 2018-01-10.
 */
public abstract class SignSequenceResultCalBaseStrategy {

    public abstract void signResultCalculate(DelegateTask delegateTask, Expression completeCondition) throws Exception;
}
