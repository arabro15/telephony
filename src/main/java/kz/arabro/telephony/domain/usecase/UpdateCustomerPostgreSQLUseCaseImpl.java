package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.UpdateCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.*;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Service
public class UpdateCustomerPostgreSQLUseCaseImpl implements UpdateCustomerPostgreSQLUseCase {

    private final CustomerPostgreSQLRepository customerPostgreSQLRepository;

    public UpdateCustomerPostgreSQLUseCaseImpl(CustomerPostgreSQLRepository customerPostgreSQLRepository) {
        this.customerPostgreSQLRepository = customerPostgreSQLRepository;
    }

    @Override
    public void updateByID(CustomerUpdateInfo info) {
        var customer = checkAndConvert(info);

        customerPostgreSQLRepository.updateByID(customer);
    }

    @Override
    public void updateByPhone(CustomerUpdateInfo info) {
        var customer = checkAndConvert(info);

        customerPostgreSQLRepository.updateByPhone(customer);
    }

    private Customer checkAndConvert(CustomerUpdateInfo info) {
        if (info == null) {
            throw UseCaseError.errCustomerUpdateInfoIsRequired();
        }

        var customerID = CustomerID.from(info.getCustomerID());
        var name = Name.of(info.getName());
        var yearOfBirth = Year.of(info.getYearOfBirth());
        var firstPhone = Phone.of(info.getFirstPhone());
        var secondPhone = Phone.of(info.getSecondPhone());

        Instant createdAt;
        try {
            createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    parse(info.getCreatedAt()).
                    toInstant();
        } catch (ParseException e) {
            throw UseCaseError.errParseCreatedAtToInstant(info.getCreatedAt(), e);
        }

        return new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();
    }
}
