package kz.arabro.telephony.adapter.repository.converter;

import kz.arabro.telephony.adapter.repository.RepositoryError;
import kz.arabro.telephony.adapter.repository.model.CustomerPostgreSQLModel;
import kz.arabro.telephony.domain.entity.*;

import java.sql.Timestamp;
import java.util.List;

public final class CustomerPostgreSQLConverter {

    public static CustomerPostgreSQLModel toModel(Customer customer) {
        if (customer == null) {
            throw RepositoryError.errCustomerIsRequired();
        }
        var id = customer.getCustomerID().getValue();
        var name = customer.getName().getValue();
        var yearOfBirth = customer.getYearOfBirth().getValue();
        var firstPhone = customer.getFirstPhone().getValue();
        var secondPhone = customer.getSecondPhone().getValue();
        var createdAt = Timestamp.from(customer.getCreatedAt());

        var customerPostgreSQLModel = new CustomerPostgreSQLModel();
        customerPostgreSQLModel.setCustomerID(id);
        customerPostgreSQLModel.setName(name);
        customerPostgreSQLModel.setYearOfBirth(yearOfBirth);
        customerPostgreSQLModel.setFirstPhone(firstPhone);
        customerPostgreSQLModel.setSecondPhone(secondPhone);
        customerPostgreSQLModel.setCreatedAt(createdAt);

        return customerPostgreSQLModel;
    }

    public static List<CustomerPostgreSQLModel> toModels(List<Customer> customers) {
        if (customers == null) {
            throw RepositoryError.errCustomersIsRequired();
        }

        return customers.stream().
                map(CustomerPostgreSQLConverter::toModel).
                toList();
    }

    public static Customer toEntity(CustomerPostgreSQLModel customerPostgreSQLModel) {
        if (customerPostgreSQLModel == null) {
            throw RepositoryError.errCustomerPostgreSQLModelIsRequired();
        }
        var customerID = CustomerID.from(customerPostgreSQLModel.getCustomerID().toString());
        var name = Name.of(customerPostgreSQLModel.getName());
        var yearOfBirth = Year.of(customerPostgreSQLModel.getYearOfBirth());
        var firstPhone = Phone.of(customerPostgreSQLModel.getFirstPhone());
        var secondPhone = Phone.of(customerPostgreSQLModel.getSecondPhone());
        var createdAt = customerPostgreSQLModel.getCreatedAt().toInstant();

        return new CustomerBuilder().
                setCustomerID(customerID).
                setName(name).
                setYearOfBirth(yearOfBirth).
                setFirstPhone(firstPhone).
                setSecondPhone(secondPhone).
                setCreatedAt(createdAt).
                build();
    }

    public static List<Customer> toEntities(List<CustomerPostgreSQLModel> customerPostgreSQLModels) {
        if (customerPostgreSQLModels == null) {
            throw RepositoryError.errCustomerPostgreSQLModelsIsRequired();
        }

        return customerPostgreSQLModels.stream().
                map(CustomerPostgreSQLConverter::toEntity).
                toList();
    }

    private CustomerPostgreSQLConverter() {}
}
