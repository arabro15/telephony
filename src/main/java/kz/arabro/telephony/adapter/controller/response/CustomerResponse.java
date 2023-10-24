package kz.arabro.telephony.adapter.controller.response;

import lombok.Data;

@Data
public class CustomerResponse {
    private String id;
    private String name;
    private String yearOfBirth;
    private String firstPhone;
    private String secondPhone;
    private String createdAt;
}
