package model;

import java.util.Objects;
import java.util.UUID;

public class AssetCategory {
    private UUID id;
    private String name;
    private UUID parentId;

    public AssetCategory() {}

    public AssetCategory(final UUID id, final String name, final UUID parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(final UUID parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final AssetCategory that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(parentId, that.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId);
    }
}
