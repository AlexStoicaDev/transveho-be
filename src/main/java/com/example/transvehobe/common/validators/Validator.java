package com.example.transvehobe.common.validators;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class Validator {

    public boolean isStringPropertyValidForUpdate(String oldStringValue, String newStringValue) {
        return newStringValue != null && !newStringValue.equals("") && !newStringValue.equals(oldStringValue);
    }

    public boolean isLocalDateTimePropertyValidForUpdate(LocalDateTime oldLocalDateTime, LocalDateTime newLocalDateTime) {
        return newLocalDateTime != null && !oldLocalDateTime.equals(newLocalDateTime);
    }
}
