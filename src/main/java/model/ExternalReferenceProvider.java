package model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ExternalReferenceProvider {
    private UUID id;
    private String name;
    private String displayName;
    private UUID iconFileId;
    private String baseUrl;
    private Map<String, Object> metadata;

    public ExternalReferenceProvider() {}

    public ExternalReferenceProvider(final UUID id,
                                     final String name,
                                     final String displayName,
                                     final UUID iconFileId,
                                     final String baseUrl,
                                     final Map<String, Object> metadata) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.iconFileId = iconFileId;
        this.baseUrl = baseUrl;
        this.metadata = metadata;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public UUID getIconFileId() {
        return iconFileId;
    }

    public void setIconFileId(final UUID iconFileId) {
        this.iconFileId = iconFileId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final ExternalReferenceProvider that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(displayName, that.displayName)
                && Objects.equals(iconFileId, that.iconFileId)
                && Objects.equals(baseUrl, that.baseUrl)
                && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, displayName, iconFileId, baseUrl, metadata);
    }
}
