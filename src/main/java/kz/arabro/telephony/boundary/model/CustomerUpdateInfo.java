package kz.arabro.telephony.boundary.model;

import lombok.Data;

@Data
public class CustomerUpdateInfo {
    private final String customerID;
    private final String name;
    private final String yearOfBirth;
    private final String firstPhone;
    private final String secondPhone;
    private final String createdAt;
}
