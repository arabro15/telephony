package kz.arabro.telephony.adapter.controller.request;

import lombok.Data;

@Data
public class GetCustomerWithFilterRequest {
    private int limit;
    private int offset;
    private String id;
    private String phone;
}
