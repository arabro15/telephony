package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadDataCustomerMongoDBUseCaseImpl implements ReadDataCustomerMongoDBUseCase {
    private final CustomerMongoDBRepository customerMongoDBRepository;

    public ReadDataCustomerMongoDBUseCaseImpl(CustomerMongoDBRepository customerMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
    }

    @Override
    public Customer findByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInFindByID();
        }

        var customerID = CustomerID.from(customerIDStr);
        var customerOpt = customerMongoDBRepository.findByID(customerID);

        return customerOpt.
                orElseThrow(() -> UseCaseError.errCustomerNotFoundByID(customerID));
    }

    @Override
    public Customer findByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errPhoneIsRequiredInFindByPhone();
        }

        var phone = Phone.of(phoneStr);
        var customerOpt = customerMongoDBRepository.findByPhone(phone);

        return customerOpt.
                orElseThrow(() -> UseCaseError.errCustomerNotFoundByPhone(phone));
    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        if (filter == null) {
            throw UseCaseError.errFilterIsRequiredInFindWithFilter();
        }

        return customerMongoDBRepository.findWithFilter(filter);
    }

    @Override
    public boolean existsByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInExistsByID();
        }

        var customerID = CustomerID.from(customerIDStr);

        return customerMongoDBRepository.existsByID(customerID);
    }

    @Override
    public boolean existsByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInExistsByPhone();
        }

        var phone = Phone.of(phoneStr);

        return customerMongoDBRepository.existsByPhone(phone);
    }
}
