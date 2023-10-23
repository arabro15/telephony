package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.boundary.usecase.DeleteCustomerMongoDBUseCase;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import org.springframework.stereotype.Service;

@Service
public class DeleteCustomerMongoDBUseCaseImpl implements DeleteCustomerMongoDBUseCase {

    private final CustomerMongoDBRepository customerMongoDBRepository;

    public DeleteCustomerMongoDBUseCaseImpl(CustomerMongoDBRepository customerMongoDBRepository) {
        this.customerMongoDBRepository = customerMongoDBRepository;
    }

    @Override
    public void deleteCustomerByID(String customerIDStr) {
        if (customerIDStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInDeleteByID();
        }

        var customerID = CustomerID.from(customerIDStr);

        customerMongoDBRepository.deleteByID(customerID);
    }

    @Override
    public void deleteCustomerByPhone(String phoneStr) {
        if (phoneStr == null) {
            throw UseCaseError.errCustomerIDIsRequiredInDeleteByPhone();
        }

        var phone = Phone.of(phoneStr);

        customerMongoDBRepository.deleteByPhone(phone);
    }
}
