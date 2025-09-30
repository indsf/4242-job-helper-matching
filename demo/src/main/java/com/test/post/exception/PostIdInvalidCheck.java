package com.test.post.exception;

import com.test.common.exception.BussinessException;


public class PostIdInvalidCheck extends BussinessException {
    public static final BussinessException BUSSINESS_EXCEPTION = new PostIdInvalidCheck();

    private PostIdInvalidCheck() {
        super(PostErrorCode.POST_NOT_FOUND);
    }
}
