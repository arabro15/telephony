package kz.arabro.telephony.domain.entity;

import java.time.Instant;

public class Customer {
    private final CustomerID customerID;
    private final Name name;
    private final Year yearOfBirth;
    private final Phone firstPhone;
    private final Phone secondPhone;
    private final Instant createdAt;

    public Customer(CustomerID customerID,
                    Name name,
                    Year yearOfBirth,
                    Phone firstPhone,
                    Phone secondPhone,
                    Instant createdAt) {
        this.customerID = customerID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.firstPhone = firstPhone;
        this.secondPhone = secondPhone;
        this.createdAt = createdAt;
    }

    public CustomerID getCustomerID() {
        return customerID;
    }

    public Name getName() {
        return name;
    }

    public Year getYearOfBirth() {
        return yearOfBirth;
    }

    public Phone getFirstPhone() {
        return firstPhone;
    }

    public Phone getSecondPhone() {
        return secondPhone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
