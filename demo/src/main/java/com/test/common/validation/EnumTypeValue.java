package com.test.common.validation;

import jakarta.servlet.annotation.HttpConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 어디에 적용 시킬 것인지 필드,클래스,메서드 파라미터도
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumTypeValueValidator.class)
public @interface EnumTypeValue {

    String message() default "올바르지 않은 열거형 값입니다.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends ValueEnum<?>> enumClass();
}
