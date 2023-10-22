package kz.arabro.telephony.domain.entity;

import java.time.Instant;

public class CustomerBuilder {
    private CustomerID customerID;
    private Name name;
    private Year yearOfBirth;
    private Phone firstPhone;
    private Phone secondPhone;
    private Instant createdAt;

    public CustomerBuilder setCustomerID(CustomerID customerID) {
        this.customerID = customerID;
        return this;
    }

    public CustomerBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder setYearOfBirth(Year yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
        return this;
    }

    public CustomerBuilder setFirstPhone(Phone firstPhone) {
        this.firstPhone = firstPhone;
        return this;
    }

    public CustomerBuilder setSecondPhone(Phone secondPhone) {
        this.secondPhone = secondPhone;
        return this;
    }

    public CustomerBuilder setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Customer build() {
        checkRequiredFields();

        return new Customer(
                customerID,
                name,
                yearOfBirth,
                firstPhone,
                secondPhone,
                createdAt
        );
    }

    private void checkRequiredFields() {
        if (customerID == null) {
            throw CustomerError.errCustomerIDIsRequired();
        }
        if (name == null) {
            throw CustomerError.errNameIsRequired();
        }
        if (yearOfBirth == null) {
            throw CustomerError.errYearOfBirthIsRequired();
        }
        if (firstPhone == null) {
            throw CustomerError.errFirstPhoneIsRequired();
        }
        if (secondPhone == null) {
            throw CustomerError.errSecondPhoneIsRequired();
        }
        if (createdAt == null) {
            throw CustomerError.errCreatedAtIsRequired();
        }
    }
}
