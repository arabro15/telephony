package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;

import java.util.List;
import java.util.Optional;

public class CustomerMongoDBRepositoryImpl implements CustomerMongoDBRepository {
    @Override
    public CustomerID save(Customer customer) {
        return null;
    }

    @Override
    public void deleteByID(CustomerID customerID) {

    }

    @Override
    public void deleteByPhone(Phone phone) {

    }

    @Override
    public void updateByID(Customer customer) {

    }

    @Override
    public void updateByPhone(Customer customer) {

    }

    @Override
    public Optional<Customer> findByID(CustomerID customerID) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByPhone(Phone phone) {
        return Optional.empty();
    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        return null;
    }

    @Override
    public boolean existsByID(CustomerID customerID) {
        return false;
    }

    @Override
    public boolean existsByPhone(Phone phone) {
        return false;
    }
}
