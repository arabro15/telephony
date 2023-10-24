package kz.arabro.telephony.adapter.controller;

import kz.arabro.telephony.adapter.controller.converter.CustomerRequestConverter;
import kz.arabro.telephony.adapter.controller.converter.CustomerResponseConverter;
import kz.arabro.telephony.adapter.controller.request.*;
import kz.arabro.telephony.adapter.controller.response.CreateCustomerResponse;
import kz.arabro.telephony.adapter.controller.response.CustomerResponse;
import kz.arabro.telephony.boundary.usecase.CreateCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerMongoDBUseCase;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerMongoDBUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/mongodb")
public class CustomerMongoDBController {

    private final CreateCustomerMongoDBUseCase createMongoDBCustomerUseCase;
    private final DeleteCustomerMongoDBUseCase deleteCustomerMongoDBUseCase;
    private final ReadDataCustomerMongoDBUseCase readDataCustomerMongoDBUseCase;
    private final UpdateCustomerMongoDBUseCase updateCustomerMongoDBUseCase;

    public CustomerMongoDBController(CreateCustomerMongoDBUseCase createMongoDBCustomerUseCase,
                                     DeleteCustomerMongoDBUseCase deleteCustomerMongoDBUseCase,
                                     ReadDataCustomerMongoDBUseCase readDataCustomerMongoDBUseCase,
                                     UpdateCustomerMongoDBUseCase updateCustomerMongoDBUseCase) {
        this.createMongoDBCustomerUseCase = createMongoDBCustomerUseCase;
        this.deleteCustomerMongoDBUseCase = deleteCustomerMongoDBUseCase;
        this.readDataCustomerMongoDBUseCase = readDataCustomerMongoDBUseCase;
        this.updateCustomerMongoDBUseCase = updateCustomerMongoDBUseCase;
    }

    @PostMapping(path = "/create-customer")
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        var info = CustomerRequestConverter.createCustomerRequestToModel(request);

        var customer = createMongoDBCustomerUseCase.execute(info);

        var response = new CreateCustomerResponse();
        response.setCustomerID(customer.getCustomerID().getValue().toString());

        return response;
    }

    @PostMapping(path = "/delete-customer-by-id")
    public ResponseEntity<Object> deleteCustomerByID(@RequestBody DeleteCustomerByIDRequest request) {
        deleteCustomerMongoDBUseCase.deleteCustomerByID(request.getCustomerID());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/delete-customer-by-phone")
    public ResponseEntity<Object> deleteCustomerByPhone(@RequestBody DeleteCustomerByPhoneRequest request) {
        deleteCustomerMongoDBUseCase.deleteCustomerByPhone(request.getPhone());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/get-customer-by-id")
    public CustomerResponse getCustomerByID(@RequestBody GetCustomerByIDRequest request) {
        var customer = readDataCustomerMongoDBUseCase.findByID(request.getCustomerID());
        return CustomerResponseConverter.customerToResponse(customer);
    }

    @PostMapping(path = "/get-customer-by-phone")
    public CustomerResponse getCustomerByPhone(@RequestBody GetCustomerByPhoneRequest request) {
        var customer = readDataCustomerMongoDBUseCase.findByPhone(request.getPhone());
        return CustomerResponseConverter.customerToResponse(customer);
    }

    @PostMapping(path = "/get-customer-with-filter")
    public List<CustomerResponse> getCustomerWithFilter(@RequestBody GetCustomerWithFilterRequest request) {
        var filter = CustomerRequestConverter.getCustomerWithFilterToModel(request);

        var customers = readDataCustomerMongoDBUseCase.findWithFilter(filter);
        return CustomerResponseConverter.customersToResponses(customers);
    }

    @PostMapping(path = "/update-customer-by-id")
    public ResponseEntity<Object> updateCustomerByID(@RequestBody UpdateCustomerRequest request) {
        var info = CustomerRequestConverter.updateCustomerToModel(request);
        updateCustomerMongoDBUseCase.updateByID(info);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/update-customer-by-phone")
    public ResponseEntity<Object> updateCustomerByPhone(@RequestBody UpdateCustomerRequest request) {
        var info = CustomerRequestConverter.updateCustomerToModel(request);
        updateCustomerMongoDBUseCase.updateByPhone(info);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
