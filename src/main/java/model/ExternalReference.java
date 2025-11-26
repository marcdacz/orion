package model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ExternalReference {
    private UUID id;
    private UUID assetId;
    private UUID providerId;
    private UUID referenceId;
    private String url;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExternalReference() {}

    public ExternalReference(final UUID id,
                             final UUID assetId,
                             final UUID providerId,
                             final UUID referenceId,
                             final String url,
                             final Map<String, Object> metadata,
                             final LocalDateTime createdAt,
                             final LocalDateTime updatedAt) {
        this.id = id;
        this.assetId = assetId;
        this.providerId = providerId;
        this.referenceId = referenceId;
        this.url = url;
        this.metadata = metadata;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(final UUID assetId) {
        this.assetId = assetId;
    }

    public UUID getProviderId() {
        return providerId;
    }

    public void setProviderId(final UUID providerId) {
        this.providerId = providerId;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(final Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final ExternalReference that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(assetId, that.assetId)
                && Objects.equals(providerId, that.providerId)
                && Objects.equals(url, that.url)
                && Objects.equals(metadata, that.metadata)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assetId, providerId, url, metadata, createdAt, updatedAt);
    }
}
