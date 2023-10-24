package kz.arabro.telephony.adapter.repository;

import kz.arabro.telephony.adapter.repository.mapper.CustomerMapper;
import kz.arabro.telephony.adapter.repository.model.CustomerPostgreSQLModel;
import kz.arabro.telephony.boundary.model.Filter;
import kz.arabro.telephony.boundary.repository.CustomerPostgreSQLRepository;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.testdoudle.entity.CustomerPostgreSQLModelStub;
import kz.arabro.telephony.testdoudle.entity.CustomerStub;
import kz.arabro.telephony.testdoudle.entity.PhoneStub;
import kz.arabro.telephony.util.exception.CodedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.Query;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerPostgreSQLRepositoryImplTest {
    @Mock
    private CustomerMapper customerMapper;

    @Captor
    private ArgumentCaptor<CustomerPostgreSQLModel> customerPostgreSQLModelCaptor;

    private CustomerPostgreSQLRepository customerPostgreSQLRepository;

    @BeforeEach
    void setUp() {
        this.customerPostgreSQLRepository = new CustomerPostgreSQLRepositoryImpl(customerMapper);
    }

    @Test
    void save_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.save(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED_IN_SAVE, ex.getCode());
    }

    @Test
    void save_ValueIsValid_CustomerSaved() {
        var customer = CustomerStub.getCustomer();

        customerPostgreSQLRepository.save(customer);
        verify(customerMapper).insertCustomer(customerPostgreSQLModelCaptor.capture());

        var customerPostgreSQLModel = customerPostgreSQLModelCaptor.getValue();
        assertNotNull(customerPostgreSQLModel);
        assertEquals(customerPostgreSQLModel.getCustomerID(), customer.getCustomerID().getValue());
        assertEquals(customerPostgreSQLModel.getName(), customer.getName().getValue());
        assertEquals(customerPostgreSQLModel.getYearOfBirth(), customer.getYearOfBirth().getValue());
        assertEquals(customerPostgreSQLModel.getFirstPhone(), customer.getFirstPhone().getValue());
        assertEquals(customerPostgreSQLModel.getSecondPhone(), customer.getSecondPhone().getValue());
        assertEquals(customerPostgreSQLModel.getCreatedAt(), Timestamp.from(customer.getCreatedAt()));
    }

    @Test
    void deleteByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.deleteByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_DELETE_BY_ID, ex.getCode());
    }

    @Test
    void deleteByID_ValueIsValid_DeleteCustomer() {
        var customerID = CustomerID.newID();
        var customerIDUuid = customerID.getValue();

        customerPostgreSQLRepository.deleteByID(customerID);
        verify(customerMapper, times(1)).deleteCustomerByID(customerIDUuid);
    }

    @Test
    void deleteByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.deleteByPhone(null));
        assertEquals(RepositoryError.CUSTOMER_PHONE_IS_REQUIRED_IN_DELETE_BY_PHONE, ex.getCode());
    }

    @Test
    void deleteByPhone_ValueIsValid_DeleteCustomer() {
        var phone = PhoneStub.getPhone();

        customerPostgreSQLRepository.deleteByPhone(phone);
        verify(customerMapper, times(1)).deleteCustomerByPhone(phone.getValue());
    }

    @Test
    void updateById_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.updateByID(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_ID, ex.getCode());
    }

    @Test
    void updateByID_ValueIsValid_UpdateCustomer() {
        var customer = CustomerStub.getCustomer();

        customerPostgreSQLRepository.updateByID(customer);

        verify(customerMapper, times(1)).updateCustomerByID(any(CustomerPostgreSQLModel.class));
    }

    @Test
    void updateByPhone_CustomerIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.updateByPhone(null));
        assertEquals(RepositoryError.CUSTOMER_IS_REQUIRED_IN_UPDATE_BY_PHONE, ex.getCode());
    }

    @Test
    void updateByPhone_ValueIsValid_UpdateCustomer() {
        var customer = CustomerStub.getCustomer();

        customerPostgreSQLRepository.updateByPhone(customer);

        verify(customerMapper, times(1)).updateCustomerByPhone(any(CustomerPostgreSQLModel.class));
    }


    @Test
    void findByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.findByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_FIND_BY_ID, ex.getCode());
    }

    @Test
    void findByID_ValueIsValid_ReturnCustomer() {
        var customerPostgreSQLModel = CustomerPostgreSQLModelStub.getCustomerPostgreSQLModel();
        when(customerMapper.selectCustomerByID(any(UUID.class))).thenReturn(customerPostgreSQLModel);

        var customerOpt = customerPostgreSQLRepository.findByID(CustomerID.newID());
        assertNotNull(customerOpt);
        assertTrue(customerOpt.isPresent());

        assertEquals(customerPostgreSQLModel.getCustomerID(), customerOpt.get().getCustomerID().getValue());
    }

    @Test
    void findByPhone_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.findByPhone(null));
        assertEquals(RepositoryError.PHONE_IS_REQUIRED_IN_FIND_BY_PHONE, ex.getCode());
    }

    @Test
    void findByPhone_ValueIsValid_ReturnCustomer() {
        var customerPostgreSQLModel = CustomerPostgreSQLModelStub.getCustomerPostgreSQLModel();
        when(customerMapper.selectCustomerByPhone(anyString())).thenReturn(customerPostgreSQLModel);

        var customerOpt = customerPostgreSQLRepository.findByPhone(PhoneStub.getPhone());
        assertNotNull(customerOpt);
        assertTrue(customerOpt.isPresent());

        assertEquals(customerPostgreSQLModel.getCustomerID(), customerOpt.get().getCustomerID().getValue());
    }

    @Test
    void findWithFilter_FilterIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.findWithFilter(null));
        assertEquals(RepositoryError.FILTER_IS_REQUIRED_IN_FIND_WITH_FILTER, ex.getCode());
    }

    @Test
    void findWithFilter_ValueIsValid_ReturnCustomers() throws ParseException {
        var count = 10;
        var filter = new Filter(10, 0, null, null);
        var customerPostgreSQLModels = CustomerPostgreSQLModelStub.getCustomerPostgreSQLModels(count);

        var query = new Query();
        query.limit(filter.getLimit()).skip(0L);
        when(customerMapper.selectCustomerWithFilter(any(Filter.class))).thenReturn(customerPostgreSQLModels);

        var customers = customerPostgreSQLRepository.findWithFilter(filter);

        assertNotNull(customers);

        for (int i = 0; i < count; i++) {
            assertNotNull(customers.get(i));
            assertNotNull(customerPostgreSQLModels.get(i));


            assertEquals(customers.get(i).getCustomerID().getValue(), customerPostgreSQLModels.get(i).getCustomerID());
            assertEquals(customers.get(i).getName().getValue(), customerPostgreSQLModels.get(i).getName());
            assertEquals(customers.get(i).getYearOfBirth().getValue(), customerPostgreSQLModels.get(i).getYearOfBirth());
            assertEquals(customers.get(i).getFirstPhone().getValue(), customerPostgreSQLModels.get(i).getFirstPhone());
            assertEquals(customers.get(i).getSecondPhone().getValue(), customerPostgreSQLModels.get(i).getSecondPhone());
        }
    }

    @Test
    void existsByID_CustomerIDIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.existsByID(null));
        assertEquals(RepositoryError.CUSTOMER_ID_IS_REQUIRED_IN_EXISTS_BY_ID, ex.getCode());
    }

    @Test
    void existsByID_ValueIsValid_ReturnFalse() {
        when(customerMapper.existsCustomerByID(any(UUID.class))).thenReturn(false);

        var customerExists = customerPostgreSQLRepository.existsByID(CustomerID.newID());

        assertFalse(customerExists);
    }

    @Test
    void existsByID_ValueIsValid_ReturnTrue() {
        when(customerMapper.existsCustomerByID(any(UUID.class))).thenReturn(true);

        var customerExists = customerPostgreSQLRepository.existsByID(CustomerID.newID());

        assertTrue(customerExists);
    }

    @Test
    void existsByPhone_PhoneIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> customerPostgreSQLRepository.existsByPhone(null));
        assertEquals(RepositoryError.PHONE_IS_REQUIRED_IN_EXISTS_BY_PHONE, ex.getCode());
    }

    @Test
    void existsByPhone_ValueIsValid_ReturnFalse() {
        when(customerMapper.existsCustomerByPhone(anyString())).thenReturn(false);

        var customerExists = customerPostgreSQLRepository.existsByPhone(PhoneStub.getPhone());

        assertFalse(customerExists);
    }

    @Test
    void existsByPhone_ValueIsValid_ReturnTrue() {
        when(customerMapper.existsCustomerByPhone(anyString())).thenReturn(true);

        var customerExists = customerPostgreSQLRepository.existsByPhone(PhoneStub.getPhone());

        assertTrue(customerExists);
    }
}