package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAssignment {
    Assignment assignment1;
    Assignment assignment2;

    @BeforeEach
    void runBefore() {
        assignment1 = new Assignment("CPSC210 Project", "Finish Phase 1", 5);
        assignment2 = new Assignment("CPSC210 Review", "MT2 Review", 4);
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210 Project", assignment1.getName());
        assertEquals("Finish Phase 1", assignment1.getDescription());
        assertEquals(5, assignment1.getExpectedTime());
        assertEquals(0, assignment1.getActualTime());
        assertFalse(assignment1.getCompleteStatus());
    }

    @Test
    void testSetActualTime() {
        assignment1.setActualTime(2);
        assertEquals(2, assignment1.getActualTime());
        assignment2.setActualTime(4);
        assertEquals(4, assignment2.getActualTime());
    }

    @Test
    void testMarkAsCompleted() {
        assignment1.markAsCompleted();
        assertTrue(assignment1.getCompleteStatus());
        assignment1.markAsCompleted();
        assertTrue(assignment1.getCompleteStatus());
    }

    @Test
    void testMarkAsIncompleted() {
        assignment2.markAsIncompleted();
        assertFalse(assignment2.getCompleteStatus());
        assignment2.markAsCompleted();
        assertTrue(assignment2.getCompleteStatus());
    }

    @Test
    void testToString() {
        assertEquals("Assignment Name: CPSC210 Project\n"
                        + "Description: Finish Phase 1\n"
                        + "Expected Completion Time: 5 hours\n"
                        + "Actual Completion Time: 0 hours\n"
                        + "Completion Status: false\n\n", 
                        assignment1.toString());
    }

}
