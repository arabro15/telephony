package kz.arabro.telephony.domain.entity;

import java.util.regex.Pattern;

public class Year {

    private static final Pattern FORMAT_PATTERN = Pattern.compile("[^0-9]");
    private final String value;

    public static Year of(String valueStr) {
        if (valueStr == null || valueStr.isBlank()) {
            throw CustomerError.errYearValueIsRequired();
        }
        if (valueStr.length() != 4) {
            throw CustomerError.errIllegalYearValue(valueStr);
        }

        var matcher = FORMAT_PATTERN.matcher(valueStr);
        if (matcher.find()) {
            throw CustomerError.errIllegalYearValue(valueStr);
        }

        return new Year(valueStr);
    }
    public String getValue() {
        return value;
    }

    private Year(String value) {
        this.value = value;
    }
}
