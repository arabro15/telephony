package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;

public class CustomerCreateInfoStub {

    public static CustomerCreateInfo getCustomerCreateInfo() {
        return new CustomerCreateInfo(
                NameStub.getName().getValue(),
                YearStub.getYear().getValue(),
                PhoneStub.getPhone().getValue(),
                PhoneStub.getPhone().getValue()
        );
    }
}
