package seedu.duke.storage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.storage.exception.StorageOperationException;
import seedu.duke.ui.Ui;

/**
 * Represents a storage file.
 */
public class StorageFile {
    private static final String DEFAULT_STORAGE_FILEPATH = "data/hospital_data.json";
    private static final Logger logger = Logger.getLogger("StorageFile");

    Ui ui = new Ui();

    static {
        logger.setLevel(Level.SEVERE);
    }

    /** The file path of the storage file. */
    private final String filePath;

    public StorageFile() {
        this(DEFAULT_STORAGE_FILEPATH);
    }

    public StorageFile(String filePath) {
        this.filePath = filePath;
        assert filePath != null : "File path cannot be null";
        checkFileFound(filePath);
    }

    public String getFilePath() {
        assert filePath != null : "File path cannot be null";
        return filePath;
    }

    private void checkFileFound(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            logger.log(Level.INFO, "File not found, creating new file: {0}", filePath);
            try {
                // Create the file if it does not exist
                f.getParentFile().mkdirs();
                f.createNewFile();
                assert f.exists() : "File should exist after creation";

                JsonUtil.saveToFile(filePath); // Save an initial hospital to the file

                logger.log(Level.INFO, "File created successfully: {0}", filePath);
            } catch (IOException e) {
                ui.showToUser("Error creating file: " + e.getMessage());
                logger.log(Level.WARNING, "Error creating file: {0}", e.getMessage());
            } catch (StorageOperationException e) {
                ui.showToUser(e.getMessage());
            }
        }
    }

    public void save(Hospital hospital) {
        assert hospital != null : "Hospital cannot be null";
        logger.log(Level.INFO, "Going to save data to file: {0}", filePath);
        try {
            JsonUtil.saveToFile(hospital, filePath);
        } catch (StorageOperationException e) {
            ui.showToUser(e.getMessage());

        }
    }

    public Hospital load() {
        logger.log(Level.INFO, "Going to load data from file: {0}", filePath);
        try {
            return JsonUtil.loadFromFile(getFilePath());
        } catch (StorageOperationException e) {
            ui.showToUser("File is Corrupted! " + e.getMessage());
            StorageBackup.createBackupFile(filePath); // Create a backup file
            return new Hospital(); // Return an empty hospital
        }
    }

    @Override
    public String toString() {
        return "File Path: " + filePath;
    }

}
