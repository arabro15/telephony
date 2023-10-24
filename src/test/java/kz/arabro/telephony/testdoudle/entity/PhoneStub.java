package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.util.generator.NumberGenerator;

public class PhoneStub {
    public static Phone getPhone() {
        return Phone.of(String.valueOf(NumberGenerator.getRandomNumber(8_700_000_0000L, 8777_999_9999L)));
    }
}
