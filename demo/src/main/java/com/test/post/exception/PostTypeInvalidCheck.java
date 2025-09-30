package com.test.post.exception;

import com.test.common.exception.BussinessException;
import com.test.common.exception.ErrorStatus;

public class PostTypeInvalidCheck extends BussinessException {
    private static final BussinessException BUSSINESS_EXCEPTION = new PostTypeInvalidCheck();

    public PostTypeInvalidCheck() {
        super(PostErrorCode.POST_TYPE_INVALID);
    }
}
