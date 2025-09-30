package com.test.post.exception;

import com.test.common.exception.BussinessException;

public class AssistanceTypeInvalidCheck extends BussinessException{
    public static final BussinessException BUSSINESS_EXCEPTION = new AssistanceTypeInvalidCheck();


    private AssistanceTypeInvalidCheck() {
        super(PostErrorCode.ASSISTANCE_TYPE_INVALID);
    }
}
