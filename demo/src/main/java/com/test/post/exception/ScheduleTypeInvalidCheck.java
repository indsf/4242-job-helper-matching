package com.test.post.exception;

import com.test.common.exception.BussinessException;
import com.test.common.exception.ErrorStatus;

public class ScheduleTypeInvalidCheck extends BussinessException {
    public static final BussinessException BUSSINESS_EXCEPTION = new ScheduleTypeInvalidCheck();

    public ScheduleTypeInvalidCheck() {
        super(PostErrorCode.SCHEDULE_TYPE_INVALID);
    }
}
