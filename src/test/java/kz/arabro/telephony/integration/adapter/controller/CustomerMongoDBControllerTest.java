package kz.arabro.telephony.integration.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.arabro.telephony.adapter.controller.response.CreateCustomerResponse;
import kz.arabro.telephony.adapter.controller.response.CustomerResponse;
import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.usecase.CreateCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerMongoDBUseCase;
import kz.arabro.telephony.testdoudle.entity.CustomerRequestStub;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.util.annotation.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class CustomerMongoDBControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CreateCustomerMongoDBUseCase createCustomerMongoDBUseCase;

    @MockBean
    private DeleteCustomerMongoDBUseCase deleteCustomerMongoDBUseCase;

    @MockBean
    private ReadDataCustomerMongoDBUseCase readDataCustomerMongoDBUseCase;

    @MockBean
    private UpdateCustomerMongoDBUseCase updateCustomerMongoDBUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.
                webAppContextSetup(webApplicationContext).
                build();
    }

    @Test
    void createCustomer_RequestIsNull_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/create-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createCustomer_RequestIsValid_ReturnCreateCustomerResponse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var createCustomerRequest = CustomerRequestStub.getCreateCustomerRequest();

        var customer = CustomerStub.getCustomer();
        when(createCustomerMongoDBUseCase.execute(any())).thenReturn(customer);

        var createCustomerResponse = new CreateCustomerResponse();
        createCustomerResponse.setCustomerID(customer.getCustomerID().getValue().toString());

        mockMvc.perform(post("/api/v1/mongodb/create-customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCustomerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(createCustomerResponse)));
    }

    @Test
    void deleteCustomerByID_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/delete-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteCustomerByID_RequestIsValid_ReturnIsStatusOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var deleteCustomerByIDRequest = CustomerRequestStub.getDeleteCustomerByIDRequest();

        doNothing().when(deleteCustomerMongoDBUseCase).deleteCustomerByID(anyString());

        mockMvc.perform(post("/api/v1/mongodb/delete-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCustomerByIDRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCustomerByPhone_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/delete-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteCustomerByPhone_RequestIsValid_ReturnIsStatusOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var deleteCustomerByPhoneRequest = CustomerRequestStub.getDeleteCustomerByPhoneRequest();

        doNothing().when(deleteCustomerMongoDBUseCase).deleteCustomerByPhone(anyString());

        mockMvc.perform(post("/api/v1/mongodb/delete-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteCustomerByPhoneRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerByID_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/get-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getCustomerByID_RequestIsValid_ReturnCustomerResponse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var customer = CustomerStub.getCustomer();
        var getCustomerByIDRequest = CustomerRequestStub.getGetCustomerByIDRequest();

        when(readDataCustomerMongoDBUseCase.findByID(anyString())).thenReturn(customer);

        var customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getCustomerID().getValue().toString());
        customerResponse.setName(customer.getName().getValue());
        customerResponse.setYearOfBirth(customer.getYearOfBirth().getValue());
        customerResponse.setFirstPhone(customer.getFirstPhone().getValue());
        customerResponse.setSecondPhone(customer.getSecondPhone().getValue());
        customerResponse.setCreatedAt(customer.getCreatedAt().toString());

        mockMvc.perform(post("/api/v1/mongodb/get-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomerByIDRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customerResponse)));
    }

    @Test
    void getCustomerByPhone_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/get-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getCustomerByPhone_RequestIsValid_ReturnCustomerResponse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        var customer = CustomerStub.getCustomer();
        var getCustomerByPhoneRequest = CustomerRequestStub.getGetCustomerByPhoneRequest();

        when(readDataCustomerMongoDBUseCase.findByPhone(anyString())).thenReturn(customer);

        var customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getCustomerID().getValue().toString());
        customerResponse.setName(customer.getName().getValue());
        customerResponse.setYearOfBirth(customer.getYearOfBirth().getValue());
        customerResponse.setFirstPhone(customer.getFirstPhone().getValue());
        customerResponse.setSecondPhone(customer.getSecondPhone().getValue());
        customerResponse.setCreatedAt(customer.getCreatedAt().toString());

        mockMvc.perform(post("/api/v1/mongodb/get-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomerByPhoneRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customerResponse)));
    }

    @Test
    void getCustomerWithFilter_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/get-customer-with-filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void getCustomerWith_RequestIsValid_ReturnCustomerResponses() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        var count = 10;
        var customers = CustomerStub.getCustomers(count);
        var getCustomerWithFilterRequest = CustomerRequestStub.getGetCustomerWithFilterRequest();

        when(readDataCustomerMongoDBUseCase.findWithFilter(any(Filter.class))).thenReturn(customers);

        var customerResponses = new ArrayList<CustomerResponse>(count);

        for (int i = 0; i < count; i++) {
            var customerResponse = new CustomerResponse();
            customerResponse.setId(customers.get(i).getCustomerID().getValue().toString());
            customerResponse.setName(customers.get(i).getName().getValue());
            customerResponse.setYearOfBirth(customers.get(i).getYearOfBirth().getValue());
            customerResponse.setFirstPhone(customers.get(i).getFirstPhone().getValue());
            customerResponse.setSecondPhone(customers.get(i).getSecondPhone().getValue());
            customerResponse.setCreatedAt(customers.get(i).getCreatedAt().toString());

            customerResponses.add(customerResponse);
        }


        mockMvc.perform(post("/api/v1/mongodb/get-customer-with-filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCustomerWithFilterRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(customerResponses)));
    }

    @Test
    void updateCustomerByID_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/update-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateCustomerByID_RequestIsValid_ReturnHttpStatusOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doNothing().when(updateCustomerMongoDBUseCase).updateByID(any(CustomerUpdateInfo.class));

        mockMvc.perform(post("/api/v1/mongodb/update-customer-by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCustomerRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomerByPhone_RequestIsInvalid_ReturnIsInternalServerError() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/mongodb/update-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateCustomerByPhone_RequestIsValid_ReturnHttpStatusOk() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        var updateCustomerRequest = CustomerRequestStub.getUpdateCustomerRequest();

        doNothing().when(updateCustomerMongoDBUseCase).updateByPhone(any(CustomerUpdateInfo.class));

        mockMvc.perform(post("/api/v1/mongodb/update-customer-by-phone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCustomerRequest)))
                .andExpect(status().isOk());
    }
}
