package persistence;

import org.json.JSONObject;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}