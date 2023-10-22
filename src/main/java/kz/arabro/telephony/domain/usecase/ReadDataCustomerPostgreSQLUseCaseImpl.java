package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;

import java.util.List;

public class ReadDataCustomerPostgreSQLUseCaseImpl implements ReadDataCustomerPostgreSQLUseCase {

    private final CustomerPostgreSQLRepository customerPostgreSQLRepository;

    public ReadDataCustomerPostgreSQLUseCaseImpl(CustomerPostgreSQLRepository customerPostgreSQLRepository) {
        this.customerPostgreSQLRepository = customerPostgreSQLRepository;
    }

    @Override
    public Customer findByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInFindByID();
        }

        var customerID = CustomerID.from(customerIDStr);
        var customerOpt = customerPostgreSQLRepository.findByID(customerID);

        return customerOpt.
                orElseThrow(() -> UseCaseError.errCustomerNotFoundByID(customerID));

    }

    @Override
    public Customer findByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errPhoneIsRequiredInFindByPhone();
        }

        var phone = Phone.of(phoneStr);
        var customerOpt = customerPostgreSQLRepository.findByPhone(phone);

        return customerOpt.
                orElseThrow(() -> UseCaseError.errCustomerNotFoundByPhone(phone));

    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        if (filter == null) {
            throw UseCaseError.errFilterIsRequiredInFindWithFilter();
        }

        return customerPostgreSQLRepository.findWithFilter(filter);
    }

    @Override
    public boolean existsByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInExistsByID();
        }

        var customerID = CustomerID.from(customerIDStr);

        return customerPostgreSQLRepository.existsByID(customerID);
    }

    @Override
    public boolean existsByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInExistsByPhone();
        }

        var phone = Phone.of(phoneStr);

        return customerPostgreSQLRepository.existsByPhone(phone);
    }
}
