package kz.arabro.telephony.boundary.model;

import lombok.Data;

@Data
public class CustomerCreateInfo {
    private final String name;
    private final String yearOfBirth;
    private final String firstPhone;
    private final String secondPhone;
}
