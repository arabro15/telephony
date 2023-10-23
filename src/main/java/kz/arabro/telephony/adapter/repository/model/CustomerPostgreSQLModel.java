package kz.arabro.telephony.adapter.repository.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class CustomerPostgreSQLModel {
    private UUID customerID;
    private String name;
    private String yearOfBirth;
    private String firstPhone;
    private String secondPhone;
    private Timestamp createdAt;
}
