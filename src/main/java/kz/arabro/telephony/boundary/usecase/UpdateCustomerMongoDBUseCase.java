package kz.arabro.telephony.boundary.usecase;

import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;

public interface UpdateCustomerMongoDBUseCase {
    void updateByID(CustomerUpdateInfo info);
    void updateByPhone(CustomerUpdateInfo info);
}
