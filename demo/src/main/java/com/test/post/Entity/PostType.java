package com.test.post.Entity;

import com.test.common.validation.ValueEnum;
import com.test.post.exception.ScheduleTypeInvalidCheck;

import java.util.Arrays;

public enum PostType implements ValueEnum<String> {
    TAKER("TAKER"),
    GIVER("GIVER");
    private final String PostTypeValue;

    PostType(String postTypeValue) {
        PostTypeValue = postTypeValue;
    }

    @Override
    public String getValue() {
        return PostTypeValue;
    }

    public static PostType postFromValue(String value){
        return Arrays.stream(PostType.values())
                .filter(ft -> ft.getValue().equals(value))
                .findFirst()
                .orElseThrow(()-> ScheduleTypeInvalidCheck.BUSSINESS_EXCEPTION);
    }
}
