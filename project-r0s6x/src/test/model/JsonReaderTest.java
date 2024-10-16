package model;

import persistence.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonReaderTest extends JsonTest {
    Assignment a1;
    Assignment a2;

    @BeforeEach
    void runBefore() {
        a1 = new Assignment("A1", "a1", 5);
        a2 = new Assignment("A2", "a2", 3);
        a2.setActualTime(4);
        a2.markAsCompleted();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AssignmentList al = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAssignmentList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAssignmentList.json");
        try {
            AssignmentList al = reader.read();
            assertEquals(0, al.getAssignmentList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAssignmentList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAssignmentList.json");
        try {
            AssignmentList al = reader.read();
            List<Assignment> assignments = al.getAssignmentList();
            assertEquals(2, assignments.size());
            checkAssignment("A1", "a1", 5, assignments.get(0));
            checkAssignment("A2", "a2", 3, assignments.get(1));
            Assignment as1 = assignments.get(0);
            Assignment as2 = assignments.get(1);
            assertEquals(0, as1.getActualTime());
            assertEquals(4, as2.getActualTime());
            assertFalse(as1.getCompleteStatus());
            assertTrue(as2.getCompleteStatus());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}