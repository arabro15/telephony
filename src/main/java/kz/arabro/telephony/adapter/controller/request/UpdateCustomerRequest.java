package kz.arabro.telephony.adapter.controller.request;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String id;
    private String name;
    private String yearOfBirth;
    private String firstPhone;
    private String secondPhone;
    private String createdAt;
}
