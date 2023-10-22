package kz.arabro.telephony.domain.entity;

public class Name {

    private final String value;

    public static Name of(String nameStr) {
        if (nameStr == null || nameStr.isBlank()) {
            throw CustomerError.errNameValueIsRequired();
        }
        return new Name(nameStr);
    }

    private Name(String nameStr) {
        this.value = nameStr;
    }

    public String getValue() {
        return this.value;
    }
}
