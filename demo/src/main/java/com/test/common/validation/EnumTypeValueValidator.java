package com.test.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumTypeValueValidator implements ConstraintValidator<EnumTypeValue,String> {

    private ValueEnum<?>[] enumValues;

    @Override
    public void initialize(EnumTypeValue constraintAnnotation) {
        Class<? extends ValueEnum<?>> enumclass = constraintAnnotation.enumClass();
        enumValues = enumclass.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return Arrays.stream(enumValues)
                .map(ValueEnum::getValue)
                .anyMatch(enumValues -> enumValues.equals(value));
    }
}
