package kz.arabro.telephony.domain.entity;

import java.util.Objects;
import java.util.regex.Pattern;

public class Phone {
    private static final Pattern FORMAT_PATTERN = Pattern.compile("[^0-9]");
    private final String value;

    public static Phone of(String valueStr) {
        if (valueStr == null || valueStr.isBlank()) {
            throw CustomerError.errPhoneValueIsRequired();
        }
        if (valueStr.length() != 11) {
            throw CustomerError.errIllegalPhoneValue(valueStr);
        }

        var matcher = FORMAT_PATTERN.matcher(valueStr);
        if (matcher.find()) {
            throw CustomerError.errIllegalPhoneValue(valueStr);
        }

        return new Phone(valueStr);
    }

    private Phone(String value) {
        this.value = value;
    }

    public String getValue() {
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
        var that = (Phone) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
