package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.usecase.CreateCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.Customer;

public class CreateCustomerPostgreSQLUseCaseImpl implements CreateCustomerPostgreSQLUseCase {
    @Override
    public Customer execute(CustomerCreateInfo info) {
        return null;
    }
}
