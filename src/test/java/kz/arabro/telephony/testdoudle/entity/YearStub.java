package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.domain.entity.Phone;
import kz.arabro.telephony.domain.entity.Year;
import kz.arabro.telephony.util.generator.NumberGenerator;

public class YearStub {
    public static Year getYear() {
        return Year.of(String.valueOf(NumberGenerator.getRandomNumber(1930, 2023)));
    }
}