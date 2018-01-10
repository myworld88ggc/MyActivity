package com.oa.will.mclistener.signtaskListener.signtaskalgorithm;

import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.parallel.OneNotePassStrategyParallel;
import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.parallel.OneNoteRejectStrategyParallel;
import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.sequence.OneNoteRejectStrategy;
import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.sequence.OneNoteTurnBackStrategy;
import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.sequence.SignSequenceResultCalBaseStrategy;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;

/**
 * Created by gao.guangcai on 2018-01-10.
 */
public class SignSequenceResultCalContext {

    private SignSequenceResultCalBaseStrategy signResultCalculate;
    //delegateTask
    private DelegateTask delegateTask;
    //会签完成条件
    private Expression completeCondition;

    public SignSequenceResultCalContext(String calType, DelegateTask delegateTask, Expression completeCondition) {
        this.delegateTask = delegateTask;
        this.completeCondition = completeCondition;

        calType = calType.trim().toLowerCase();
        switch (calType) {

            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_ONE_NOTE_REJECT://一票否决
                this.signResultCalculate = new OneNoteRejectStrategy();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_ONE_NOTE_TURNBACK://一票退回
                this.signResultCalculate = new OneNoteTurnBackStrategy();
                break;
            default:
                break;
        }
    }

    public void calSignResult() throws Exception {
        if (signResultCalculate == null) {
            throw new Exception("会签统计结果算法signResultCalculate为NULL,请核对流程图会签统计结果类型");
        }
        signResultCalculate.signResultCalculate(delegateTask, completeCondition);
    }
}
