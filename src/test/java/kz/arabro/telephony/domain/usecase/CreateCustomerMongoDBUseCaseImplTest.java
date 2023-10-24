package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.CreateCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.testdoudle.entity.CustomerCreateInfoStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerMongoDBUseCaseImplTest {

    @Mock
    private CustomerMongoDBRepository customerMongoDBRepository;

    private CreateCustomerMongoDBUseCase createCustomerMongoDBUseCase;

    @BeforeEach
    void setUp() {
        this.createCustomerMongoDBUseCase = new CreateCustomerMongoDBUseCaseImpl(customerMongoDBRepository);
    }

    @Test
    void execute_InfoIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> createCustomerMongoDBUseCase.execute(null));
        assertEquals(UseCaseError.CUSTOMER_CREATE_INFO_IS_REQUIRED, ex.getCode());
    }

    @Test
    void execute_PhoneIsAlreadyExists_ThrowEx() {
        when(customerMongoDBRepository.existsByPhone(any(Phone.class))).thenReturn(true);

        var info = CustomerCreateInfoStub.getCustomerCreateInfo();

        var ex = assertThrows(CodedException.class, () -> createCustomerMongoDBUseCase.execute(info));
        assertEquals(UseCaseError.CUSTOMER_PHONE_ALREADY_EXISTS, ex.getCode());
    }

    @Test
    void execute_SaveCustomerIsThrowEx_ThrowEx() {
        doThrow(RuntimeException.class).when(customerMongoDBRepository).save(any(Customer.class));

        var info = CustomerCreateInfoStub.getCustomerCreateInfo();

        assertThrows(RuntimeException.class, () -> createCustomerMongoDBUseCase.execute(info));
    }

    @Test
    void execute_NoThrowEx_ReturnCustomer() {
        var info = CustomerCreateInfoStub.getCustomerCreateInfo();

        var customer = createCustomerMongoDBUseCase.execute(info);

        assertNotNull(customer);
        assertEquals(info.getName(), customer.getName().getValue());
        assertEquals(info.getYearOfBirth(), customer.getYearOfBirth().getValue());
        assertEquals(info.getFirstPhone(), customer.getFirstPhone().getValue());
        assertEquals(info.getSecondPhone(), customer.getSecondPhone().getValue());
        verify(customerMongoDBRepository, times(1)).save(customer);
    }
}