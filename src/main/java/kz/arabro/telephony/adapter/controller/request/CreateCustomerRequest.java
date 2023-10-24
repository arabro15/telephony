package kz.arabro.telephony.adapter.controller.request;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String name;
    private String yearOfBirth;
    private String firstPhone;
    private String secondPhone;
}
