package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import org.springframework.stereotype.Service;

@Service
public class DeleteCustomerPostgreSQLUseCaseImpl implements DeleteCustomerPostgreSQLUseCase {

    private final CustomerPostgreSQLRepository customerPostgreSQLRepository;

    public DeleteCustomerPostgreSQLUseCaseImpl(CustomerPostgreSQLRepository customerPostgreSQLRepository) {
        this.customerPostgreSQLRepository = customerPostgreSQLRepository;
    }

    @Override
    public void deleteCustomerByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInDeleteByID();
        }

        var customerID = CustomerID.from(customerIDStr);

        customerPostgreSQLRepository.deleteByID(customerID);
    }

    @Override
    public void deleteCustomerByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInDeleteByPhone();
        }

        var phone = Phone.of(phoneStr);

        customerPostgreSQLRepository.deleteByPhone(phone);
    }
}
