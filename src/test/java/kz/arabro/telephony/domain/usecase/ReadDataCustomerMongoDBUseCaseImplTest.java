package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReadDataCustomerMongoDBUseCaseImplTest {

    @Mock
    private CustomerMongoDBRepository customerMongoDBRepository;

    private ReadDataCustomerMongoDBUseCase readDataCustomerMongoDBUseCase;

    @BeforeEach
    void setUp() {
        this.readDataCustomerMongoDBUseCase = new ReadDataCustomerMongoDBUseCaseImpl(customerMongoDBRepository);
    }

    @Test
    void findByID_CustomerIDStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.findByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_CustomerIsNotFound_ThrowEx() {
        var customerID = CustomerID.newID().getValue().toString();

        when(customerMongoDBRepository.findByID(any(CustomerID.class))).thenReturn(Optional.empty());

        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.findByID(customerID));
        assertEquals(UseCaseError.CUSTOMER_IS_NOT_FOUND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_CustomerExists_ReturnCustomer() {
        var customer = CustomerStub.getCustomer();
        var customerIDStr = customer.getCustomerID().getValue().toString();

        when(customerMongoDBRepository.findByID(any(CustomerID.class))).thenReturn(Optional.of(customer));

        var actualCustomer = readDataCustomerMongoDBUseCase.findByID(customerIDStr);
        assertNotNull(actualCustomer);
        assertEquals(customer, actualCustomer);
    }

    @Test
    void findByPhone_PhoneStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.findByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_CustomerIsNotFound_ThrowEx() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerMongoDBRepository.findByPhone(any(Phone.class))).thenReturn(Optional.empty());

        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.findByPhone(phoneStr));
        assertEquals(UseCaseError.CUSTOMER_IS_NOT_FOUND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_CustomerExists_ReturnCustomer() {
        var customer = CustomerStub.getCustomer();
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerMongoDBRepository.findByPhone(any(Phone.class))).thenReturn(Optional.of(customer));

        var actualCustomer = readDataCustomerMongoDBUseCase.findByPhone(phoneStr);
        assertNotNull(actualCustomer);
        assertEquals(customer, actualCustomer);
    }

    @Test
    void findWithFilter_FilterIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.findWithFilter(null));
        assertEquals(UseCaseError.FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, ex.getCode());
    }
    @Test
    void findWithFilter_CustomersExists_ReturnCustomers() {
        var customers = CustomerStub.getCustomers(10);

        var limit = 10;
        var offset = 0;

        var filter = new Filter(limit, offset, null, null);

        when(customerMongoDBRepository.findWithFilter(any(Filter.class))).thenReturn(customers);

        var actualCustomer = readDataCustomerMongoDBUseCase.findWithFilter(filter);

        assertNotNull(actualCustomer);
        assertEquals(customers, actualCustomer);
    }

    @Test
    void existsByID_CustomerIDStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.existsByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, ex.getCode());
    }

    @Test
    void existsByID_CustomerNotFound_ReturnFalse() {
        var customerIDStr = CustomerID.newID().getValue().toString();

        when(customerMongoDBRepository.existsByID(any(CustomerID.class))).thenReturn(false);

        var customerExists = readDataCustomerMongoDBUseCase.existsByID(customerIDStr);

        assertFalse(customerExists);
    }

    @Test
    void existsByID_CustomerExists_ReturnTrue() {
        var customerIDStr = CustomerID.newID().getValue().toString();

        when(customerMongoDBRepository.existsByID(any(CustomerID.class))).thenReturn(true);

        var customerExists = readDataCustomerMongoDBUseCase.existsByID(customerIDStr);

        assertTrue(customerExists);
    }

    @Test
    void existsByPhone_PhoneStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerMongoDBUseCase.existsByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, ex.getCode());
    }

    @Test
    void existsByPhone_CustomerNotFound_ReturnFalse() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerMongoDBRepository.existsByPhone(any(Phone.class))).thenReturn(false);

        var customerExists = readDataCustomerMongoDBUseCase.existsByPhone(phoneStr);

        assertFalse(customerExists);
    }

    @Test
    void existsByPhone_CustomerExists_ReturnTrue() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerMongoDBRepository.existsByPhone(any(Phone.class))).thenReturn(true);

        var customerExists = readDataCustomerMongoDBUseCase.existsByPhone(phoneStr);

        assertTrue(customerExists);
    }


}