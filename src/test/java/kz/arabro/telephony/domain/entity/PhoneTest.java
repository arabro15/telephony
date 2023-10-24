package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void of_PhoneIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, ()-> Phone.of(null));
        assertEquals(CustomerError.PHONE_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_PhoneIsBlank_ThrowEx() {
        var blankValue = " ";
        var ex = assertThrows(CodedException.class, ()-> Phone.of(blankValue));
        assertEquals(CustomerError.PHONE_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_PhoneIsLengthLarge_ThrowEx() {
        var largeLength = String.valueOf(NumberGenerator.getRandomNumber(877_700_000_000L, 877_777_999_999L));
        var ex = assertThrows(CodedException.class, ()-> Phone.of(largeLength));
        assertEquals(CustomerError.ILLEGAL_PHONE_VALUE, ex.getCode());
    }

    @Test
    void of_PhoneIsIllegalFormatValue_ThrowEx() {
        var illegalFormat = StringGenerator.getRandomString(11);
        var ex = assertThrows(CodedException.class, ()-> Phone.of(illegalFormat));
        assertEquals(CustomerError.ILLEGAL_PHONE_VALUE, ex.getCode());
    }

    @Test
    void of_ValueIsValid_ReturnPhone() {
        var phoneStr = String.valueOf(NumberGenerator.getRandomNumber(87_700_000_000L, 87_777_999_999L));
        var phone = Phone.of(phoneStr);

        assertNotNull(phone);
        assertEquals(phoneStr, phone.getValue());
    }
}