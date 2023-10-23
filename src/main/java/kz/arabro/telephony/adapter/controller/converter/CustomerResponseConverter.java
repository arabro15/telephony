package kz.arabro.telephony.adapter.controller.converter;

import kz.arabro.telephony.adapter.controller.ControllerError;
import kz.arabro.telephony.adapter.controller.response.CustomerResponse;
import kz.arabro.telephony.domain.entity.Customer;

import java.util.List;

public final class CustomerResponseConverter {
    public static CustomerResponse customerToResponse(Customer customer) {
        if (customer == null) {
            throw ControllerError.errCustomerIsRequired();
        }

        var id = customer.getCustomerID().getValue().toString();
        var name = customer.getName().getValue();
        var yearOfBirth = customer.getYearOfBirth().getValue();
        var firstPhone = customer.getFirstPhone().getValue();
        var secondPhone = customer.getSecondPhone().getValue();
        var createdAt = customer.getCreatedAt().toString();

        var customerResponse = new CustomerResponse();
        customerResponse.setId(id);
        customerResponse.setName(name);
        customerResponse.setYearOfBirth(yearOfBirth);
        customerResponse.setFirstPhone(firstPhone);
        customerResponse.setSecondPhone(secondPhone);
        customerResponse.setCreatedAt(createdAt);

        return customerResponse;
    }

    public static List<CustomerResponse> customersToResponses(List<Customer> customers) {
        if (customers == null) {
            throw ControllerError.errCustomersIsRequired();
        }

        return customers.stream().
                map(CustomerResponseConverter::customerToResponse).
                toList();
    }

    private CustomerResponseConverter() {}
}
