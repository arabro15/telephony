package kz.arabro.telephony.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class CustomerID {

    private final UUID value;

    public static CustomerID newID() {
        var value = UUID.randomUUID();
        return new CustomerID(value);
    }

    public static CustomerID from(String valueStr) {
        if (valueStr == null || valueStr.isBlank()) {
            throw CustomerError.errCustomerIDValueIsRequired();
        }

        try {
            var value = UUID.fromString(valueStr);
            return new CustomerID(value);
        } catch (IllegalArgumentException e) {
            throw CustomerError.errIllegalCustomerIDValue(valueStr, e);
        }
    }

    private CustomerID(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (CustomerID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
