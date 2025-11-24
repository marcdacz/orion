package model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Asset {
    private UUID id;
    private String name;
    private String description;

    // Relationships
    private AssetCategory category;
    private Set<Tag> tags;

    // Audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Asset() {}

    public Asset(final UUID id,
                 final String name,
                 final String description,
                 final AssetCategory category,
                 final Set<Tag> tags,
                 final LocalDateTime createdAt,
                 final LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(final AssetCategory category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(final Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(final Tag tag) {
        this.tags.remove(tag);
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
}
