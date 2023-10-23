package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.adapter.repository.converter.CustomerMongoDBConverter;
import kz.arabro.telephony.adapter.repository.dao.CustomerDao;
import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.domain.entity.Phone;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerMongoDBRepositoryImpl implements CustomerMongoDBRepository {

    private final CustomerDao customerDao;
    private final MongoTemplate mongoTemplate;

    public CustomerMongoDBRepositoryImpl(CustomerDao customerDao, MongoTemplate mongoTemplate) {
        this.customerDao = customerDao;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public CustomerID save(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequired();
        }

        var customerMongoDbModel = CustomerMongoDBConverter.toModel(customer);

        customerDao.save(customerMongoDbModel);

        return customer.getCustomerID();
    }

    @Override
    public void deleteByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInDeleteByID();
        }

        customerDao.deleteById(customerID.getValue());
    }

    @Override
    public void deleteByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errCustomerPhoneIsRequiredInDeleteByPhone();
        }

        var phoneStr = phone.getValue();

        customerDao.deleteByFirstPhoneOrSecondPhone(phoneStr, phoneStr);
    }

    @Override
    public void updateByID(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInUpdateByID();
        }

        var id = customer.getCustomerID().getValue();

        if (customerDao.existsById(id)) {
            save(customer);
        }
    }

    @Override
    public void updateByPhone(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInUpdateByID();
        }

        var firstPhone = customer.getFirstPhone().getValue();
        var secondPhone = customer.getSecondPhone().getValue();

        var customerOpt = customerDao.findByFirstPhoneOrSecondPhone(firstPhone, secondPhone);
        if (customerOpt.isPresent()) {
            var id = customer.getCustomerID().getValue();
            var idFromDb = customerOpt.get().getCustomerID();

            if (id == idFromDb) {
                save(customer);
            }
        }
    }

    @Override
    public Optional<Customer> findByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInFindByID();
        }

        var id = customerID.getValue();

        return customerDao.findById(id).
                map(CustomerMongoDBConverter::toEntity);
    }

    @Override
    public Optional<Customer> findByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errPhoneIsRequiredInFindByPhone();
        }

        var phoneStr = phone.getValue();

        return customerDao.findByFirstPhoneOrSecondPhone(phoneStr, phoneStr).
                map(CustomerMongoDBConverter::toEntity);

    }

    @Override
    public List<Customer> findWithFilter(Filter filter) {
        if (filter == null) {
            throw RepositoryError.errFilterIsRequiredInFindWithFilter();
        }

        var id = filter.getId();
        var phone = filter.getPhone();
        var limit = filter.getLimit();
        var offset = Long.valueOf(filter.getOffset());

        var query = new Query();
        var customerMongoDBModels = new ArrayList<CustomerMongoDBModel>();

        if (id != null) {
            var idUuid = UUID.fromString(id);
            query.addCriteria(Criteria.where("_id").is(idUuid)).limit(limit).skip(offset);
        }

        if (phone != null) {
            var criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("firstPhone").is(phone),
                    Criteria.where("secondPhone").is(phone)
            );
            query.addCriteria(criteria);
        }

        if (id == null && phone == null) {
            query.limit(limit).skip(offset);
            customerMongoDBModels.addAll(mongoTemplate.find(query, CustomerMongoDBModel.class));
        }

        customerMongoDBModels.addAll(mongoTemplate.find(query, CustomerMongoDBModel.class));

        return CustomerMongoDBConverter.toEntities(customerMongoDBModels);
    }

    @Override
    public boolean existsByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInExistsByID();
        }

        var id = customerID.getValue();

        return customerDao.existsById(id);
    }

    @Override
    public boolean existsByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errPhoneIsRequiredInExistsByPhone();
        }

        var phoneStr = phone.getValue();

        return customerDao.existsByFirstPhoneOrSecondPhone(phoneStr, phoneStr);
    }
}
