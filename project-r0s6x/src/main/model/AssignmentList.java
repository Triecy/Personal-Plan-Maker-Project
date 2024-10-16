package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// an assignment list that has all the listed assignments in the plan,
// users are allowed to add or remove assignment from the list
public class AssignmentList implements Writable {
    private ArrayList<Assignment> assignmentList;

    public AssignmentList() {
        this.assignmentList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the assignment into the assignment list
    public void addAssignment(Assignment assignment) {
        assignmentList.add(assignment);
        EventLog.getInstance().logEvent(new Event("Added Assignment: " + assignment.getName()));
    }

    // MODIFIES: this
    // EFFECTS: removes the assignment from the assignment list
    public void removeAssignment(Assignment assignment) {
        assignmentList.remove(assignment);
        EventLog.getInstance().logEvent(new Event("Removed Assignment: " + assignment.getName()));
    }

    public ArrayList<Assignment> getAssignmentList() {
        EventLog.getInstance().logEvent(new Event("Plan Viewed"));
        return assignmentList;
    }

    // REQUIRES: the assignment should already be in the list
    // EFFECTS: finds the given assignment from the assignment list
    public Assignment findAssignment(String assignName) {
        for (Assignment next : assignmentList) {
            if (assignName.equals(next.getName())) {
                return next;
            }
        }
        return null;
    }

    // EFFECTS: gets the total expected time spend on the assignment list
    public int getTotalExpectedTime() {
        int totalExpectedTime = 0;
        for (Assignment next : assignmentList) {
            totalExpectedTime += next.getExpectedTime();
        }
        return totalExpectedTime;
    }

    // EFFECTS: gets the total actual time spend on the assignment list
    public int getTotalActualTime() {
        int totalActualTime = 0;
        for (Assignment next : assignmentList) {
            totalActualTime += next.getActualTime();
        }
        return totalActualTime;
    }

    // EFFECTS: lists the incompleted assignments
    public ArrayList<Assignment> getIncompletedAssignments() {
        ArrayList<Assignment> incompletedAssignments = new ArrayList<Assignment>();
        for (Assignment next : assignmentList) {
            if (!next.getCompleteStatus()) {
                incompletedAssignments.add(next);
            }
        }
        EventLog.getInstance().logEvent(new Event("Report Viewd"));
        return incompletedAssignments;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Daily Plan", assignmentsToJson());
        return json;
    }

    // EFFECTS: returns assignments in this assignment list as a JSON array
    private JSONArray assignmentsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Assignment assignment : assignmentList) {
            jsonArray.put(assignment.toJson());
        }

        return jsonArray;
    }



}
