package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.adapter.controller.request.*;
import kz.arabro.telephony.domain.entity.CustomerID;

import java.time.Instant;

public class CustomerRequestStub {

    public static CreateCustomerRequest getCreateCustomerRequest() {
        var createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setName(NameStub.getName().getValue());
        createCustomerRequest.setYearOfBirth(YearStub.getYear().getValue());
        createCustomerRequest.setFirstPhone(PhoneStub.getPhone().getValue());
        createCustomerRequest.setSecondPhone(PhoneStub.getPhone().getValue());

        return createCustomerRequest;
    }

    public static DeleteCustomerByIDRequest getDeleteCustomerByIDRequest() {
        var deleteCustomerByIDRequest = new DeleteCustomerByIDRequest();
        deleteCustomerByIDRequest.setCustomerID(CustomerID.newID().getValue().toString());

        return deleteCustomerByIDRequest;
    }

    public static DeleteCustomerByPhoneRequest getDeleteCustomerByPhoneRequest() {
        var deleteCustomerByPhoneRequest = new DeleteCustomerByPhoneRequest();
        deleteCustomerByPhoneRequest.setPhone(PhoneStub.getPhone().getValue());

        return deleteCustomerByPhoneRequest;
    }

    public static GetCustomerByIDRequest getGetCustomerByIDRequest() {
        var getCustomerByIDRequest = new GetCustomerByIDRequest();
        getCustomerByIDRequest.setCustomerID(CustomerID.newID().getValue().toString());

        return getCustomerByIDRequest;
    }

    public static GetCustomerByPhoneRequest getGetCustomerByPhoneRequest() {
        var getCustomerByPhoneRequest = new GetCustomerByPhoneRequest();
        getCustomerByPhoneRequest.setPhone(PhoneStub.getPhone().getValue());

        return getCustomerByPhoneRequest;
    }

    public static GetCustomerWithFilterRequest getGetCustomerWithFilterRequest() {
        var getCustomerWithFilterRequest = new GetCustomerWithFilterRequest();
        getCustomerWithFilterRequest.setLimit(10);
        getCustomerWithFilterRequest.setOffset(0);
        getCustomerWithFilterRequest.setId(null);
        getCustomerWithFilterRequest.setPhone(null);

        return getCustomerWithFilterRequest;
    }

    public static UpdateCustomerRequest getUpdateCustomerRequest() {
        var updateCustomerRequest = new UpdateCustomerRequest();
        updateCustomerRequest.setId(CustomerID.newID().getValue().toString());
        updateCustomerRequest.setName(NameStub.getName().getValue());
        updateCustomerRequest.setYearOfBirth(YearStub.getYear().getValue());
        updateCustomerRequest.setFirstPhone(PhoneStub.getPhone().getValue());
        updateCustomerRequest.setSecondPhone(PhoneStub.getPhone().getValue());
        updateCustomerRequest.setCreatedAt(Instant.now().toString());

        return updateCustomerRequest;
    }
}
