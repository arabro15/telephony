package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.CreateCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CreateCustomerMongoDBUseCaseImpl implements CreateCustomerMongoDBUseCase {

    private final CustomerMongoDBRepository customerMongoDBRepository;

    public CreateCustomerMongoDBUseCaseImpl(CustomerMongoDBRepository customerMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
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

        var checkFirstPhone = customerMongoDBRepository.existsByPhone(firstPhone);
        if (checkFirstPhone) {
            throw UseCaseError.errCustomerPhoneAlreadyExists(firstPhone.getValue());
        }

        var checkSecondPhone = customerMongoDBRepository.existsByPhone(secondPhone);
        if (checkSecondPhone) {
            throw UseCaseError.errCustomerPhoneAlreadyExists(secondPhone.getValue());
        }

        customerMongoDBRepository.save(customer);
        return customer;
    }
}
