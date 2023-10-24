package kz.arabro.telephony.adapter.controller;

import kz.arabro.telephony.adapter.controller.converter.CustomerRequestConverter;
import kz.arabro.telephony.adapter.controller.converter.CustomerResponseConverter;
import kz.arabro.telephony.adapter.controller.request.*;
import kz.arabro.telephony.adapter.controller.response.CreateCustomerResponse;
import kz.arabro.telephony.adapter.controller.response.CustomerResponse;
import kz.arabro.telephony.boundary.usecase.CreateCustomerPostgreSQLUseCase;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerPostgreSQLUseCase;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerPostgreSQLUseCase;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerPostgreSQLUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/postgresql")
public class CustomerPostgreSQLController {

    private final CreateCustomerPostgreSQLUseCase createCustomerPostgreSQLUseCase;
    private final DeleteCustomerPostgreSQLUseCase deleteCustomerPostgreSQLUseCase;
    private final ReadDataCustomerPostgreSQLUseCase readDataCustomerPostgreSQLUseCase;
    private final UpdateCustomerPostgreSQLUseCase updateCustomerPostgreSQLUseCase;

    public CustomerPostgreSQLController(CreateCustomerPostgreSQLUseCase createCustomerPostgreSQLUseCase,
                                        DeleteCustomerPostgreSQLUseCase deleteCustomerPostgreSQLUseCase,
                                        ReadDataCustomerPostgreSQLUseCase readDataCustomerPostgreSQLUseCase,
                                        UpdateCustomerPostgreSQLUseCase updateCustomerPostgreSQLUseCase) {
        this.createCustomerPostgreSQLUseCase = createCustomerPostgreSQLUseCase;
        this.deleteCustomerPostgreSQLUseCase = deleteCustomerPostgreSQLUseCase;
        this.readDataCustomerPostgreSQLUseCase = readDataCustomerPostgreSQLUseCase;
        this.updateCustomerPostgreSQLUseCase = updateCustomerPostgreSQLUseCase;
    }


    @PostMapping(path = "/create-customer")
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        var info = CustomerRequestConverter.createCustomerRequestToModel(request);

        var customer = createCustomerPostgreSQLUseCase.execute(info);

        var response = new CreateCustomerResponse();
        response.setCustomerID(customer.getCustomerID().getValue().toString());

        return response;
    }

    @PostMapping(path = "/delete-customer-by-id")
    public ResponseEntity<Object> deleteCustomerByID(@RequestBody DeleteCustomerByIDRequest request) {
        deleteCustomerPostgreSQLUseCase.deleteCustomerByID(request.getCustomerID());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/delete-customer-by-phone")
    public ResponseEntity<Object> deleteCustomerByPhone(@RequestBody DeleteCustomerByPhoneRequest request) {
        deleteCustomerPostgreSQLUseCase.deleteCustomerByPhone(request.getPhone());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/get-customer-by-id")
    public CustomerResponse getCustomerByID(@RequestBody GetCustomerByIDRequest request) {
        var customer = readDataCustomerPostgreSQLUseCase.findByID(request.getCustomerID());
        return CustomerResponseConverter.customerToResponse(customer);
    }

    @PostMapping(path = "/get-customer-by-phone")
    public CustomerResponse getCustomerByPhone(@RequestBody GetCustomerByPhoneRequest request) {
        var customer = readDataCustomerPostgreSQLUseCase.findByPhone(request.getPhone());
        return CustomerResponseConverter.customerToResponse(customer);
    }

    @PostMapping(path = "/get-customer-with-filter")
    public List<CustomerResponse> getCustomerWithFilter(@RequestBody GetCustomerWithFilterRequest request) {
        var filter = CustomerRequestConverter.getCustomerWithFilterToModel(request);

        var customers = readDataCustomerPostgreSQLUseCase.findWithFilter(filter);
        return CustomerResponseConverter.customersToResponses(customers);
    }

    @PostMapping(path = "/update-customer-by-id")
    public ResponseEntity<Object> updateCustomerByID(@RequestBody UpdateCustomerRequest request) {
        var info = CustomerRequestConverter.updateCustomerToModel(request);
        updateCustomerPostgreSQLUseCase.updateByID(info);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping(path = "/update-customer-by-phone")
    public ResponseEntity<Object> updateCustomerByPhone(@RequestBody UpdateCustomerRequest request) {
        var info = CustomerRequestConverter.updateCustomerToModel(request);
        updateCustomerPostgreSQLUseCase.updateByPhone(info);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
