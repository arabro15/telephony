package kz.arabro.telephony.domain.usecase;

import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.usecase.ReadDataCustomerPostgreSQLUseCase;
import kz.arabro.telephony.domain.entity.Customer;

import java.util.List;

public class ReadDataCustomerPostgreSQLUseCaseImpl implements ReadDataCustomerPostgreSQLUseCase {
    @Override
    public Customer findByID(String customerIDStr) {
        return null;
    }

    @Override
    public Customer findByPhone(String phoneStr) {
        return null;
    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        return null;
    }

    @Override
    public boolean existsByID(String customerIDStr) {
        return false;
    }

    @Override
    public boolean existsByPhone(String phoneStr) {
        return false;
    }
}
