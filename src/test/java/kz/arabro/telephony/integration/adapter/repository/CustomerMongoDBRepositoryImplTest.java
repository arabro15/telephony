package kz.arabro.telephony.integration.adapter.repository;

import kz.arabro.telephony.adapter.repository.dao.CustomerDao;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
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
class CustomerMongoDBRepositoryImplTest {

    @Autowired
    private CustomerMongoDBRepository customerMongoDBRepository;

    @Autowired
    private CustomerDao customerDao;

    private static final Customer CUSTOMER = CustomerStub.getCustomer();

    @BeforeEach
    void setUp() {
        customerMongoDBRepository.save(CUSTOMER);
    }

    @AfterEach
    void tearDown() {
        customerDao.deleteAll();
    }

    @Test
    void deleteByID_CustomerDeleted(){
        var customerToDelete = CustomerStub.getCustomer();
        customerMongoDBRepository.save(customerToDelete);

        var isExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerMongoDBRepository.deleteByID(customerToDelete.getCustomerID());

        var isNotExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void deleteByPhone_CustomerDeleteByFirstPhone(){
        var customerToDelete = CustomerStub.getCustomer();
        customerMongoDBRepository.save(customerToDelete);

        var isExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerMongoDBRepository.deleteByPhone(customerToDelete.getFirstPhone());

        var isNotExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void deleteByPhone_CustomerDeleteBySecondPhone(){
        var customerToDelete = CustomerStub.getCustomer();
        customerMongoDBRepository.save(customerToDelete);

        var isExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertTrue(isExists);

        customerMongoDBRepository.deleteByPhone(customerToDelete.getSecondPhone());

        var isNotExists = customerMongoDBRepository.existsByID(customerToDelete.getCustomerID());
        assertFalse(isNotExists);
    }

    @Test
    void updateByID_CustomerUpdated() {
        var customer = CustomerStub.getCustomer();
        customerMongoDBRepository.save(customer);

        var customerToUpdate = new CustomerBuilder().
                setCustomerID(customer.getCustomerID()).
                setName(NameStub.getName()).
                setYearOfBirth(customer.getYearOfBirth()).
                setFirstPhone(customer.getFirstPhone()).
                setSecondPhone(customer.getSecondPhone()).
                setCreatedAt(customer.getCreatedAt()).
                build();

        customerMongoDBRepository.updateByID(customerToUpdate);

        var customerFromDBOpt = customerMongoDBRepository.findByID(customerToUpdate.getCustomerID());

        assertTrue(customerFromDBOpt.isPresent());
        assertEquals(customer.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getName().getValue(), customerFromDBOpt.get().getName().getValue());
    }

    @Test
    void updateByPhone_CustomerUpdated() {
        var customer = CustomerStub.getCustomer();
        customerMongoDBRepository.save(customer);

        var customerToUpdate = new CustomerBuilder().
                setCustomerID(customer.getCustomerID()).
                setName(NameStub.getName()).
                setYearOfBirth(customer.getYearOfBirth()).
                setFirstPhone(customer.getFirstPhone()).
                setSecondPhone(PhoneStub.getPhone()).
                setCreatedAt(customer.getCreatedAt()).
                build();

        customerMongoDBRepository.updateByPhone(customerToUpdate);

        var customerFromDBOpt = customerMongoDBRepository.findByPhone(customerToUpdate.getFirstPhone());

        assertTrue(customerFromDBOpt.isPresent());
        assertEquals(customer.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getCustomerID().getValue(), customerFromDBOpt.get().getCustomerID().getValue());
        assertEquals(customerToUpdate.getName().getValue(), customerFromDBOpt.get().getName().getValue());
        assertEquals(customerToUpdate.getFirstPhone().getValue(), customerFromDBOpt.get().getFirstPhone().getValue());
        assertEquals(customerToUpdate.getSecondPhone().getValue(), customerFromDBOpt.get().getSecondPhone().getValue());
    }

    @Test
    void findByID_CustomerNotExist_ReturnEmptyOptional() {
        var customerOpt = customerMongoDBRepository.findByID(CustomerID.newID());
        assertTrue(customerOpt.isEmpty());
    }

    @Test
    void findByID_CustomerExist_ReturnCustomer() {
        var customerOpt = customerMongoDBRepository.findByID(CUSTOMER.getCustomerID());
        assertTrue(customerOpt.isPresent());

        var customerFromDB = customerOpt.get();
        assertEquals(CUSTOMER.getCustomerID().getValue(), customerFromDB.getCustomerID().getValue());
    }

    @Test
    void findByPhone_CustomerNotExist_ReturnEmptyOptional() {
        var customerOpt = customerMongoDBRepository.findByPhone(PhoneStub.getPhone());
        assertTrue(customerOpt.isEmpty());
    }

    @Test
    void findByPhone_CustomerExist_ReturnCustomer() {
        var customerOpt = customerMongoDBRepository.findByPhone(CUSTOMER.getFirstPhone());
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
        customers.forEach(customerMongoDBRepository::save);

        var filter = new Filter(limit, 0, null, null);

        var customersFromDB = customerMongoDBRepository.findWithFilter(filter);

        assertNotNull(customersFromDB);
        assertFalse(customersFromDB.isEmpty());
        assertEquals(limit, customersFromDB.size());
        customersFromDB.forEach(Assertions::assertNotNull);
    }

    @Test
    void existsByID_CustomerID_ReturnTrue() {
        var isExists = customerMongoDBRepository.existsByID(CUSTOMER.getCustomerID());
        assertTrue(isExists);
    }

    @Test
    void existByID_UnknownCustomer_ReturnFalse() {
        var isExists = customerMongoDBRepository.existsByID(CustomerID.newID());
        assertFalse(isExists);
    }

    @Test
    void existsByPhone_CustomerID_ReturnTrue() {
        var isExists = customerMongoDBRepository.existsByPhone(CUSTOMER.getFirstPhone());
        assertTrue(isExists);
    }

    @Test
    void existByPhone_UnknownCustomer_ReturnFalse() {
        var isExists = customerMongoDBRepository.existsByPhone(PhoneStub.getPhone());
        assertFalse(isExists);
    }
}
