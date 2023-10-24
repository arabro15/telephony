package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YearTest {

    @Test
    void of_YearIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, ()-> Year.of(null));
        assertEquals(CustomerError.YEAR_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_YearIsBlank_ThrowEx() {
        var blankValue = " ";
        var ex = assertThrows(CodedException.class, ()-> Year.of(blankValue));
        assertEquals(CustomerError.YEAR_VALUE_IS_REQUIRED, ex.getCode());
    }

    @Test
    void of_YearIsLengthLarge_ThrowEx() {
        var largeLength = String.valueOf(NumberGenerator.getRandomNumber(19030, 20023));
        var ex = assertThrows(CodedException.class, ()-> Year.of(largeLength));
        assertEquals(CustomerError.ILLEGAL_YEAR_VALUE, ex.getCode());
    }

    @Test
    void of_YearIsIllegalFormatValue_ThrowEx() {
        var illegalFormat = StringGenerator.getRandomString(4);
        var ex = assertThrows(CodedException.class, ()-> Year.of(illegalFormat));
        assertEquals(CustomerError.ILLEGAL_YEAR_VALUE, ex.getCode());
    }

    @Test
    void of_ValueIsValid_ReturnYear() {
        var yearStr = String.valueOf(NumberGenerator.getRandomNumber(1930, 2023));
        var year = Year.of(yearStr);

        assertNotNull(year);
        assertEquals(yearStr, year.getValue());
    }

}