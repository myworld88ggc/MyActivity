package com.oa.will.mclistener.signtaskListener.signtaskalgorithm;

import com.oa.will.mclistener.signtaskListener.signtaskalgorithm.parallel.*;
import com.oa.will.oaconst.OaBPMSetting;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;

/**
 * 会签统计Context
 * Created by Will on 2018/01/06.
 */
public class SignParallelResultCalContext {
    //会签结果统计方式
    private SignParallelResultCalBaseStrategy signResultCalculate;
    //执行监听execution对象
    private DelegateExecution execution;
    //会签完成条件
    private Expression completeCondition;

    public SignParallelResultCalContext(String calType, DelegateExecution execution, Expression completeCondition) {
        this.execution = execution;
        this.completeCondition = completeCondition;
        calType = calType.trim().toLowerCase();
        switch (calType) {
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_ONE_NOTE_PASS://一票通过
                this.signResultCalculate = new OneNotePassStrategyParallel();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_ONE_NOTE_REJECT://一票否决
                this.signResultCalculate = new OneNoteRejectStrategyParallel();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_ONE_NOTE_TURNBACK://一票驳回
                this.signResultCalculate = new OneNoteTurnbackStrategyParallel();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_PERCENT_PASS_IF_NOT_REJECT://百分比通过,否则拒绝
                this.signResultCalculate = new PercentPassIfNotRejectStrategyParallel();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_PERCENT_PASS_IF_NOT_TURNBACK://百分比通过,否则驳回
                this.signResultCalculate = new PercentPassIfNotTurnBackStrategyParallel();
                break;
            case OaBPMSetting.SignNodeSetting.SIGN_TYPE_CALCULATE_AGREE_AND_REJECT_COUNT://统计 同意和拒绝 结果
                this.signResultCalculate = new CalculateAgreeAndRejectParallelResultCountStrategy();
                break;
            default:

        }
    }

    public void calSignResult() throws Exception {
        if (signResultCalculate == null) {
            throw new Exception("会签统计结果算法signResultCalculate为NULL,请核对流程图会签统计结果类型");
        }
        signResultCalculate.signResultCalculate(execution, completeCondition);
    }
}
