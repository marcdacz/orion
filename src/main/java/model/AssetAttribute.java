package model;

import java.util.Objects;

public class AssetAttribute {
    private String key;
    private AttributeType type;
    private Object value;

    public AssetAttribute() {}

    public AssetAttribute(final String key,
                          final AttributeType type,
                          final Object value) {
        this.key = key;
        this.type = type;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(final AttributeType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AssetAttribute{" +
                "key='" + key + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final AssetAttribute that)) return false;
        return Objects.equals(key, that.key) && type == that.type && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, type, value);
    }
}
