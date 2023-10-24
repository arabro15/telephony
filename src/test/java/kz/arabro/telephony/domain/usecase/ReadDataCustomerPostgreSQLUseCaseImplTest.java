package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerPostgreSQLUseCase;
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
class ReadDataCustomerPostgreSQLUseCaseImplTest {

    @Mock
    private CustomerPostgreSQLRepository customerPostgreSQLRepository;

    private ReadDataCustomerPostgreSQLUseCase readDataCustomerPostgreSQLUseCase;

    @BeforeEach
    void setUp() {
        this.readDataCustomerPostgreSQLUseCase = new ReadDataCustomerPostgreSQLUseCaseImpl(customerPostgreSQLRepository);
    }

    @Test
    void findByID_CustomerIDStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.findByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_CustomerIsNotFound_ThrowEx() {
        var customerID = CustomerID.newID().getValue().toString();

        when(customerPostgreSQLRepository.findByID(any(CustomerID.class))).thenReturn(Optional.empty());

        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.findByID(customerID));
        assertEquals(UseCaseError.CUSTOMER_IS_NOT_FOUND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_CustomerExists_ReturnCustomer() {
        var customer = CustomerStub.getCustomer();
        var customerIDStr = customer.getCustomerID().getValue().toString();

        when(customerPostgreSQLRepository.findByID(any(CustomerID.class))).thenReturn(Optional.of(customer));

        var actualCustomer = readDataCustomerPostgreSQLUseCase.findByID(customerIDStr);
        assertNotNull(actualCustomer);
        assertEquals(customer, actualCustomer);
    }

    @Test
    void findByPhone_PhoneStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.findByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_CustomerIsNotFound_ThrowEx() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerPostgreSQLRepository.findByPhone(any(Phone.class))).thenReturn(Optional.empty());

        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.findByPhone(phoneStr));
        assertEquals(UseCaseError.CUSTOMER_IS_NOT_FOUND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_CustomerExists_ReturnCustomer() {
        var customer = CustomerStub.getCustomer();
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerPostgreSQLRepository.findByPhone(any(Phone.class))).thenReturn(Optional.of(customer));

        var actualCustomer = readDataCustomerPostgreSQLUseCase.findByPhone(phoneStr);
        assertNotNull(actualCustomer);
        assertEquals(customer, actualCustomer);
    }

    @Test
    void findWithFilter_FilterIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.findWithFilter(null));
        assertEquals(UseCaseError.FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, ex.getCode());
    }
    @Test
    void findWithFilter_CustomersExists_ReturnCustomers() {
        var customers = CustomerStub.getCustomers(10);

        var limit = 10;
        var offset = 0;

        var filter = new Filter(limit, offset, null, null);

        when(customerPostgreSQLRepository.findWithFilter(any(Filter.class))).thenReturn(customers);

        var actualCustomer = readDataCustomerPostgreSQLUseCase.findWithFilter(filter);

        assertNotNull(actualCustomer);
        assertEquals(customers, actualCustomer);
    }

    @Test
    void existsByID_CustomerIDStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.existsByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, ex.getCode());
    }

    @Test
    void existsByID_CustomerNotFound_ReturnFalse() {
        var customerIDStr = CustomerID.newID().getValue().toString();

        when(customerPostgreSQLRepository.existsByID(any(CustomerID.class))).thenReturn(false);

        var customerExists = readDataCustomerPostgreSQLUseCase.existsByID(customerIDStr);

        assertFalse(customerExists);
    }

    @Test
    void existsByID_CustomerExists_ReturnTrue() {
        var customerIDStr = CustomerID.newID().getValue().toString();

        when(customerPostgreSQLRepository.existsByID(any(CustomerID.class))).thenReturn(true);

        var customerExists = readDataCustomerPostgreSQLUseCase.existsByID(customerIDStr);

        assertTrue(customerExists);
    }

    @Test
    void existsByPhone_PhoneStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> readDataCustomerPostgreSQLUseCase.existsByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, ex.getCode());
    }

    @Test
    void existsByPhone_CustomerNotFound_ReturnFalse() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerPostgreSQLRepository.existsByPhone(any(Phone.class))).thenReturn(false);

        var customerExists = readDataCustomerPostgreSQLUseCase.existsByPhone(phoneStr);

        assertFalse(customerExists);
    }

    @Test
    void existsByPhone_CustomerExists_ReturnTrue() {
        var phoneStr = PhoneStub.getPhone().getValue();

        when(customerPostgreSQLRepository.existsByPhone(any(Phone.class))).thenReturn(true);

        var customerExists = readDataCustomerPostgreSQLUseCase.existsByPhone(phoneStr);

        assertTrue(customerExists);
    }

}