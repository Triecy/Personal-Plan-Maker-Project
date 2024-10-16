package model;

import org.json.JSONObject;
import persistence.Writable;

// Reference: B04-SimpleCalculatorStarterLecLab, Paul Carter, 11/9/2019, java,
// https://github.students.cs.ubc.ca/CPSC210/B04-SimpleCalculatorStarterLecLab
// JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represent an assignment in the daily plan that has
// an assignment name, a brief description, an expected time spent(in hr),
// an actual time spent (in hr), and a complete status.
public class Assignment implements Writable {
    private String assignName;
    private String description;
    private int expectedTime;
    private int actualTime;
    private boolean completeStatus;

    // REQUIRES: expectedTime >= 0
    // EFFECTS: construct an assignment with a assignmet name, a brief discription,
    // and an expected time spent;
    // the actual time spent so far is 0, the complete status is false for now
    public Assignment(String assignName, String description, int expectedTime) {
        this.assignName = assignName;
        this.description = description;
        this.expectedTime = expectedTime;
        actualTime = 0;
        completeStatus = false;
    }

    public void setActualTime(int actualTime) {
        this.actualTime = actualTime;
        EventLog.getInstance().logEvent(new Event("Actual Time Updated: " + getName()));
    }

    // MODIFIES: this
    // EFFECTS: marks the assignment as completed
    public void markAsCompleted() {
        completeStatus = true;
        EventLog.getInstance().logEvent(new Event("Marked as Completed: " + getName()));
    }

    // MODIFIES: this
    // EFFECTS: marks the assignment as incompleted
    public void markAsIncompleted() {
        completeStatus = false;
        EventLog.getInstance().logEvent(new Event("Marked as Incompleted: " + getName()));
    }

    public String getName() {
        return assignName;
    }

    public String getDescription() {
        EventLog.getInstance().logEvent(new Event("Plan viewed"));
        return description;
    }

    public int getExpectedTime() {
        return expectedTime;
    }

    public int getActualTime() {
        return actualTime;
    }

    public boolean getCompleteStatus() {
        return completeStatus;
    }

    // EFFECTS: converts the assignment to string
    public String toString() {
        return "Assignment Name: " + assignName + "\nDescription: " + description + "\nExpected Completion Time: "
                + expectedTime
                + " hours" + "\nActual Completion Time: " + actualTime + " hours" + "\nCompletion Status: "
                + completeStatus
                + "\n\n";
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Assignment Name", assignName);
        json.put("Description", description);
        json.put("Expected Completion Time", expectedTime);
        json.put("Actual Completion Time", actualTime);
        json.put("Completion Status", completeStatus);
        return json;
    }

}
