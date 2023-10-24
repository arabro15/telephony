package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.domain.entity.Name;
import kz.arabro.telephony.util.generator.StringGenerator;

public class NameStub {
    public static Name getName() {
        return Name.of(StringGenerator.getRandomString());
    }
}
