package kz.arabro.telephony.adapter.controller.converter;

import kz.arabro.telephony.adapter.controller.ControllerError;
import kz.arabro.telephony.adapter.controller.request.CreateCustomerRequest;
import kz.arabro.telephony.adapter.controller.request.GetCustomerWithFilterRequest;
import kz.arabro.telephony.adapter.controller.request.UpdateCustomerRequest;
import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.boundary.model.Filter;

public final class CustomerRequestConverter {

    public static CustomerCreateInfo createCustomerRequestToModel(CreateCustomerRequest request) {
        if (request == null) {
            throw ControllerError.errCreateCustomerRequestIsRequired();
        }

        var name = request.getName();
        var yearOfBirth = request.getYearOfBirth();
        var firstPhone = request.getFirstPhone();
        var secondPhone = request.getSecondPhone();

        return new CustomerCreateInfo(
                name,
                yearOfBirth,
                firstPhone,
                secondPhone
        );
    }

    public static Filter getCustomerWithFilterToModel(GetCustomerWithFilterRequest request) {
        if (request == null) {
            throw ControllerError.errGetCustomerWithFilterRequestIsRequired();
        }

        var limit = request.getLimit();
        var offset = request.getOffset();
        var id = request.getId();
        var phone = request.getPhone();

        return new Filter(
                limit,
                offset,
                id,
                phone
        );
    }

    public static CustomerUpdateInfo updateCustomerToModel(UpdateCustomerRequest request) {
        if (request == null) {
            throw ControllerError.errUpdateCustomerRequestIsRequired();
        }

        var id = request.getId();
        var name = request.getName();
        var yearOfBirth = request.getYearOfBirth();
        var firstPhone = request.getFirstPhone();
        var secondPhone = request.getSecondPhone();
        var createdAt = request.getCreatedAt();

        return new CustomerUpdateInfo(
                id,
                name,
                yearOfBirth,
                firstPhone,
                secondPhone,
                createdAt
        );
    }

    private CustomerRequestConverter() {}
}
