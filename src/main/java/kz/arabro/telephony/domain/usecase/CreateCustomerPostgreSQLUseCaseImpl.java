package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.CreateCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CreateCustomerPostgreSQLUseCaseImpl implements CreateCustomerPostgreSQLUseCase {

    private final CustomerPostgreSQLRepository customerPostgreSQLRepository;

    public CreateCustomerPostgreSQLUseCaseImpl(CustomerPostgreSQLRepository customerPostgreSQLRepository) {
        this.customerPostgreSQLRepository = customerPostgreSQLRepository;
    }

    @Override
    public Customer execute(CustomerCreateInfo info) {
        if (info == null) {
            throw UseCaseError.errCustomerCreateInfoIsRequired();
        }

        var customerID = CustomerID.newID();
        var name = Name.of(info.getName());
        var yearOfBirth = Year.of(info.getYearOfBirth());
        var firstPhone = Phone.of(info.getFirstPhone());
        var secondPhone = Phone.of(info.getSecondPhone());
        var createdAt = Instant.now();

        var customer = new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();

        var checkFirstPhone = customerPostgreSQLRepository.existsByPhone(firstPhone);
        if (checkFirstPhone) {
            throw UseCaseError.errCustomerPhoneAlreadyExists(firstPhone.getValue());
        }

        var checkSecondPhone = customerPostgreSQLRepository.existsByPhone(secondPhone);
        if (checkSecondPhone) {
            throw UseCaseError.errCustomerPhoneAlreadyExists(secondPhone.getValue());
        }

        customerPostgreSQLRepository.save(customer);
        return customer;
    }
}
