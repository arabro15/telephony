package kz.arabro.telephony.boundary.model;

import lombok.Data;

@Data
public class Filter {
    private final int limit;
    private final int offset;
    private final String id;
    private final String phone;
}
