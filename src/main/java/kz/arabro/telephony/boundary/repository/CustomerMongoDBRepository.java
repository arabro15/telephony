package kz.arabro.telephony.boundary.repository;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;

import java.util.List;
import java.util.Optional;

public interface CustomerMongoDBRepository {
    CustomerID save(Customer customer);
    void deleteByID(CustomerID customerID);
    void deleteByPhone(Phone phone);
    void updateByID(Customer customer);
    void updateByPhone(Customer customer);
    Optional<Customer> findByID(CustomerID customerID);
    Optional<Customer> findByPhone(Phone phone);
    List<Customer> findWithFilter(Filter filter);
    boolean existsByID(CustomerID customerID);
    boolean existsByPhone(Phone phone);
}
