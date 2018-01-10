package com.oa.will.mclistener.signtaskListener;

import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.SignSequenceResultCalContext;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

/**
 * 会签任务完成监听器
 * Created by gao.guangcai on 2018-01-10.
 */
public class SignTaskCompletedListener implements TaskListener {

    private Expression completeType;
    private Expression completeCondition;

    @Override
    public void notify(DelegateTask delegateTask) {

        SignSequenceResultCalContext signSequenceResultCalContext = new SignSequenceResultCalContext(completeType.getExpressionText(), delegateTask, completeCondition);
        if (signSequenceResultCalContext != null) {
            try {
                signSequenceResultCalContext.calSignResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Expression getCompleteType() {
        return completeType;
    }

    public void setCompleteType(Expression completeType) {
        this.completeType = completeType;
    }

    public Expression getCompleteCondition() {
        return completeCondition;
    }

    public void setCompleteCondition(Expression completeCondition) {
        this.completeCondition = completeCondition;
    }
}
