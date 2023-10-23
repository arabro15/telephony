package kz.arabro.telephony.boundary.usecase;

public interface DeleteCustomerMongoDBUseCase {
    void deleteCustomerByID(String customerIDStr);
    void deleteCustomerByPhone(String phoneStr);
}
