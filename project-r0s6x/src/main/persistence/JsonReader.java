package persistence;

import model.Assignment;
import model.AssignmentList;
import model.EventLog;

import model.Event;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads assignment list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads assignment list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AssignmentList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Plan Loaded"));
        return parseAssignmentList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses assignment list from JSON object and returns it
    private AssignmentList parseAssignmentList(JSONObject jsonObject) {
        AssignmentList al = new AssignmentList();
        addAssignments(al, jsonObject);
        return al;
    }

    // MODIFIES: al
    // EFFECTS: parses assignments from JSON object and adds them to assignment list
    private void addAssignments(AssignmentList al, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Daily Plan");
        for (Object json : jsonArray) {
            JSONObject nextAssignment = (JSONObject) json;
            addAssignment(al, nextAssignment);
        }
    }

    // MODIFIES: al
    // EFFECTS: parses assignment from JSON object and adds it to assignment list
    private void addAssignment(AssignmentList al, JSONObject jsonObject) {
        String assignName = jsonObject.getString("Assignment Name");
        String description = jsonObject.getString("Description");
        int expectedTime = jsonObject.getInt("Expected Completion Time");
        int actualTime = jsonObject.getInt("Actual Completion Time");
        boolean completeStatus = jsonObject.getBoolean("Completion Status");
        Assignment assignment = new Assignment(assignName, description, expectedTime);
        assignment.setActualTime(actualTime);
        if (completeStatus) {
            assignment.markAsCompleted();
        } else {
            assignment.markAsIncompleted();
        }
        al.addAssignment(assignment);
    }
}