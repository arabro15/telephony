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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerMongoDBRepositoryImpl implements CustomerMongoDBRepository {

    private final CustomerDao customerDao;
    private final MongoTemplate mongoTemplate;

    public CustomerMongoDBRepositoryImpl(CustomerDao customerDao, MongoTemplate mongoTemplate) {
        this.customerDao = customerDao;
        this.mongoTemplate = mongoTemplate;
    }

    @Transactional
    @Override
    public CustomerID save(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequired();
        }

        var customerMongoDbModel = CustomerMongoDBConverter.toModel(customer);

        customerDao.save(customerMongoDbModel);

        return customer.getCustomerID();
    }

    @Transactional
    @Override
    public void deleteByID(CustomerID customerID) {
        if (customerID == null) {
            throw RepositoryError.errCustomerIDIsRequiredInDeleteByID();
        }

        customerDao.deleteById(customerID.getValue());
    }

    @Transactional
    @Override
    public void deleteByPhone(Phone phone) {
        if (phone == null) {
            throw RepositoryError.errCustomerPhoneIsRequiredInDeleteByPhone();
        }

        var phoneStr = phone.getValue();

        customerDao.deleteByFirstPhoneOrSecondPhone(phoneStr, phoneStr);
    }

    @Transactional
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

    @Transactional
    @Override
    public void updateByPhone(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequiredInUpdateByPhone();
        }

        var firstPhone = customer.getFirstPhone().getValue();
        var secondPhone = customer.getSecondPhone().getValue();

        var customerOpt = customerDao.findByFirstPhoneOrSecondPhone(firstPhone, secondPhone);

        if (customerOpt.isPresent()) {
            var id = customer.getCustomerID().getValue().toString();
            var idFromDb = customerOpt.get().getCustomerID().toString();

            if (id.equals(idFromDb)) {
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
        query.limit(limit).skip(offset);

        var customerMongoDBModels = new ArrayList<CustomerMongoDBModel>();

        if (id != null) {
            var idUuid = UUID.fromString(id);
            query.addCriteria(Criteria.where("_id").is(idUuid));
            customerMongoDBModels.addAll(mongoTemplate.find(query, CustomerMongoDBModel.class));
        }

        if (phone != null) {
            var criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("firstPhone").is(phone),
                    Criteria.where("secondPhone").is(phone)
            );
            query.addCriteria(criteria);
            customerMongoDBModels.addAll(mongoTemplate.find(query, CustomerMongoDBModel.class));
        }

        if (id == null && phone == null) {
            customerMongoDBModels.addAll(mongoTemplate.find(query, CustomerMongoDBModel.class));
        }

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
