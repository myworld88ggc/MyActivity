package com.oa.will.oaconst;

/**
 * 会签节点设定
 * Created by gao.guangcai on 2018-01-05.
 */
public final class SignNodeSetting {

    //系统会签变量
    public  static  final String NR_OF_ACTIVE_INSTANCES="nrOfActiveInstances";
    public  static  final String LOOP_COUNTER="loopCounter";
    public  static  final String NR_OF_INSTANCES="nrOfInstances";
    public  static  final String NR_OF_COMPLETED_INSTANCES="nrOfCompletedInstances";

    //会签通过类型
    public  static  final String SIGN_TYPE_ONE_NOTE_PASS="one_note_pass";//一票同意
    public  static  final String SIGN_TYPE_ONE_NOTE_REJECT="one_note_reject";//一票否决
    public  static  final String SIGN_TYPE_PERCENT_PASS="agree_percent";//同意百分比

    //会签变量前后最
    public  static  final String COMPLETION_SUFFIX="_Completion";
    public  static  final String APPROVAL_RESULT="_SignResult";
}
