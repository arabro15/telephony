package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.util.exception.CodedException;

public final class RepositoryError {
    public static final String CUSTOMER_IS_REQUIRED = "fda5aa34-001";
    public static final String CUSTOMER_IS_REQUIRED_IN_SAVE = "fda5aa34-002";
    public static final String CUSTOMERS_IS_REQUIRED = "fda5aa34-003";
    public static final String CUSTOMER_POSTGRESQL_MODEL_IS_REQUIRED = "fda5aa34-004";
    public static final String CUSTOMER_POSTGRESQL_MODELS_IS_REQUIRED = "fda5aa34-005";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID = "fda5aa34-006";
    public static final String CUSTOMER_PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE = "fda5aa34-007";
    public static final String CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_ID = "fda5aa34-008";
    public static final String CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_PHONE = "fda5aa34-009";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID = "fda5aa34-0010";
    public static final String PHONE_IS_REQUIRED_IN_FIND_BY_PHONE = "fda5aa34-011";
    public static final String FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER = "fda5aa34-012";
    public static final String CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID = "fda5aa34-013";
    public static final String PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE = "fda5aa34-014";
    public static final String CUSTOMER_MONGO_DB_MODEL_IS_REQUIRED = "fda5aa34-015";
    public static final String CUSTOMER_MONGO_DB_MODELS_IS_REQUIRED = "fda5aa34-016";
    public static final String PARSE_CREATED_AT_TO_INSTANT = "fda5aa34-017";

    public static CodedException errCustomerIsRequired() {
        var errMsg = "Customer is required";
        return new CodedException(CUSTOMER_IS_REQUIRED, errMsg);
    }

    public static CodedException errCustomerIsRequiredInSave() {
        var errMsg = "Customer is required in Save method";
        return new CodedException(CUSTOMER_IS_REQUIRED_IN_SAVE, errMsg);
    }

    public static CodedException errCustomersIsRequired() {
        var errMsg = "Customers is required";
        return new CodedException(CUSTOMERS_IS_REQUIRED, errMsg);
    }

    public static CodedException errCustomerPostgreSQLModelIsRequired() {
        var errMsg = "CustomerPostgreSQLModel is required";
        return new CodedException(CUSTOMER_POSTGRESQL_MODEL_IS_REQUIRED, errMsg);
    }
    public static CodedException errCustomerPostgreSQLModelsIsRequired() {
        var errMsg = "CustomerPostgreSQLModels is required";
        return new CodedException(CUSTOMER_POSTGRESQL_MODELS_IS_REQUIRED, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInDeleteByID() {
        var errMsg = "CustomerID is required in DeleteByID method";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, errMsg);
    }

    public static CodedException errCustomerPhoneIsRequiredInDeleteByPhone() {
        var errMsg = "Customer Phone is required in DeleteByPhone method";
        return new CodedException(CUSTOMER_PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerIsRequiredInUpdateByID() {
        var errMsg = "Customer is required in UpdateByID method";
        return new CodedException(CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_ID, errMsg);
    }

    public static CodedException errCustomerIsRequiredInUpdateByPhone() {
        var errMsg = "Customer is required in UpdateByPhone method";
        return new CodedException(CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInFindByID() {
        var errMsg = "CustomerID is required in FindByID method";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, errMsg);
    }

    public static CodedException errPhoneIsRequiredInFindByPhone() {
        var errMsg = "Phone is required in FindByPhone method";
        return new CodedException(PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, errMsg);
    }

    public static CodedException errFilterIsRequiredInFindWithFilter() {
        var errMsg = "Filter is required in FindWithFilter method";
        return new CodedException(FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, errMsg);
    }

    public static CodedException errCustomerIDIsRequiredInExistsByID() {
        var errMsg = "CustomerID is required in ExistsByID method";
        return new CodedException(CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, errMsg);
    }

    public static CodedException errPhoneIsRequiredInExistsByPhone() {
        var errMsg = "Phone is required in ExistsByPhone method";
        return new CodedException(PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, errMsg);
    }

    public static CodedException errCustomerMongoDBModelIsRequired() {
        var errMsg = "CustomerMongoDBModel is required";
        return new CodedException(CUSTOMER_MONGO_DB_MODEL_IS_REQUIRED, errMsg);
    }
    public static CodedException errCustomerMongoDBModelsIsRequired() {
        var errMsg = "CustomerMongoDBModels is required";
        return new CodedException(CUSTOMER_MONGO_DB_MODELS_IS_REQUIRED, errMsg);
    }

    public static CodedException errParseCreatedAtToInstant(String valueStr, Throwable cause) {
        var errMsg = String.format("Parse CreatedAt String to Instant. Illegal value = '%S'", valueStr);
        return new CodedException(PARSE_CREATED_AT_TO_INSTANT, errMsg, cause);
    }

    private RepositoryError() {}
}
