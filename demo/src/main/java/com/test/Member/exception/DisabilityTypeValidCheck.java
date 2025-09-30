package com.test.Member.exception;

import com.test.common.exception.BussinessException;
import com.test.common.exception.ErrorStatus;

public class DisabilityTypeValidCheck extends BussinessException {
    public static final BussinessException bussinessException = new DisabilityTypeValidCheck();

    private DisabilityTypeValidCheck() {
        super(MemberErrorCode.DISABILITY_INVALID_TYPE);
    }
}
