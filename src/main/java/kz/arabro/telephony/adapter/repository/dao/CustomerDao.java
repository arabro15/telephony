package kz.arabro.telephony.adapter.repository.dao;

import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerDao extends MongoRepository<CustomerMongoDBModel, UUID> {

    void deleteByFirstPhoneOrSecondPhone(String firstPhone, String secondPhone);
    Optional<CustomerMongoDBModel> findByFirstPhoneOrSecondPhone(String firstPhone, String secondPhone);
    boolean existsByFirstPhoneOrSecondPhone(String firstPhone, String secondPhone);
}
