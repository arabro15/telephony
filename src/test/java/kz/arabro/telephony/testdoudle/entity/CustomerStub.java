package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.domain.entity.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CustomerStub {

    public static Customer getCustomer() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        return new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();
    }

    public static List<Customer> getCustomers(int count) {
        var customers = new ArrayList<Customer>();

        for (int i = 0; i < count; i++) {
            customers.add(getCustomer());
        }

        return customers;
    }
}
