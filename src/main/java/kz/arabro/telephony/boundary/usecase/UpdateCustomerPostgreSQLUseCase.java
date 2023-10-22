package kz.arabro.telephony.boundary.usecase;

import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;

public interface UpdateCustomerPostgreSQLUseCase {
    void updateByID(CustomerUpdateInfo info);
    void updateByPhone(CustomerUpdateInfo info);
}
