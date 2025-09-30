package com.test.post.Entity;

import com.test.common.validation.ValueEnum;
import com.test.post.exception.ScheduleTypeInvalidCheck;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum ScheduleType implements ValueEnum<String> {
    정기적("정기적"),
    주기적("주기적");

    private final String schType;

    ScheduleType(String schType) {
        this.schType = schType;
    }

    @Override
    public String getValue() {
        return schType;
    }

    public static ScheduleType schFromValue(String value){
        return Arrays.stream(ScheduleType.values())
                .filter(ft -> ft.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> ScheduleTypeInvalidCheck.BUSSINESS_EXCEPTION);
    }


}
