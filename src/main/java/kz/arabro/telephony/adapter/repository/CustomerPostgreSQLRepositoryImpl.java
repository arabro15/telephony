package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.adapter.repository.converter.CustomerPostgreSQLConverter;
import kz.arabro.telephony.adapter.repository.mapper.CustomerMapper;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerPostgreSQLRepositoryImpl implements CustomerPostgreSQLRepository {

    private final CustomerMapper customerMapper;

    public CustomerPostgreSQLRepositoryImpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Transactional
    @Override
    public CustomerID save(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInSave();
        }

        var customerPostgreSQLModel = CustomerPostgreSQLConverter.toModel(customer);
        customerMapper.insertCustomer(customerPostgreSQLModel);

        return customer.getCustomerID();
    }

    @Transactional
    @Override
    public void deleteByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInDeleteByID();
        }

        var id = customerID.getValue();

        customerMapper.deleteCustomerByID(id);
    }

    @Transactional
    @Override
    public void deleteByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errCustomerPhoneIsRequiredInDeleteByPhone();
        }

        var phoneStr = phone.getValue();

        customerMapper.deleteCustomerByPhone(phoneStr);
    }

    @Override
    public void updateByID(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInUpdateByID();
        }

        var customerPostgreSQLModel = CustomerPostgreSQLConverter.toModel(customer);

        customerMapper.updateCustomerByID(customerPostgreSQLModel);
    }

    @Transactional
    @Override
    public void updateByPhone(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInUpdateByPhone();
        }

        var customerPostgreSQLModel = CustomerPostgreSQLConverter.toModel(customer);

        customerMapper.updateCustomerByPhone(customerPostgreSQLModel);
    }

    @Override
    public Optional<Customer> findByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInFindByID();
        }

        var id = customerID.getValue();

        var customerPostgreSQLModel = customerMapper.selectCustomerByID(id);

        return customerPostgreSQLModel == null ?
                Optional.empty() :
                Optional.of(CustomerPostgreSQLConverter.toEntity(customerPostgreSQLModel));

    }

    @Override
    public Optional<Customer> findByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errPhoneIsRequiredInFindByPhone();
        }

        var phoneStr = phone.getValue();

        var customerPostgreSQLModel = customerMapper.selectCustomerByPhone(phoneStr);

        return customerPostgreSQLModel == null ?
                Optional.empty() :
                Optional.of(CustomerPostgreSQLConverter.toEntity(customerPostgreSQLModel));

    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        if (filter == null) {
            throw RepositoryError.errFilterIsRequiredInFindWithFilter();
        }

        var customerPostgreSQLModels = customerMapper.selectCustomerWithFilter(filter);

        return CustomerPostgreSQLConverter.toEntities(customerPostgreSQLModels);
    }

    @Override
    public boolean existsByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInExistsByID();
        }

        var id = customerID.getValue();

        return customerMapper.existsCustomerByID(id);
    }

    @Override
    public boolean existsByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errPhoneIsRequiredInExistsByPhone();
        }

        var phoneStr = phone.getValue();

        return customerMapper.existsCustomerByPhone(phoneStr);
    }
}
