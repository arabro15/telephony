package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.Customer;
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
class UpdateCustomerPostgreSQLUseCaseImplTest {

    @Mock
    private CustomerPostgreSQLRepository customerPostgreSQLRepository;

    private UpdateCustomerPostgreSQLUseCase updateCustomerPostgreSQLUseCase;

    @BeforeEach
    void setUp() {
        this.updateCustomerPostgreSQLUseCase = new UpdateCustomerPostgreSQLUseCaseImpl(customerPostgreSQLRepository);
    }

    @Test
    void updateByID_CustomerUpdateInfoIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> updateCustomerPostgreSQLUseCase.updateByID(null));
        assertEquals(UseCaseError.CUSTOMER_UPDATE_INFO_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateByID_CreatedAtIsInvalid_ThrowEx() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfoWithInvalidCreatedAt();

        var ex = assertThrows(CodedException.class, () -> updateCustomerPostgreSQLUseCase.updateByID(customerUpdateInfo));
        assertEquals(UseCaseError.PARSE_CREATED_AT_TO_INSTANT, ex.getCode());
    }

    @Test
    void updateByID_ValueIsValid_UpdateCustomer() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfo();

        updateCustomerPostgreSQLUseCase.updateByID(customerUpdateInfo);
        verify(customerPostgreSQLRepository, times(1)).updateByID(any(Customer.class));
    }

    @Test
    void updateByPhone_CustomerUpdateInfoIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> updateCustomerPostgreSQLUseCase.updateByPhone(null));
        assertEquals(UseCaseError.CUSTOMER_UPDATE_INFO_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateByPhone_CreatedAtIsInvalid_ThrowEx() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfoWithInvalidCreatedAt();

        var ex = assertThrows(CodedException.class, () -> updateCustomerPostgreSQLUseCase.updateByPhone(customerUpdateInfo));
        assertEquals(UseCaseError.PARSE_CREATED_AT_TO_INSTANT, ex.getCode());
    }

    @Test
    void updateByPhone_ValueIsValid_UpdateCustomer() {
        var customerUpdateInfo = CustomerUpdateInfoStub.getCustomerUpdateInfo();

        updateCustomerPostgreSQLUseCase.updateByPhone(customerUpdateInfo);
        verify(customerPostgreSQLRepository, times(1)).updateByPhone(any(Customer.class));
    }

}