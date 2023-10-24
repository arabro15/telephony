package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.testdoudle.entity.CustomerUpdateInfoStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerMongoDBUseCaseImplTest {

    @Mock
    private CustomerMongoDBRepository customerMongoDBRepository;

    private UpdateCustomerMongoDBUseCase updateCustomerMongoDBUseCase;

    @BeforeEach
    void setUp() {
        this.updateCustomerMongoDBUseCase = new UpdateCustomerMongoDBUseCaseImpl(customerMongoDBRepository);
    }

    @Test
    void updateByID_CustomerUpdateInfoIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> updateCustomerMongoDBUseCase.updateByID(null));
        assertEquals(UseCaseError.CUSTOMER_UPDATE_INFO_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateByID_CreatedAtIsInvalid_ThrowEx() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfoWithInvalidCreatedAt();

        var ex = assertThrows(CodedException.class, () -> updateCustomerMongoDBUseCase.updateByID(customerUpdateInfo));
        assertEquals(UseCaseError.PARSE_CREATED_AT_TO_INSTANT, ex.getCode());
    }

    @Test
    void updateByID_ValueIsValid_UpdateCustomer() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfo();

        updateCustomerMongoDBUseCase.updateByID(customerUpdateInfo);
        verify(customerMongoDBRepository, times(1)).updateByID(any(Customer.class));
    }

    @Test
    void updateByPhone_CustomerUpdateInfoIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> updateCustomerMongoDBUseCase.updateByPhone(null));
        assertEquals(UseCaseError.CUSTOMER_UPDATE_INFO_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateByPhone_CreatedAtIsInvalid_ThrowEx() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfoWithInvalidCreatedAt();

        var ex = assertThrows(CodedException.class, () -> updateCustomerMongoDBUseCase.updateByPhone(customerUpdateInfo));
        assertEquals(UseCaseError.PARSE_CREATED_AT_TO_INSTANT, ex.getCode());
    }

    @Test
    void updateByPhone_ValueIsValid_UpdateCustomer() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfo();

        updateCustomerMongoDBUseCase.updateByPhone(customerUpdateInfo);
        verify(customerMongoDBRepository, times(1)).updateByPhone(any(Customer.class));
    }

}