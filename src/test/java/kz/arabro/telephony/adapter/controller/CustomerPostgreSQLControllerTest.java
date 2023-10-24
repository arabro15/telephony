package kz.arabro.telephony.adapter.controller;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.usecase.*;
import kz.arabro.telephony.testdoudle.entity.CustomerRequestStub;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class CustomerPostgreSQLControllerTest {
    @Mock
    private CreateCustomerPostgreSQLUseCase createCustomerPostgreSQLUseCase;

    @Mock
    private DeleteCustomerPostgreSQLUseCase deleteCustomerPostgreSQLUseCase;

    @Mock
    private ReadDataCustomerPostgreSQLUseCase readDataCustomerPostgreSQLUseCase;

    @Mock
    private UpdateCustomerPostgreSQLUseCase updateCustomerPostgreSQLUseCase;

    private CustomerPostgreSQLController customerPostgreSQLController;

    @BeforeEach
    void setUp() {
        this.customerPostgreSQLController = new CustomerPostgreSQLController(
                createCustomerPostgreSQLUseCase,
                deleteCustomerPostgreSQLUseCase,
                readDataCustomerPostgreSQLUseCase,
                updateCustomerPostgreSQLUseCase
        );
    }

    @Test
    void createCustomer_CreateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLController.createCustomer(null));
        assertEquals(ControllerError.CREATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void createCustomer_UseCaseIsThrowEx_ThrowEx() {
        var createCustomerRequest = CustomerRequestStub.getCreateCustomerRequest();

        when(createCustomerPostgreSQLUseCase.execute(any(CustomerCreateInfo.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.createCustomer(createCustomerRequest));
    }

    @Test
    void createCustomer_NoException_ReturnCreateCustomerResponse() {
        var customer = CustomerStub.getCustomer();
        when(createCustomerPostgreSQLUseCase.execute(any(CustomerCreateInfo.class))).thenReturn(customer);

        var createCustomerRequest = CustomerRequestStub.getCreateCustomerRequest();
        var createCustomerResponse = customerPostgreSQLController.createCustomer(createCustomerRequest);
        assertEquals(customer.getCustomerID().getValue().toString(), createCustomerResponse.getCustomerID());
    }

    @Test
    void deleteCustomerByID_DeleteCustomerByIDRequestIsThrowEx_ThrowEx() {
        doThrow(RuntimeException.class).when(deleteCustomerPostgreSQLUseCase).deleteCustomerByID(anyString());

        var deleteCustomerByIDRequest = CustomerRequestStub.getDeleteCustomerByIDRequest();

        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.deleteCustomerByID(deleteCustomerByIDRequest));
    }

    @Test
    void deleteCustomerByID_NoException_ReturnHttpStatusOk() {
        doNothing().when(deleteCustomerPostgreSQLUseCase).deleteCustomerByID((anyString()));

        var deleteCustomerByIDRequest = CustomerRequestStub.getDeleteCustomerByIDRequest();

        var deleteCustomerResponse = customerPostgreSQLController.deleteCustomerByID(deleteCustomerByIDRequest);

        assertEquals(HttpStatus.OK, deleteCustomerResponse.getStatusCode());
        assertNull(deleteCustomerResponse.getBody());
    }

    @Test
    void deleteCustomerByPhone_DeleteCustomerByIDRequestIsThrowEx_ThrowEx() {
        doThrow(RuntimeException.class).when(deleteCustomerPostgreSQLUseCase).deleteCustomerByPhone(anyString());

        var deleteCustomerByPhoneRequest = CustomerRequestStub.getDeleteCustomerByPhoneRequest();

        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.deleteCustomerByPhone(deleteCustomerByPhoneRequest));
    }

    @Test
    void deleteCustomerByPhone_NoException_ReturnHttpStatusOk() {
        doNothing().when(deleteCustomerPostgreSQLUseCase).deleteCustomerByPhone((anyString()));

        var deleteCustomerByPhoneRequest = CustomerRequestStub.getDeleteCustomerByPhoneRequest();

        var deleteCustomerResponse = customerPostgreSQLController.deleteCustomerByPhone(deleteCustomerByPhoneRequest);

        assertEquals(HttpStatus.OK, deleteCustomerResponse.getStatusCode());
        assertNull(deleteCustomerResponse.getBody());
    }

    @Test
    void getCustomerByID_GetCustomerByIDRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.getCustomerByID(null));
    }

    @Test
    void getCustomerByID_NoException_ReturnCustomerResponse() {
        var customer = CustomerStub.getCustomer();

        when(readDataCustomerPostgreSQLUseCase.findByID(anyString())).thenReturn(customer);

        var getCustomerByIDRequest = CustomerRequestStub.getGetCustomerByIDRequest();

        var customerResponse = customerPostgreSQLController.getCustomerByID(getCustomerByIDRequest);

        assertNotNull(customerResponse);
        assertEquals(customer.getCustomerID().getValue().toString(), customerResponse.getId());
    }

    @Test
    void getCustomerByPhone_GetCustomerByPhoneRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.getCustomerByPhone(null));
    }

    @Test
    void getCustomerByPhone_NoException_ReturnCustomerResponse() {
        var customer = CustomerStub.getCustomer();

        when(readDataCustomerPostgreSQLUseCase.findByPhone(anyString())).thenReturn(customer);

        var getCustomerByPhoneRequest = CustomerRequestStub.getGetCustomerByPhoneRequest();

        var customerResponse = customerPostgreSQLController.getCustomerByPhone(getCustomerByPhoneRequest);

        assertNotNull(customerResponse);
        assertEquals(customer.getCustomerID().getValue().toString(), customerResponse.getId());
    }

    @Test
    void getCustomerWithFilter_GetCustomerByPhoneRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.getCustomerWithFilter(null));
    }

    @Test
    void getCustomerWithFilter_NoException_ReturnCustomerResponses() {
        var count = 10;
        var customers = CustomerStub.getCustomers(count);

        when(readDataCustomerPostgreSQLUseCase.findWithFilter(any(Filter.class))).thenReturn(customers);

        var getCustomerWithFilterRequest = CustomerRequestStub.getGetCustomerWithFilterRequest();

        var customerResponses = customerPostgreSQLController.getCustomerWithFilter(getCustomerWithFilterRequest);

        assertNotNull(customerResponses);
        for (int i = 0; i < count; i++) {
            assertEquals(customers.get(i).getCustomerID().getValue().toString(), customerResponses.get(i).getId());
        }
    }

    @Test
    void updateCustomerByID_UpdateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLController.updateCustomerByID(null));
        assertEquals(ControllerError.UPDATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateCustomerByID_UseCaseIsThrowEx_ThrowEx() {
        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doThrow(RuntimeException.class).when(updateCustomerPostgreSQLUseCase).updateByID(any(CustomerUpdateInfo.class));
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.updateCustomerByID(updateCustomerRequest));
    }

    @Test
    void updateCustomerByID_NoException_ReturnHttpStatusOk() {
        doNothing().when(updateCustomerPostgreSQLUseCase).updateByID(any(CustomerUpdateInfo.class));

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        var updateCustomerResponse = customerPostgreSQLController.updateCustomerByID(updateCustomerRequest);

        assertEquals(HttpStatus.OK, updateCustomerResponse.getStatusCode());
        assertNull(updateCustomerResponse.getBody());
    }

    @Test
    void updateCustomerByPhone_UpdateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLController.updateCustomerByPhone(null));
        assertEquals(ControllerError.UPDATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateCustomerByPhone_UseCaseIsThrowEx_ThrowEx() {
        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doThrow(RuntimeException.class).when(updateCustomerPostgreSQLUseCase).updateByPhone(any(CustomerUpdateInfo.class));
        assertThrows(RuntimeException.class, () -> customerPostgreSQLController.updateCustomerByPhone(updateCustomerRequest));
    }

    @Test
    void updateCustomerByPhone_NoException_ReturnHttpStatusOk() {
        doNothing().when(updateCustomerPostgreSQLUseCase).updateByPhone(any(CustomerUpdateInfo.class));

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        var updateCustomerResponse = customerPostgreSQLController.updateCustomerByPhone(updateCustomerRequest);

        assertEquals(HttpStatus.OK, updateCustomerResponse.getStatusCode());
        assertNull(updateCustomerResponse.getBody());
    }
}