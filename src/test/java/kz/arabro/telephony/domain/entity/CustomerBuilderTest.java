package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.testdoudle.entity.NameStub;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.testdoudle.entity.YearStub;
import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class CustomerBuilderTest {

    @Test
    void build_CustomerIDIsNull_ThrowEx() {
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var builder = new CustomerBuilder().
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.CUSTOMER_ID_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_NameIsNull_ThrowEx() {
        var customerID = CustomerID.newID();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var builder = new CustomerBuilder().
                setCustomerID(customerID).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.NAME_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_YearOfBirthIsNull_ThrowEx() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var builder = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.YEAR_OF_BIRTH_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_FirstPhoneIsNull_ThrowEx() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var builder = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.FIRST_PHONE_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_SecondPhoneIsNull_ThrowEx() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var builder = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setCreatedAt(createdAt);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.SECOND_PHONE_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_CreatedAtIsNull_ThrowEx() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();

        var builder = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone);

        var ex = assertThrows(CodedException.class, builder::build);
        assertEquals(CustomerError.CREATED_AT_IS_REQUIRED_IN_BUILDER, ex.getCode());
    }

    @Test
    void build_AllParamIsValid_ReturnCustomer() {
        var customerID = CustomerID.newID();
        var name = NameStub.getName();
        var yearOfBirth = YearStub.getYear();
        var firstPhone = PhoneStub.getPhone();
        var secondPhone = PhoneStub.getPhone();
        var createdAt = Instant.now();

        var customer = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();

        assertNotNull(customer);
        assertEquals(customerID, customer.getCustomerID());
        assertEquals(name, customer.getName());
        assertEquals(yearOfBirth, customer.getYearOfBirth());
        assertEquals(firstPhone, customer.getFirstPhone());
        assertEquals(secondPhone, customer.getSecondPhone());
        assertEquals(createdAt, customer.getCreatedAt());
    }
}