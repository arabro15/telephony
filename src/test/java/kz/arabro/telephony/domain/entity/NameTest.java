package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.generator.StringGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void of_NameIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, ()-> Name.of(null));
        assertEquals(CustomerError.NAME_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_NameIsBlank_ThrowEx() {
        var blankValue = " ";
        var ex = assertThrows(CodedException.class, ()-> Name.of(blankValue));
        assertEquals(CustomerError.NAME_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_ValueIsValid_ReturnName() {
        var nameStr = StringGenerator.getRandomString();
        var name = Name.of(nameStr);

        assertNotNull(name);
        assertEquals(nameStr, name.getValue());
    }

}