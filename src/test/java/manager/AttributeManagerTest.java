package manager;

import exception.ValidationException;
import model.AssetAttribute;
import model.AttributeType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AttributeManagerTest {

    // -----------------------------
    // STRING
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_string() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("name", AttributeType.STRING, "Laptop");
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_string() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("name", AttributeType.STRING, 12345);
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // INTEGER
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_integer() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("price", AttributeType.INTEGER, 1000);
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_integer() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("price", AttributeType.INTEGER, "NotAnInteger");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // FLOAT
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_float() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("weight", AttributeType.FLOAT, 12.5);
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_float() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("weight", AttributeType.FLOAT, "NotAFloat");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // BOOLEAN
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_boolean() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("isInsured", AttributeType.BOOLEAN, true);
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_boolean() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("isInsured", AttributeType.BOOLEAN, "true");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // DATE
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_date() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("warrantyExpiry", AttributeType.DATE, LocalDate.now());
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_date() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("warrantyExpiry", AttributeType.DATE, "2025-12-31");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // DATETIME
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_datetime() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("lastService", AttributeType.DATETIME, LocalDateTime.now());
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_datetime() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("lastService", AttributeType.DATETIME, "2025-12-31T10:00");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // EMAIL
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_email() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("ownerEmail", AttributeType.EMAIL, "user@example.com");
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_email() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("ownerEmail", AttributeType.EMAIL, "not-an-email");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // URL
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_url() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("link", AttributeType.URL, "https://example.com");
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_url() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("link", AttributeType.URL, "ht!tp://badurl");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // IP_ADDRESS
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_ip_address() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("ip", AttributeType.IP_ADDRESS, "192.168.1.1");
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_ip_address() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("ip", AttributeType.IP_ADDRESS, "999.999.999.999");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // PHONE_NUMBER
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_phone_number() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("phone", AttributeType.PHONE_NUMBER, "+61412345678");
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_phone_number() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("phone", AttributeType.PHONE_NUMBER, "abcd1234");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }

    // -----------------------------
    // ASSET_REFERENCE
    // -----------------------------
    @Test
    void validate__does_not_throw__valid_asset_reference() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("pairedAsset", AttributeType.ASSET_REFERENCE, UUID.randomUUID().toString());
        manager.validate(attr);
    }

    @Test
    void validate__throws_validation_exception__invalid_asset_reference() {
        AttributeManager manager = new AttributeManager();
        AssetAttribute attr = new AssetAttribute("pairedAsset", AttributeType.ASSET_REFERENCE, "not-a-uuid");
        assertThrows(ValidationException.class, () -> manager.validate(attr));
    }
}