package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.util.exception.CodedException;

public final class UseCaseError {
    public static final String CUSTOMER_CREATE_INFO_IS_REQUIRED = "cc394b68-001";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID = "cc394b68-002";
    public static final String PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE = "cc394b68-003";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID = "cc394b68-004";
    public static final String CUSTOMER_IS_NOT_FOUND_BY_ID = "cc394b68-005";
    public static final String PHONE_IS_REQUIRED_IN_FIND_BY_PHONE = "cc394b68-006";
    public static final String CUSTOMER_IS_NOT_FOUND_BY_PHONE = "cc394b68-007";
    public static final String FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER = "cc394b68-008";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID = "cc394b68-009";
    public static final String PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE = "cc394b68-010";
    public static final String CUSTOMER_UPDATE_INFO_IS_REQUIRED = "cc394b68-011";
    public static final String PARSE_CREATED_AT_TO_INSTANT = "cc394b68-012";
    public static final String CUSTOMER_PHONE_ALREADY_EXISTS = "cc394b68-013";

    public static CodedException errCustomerCreateInfoIsRequired() {
        var errMsg = "CustomerCreateInfo is required";
        return new CodedException(CUSTOMER_CREATE_INFO_IS_REQUIRED, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInDeleteByID() {
        var errMsg = "CustomerID is required in DeleteByID";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, errMsg);
    }
    public static CodedException errPhoneIsRequiredInDeleteByPhone() {
        var errMsg = "Phone is required in DeleteByPhone";
        return new CodedException(PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInFindByID() {
        var errMsg = "CustomerID is required in FindByID";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, errMsg);
    }

    public static CodedException errCustomerNotFoundByID(CustomerID customerID) {
        var errMsg = String.format("Customer is not found by ID: %s", customerID.getValue().toString());
        return new CodedException(CUSTOMER_IS_NOT_FOUND_BY_ID, errMsg);
    }

    public static CodedException errPhoneIsRequiredInFindByPhone() {
        var errMsg = "Phone is required in FindByPhone";
        return new CodedException(PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerNotFoundByPhone(Phone phone) {
        var errMsg = String.format("Customer is not found by phone: %s", phone.getValue());
        return new CodedException(CUSTOMER_IS_NOT_FOUND_BY_PHONE, errMsg);
    }

    public static CodedException errFilterIsRequiredInFindWithFilter() {
        var errMsg = "Filter is required in FindWithFilter";
        return new CodedException(FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInExistsByID() {
        var errMsg = "CustomerID is required in ExistsByID";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, errMsg);
    }

    public static CodedException errPhoneIsRequiredInExistsByPhone() {
        var errMsg = "Phone is required in ExistsByPhone";
        return new CodedException(PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerUpdateInfoIsRequired() {
        var errMsg = "CustomerUpdateInfo is required";
        return new CodedException(CUSTOMER_UPDATE_INFO_IS_REQUIRED, errMsg);
    }

    public static CodedException errParseCreatedAtToInstant(String valueStr, Throwable cause) {
        var errMsg = String.format("Parse CreatedAt String to Instant. Illegal value = '%S'", valueStr);
        return new CodedException(PARSE_CREATED_AT_TO_INSTANT, errMsg, cause);
    }

    public static CodedException errCustomerPhoneAlreadyExists(String valueStr) {
        var errMsg = String.format("Customer with this Phone = %S, already exists", valueStr);
        return new CodedException(CUSTOMER_PHONE_ALREADY_EXISTS, errMsg);
    }

    private UseCaseError() {}
}
