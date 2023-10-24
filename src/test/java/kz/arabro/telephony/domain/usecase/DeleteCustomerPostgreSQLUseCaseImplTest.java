package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerPostgreSQLUseCaseImplTest {

    @Mock
    private CustomerPostgreSQLRepository customerPostgreSQLRepository;

    private DeleteCustomerPostgreSQLUseCase deleteCustomerPostgreSQLUseCase;

    @BeforeEach
    void setUp() {
        this.deleteCustomerPostgreSQLUseCase = new DeleteCustomerPostgreSQLUseCaseImpl(customerPostgreSQLRepository);
    }

    @Test
    void deleteCustomerByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> deleteCustomerPostgreSQLUseCase.deleteCustomerByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, ex.getCode());
    }

    @Test
    void deleteCustomerByID_ValueIsValid_DeleteCustomer() {
        var customerID = CustomerID.newID();
        var customerIDStr = customerID.getValue().toString();

        deleteCustomerPostgreSQLUseCase.deleteCustomerByID(customerIDStr);
        verify(customerPostgreSQLRepository, times(1)).deleteByID(customerID);
    }

    @Test
    void deleteCustomerByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> deleteCustomerPostgreSQLUseCase.deleteCustomerByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, ex.getCode());
    }

    @Test
    void deleteCustomerByPhone_ValueIsValid_DeleteCustomer() {
        var phone = PhoneStub.getPhone();
        var phoneStr = phone.getValue();

        deleteCustomerPostgreSQLUseCase.deleteCustomerByPhone(phoneStr);
        verify(customerPostgreSQLRepository, times(1)).deleteByPhone(phone);
    }

}