package manager;

import exception.ValidationException;
import model.AssetAttribute;
import model.AttributeType;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class AttributeManager {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^((25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.|$)){4}$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{7,15}$");

    public void validate(final AssetAttribute attribute) {
        if (attribute == null) {
            throw new ValidationException("Attribute cannot be null");
        }

        if (attribute.getKey() == null || attribute.getKey().isBlank()) {
            throw new ValidationException("Attribute key cannot be empty");
        }

        validateValue(attribute.getType(), attribute.getValue());
    }

    private void validateValue(final AttributeType type, final Object value) {
        switch (type) {

            case STRING:
                if (!(value instanceof String)) {
                    throw new ValidationException("Expected STRING");
                }
                break;

            case INTEGER:
                if (!(value instanceof Integer)) {
                    throw new ValidationException("Expected INTEGER");
                }
                break;

            case FLOAT:
                if (!(value instanceof Double) && !(value instanceof Float)) {
                    throw new ValidationException("Expected FLOAT");
                }
                break;

            case BOOLEAN:
                if (!(value instanceof Boolean)) {
                    throw new ValidationException("Expected BOOLEAN");
                }
                break;

            case DATE:
                if (!(value instanceof LocalDate)) {
                    throw new ValidationException("Expected DATE");
                }
                break;

            case DATETIME:
                if (!(value instanceof LocalDateTime)) {
                    throw new ValidationException("Expected DATETIME");
                }
                break;

            case EMAIL:
                if (!(value instanceof String) ||
                        !EMAIL_PATTERN.matcher((String) value).matches()) {
                    throw new ValidationException("Invalid EMAIL");
                }
                break;

            case URL:
                if (!(value instanceof String)) {
                    throw new ValidationException("Invalid URL");
                }
                try {
                    URI uri = new URI((String) value);
                } catch (final URISyntaxException e) {
                    throw new ValidationException("Invalid URL: " + e.getMessage());
                }
                break;

            case IP_ADDRESS:
                if (!(value instanceof String) || !IPV4_PATTERN.matcher((String) value).matches()) {
                    throw new ValidationException("Invalid IP address: " + value);
                }
                break;

            case PHONE_NUMBER:
                if (!(value instanceof String) || !PHONE_PATTERN.matcher((String) value).matches()) {
                    throw new ValidationException("Invalid phone number: " + value);
                }
                break;

            case ASSET_REFERENCE:
                try {
                    UUID.fromString((String) value);
                } catch (Exception e) {
                    throw new ValidationException("Invalid asset reference UUID");
                }
                break;

            default:
                break;
        }
    }
}
