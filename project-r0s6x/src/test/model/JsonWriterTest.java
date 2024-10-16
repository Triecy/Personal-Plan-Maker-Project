package model;

import persistence.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

class JsonWriterTest extends JsonTest {
    private AssignmentList al;
    private Assignment a1;
    private Assignment a2;

    @BeforeEach
    void runBefore() {
        al = new AssignmentList();
        a1 = new Assignment("A1", "a1", 5);
        a2 = new Assignment("A2", "a2", 3);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAssignmentList() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAssignmentList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAssignmentList.json");
            al = reader.read();
            assertEquals(0, al.getAssignmentList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAssignmentList() {
        try {
            al.addAssignment(a1);
            al.addAssignment(a2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAssignmentList.json");
            writer.open();
            writer.write(al);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAssignmentList.json");
            al = reader.read();
            List<Assignment> assignments = al.getAssignmentList();
            assertEquals(2, assignments.size());
            checkAssignment("A1", "a1", 5, assignments.get(0));
            checkAssignment("A2", "a2", 3, assignments.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
