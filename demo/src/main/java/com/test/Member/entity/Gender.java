package com.test.Member.entity;

import com.test.common.validation.ValueEnum;

public enum Gender implements ValueEnum<String> {
    남성("남성"),
    여성("여성");

    private final String dbValue;

    Gender(String dbValue) {
        this.dbValue = dbValue;
    }

    @Override
    public String getValue() {
        return dbValue;
    }
}
