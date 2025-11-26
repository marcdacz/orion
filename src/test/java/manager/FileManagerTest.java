package manager;

import exception.ValidationException;
import model.StoredFile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileManagerTest {
    private FileManager fileManager;
    private Path tempStorageRoot;

    @BeforeEach
    void setUp() throws IOException {
        tempStorageRoot = Files.createTempDirectory("file-manager-test");
        fileManager = new FileManager(tempStorageRoot);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempStorageRoot)
                .map(Path::toFile)
                .forEach(file -> {
                    if (!file.delete()) {
                        file.deleteOnExit();
                    }
                });
    }

    @Test
    void saveFile__throws_validation_exception__when_original_filename_is_null_or_blank() {
        assertThrows(ValidationException.class, () -> fileManager.saveFile(null, "text/plain", new byte[0]));
        assertThrows(ValidationException.class, () -> fileManager.saveFile("", "text/plain", new byte[0]));
    }

    @Test
    void saveFile__throws_validation_exception__when_data_is_null() {
        assertThrows(ValidationException.class, () -> fileManager.saveFile("test.txt", "text/plain", null));
    }

    @Test
    void saveFile__saves_file_and_returns_metadata() throws IOException {
        String originalFilename = "test.txt";
        String contentType = "text/plain";
        byte[] data = "Hello, World!".getBytes();

        StoredFile storedFile = fileManager.saveFile(originalFilename, contentType, data);

        assertNotNull(storedFile.getId());
        assertEquals(originalFilename, storedFile.getOriginalFilename());
        assertEquals(data.length, storedFile.getFileSize());
        assertEquals(contentType, storedFile.getContentType());

        Path savedFilePath = tempStorageRoot.resolve(storedFile.getStoragePath());
        assertTrue(Files.exists(savedFilePath));
        assertArrayEquals(data, Files.readAllBytes(savedFilePath));
    }

    @Test
    void resolvePath__returns_correct_path() {
        StoredFile storedFile = new StoredFile();
        storedFile.setStoragePath("test.txt");

        Path resolvedPath = fileManager.resolvePath(storedFile);

        assertEquals(tempStorageRoot.resolve("test.txt"), resolvedPath);
    }

    @Test
    void deleteFile__deletes_existing_file() throws IOException {
        String originalFilename = "test.txt";
        byte[] data = "Hello, World!".getBytes();

        StoredFile storedFile = fileManager.saveFile(originalFilename, "text/plain", data);
        Path savedFilePath = tempStorageRoot.resolve(storedFile.getStoragePath());

        assertTrue(Files.exists(savedFilePath));

        fileManager.deleteFile(storedFile);

        assertFalse(Files.exists(savedFilePath));
    }

    @Test
    void deleteFile__does_not_throw_when_file_does_not_exist() {
        StoredFile storedFile = new StoredFile();
        storedFile.setStoragePath("nonexistent.txt");

        assertDoesNotThrow(() -> fileManager.deleteFile(storedFile));
    }
}