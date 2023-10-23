package kz.arabro.telephony.adapter.repository.converter;

import kz.arabro.telephony.adapter.repository.RepositoryError;
import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.domain.entity.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

public final class CustomerMongoDBConverter {
    public static CustomerMongoDBModel toModel(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequired();
        }
        var id = customer.getCustomerID().getValue();
        var name = customer.getName().getValue();
        var yearOfBirth = customer.getYearOfBirth().getValue();
        var firstPhone = customer.getFirstPhone().getValue();
        var secondPhone = customer.getSecondPhone().getValue();
        var createdAt = Timestamp.from(customer.getCreatedAt()).toString();

        var customerMongoDBModel = new CustomerMongoDBModel();
        customerMongoDBModel.setCustomerID(id);
        customerMongoDBModel.setName(name);
        customerMongoDBModel.setYearOfBirth(yearOfBirth);
        customerMongoDBModel.setFirstPhone(firstPhone);
        customerMongoDBModel.setSecondPhone(secondPhone);
        customerMongoDBModel.setCreatedAt(createdAt);

        return customerMongoDBModel;
    }

    public static List<CustomerMongoDBModel> toModels(List<Customer> customers) {
        if (customers == null) {
            throw RepositoryError.errCustomersIsRequired();
        }

        return customers.stream().
                map(CustomerMongoDBConverter::toModel).
                toList();
    }

    public static Customer toEntity(CustomerMongoDBModel customerMongoDBModel) {
        if (customerMongoDBModel == null) {
            throw RepositoryError.errCustomerMongoDBModelIsRequired();
        }
        var customerID = CustomerID.from(customerMongoDBModel.getCustomerID().toString());
        var name = Name.of(customerMongoDBModel.getName());
        var yearOfBirth = Year.of(customerMongoDBModel.getYearOfBirth());
        var firstPhone = Phone.of(customerMongoDBModel.getFirstPhone());
        var secondPhone = Phone.of(customerMongoDBModel.getSecondPhone());

        Instant createdAt;
        try {
            createdAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
                    parse(customerMongoDBModel.getCreatedAt()).
                    toInstant();
        } catch (ParseException e) {
            throw RepositoryError.errParseCreatedAtToInstant(customerMongoDBModel.getCreatedAt(), e);
        }

        return new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();
    }

    public static List<Customer> toEntities(List<CustomerMongoDBModel> customerMongoDBModels) {
        if (customerMongoDBModels == null) {
            throw RepositoryError.errCustomerMongoDBModelsIsRequired();
        }

        return customerMongoDBModels.stream().
                map(CustomerMongoDBConverter::toEntity).
                toList();
    }

    private CustomerMongoDBConverter() {}
}
