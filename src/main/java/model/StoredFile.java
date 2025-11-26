package model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class StoredFile {
    private UUID id;
    private String originalFilename;
    private String storagePath;
    private long fileSize;
    private String contentType;
    private LocalDateTime addedAt;

    public StoredFile() {}

    public StoredFile(final UUID id,
                      final String originalFilename,
                      final String storagePath,
                      final long fileSize,
                      final String contentType,
                      final LocalDateTime addedAt) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.storagePath = storagePath;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.addedAt = addedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(final String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(final String storagePath) {
        this.storagePath = storagePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(final long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(final LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final StoredFile that)) return false;
        return fileSize == that.fileSize
                && Objects.equals(id, that.id)
                && Objects.equals(originalFilename, that.originalFilename)
                && Objects.equals(storagePath, that.storagePath)
                && Objects.equals(contentType, that.contentType)
                && Objects.equals(addedAt, that.addedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalFilename, storagePath, fileSize, contentType, addedAt);
    }
}
