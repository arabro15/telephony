package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.adapter.repository.dao.CustomerDao;
import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerMongoDBRepository;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.stub.CustomerMongoDbModelStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerMongoDBRepositoryImplTest {

    @Mock
    private CustomerDao customerDao;
    @Mock
    private MongoTemplate mongoTemplate;

    @Captor
    private ArgumentCaptor<CustomerMongoDBModel> customerMongoDBModelCaptor;

    private CustomerMongoDBRepository customerMongoDBRepository;

    @BeforeEach
    void setUp() {
        this.customerMongoDBRepository = new CustomerMongoDBRepositoryImpl(customerDao, mongoTemplate);
    }

    @Test
    void save_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.save(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED, ex.getCode());
    }

    @Test
    void save_ValueIsValid_CustomerSaved() {
        var customer = CustomerStub.getCustomer();

        customerMongoDBRepository.save(customer);
        verify(customerDao).save(customerMongoDBModelCaptor.capture());

        var customerMongoDBModel = customerMongoDBModelCaptor.getValue();
        assertNotNull(customerMongoDBModel);
        assertEquals(customerMongoDBModel.getCustomerID(), customer.getCustomerID().getValue());
        assertEquals(customerMongoDBModel.getName(), customer.getName().getValue());
        assertEquals(customerMongoDBModel.getYearOfBirth(), customer.getYearOfBirth().getValue());
        assertEquals(customerMongoDBModel.getFirstPhone(), customer.getFirstPhone().getValue());
        assertEquals(customerMongoDBModel.getSecondPhone(), customer.getSecondPhone().getValue());
        assertEquals(customerMongoDBModel.getCreatedAt(), Timestamp.from(customer.getCreatedAt()).toString());
    }

    @Test
    void deleteByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.deleteByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, ex.getCode());
    }

    @Test
    void deleteByID_ValueIsValid_DeleteCustomer() {
        var customerID = CustomerID.newID();
        var customerIDUuid = customerID.getValue();

        customerMongoDBRepository.deleteByID(customerID);
        verify(customerDao, times(1)).deleteById(customerIDUuid);
    }

    @Test
    void deleteByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.deleteByPhone(null));
        assertEquals(RepositoryError.CUSTOMER_PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, ex.getCode());
    }

    @Test
    void deleteByPhone_ValueIsValid_DeleteCustomer() {
        var phone = PhoneStub.getPhone();

        customerMongoDBRepository.deleteByPhone(phone);
        verify(customerDao, times(1)).deleteByFirstPhoneOrSecondPhone(phone.getValue(), phone.getValue());
    }

    @Test
    void updateById_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.updateByID(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_ID, ex.getCode());
    }

    @Test
    void updateByID_ValueIsValid_UpdateCustomer() {
        var customer = CustomerStub.getCustomer();

        customerMongoDBRepository.updateByID(customer);

        verify(customerDao, times(1)).existsById(any(UUID.class));
    }

    @Test
    void updateByPhone_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.updateByPhone(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_PHONE, ex.getCode());
    }

    @Test
    void updateByPhone_ValueIsValid_UpdateCustomer() {
        var customer = CustomerStub.getCustomer();

        customerMongoDBRepository.updateByPhone(customer);

        verify(customerDao, times(1)).findByFirstPhoneOrSecondPhone(customer.getFirstPhone().getValue(), customer.getSecondPhone().getValue());
    }


    @Test
    void findByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.findByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_ValueIsValid_ReturnCustomer() {
        var customerMongoDBModel = CustomerMongoDbModelStub.getCustomerMongoDBModel();
        when(customerDao.findById(any(UUID.class))).thenReturn(Optional.of(customerMongoDBModel));

        var customerOpt = customerMongoDBRepository.findByID(CustomerID.newID());
        assertNotNull(customerOpt);
        assertTrue(customerOpt.isPresent());

        assertEquals(customerMongoDBModel.getCustomerID(), customerOpt.get().getCustomerID().getValue());
    }

    @Test
    void findByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.findByPhone(null));
        assertEquals(RepositoryError.PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_ValueIsValid_ReturnCustomer() {
        var customerMongoDBModel = CustomerMongoDbModelStub.getCustomerMongoDBModel();
        when(customerDao.findByFirstPhoneOrSecondPhone(anyString(), anyString())).thenReturn(Optional.of(customerMongoDBModel));

        var customerOpt = customerMongoDBRepository.findByPhone(PhoneStub.getPhone());
        assertNotNull(customerOpt);
        assertTrue(customerOpt.isPresent());

        assertEquals(customerMongoDBModel.getCustomerID(), customerOpt.get().getCustomerID().getValue());
    }

    @Test
    void findWithFilter_FilterIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.findWithFilter(null));
        assertEquals(RepositoryError.FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, ex.getCode());
    }

    @Test
    void findWithFilter_ValueIsValid_ReturnCustomers() throws ParseException {
        var count = 10;
        var filter = new Filter(10, 0, null, null);
        var customerMongoDBModels = CustomerMongoDbModelStub.getCustomerMongoDBModels(count);

        var query = new Query();
        query.limit(filter.getLimit()).skip(0L);
        when(mongoTemplate.find(query, CustomerMongoDBModel.class)).thenReturn(customerMongoDBModels);

        var customers = customerMongoDBRepository.findWithFilter(filter);

        assertNotNull(customers);

        for (int i = 0; i < count; i++) {
            assertNotNull(customers.get(i));
            assertNotNull(customerMongoDBModels.get(i));


            assertEquals(customers.get(i).getCustomerID().getValue(), customerMongoDBModels.get(i).getCustomerID());
            assertEquals(customers.get(i).getName().getValue(), customerMongoDBModels.get(i).getName());
            assertEquals(customers.get(i).getYearOfBirth().getValue(), customerMongoDBModels.get(i).getYearOfBirth());
            assertEquals(customers.get(i).getFirstPhone().getValue(), customerMongoDBModels.get(i).getFirstPhone());
            assertEquals(customers.get(i).getSecondPhone().getValue(), customerMongoDBModels.get(i).getSecondPhone());
        }
    }

    @Test
    void existsByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.existsByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, ex.getCode());
    }

    @Test
    void existsByID_ValueIsValid_ReturnFalse() {
        when(customerDao.existsById(any(UUID.class))).thenReturn(false);

        var customerExists = customerMongoDBRepository.existsByID(CustomerID.newID());

        assertFalse(customerExists);
    }

    @Test
    void existsByID_ValueIsValid_ReturnTrue() {
        when(customerDao.existsById(any(UUID.class))).thenReturn(true);

        var customerExists = customerMongoDBRepository.existsByID(CustomerID.newID());

        assertTrue(customerExists);
    }

    @Test
    void existsByPhone_PhoneIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerMongoDBRepository.existsByPhone(null));
        assertEquals(RepositoryError.PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, ex.getCode());
    }

    @Test
    void existsByPhone_ValueIsValid_ReturnFalse() {
        when(customerDao.existsByFirstPhoneOrSecondPhone(anyString(), anyString())).thenReturn(false);

        var customerExists = customerMongoDBRepository.existsByPhone(PhoneStub.getPhone());

        assertFalse(customerExists);
    }

    @Test
    void existsByPhone_ValueIsValid_ReturnTrue() {
        when(customerDao.existsByFirstPhoneOrSecondPhone(anyString(), anyString())).thenReturn(true);

        var customerExists = customerMongoDBRepository.existsByPhone(PhoneStub.getPhone());

        assertTrue(customerExists);
    }
}