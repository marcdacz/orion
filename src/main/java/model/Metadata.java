package model;

public class Metadata {
    private String key;
    private String value;

    public Metadata() {}

    public Metadata(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }
}
