package kz.arabro.telephony.adapter.controller;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.usecase.CreateCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerMongoDBUseCase;
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

@ExtendWith(MockitoExtension.class)
class CustomerMongoDBControllerTest {

    @Mock
    private CreateCustomerMongoDBUseCase createCustomerMongoDBUseCase;

    @Mock
    private DeleteCustomerMongoDBUseCase deleteCustomerMongoDBUseCase;

    @Mock
    private ReadDataCustomerMongoDBUseCase readDataCustomerMongoDBUseCase;

    @Mock
    private UpdateCustomerMongoDBUseCase updateCustomerMongoDBUseCase;

    private CustomerMongoDBController customerMongoDBController;

    @BeforeEach
    void setUp() {
        this.customerMongoDBController = new CustomerMongoDBController(
                createCustomerMongoDBUseCase,
                deleteCustomerMongoDBUseCase,
                readDataCustomerMongoDBUseCase,
                updateCustomerMongoDBUseCase
        );
    }

    @Test
    void createCustomer_CreateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBController.createCustomer(null));
        assertEquals(ControllerError.CREATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void createCustomer_UseCaseIsThrowEx_ThrowEx() {
        var createCustomerRequest = CustomerRequestStub.getCreateCustomerRequest();

        when(createCustomerMongoDBUseCase.execute(any(CustomerCreateInfo.class))).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> customerMongoDBController.createCustomer(createCustomerRequest));
    }

    @Test
    void createCustomer_NoException_ReturnCreateCustomerResponse() {
        var customer = CustomerStub.getCustomer();
        when(createCustomerMongoDBUseCase.execute(any(CustomerCreateInfo.class))).thenReturn(customer);

        var createCustomerRequest = CustomerRequestStub.getCreateCustomerRequest();
        var createCustomerResponse = customerMongoDBController.createCustomer(createCustomerRequest);
        assertEquals(customer.getCustomerID().getValue().toString(), createCustomerResponse.getCustomerID());
    }

    @Test
    void deleteCustomerByID_DeleteCustomerByIDRequestIsThrowEx_ThrowEx() {
        doThrow(RuntimeException.class).when(deleteCustomerMongoDBUseCase).deleteCustomerByID(anyString());

        var deleteCustomerByIDRequest = CustomerRequestStub.getDeleteCustomerByIDRequest();

        assertThrows(RuntimeException.class, () -> customerMongoDBController.deleteCustomerByID(deleteCustomerByIDRequest));
    }

    @Test
    void deleteCustomerByID_NoException_ReturnHttpStatusOk() {
        doNothing().when(deleteCustomerMongoDBUseCase).deleteCustomerByID((anyString()));

        var deleteCustomerByIDRequest = CustomerRequestStub.getDeleteCustomerByIDRequest();

        var deleteCustomerResponse = customerMongoDBController.deleteCustomerByID(deleteCustomerByIDRequest);

        assertEquals(HttpStatus.OK, deleteCustomerResponse.getStatusCode());
        assertNull(deleteCustomerResponse.getBody());
    }

    @Test
    void deleteCustomerByPhone_DeleteCustomerByIDRequestIsThrowEx_ThrowEx() {
        doThrow(RuntimeException.class).when(deleteCustomerMongoDBUseCase).deleteCustomerByPhone(anyString());

        var deleteCustomerByPhoneRequest = CustomerRequestStub.getDeleteCustomerByPhoneRequest();

        assertThrows(RuntimeException.class, () -> customerMongoDBController.deleteCustomerByPhone(deleteCustomerByPhoneRequest));
    }

    @Test
    void deleteCustomerByPhone_NoException_ReturnHttpStatusOk() {
        doNothing().when(deleteCustomerMongoDBUseCase).deleteCustomerByPhone((anyString()));

        var deleteCustomerByPhoneRequest = CustomerRequestStub.getDeleteCustomerByPhoneRequest();

        var deleteCustomerResponse = customerMongoDBController.deleteCustomerByPhone(deleteCustomerByPhoneRequest);

        assertEquals(HttpStatus.OK, deleteCustomerResponse.getStatusCode());
        assertNull(deleteCustomerResponse.getBody());
    }

    @Test
    void getCustomerByID_GetCustomerByIDRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerMongoDBController.getCustomerByID(null));
    }

    @Test
    void getCustomerByID_NoException_ReturnCustomerResponse() {
        var customer = CustomerStub.getCustomer();

        when(readDataCustomerMongoDBUseCase.findByID(anyString())).thenReturn(customer);

        var getCustomerByIDRequest = CustomerRequestStub.getGetCustomerByIDRequest();

        var customerResponse = customerMongoDBController.getCustomerByID(getCustomerByIDRequest);

        assertNotNull(customerResponse);
        assertEquals(customer.getCustomerID().getValue().toString(), customerResponse.getId());
    }

    @Test
    void getCustomerByPhone_GetCustomerByPhoneRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerMongoDBController.getCustomerByPhone(null));
    }

    @Test
    void getCustomerByPhone_NoException_ReturnCustomerResponse() {
        var customer = CustomerStub.getCustomer();

        when(readDataCustomerMongoDBUseCase.findByPhone(anyString())).thenReturn(customer);

        var getCustomerByPhoneRequest = CustomerRequestStub.getGetCustomerByPhoneRequest();

        var customerResponse = customerMongoDBController.getCustomerByPhone(getCustomerByPhoneRequest);

        assertNotNull(customerResponse);
        assertEquals(customer.getCustomerID().getValue().toString(), customerResponse.getId());
    }

    @Test
    void getCustomerWithFilter_GetCustomerByPhoneRequestIsNull_RuntimeEx() {
        assertThrows(RuntimeException.class, () -> customerMongoDBController.getCustomerWithFilter(null));
    }

    @Test
    void getCustomerWithFilter_NoException_ReturnCustomerResponses() {
        var count = 10;
        var customers = CustomerStub.getCustomers(count);

        when(readDataCustomerMongoDBUseCase.findWithFilter(any(Filter.class))).thenReturn(customers);

        var getCustomerWithFilterRequest = CustomerRequestStub.getGetCustomerWithFilterRequest();

        var customerResponses = customerMongoDBController.getCustomerWithFilter(getCustomerWithFilterRequest);

        assertNotNull(customerResponses);
        for (int i = 0; i < count; i++) {
            assertEquals(customers.get(i).getCustomerID().getValue().toString(), customerResponses.get(i).getId());
        }
    }

    @Test
    void updateCustomerByID_UpdateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBController.updateCustomerByID(null));
        assertEquals(ControllerError.UPDATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateCustomerByID_UseCaseIsThrowEx_ThrowEx() {
        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doThrow(RuntimeException.class).when(updateCustomerMongoDBUseCase).updateByID(any(CustomerUpdateInfo.class));
        assertThrows(RuntimeException.class, () -> customerMongoDBController.updateCustomerByID(updateCustomerRequest));
    }

    @Test
    void updateCustomerByID_NoException_ReturnHttpStatusOk() {
        doNothing().when(updateCustomerMongoDBUseCase).updateByID(any(CustomerUpdateInfo.class));

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        var updateCustomerResponse = customerMongoDBController.updateCustomerByID(updateCustomerRequest);

        assertEquals(HttpStatus.OK, updateCustomerResponse.getStatusCode());
        assertNull(updateCustomerResponse.getBody());
    }

    @Test
    void updateCustomerByPhone_UpdateCustomerRequestIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBController.updateCustomerByPhone(null));
        assertEquals(ControllerError.UPDATE_CUSTOMER_REQUEST_IS_REQUIRED, ex.getCode());
    }

    @Test
    void updateCustomerByPhone_UseCaseIsThrowEx_ThrowEx() {
        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doThrow(RuntimeException.class).when(updateCustomerMongoDBUseCase).updateByPhone(any(CustomerUpdateInfo.class));
        assertThrows(RuntimeException.class, () -> customerMongoDBController.updateCustomerByPhone(updateCustomerRequest));
    }

    @Test
    void updateCustomerByPhone_NoException_ReturnHttpStatusOk() {
        doNothing().when(updateCustomerMongoDBUseCase).updateByPhone(any(CustomerUpdateInfo.class));

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        var updateCustomerResponse = customerMongoDBController.updateCustomerByPhone(updateCustomerRequest);

        assertEquals(HttpStatus.OK, updateCustomerResponse.getStatusCode());
        assertNull(updateCustomerResponse.getBody());
    }
}