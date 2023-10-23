package kz.arabro.telephony.boundary.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.domain.entity.Customer;

import java.util.List;

public interface ReadDataCustomerMongoDBUseCase {
    Customer findByID(String customerIDStr);
    Customer findByPhone(String phoneStr);
    List<Customer> findWithFilter(Filter filter);
    boolean existsByID(String customerIDStr);
    boolean existsByPhone(String phoneStr);
}
