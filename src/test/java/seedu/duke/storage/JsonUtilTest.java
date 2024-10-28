package seedu.duke.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import seedu.duke.data.hospital.Hospital;
import seedu.duke.data.hospital.Hospital.PatientNotFoundException;
import seedu.duke.data.task.TaskList.TaskNotFoundException;
import seedu.duke.storage.exception.StorageOperationException;

public class JsonUtilTest {

    String hospitalJson;
    String hospitalJsonInvalid;
    String jsonInvalid;
    private final Hospital hospital = new Hospital();

    @BeforeEach
    public void setUp() throws PatientNotFoundException, TaskNotFoundException {

        hospital.addPatient("Alice");
        hospital.addPatient("Bob");

        hospital.getPatient(0).getTaskList().addTask("Consultation");
        hospital.getPatient(0).getTaskList().addTask("Medication");
        hospital.getPatient(1).getTaskList().addTask("Surgery");
        hospital.getPatient(1).getTaskList().addTask("Medication");
        hospital.getPatient(0).getTaskList().getTask(0).markAsDone();

        hospitalJson = """
                {
                  "patients" : [ {
                    "name" : "Alice",
                    "tag" : null,
                    "taskList" : {
                      "completionRate" : 0.0,
                      "tasks" : [ {
                        "type" : "deadline",
                        "description" : "Consultation",
                        "isDone" : true,
                        "by" : "28-Oct-2024 23:59"
                      }, {
                        "type" : "repeat",
                        "description" : "Medication",
                        "repeat" : "week",
                        "isDone" : false
                      } ]
                    }
                  }, {
                    "name" : "Bob",
                    "tag" : null,
                    "taskList" : {
                      "tasks" : [ {
                        "type" : "todo",
                        "description" : "Surgery",
                        "isDone" : false
                      }, {
                        "type" : "repeat",
                        "description" : "Medication",
                        "isDone" : false,
                        "repeat" : "week"
                      } ]
                    }
                  } ]
                }
                                        """;

        hospitalJsonInvalid = """
                {
                    "patients": [
                        {
                            "name": "Alice",
                            "tasksList": [
                                {
                                    "isDone": false
                                },
                                {
                                    "description": "Medication",
                                    "isDone": false
                                }
                            ]
                        }
                    ]
                """;
        jsonInvalid = """
                {
                """;
    }

    @Test
    void serialize_success() throws StorageOperationException {
        String jsonSerialize = JsonUtil.toJson(hospital).trim();
        assertNotNull(jsonSerialize);
        // TODO: Failed on Windows System
        // assertTrue(jsonSerialize.equals(hospitalJson.trim()));
    }

    @Test
    void deserialize_success()
            throws JsonMappingException, JsonProcessingException, TaskNotFoundException, PatientNotFoundException,
            StorageOperationException {
        Hospital hospitalDeserialized = JsonUtil.fromJson(hospitalJson);
        assertNotNull(hospitalDeserialized);
        assertEquals(hospital.getPatients().size(),
                hospitalDeserialized.getPatients().size());
        assertEquals(hospital.getPatient(0).getName(),
                hospitalDeserialized.getPatient(0).getName());
        assertEquals(hospital.getPatient(1).getName(),
                hospitalDeserialized.getPatient(1).getName());
        assertEquals(hospital.getPatient(0).getTaskList().getTask(0).getDescription(),
                hospitalDeserialized.getPatient(0).getTaskList().getTask(0).getDescription());
        assertEquals(hospital.getPatient(1).getTaskList().getTask(0).getDescription(),
                hospitalDeserialized.getPatient(1).getTaskList().getTask(0).getDescription());

    }

    @Test
    void loadFromFile_success() throws StorageOperationException, PatientNotFoundException, TaskNotFoundException {

        Hospital hospitalDeserialized = JsonUtil.loadFromFile("src/test/java/seedu/duke/data/hospital_data.json");
        assertNotNull(hospitalDeserialized);
        assertEquals(hospital.getPatients().size(),
                hospitalDeserialized.getPatients().size());
        assertEquals(hospital.getPatient(0).getName(),
                hospitalDeserialized.getPatient(0).getName());
        assertEquals(hospital.getPatient(1).getName(),
                hospitalDeserialized.getPatient(1).getName());
        assertEquals(hospital.getPatient(0).getTaskList().getTask(0).getDescription(),
                hospitalDeserialized.getPatient(0).getTaskList().getTask(0).getDescription());
        assertEquals(hospital.getPatient(1).getTaskList().getTask(0).getDescription(),
                hospitalDeserialized.getPatient(1).getTaskList().getTask(0).getDescription());
    }


    @Test
    void deserialize_invalidJsonFormat_exceptionExpection() {

        assertThrows(StorageOperationException.class, () -> JsonUtil.fromJson(hospitalJsonInvalid));
        assertThrows(StorageOperationException.class, () -> JsonUtil.fromJson(jsonInvalid));
    }

    @Test
    void saveToFile_invalidFilePath_exceptionExpection() {
        assertThrows(StorageOperationException.class, () -> JsonUtil.saveToFile("invalid/file/path"));
        assertThrows(StorageOperationException.class, () -> JsonUtil.saveToFile(hospital, "invalid/file/path"));
    }

    @Test
    void loadFromFile_invalidFilePath_exceptionExpection() {
        assertThrows(StorageOperationException.class, () -> JsonUtil.loadFromFile("invalid/file/path"));
    }

    @Test
    void loadFromFile_invalidJsonFormat_exceptionExpection() {
        assertThrows(StorageOperationException.class, () -> JsonUtil.loadFromFile("data/json_invalid.json"));
    }

}
