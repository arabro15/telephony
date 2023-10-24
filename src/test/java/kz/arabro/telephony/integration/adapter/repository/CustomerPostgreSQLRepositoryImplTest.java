package kz.arabro.telephony.integration.adapter.repository;

import kz.arabro.telephony.adapter.repository.mapper.CustomerMapper;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.domain.entity.Customer;
import kz.arabro.telephony.domain.entity.CustomerBuilder;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.testdoudle.entity.NameStub;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class CustomerPostgreSQLRepositoryImplTest {

    @Autowired
    private CustomerPostgreSQLRepository customerPostgreSQLRepository;

    @Autowired
    private CustomerMapper customerMapper;

    private static final Customer CUSTOMER = CustomerStub.getCustomer();

    @BeforeEach
    void setUp() {
        customerPostgreSQLRepository.save(CUSTOMER);
    }

    @AfterEach
    void tearDown() {
        customerMapper.deleteAll();
    }

    @Test
    void deleteByID_CustomerDeleted(){
        var customerToDelete = CustomerStub.getCustomer();
        customerPostgreSQLRepository.save(customerToDelete);

        var isExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerPostgreSQLRepository.deleteByID(customerToDelete.getCustomerID());

        var isNotExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void deleteByPhone_CustomerDeleteByFirstPhone(){
        var customerToDelete = CustomerStub.getCustomer();
        customerPostgreSQLRepository.save(customerToDelete);

        var isExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerPostgreSQLRepository.deleteByPhone(customerToDelete.getFirstPhone());

        var isNotExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void deleteByPhone_CustomerDeleteBySecondPhone(){
        var customerToDelete = CustomerStub.getCustomer();
        customerPostgreSQLRepository.save(customerToDelete);

        var isExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerPostgreSQLRepository.deleteByPhone(customerToDelete.getSecondPhone());

        var isNotExists = customerPostgreSQLRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void updateByID_CustomerUpdated() {
        var customer = CustomerStub.getCustomer();
        customerPostgreSQLRepository.save(customer);

        var customerToUpdate = new CustomerBuilder().
                setCustomerID(customer.getCustomerID()).
                setName(NameStub.getName()).
                setYearOfBirth(customer.getYearOfBirth()).
                setFirstPhone(customer.getFirstPhone()).
                setSecondPhone(customer.getSecondPhone()).
                setCreatedAt(customer.getCreatedAt()).
                build();

        customerPostgreSQLRepository.updateByID(customerToUpdate);

        var customerFromDBOpt = customerPostgreSQLRepository.findByID(customerToUpdate.getCustomerID());

        assertTrue(customerFromDBOpt.isPresent());
        assertEquals(customer.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getName().getValue(), customerFromDBOpt.get().getName().getValue());
    }

    @Test
    void updateByPhone_CustomerUpdated() {
        var customer = CustomerStub.getCustomer();
        customerPostgreSQLRepository.save(customer);

        var customerToUpdate = new CustomerBuilder().
                setCustomerID(customer.getCustomerID()).
                setName(NameStub.getName()).
                setYearOfBirth(customer.getYearOfBirth()).
                setFirstPhone(customer.getFirstPhone()).
                setSecondPhone(PhoneStub.getPhone()).
                setCreatedAt(customer.getCreatedAt()).
                build();

        customerPostgreSQLRepository.updateByPhone(customerToUpdate);

        var customerFromDBOpt = customerPostgreSQLRepository.findByPhone(customerToUpdate.getFirstPhone());

        assertTrue(customerFromDBOpt.isPresent());
        assertEquals(customer.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getName().getValue(), customerFromDBOpt.get().getName().getValue());
        assertEquals(customerToUpdate.getFirstPhone().getValue(), customerFromDBOpt.get().getFirstPhone().getValue());
        assertEquals(customerToUpdate.getSecondPhone().getValue(), customerFromDBOpt.get().getSecondPhone().getValue());
    }

    @Test
    void findByID_CustomerNotExist_ReturnEmptyOptional() {
        var customerOpt = customerPostgreSQLRepository.findByID(CustomerID.newID());
        assertTrue(customerOpt.isEmpty());
    }

    @Test
    void findByID_CustomerExist_ReturnCustomer() {
        var customerOpt = customerPostgreSQLRepository.findByID(CUSTOMER.getCustomerID());
        assertTrue(customerOpt.isPresent());

        var customerFromDB = customerOpt.get();
        assertEquals(CUSTOMER.getCustomerID().getValue(), customerFromDB.getCustomerID().getValue());
    }

    @Test
    void findByPhone_CustomerNotExist_ReturnEmptyOptional() {
        var customerOpt = customerPostgreSQLRepository.findByPhone(PhoneStub.getPhone());
        assertTrue(customerOpt.isEmpty());
    }

    @Test
    void findByPhone_CustomerExist_ReturnCustomer() {
        var customerOpt = customerPostgreSQLRepository.findByPhone(CUSTOMER.getFirstPhone());
        assertTrue(customerOpt.isPresent());

        var customerFromDB = customerOpt.get();
        assertEquals(CUSTOMER.getCustomerID().getValue(), customerFromDB.getCustomerID().getValue());
        assertEquals(CUSTOMER.getFirstPhone().getValue(), customerFromDB.getFirstPhone().getValue());
    }

    @Test
    void findWithFilter_ReturnCustomers() {
        var count = 10;
        var limit = 5;
        var customers = CustomerStub.getCustomers(count);
        customers.forEach(customerPostgreSQLRepository::save);

        var filter = new Filter(limit, 0, null, null);

        var customersFromDB = customerPostgreSQLRepository.findWithFilter(filter);

        assertNotNull(customersFromDB);
        assertFalse(customersFromDB.isEmpty());
        assertEquals(limit, customersFromDB.size());
        customersFromDB.forEach(Assertions::assertNotNull);
    }

    @Test
    void existsByID_CustomerID_ReturnTrue() {
        var isExists = customerPostgreSQLRepository.existsByID(CUSTOMER.getCustomerID());
        assertTrue(isExists);
    }

    @Test
    void existByID_UnknownCustomer_ReturnFalse() {
        var isExists = customerPostgreSQLRepository.existsByID(CustomerID.newID());
        assertFalse(isExists);
    }

    @Test
    void existsByPhone_CustomerID_ReturnTrue() {
        var isExists = customerPostgreSQLRepository.existsByPhone(CUSTOMER.getFirstPhone());
        assertTrue(isExists);
    }

    @Test
    void existByPhone_UnknownCustomer_ReturnFalse() {
        var isExists = customerPostgreSQLRepository.existsByPhone(PhoneStub.getPhone());
        assertFalse(isExists);
    }
}
