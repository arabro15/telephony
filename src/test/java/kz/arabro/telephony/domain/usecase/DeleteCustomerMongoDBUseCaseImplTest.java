package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerMongoDBUseCaseImplTest {

    @Mock
    private CustomerMongoDBRepository customerMongoDBRepository;

    private DeleteCustomerMongoDBUseCase deleteCustomerMongoDBUseCase;

    @BeforeEach
    void setUp() {
        this.deleteCustomerMongoDBUseCase = new DeleteCustomerMongoDBUseCaseImpl(customerMongoDBRepository);
    }

    @Test
    void deleteCustomerByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> deleteCustomerMongoDBUseCase.deleteCustomerByID(null));
        assertEquals(UseCaseError.CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, ex.getCode());
    }

    @Test
    void deleteCustomerByID_ValueIsValid_DeleteCustomer() {
        var customerID = CustomerID.newID();
        var customerIDStr = customerID.getValue().toString();

        deleteCustomerMongoDBUseCase.deleteCustomerByID(customerIDStr);
        verify(customerMongoDBRepository, times(1)).deleteByID(customerID);
    }

    @Test
    void deleteCustomerByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> deleteCustomerMongoDBUseCase.deleteCustomerByPhone(null));
        assertEquals(UseCaseError.PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, ex.getCode());
    }

    @Test
    void deleteCustomerByPhone_ValueIsValid_DeleteCustomer() {
        var phone = PhoneStub.getPhone();
        var phoneStr = phone.getValue();

        deleteCustomerMongoDBUseCase.deleteCustomerByPhone(phoneStr);
        verify(customerMongoDBRepository, times(1)).deleteByPhone(phone);
    }



}