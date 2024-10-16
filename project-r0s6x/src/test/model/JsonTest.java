package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    protected void checkAssignment(String assignName, String description, int expectedTime, Assignment assignment) {
        assertEquals(assignName, assignment.getName());
        assertEquals(description, assignment.getDescription());
        assertEquals(expectedTime, assignment.getExpectedTime());
    }
}
