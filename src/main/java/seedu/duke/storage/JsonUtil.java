package seedu.duke.storage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.storage.exception.StorageOperationException;

/**
 * Represents a utility class for JSON operations.
 */
public class JsonUtil {
    private static final Logger logger = Logger.getLogger("JsonUtil");

    static {
        logger.setLevel(Level.SEVERE); // Only show warnings and errors
    }

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT) // Readable format
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // Ignore unknown properties
            .findAndRegisterModules(); // Automatically register additional modules (future use);

    public static void saveToFile(String filePath) throws StorageOperationException {
        try {
            objectMapper.writeValue(new File(filePath), new Hospital());
        } catch (StreamWriteException e) {
            logger.log(Level.WARNING, "Json Stream Write Exception Caught. Failed to save data to file: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Stream Write Exception Caught. Failed to save data to file: " + e.getMessage());
        } catch (DatabindException e) {
            logger.log(Level.WARNING, "Json Data Bind Exception Caught. Failed to save data to file: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Data Bind Exception Caught. Failed to save data to file: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.WARNING, "I/O Exception Caught. Failed to save data to file: {0}", e.getMessage());
            throw new StorageOperationException("I/O Exception Caught. Failed to save data to file: " + e.getMessage());
        }

        logger.log(Level.INFO, "Data saved successfully at: {0}", filePath);
    }

    public static void saveToFile(Hospital hospital, String filePath) throws StorageOperationException {
        try {
            objectMapper.writeValue(new File(filePath), hospital);
        } catch (StreamWriteException e) {
            logger.log(Level.WARNING, "Json Stream Write Exception Caught. Failed to save data to file: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Stream Write Exception Caught. Failed to save data to file: " + e.getMessage());
        } catch (DatabindException e) {
            logger.log(Level.WARNING, "Json Data Bind Exception Caught. Failed to save data to file: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Data Bind Exception Caught. Failed to save data to file: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.WARNING, "I/O Exception Caught. Failed to save data to file: {0}", e.getMessage());
            throw new StorageOperationException("I/O Exception Caught. Failed to save data to file: " + e.getMessage());
        }

        logger.log(Level.INFO, "Data saved successfully at: {0}", filePath);
    }

    public static Hospital loadFromFile(String filePath) throws StorageOperationException {
        try {
            Hospital hospital = objectMapper.readValue(new File(filePath), Hospital.class);

            assert hospital != null : "Hospital object cannot be null";
            logger.log(Level.INFO, "Data loaded successfully from: {0}", filePath);

            return hospital;
        } catch (JsonParseException e) {
            logger.log(Level.WARNING, "Corrupted JSON data: {0}", e.getMessage());
            throw new StorageOperationException("Corrupted JSON data: " + e.getMessage());
        } catch (JsonMappingException e) {
            logger.log(Level.WARNING, "Error mapping JSON to object: {0}", e.getMessage());
            throw new StorageOperationException("Error mapping JSON to object: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.WARNING, "I/O error: {0}", e.getMessage());
            throw new StorageOperationException("I/O error: " + e.getMessage());
        }

    }

    public static String toJson(Hospital hospital) throws StorageOperationException {
        logger.log(Level.INFO, "Converting object to JSON");
        try {
            String hospitalJson = objectMapper.writeValueAsString(hospital);

            logger.log(Level.INFO, "Object converted to JSON successfully");
            assert hospitalJson != null : "JSON string cannot be null";

            return hospitalJson;
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Json Processing Exception Caught. Failed to convert object to JSON: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Processing Exception Caught. Failed to convert object to JSON: " + e.getMessage());
        }

    }

    public static Hospital fromJson(String json) throws StorageOperationException {
        logger.log(Level.INFO, "Converting JSON to object");
        Hospital hospital = null;

        try {
            hospital = objectMapper.readValue(json, Hospital.class);
        } catch (JsonMappingException e) {
            logger.log(Level.WARNING, "Error mapping JSON to object: {0}", e.getMessage());
            throw new StorageOperationException("Error mapping JSON to object: " + e.getMessage());
        } catch (JsonProcessingException e) {
            logger.log(Level.WARNING, "Json Processing Exception Caught. Failed to convert JSON to object: {0}",
                    e.getMessage());
            throw new StorageOperationException(
                    "Json Processing Exception Caught. Failed to convert JSON to object: " + e.getMessage());
        }

        if (hospital == null) {
            logger.log(Level.WARNING, "Failed to convert JSON to object");
            return new Hospital();
        }

        logger.log(Level.INFO, "JSON converted to object successfully");
        return hospital;
    }

}
