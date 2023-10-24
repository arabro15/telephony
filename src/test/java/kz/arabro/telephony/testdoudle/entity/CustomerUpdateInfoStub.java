package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.boundary.model.CustomerCreateInfo;
import kz.arabro.telephony.boundary.model.CustomerUpdateInfo;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.util.generator.StringGenerator;

import java.sql.Timestamp;
import java.time.Instant;

public class CustomerUpdateInfoStub {

    public static CustomerUpdateInfo getCustomerUpdateInfo() {
        return new CustomerUpdateInfo(
                CustomerID.newID().getValue().toString(),
                NameStub.getName().getValue(),
                YearStub.getYear().getValue(),
                PhoneStub.getPhone().getValue(),
                PhoneStub.getPhone().getValue(),
                Timestamp.from(Instant.now()).toString()
        );
    }

    public static CustomerUpdateInfo getCustomerUpdateInfoWithInvalidCreatedAt() {
        return new CustomerUpdateInfo(
                CustomerID.newID().getValue().toString(),
                NameStub.getName().getValue(),
                YearStub.getYear().getValue(),
                PhoneStub.getPhone().getValue(),
                PhoneStub.getPhone().getValue(),
                StringGenerator.getRandomString()
        );
    }
}
