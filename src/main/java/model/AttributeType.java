package model;

public enum AttributeType {
    // Basic scalar types
    STRING,
    INTEGER,
    FLOAT,
    BOOLEAN,
    DATE,
    DATETIME,

    // Specialized scalar types
    EMAIL,
    URL,
    IP_ADDRESS,
    PHONE_NUMBER,

    // Reference types
    ASSET_REFERENCE,
    EXTERNAL_REFERENCE,

    // Collection/list types
    STRING_LIST,
    INTEGER_LIST,
    FLOAT_LIST,
    BOOLEAN_LIST
}
