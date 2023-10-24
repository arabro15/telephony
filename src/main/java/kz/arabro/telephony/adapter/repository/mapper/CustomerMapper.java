package kz.arabro.telephony.adapter.repository.mapper;

import kz.arabro.telephony.adapter.repository.model.CustomerPostgreSQLModel;
import kz.arabro.telephony.boundary.model.Filter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CustomerMapper {
    void insertCustomer(@Param("model") CustomerPostgreSQLModel model);
    void deleteCustomerByID(@Param("id") UUID id);
    void deleteCustomerByPhone(@Param("phone") String phone);
    void deleteAll();
    void updateCustomerByID(@Param("model") CustomerPostgreSQLModel model);
    void updateCustomerByPhone(@Param("model") CustomerPostgreSQLModel model);
    CustomerPostgreSQLModel selectCustomerByID(@Param("id") UUID id);
    CustomerPostgreSQLModel selectCustomerByPhone(@Param("phone") String phone);
    List<CustomerPostgreSQLModel> selectCustomerWithFilter(@Param("filter") Filter filter);
    boolean existsCustomerByID(@Param("id") UUID id);
    boolean existsCustomerByPhone(@Param("phone") String phone);
}
