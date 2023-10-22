package kz.arabro.telephony.boundary.usecase;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.domain.entity.Customer;

public interface CreateCustomerPostgreSQLUseCase {
    Customer execute(CustomerCreateInfo info);
}
