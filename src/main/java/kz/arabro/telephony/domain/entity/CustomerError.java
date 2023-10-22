package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.util.exception.CodedException;

public final class CustomerError {

    public static final String CUSTOMER_ID_VALUE_IS_REQUIRED = "c3cb4196-001";
    public static final String ILLEGAL_CUSTOMER_ID_VALUE = "c3cb4196-002";
    public static final String NAME_VALUE_IS_REQUIRED = "c3cb4196-003";
    public static final String YEAR_VALUE_IS_REQUIRED = "c3cb4196-004";
    public static final String ILLEGAL_YEAR_VALUE = "c3cb4196-005";
    public static final String PHONE_VALUE_IS_REQUIRED = "c3cb4196-006";
    public static final String ILLEGAL_PHONE_VALUE = "c3cb4196-007";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_BUILDER = "c3cb4196-008";
    public static final String NAME_IS_REQUIRED_IN_BUILDER = "c3cb4196-009";
    public static final String YEAR_OF_BIRTH_IS_REQUIRED_IN_BUILDER = "c3cb4196-010";
    public static final String FIRST_PHONE_IS_REQUIRED_IN_BUILDER = "c3cb4196-011";
    public static final String SECOND_PHONE_IS_REQUIRED_IN_BUILDER = "c3cb4196-012";
    public static final String CREATED_AT_IS_REQUIRED_IN_BUILDER = "c3cb4196-013";

    public static CodedException errCustomerIDValueIsRequired() {
        var errMsg = "Value to create CustomerID is required";
        return new CodedException(CUSTOMER_ID_VALUE_IS_REQUIRED, errMsg);
    }

    public static CodedException errIllegalCustomerIDValue(String valueStr, Throwable cause) {
        var errMsg = String.format("Illegal value = '%s' format to create CustomerID", valueStr);
        return new CodedException(ILLEGAL_CUSTOMER_ID_VALUE, errMsg, cause);
    }

    public static CodedException errNameValueIsRequired() {
        var errMsg = "Value to create Name is required";
        return new CodedException(NAME_VALUE_IS_REQUIRED, errMsg);
    }

    public static CodedException errYearValueIsRequired() {
        var errMsg = "Value to create Year is required";
        return new CodedException(YEAR_VALUE_IS_REQUIRED, errMsg);
    }

    public static CodedException errIllegalYearValue(String valueStr) {
        var errMsg = String.format("Illegal value = '%s' format to create Year", valueStr);
        return new CodedException(ILLEGAL_YEAR_VALUE, errMsg);
    }

    public static CodedException errPhoneValueIsRequired() {
        var errMsg = "Value to create Phone is required";
        return new CodedException(PHONE_VALUE_IS_REQUIRED, errMsg);
    }

    public static CodedException errIllegalPhoneValue(String valueStr) {
        var errMsg = String.format("Illegal value = '%s' format to create Phone", valueStr);
        return new CodedException(ILLEGAL_PHONE_VALUE, errMsg);
    }

    public static CodedException errCustomerIDIsRequired() {
        var errMsg = "CustomerID is required in CustomerBuilder";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    public static CodedException errNameIsRequired() {
        var errMsg = "Name is required in CustomerBuilder";
        return new CodedException(NAME_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    public static CodedException errYearOfBirthIsRequired() {
        var errMsg = "YearOfBirth is required in CustomerBuilder";
        return new CodedException(YEAR_OF_BIRTH_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    public static CodedException errFirstPhoneIsRequired() {
        var errMsg = "FirstPhone is required in CustomerBuilder";
        return new CodedException(FIRST_PHONE_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    public static CodedException errSecondPhoneIsRequired() {
        var errMsg = "SecondPhone is required in CustomerBuilder";
        return new CodedException(SECOND_PHONE_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    public static CodedException errCreatedAtIsRequired() {
        var errMsg = "CreatedAt is required in CustomerBuilder";
        return new CodedException(CREATED_AT_IS_REQUIRED_IN_BUILDER, errMsg);
    }

    private CustomerError() {}
}
