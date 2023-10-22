package kz.arabro.telephony.boundary.usecase;

public interface DeleteCustomerPostgreSQLUseCase {
    void deleteCustomerByID(String customerIDStr);
    void deleteCustomerByPhone(String phoneStr);
}
