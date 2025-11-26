package manager;

import exception.ValidationException;
import model.StoredFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileManager {
    private final Path storageRoot;

    public FileManager(final Path storageRoot) {
        this.storageRoot = storageRoot;

        try {
            Files.createDirectories(storageRoot);
        } catch (final IOException e) {
            throw new RuntimeException("Unable to create storage root: " + storageRoot, e);
        }
    }

    /**
     * Save bytes for a file; returns StoredFile metadata.
     *
     * @param originalFilename display name
     * @param contentType mime-type (optional)
     * @param data file bytes
     * @return StoredFile metadata (id + storage path)
     */
    public StoredFile saveFile(final String originalFilename,
                               final String contentType,
                               final byte[] data) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new ValidationException("originalFilename cannot be empty");
        }
        if (data == null) {
            throw new ValidationException("file data cannot be null");
        }

        UUID id = UUID.randomUUID();
        String fileName = id.toString();
        Path target = storageRoot.resolve(fileName);

        try {
            Files.write(target, data, StandardOpenOption.CREATE_NEW);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to write file", e);
        }

        StoredFile sf = new StoredFile();
        sf.setId(id);
        sf.setOriginalFilename(originalFilename);
        sf.setStoragePath(storageRoot.relativize(target).toString());
        sf.setFileSize(data.length);
        sf.setContentType(contentType);
        sf.setAddedAt(LocalDateTime.now());
        return sf;
    }

    public Path resolvePath(final StoredFile file) {
        return storageRoot.resolve(file.getStoragePath());
    }

    public void deleteFile(final StoredFile file) {
        try {
            Files.deleteIfExists(resolvePath(file));
        } catch (final IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }
}
