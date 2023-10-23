package kz.arabro.telephony.adapter.repository.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("customer")
public class CustomerMongoDBModel {
    @Id
    private UUID customerID;
    private String name;
    private String yearOfBirth;
    private String firstPhone;
    private String secondPhone;
    private String createdAt;
}
