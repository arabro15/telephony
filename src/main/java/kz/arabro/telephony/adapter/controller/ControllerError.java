package kz.arabro.telephony.adapter.controller;

import kz.arabro.telephony.util.exception.CodedException;

public final class ControllerError {
    public static final String CREATE_CUSTOMER_REQUEST_IS_REQUIRED = "e34719b0-001";
    public static final String CUSTOMER_IS_REQUIRED = "e34719b0-002";
    public static final String GET_CUSTOMER_WITH_FILTER_REQUEST_IS_REQUIRED = "e34719b0-003";
    public static final String CUSTOMERS_IS_REQUIRED = "e34719b0-004";
    public static final String UPDATE_CUSTOMER_REQUEST_IS_REQUIRED = "e34719b0-005";

    public static CodedException errCreateCustomerRequestIsRequired() {
        var errMsg = "CreateCustomerRequest is required";
        return new CodedException(CREATE_CUSTOMER_REQUEST_IS_REQUIRED , errMsg);
    }

    public static CodedException errCustomerIsRequired() {
        var errMsg = "Customer is required";
        return new CodedException(CUSTOMER_IS_REQUIRED , errMsg);
    }

    public static CodedException errGetCustomerWithFilterRequestIsRequired() {
        var errMsg = "GetCustomerWithFilterRequest is required";
        return new CodedException(GET_CUSTOMER_WITH_FILTER_REQUEST_IS_REQUIRED, errMsg);
    }

    public static CodedException errCustomersIsRequired() {
        var errMsg = "Customers is required";
        return new CodedException(CUSTOMERS_IS_REQUIRED , errMsg);
    }

    public static CodedException errUpdateCustomerRequestIsRequired() {
        var errMsg = "UpdateCustomerRequest is required";
        return new CodedException(UPDATE_CUSTOMER_REQUEST_IS_REQUIRED , errMsg);
    }

    private ControllerError() {}
}
