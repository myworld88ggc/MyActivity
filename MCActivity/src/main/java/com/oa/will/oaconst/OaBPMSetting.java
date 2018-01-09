package com.oa.will.oaconst;

/**
 * BPM设定
 * Created by gao.guangcai on 2018-01-05.
 */
public final class OaBPMSetting {



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
        public  static  final String SIGN_TYPE_ONE_NOTE_TURNBACK="one_note_turnback";//一票驳回
        public  static  final String SIGN_TYPE_PERCENT_PASS_IF_NOT_REJECT ="agree_percent_if_not_reject";//百分比同意，否则拒绝
        public  static  final String SIGN_TYPE_PERCENT_PASS_IF_NOT_TURNBACK ="agree_percent_if_not_turnback";//百分比同意，否则驳回
        public  static  final String SIGN_TYPE_CALCULATE_AGREE_AND_REJECT_COUNT="calculate_agree_and_reject_count";//计算同意和拒绝总数

        //任务审批结果变量
        public  static  final String TASK_APPROVAL_RESULT="approvalResult";

        //会签变量后缀
        public  static  final String COMPLETION_SUFFIX="_Completion";
        //同意总数变量后缀
        public  static  final String SIGN_OK_ACCOUNT_SUFFIX="_Ok_Account";
        //拒绝总数变量后缀
        public  static  final String SIGN_REJECT_ACCOUNT_SUFFIX="_Reject_Account";
        //驳回变量后缀
        public  static  final String SIGN_RESULT_TURNBACK_SUFFIX="_Is_TurnBack";

    }


    public final class ApprovalResultSetting {
        //系统会签变量
        public static final String OK = "1";
        public static final String REJECT = "2";
        public static final String TRUNBACK = "3";

    }
}


