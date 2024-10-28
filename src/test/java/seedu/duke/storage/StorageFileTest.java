package seedu.duke.storage;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.hospital.Hospital.PatientNotFoundException;
import seedu.duke.data.task.TaskList.TaskNotFoundException;
import seedu.duke.storage.exception.StorageOperationException;

public class StorageFileTest {

    String hospitalJson;
    String filePath = "src/test/java/seedu/duke/data/hospital_data_load.json";
    private Hospital hospital = new Hospital();

    @BeforeEach
    public void setUp() {
        hospital.addPatient("Alice");
        hospital.addPatient("Bob");

        hospitalJson = """
                {
                  "patients" : [ {
                    "name" : "Alice",
                    "taskList" : {
                      "tasks" : [ {
                        "description" : "Consultation",
                        "isDone" : true
                      }, {
                        "description" : "Medication",
                        "isDone" : false
                      } ]
                    }
                  }, {
                    "name" : "Bob",
                    "taskList" : {
                      "tasks" : [ {
                        "description" : "Surgery",
                        "isDone" : false
                      }, {
                        "description" : "Medication",
                        "isDone" : false
                      } ]
                    }
                  } ]
                }
                    """;
    }

    @Test
    public void checkDefaultFilePath_success() {
        StorageFile storage = new StorageFile();
        assertEquals("data/hospital_data.json", storage.getFilePath());
    }

    @Test
    public void checkDefaultFilePathString_success() {
        StorageFile storage = new StorageFile();
        assertEquals("File Path: " + "data/hospital_data.json", storage.toString());
    }

    @Test
    public void checkFileNotFound_success() {
        String filePathNotFound = "src/test/java/seedu/duke/data/hospital_data_not_found.json";
        File file = new File(filePathNotFound);
        assertTrue(!file.exists());

        StorageFile storage = new StorageFile(filePathNotFound);

        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void checkFileFound_success() {
        StorageFile storage = new StorageFile(filePath);
        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    public void loadFromFile_success() throws TaskNotFoundException, PatientNotFoundException {
        StorageFile storage = new StorageFile(filePath);
        Hospital loadHospital = storage.load();

        assertEquals("Alice", loadHospital.getPatient(0).getName());
        assertEquals("Bob", loadHospital.getPatient(1).getName());
        assertEquals("Consultation",
                loadHospital.getPatient(0).getTaskList().getTask(0).getDescription());
        assertEquals("Medication",
                loadHospital.getPatient(0).getTaskList().getTask(1).getDescription());
        assertTrue(loadHospital.getPatient(0).getTaskList().getTask(0).isDone());
    }

    @Test
    public void saveToFile_success() throws StorageOperationException {
        String pathToSave = "src/test/java/seedu/duke/data/hospital_data_save.json";
        StorageFile storage = new StorageFile(pathToSave);

        File file = new File(pathToSave);
        // check if file exists
        if (file.exists()) {
            file.delete();
        }

        storage.save(hospital);
        assertTrue(file.exists());
    }

    @Test
    public void backupFile_success() {
        String filePath = "src/test/java/seedu/duke/data/hospital_data.json";
        String backupFilePath = "src/test/java/seedu/duke/data/hospital_data_backup.json";
        StorageBackup storageBackup = new StorageBackup(backupFilePath);

        File file = new File(backupFilePath);
        // check if file exists
        if (file.exists()) {
            file.delete();
        }

        storageBackup.createBackupFile(filePath);
        assertTrue(file.exists());

        // Delete the file after testing
        if (file.exists()) {
            file.delete();
        }
    }

}
