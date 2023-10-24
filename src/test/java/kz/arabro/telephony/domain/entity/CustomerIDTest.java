package kz.arabro.telephony.domain.entity;

import kz.arabro.telephony.util.exception.CodedException;
import kz.arabro.telephony.util.generator.StringGenerator;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerIDTest {

    @Test
    void newID_ReturnCustomerID() {
        var customerID = CustomerID.newID();
        assertNotNull(customerID);
    }

    @Test
    void from_ValueStrIsNull_ThrowEx() {
        var ex = assertThrows(CodedException.class, () -> CustomerID.from(null));
        assertEquals(CustomerError.CUSTOMER_ID_VALUE_IS_REQUIRED, ex.getCode());
        assertFalse(ex.getMessage().isEmpty());
    }

    @Test
    void from_ValueStrIsEmpty_ThrowEx() {
        var emptyValueStr = "";
        var ex = assertThrows(CodedException.class, () -> CustomerID.from(emptyValueStr));
        assertEquals(CustomerError.CUSTOMER_ID_VALUE_IS_REQUIRED, ex.getCode());
        assertFalse(ex.getMessage().isEmpty());
    }

    @Test
    void from_IllegalFormatOfValueStr_ThrowEx() {
        var strWithIllegalFormat = StringGenerator.getRandomString();
        var ex = assertThrows(CodedException.class, () -> CustomerID.from(strWithIllegalFormat));
        assertEquals(CustomerError.ILLEGAL_CUSTOMER_ID_VALUE, ex.getCode());
        assertFalse(ex.getMessage().isEmpty());
        assertNotNull(ex.getCause());
    }

    @Test
    void from_ValidValueStr_ReturnCustomerID() {
        var validValueStr = UUID.randomUUID().toString();
        var customerID = CustomerID.from(validValueStr);
        assertNotNull(customerID);
        assertEquals(validValueStr, customerID.getValue().toString());
    }

}