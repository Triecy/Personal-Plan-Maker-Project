package model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAssignmentList {
    AssignmentList assignmentList;
    Assignment assignment1;
    Assignment assignment2;
    Assignment assignment3;

    @BeforeEach
    void runbefore() {
        assignment1 = new Assignment("CPSC210 Project", "Finish Phase 1", 5);
        assignment2 = new Assignment("CPSC210 Review", "MT2 Review", 4);
        assignment3 = new Assignment("MATH200 Webwork", "Question 1-10", 3);
        assignment1.setActualTime(3);
        assignment1.markAsCompleted();
        assignment2.setActualTime(5);
        assignment2.markAsCompleted();
        assignment3.setActualTime(5);
        assignment3.markAsIncompleted();
        assignmentList = new AssignmentList();

    }

    @Test
    void testConstructor() {
        assertEquals(new ArrayList<Assignment>(), assignmentList.getAssignmentList());
    }

    @Test
    void testAddAssignment() {
        assignmentList.addAssignment(assignment1);
        ArrayList<Assignment> plan1 = assignmentList.getAssignmentList();
        assertEquals(1, plan1.size());
        assertEquals(assignment1, plan1.get(0));

        assignmentList.addAssignment(assignment3);
        ArrayList<Assignment> plan2 = assignmentList.getAssignmentList();
        assertEquals(2, plan2.size());
        assertEquals(assignment1, plan2.get(0));
        assertEquals(assignment3, plan2.get(1));
    }

    @Test
    void testRemoveAssignment() {
        assignmentList.addAssignment(assignment2);
        assignmentList.removeAssignment(assignment2);
        ArrayList<Assignment> plan3 = assignmentList.getAssignmentList();
        assertEquals(0, plan3.size());

        assignmentList.addAssignment(assignment2);
        assignmentList.removeAssignment(assignment1);
        ArrayList<Assignment> plan4 = assignmentList.getAssignmentList();
        assertEquals(1, plan4.size());
        assertEquals(assignment2, plan4.get(0));
    }

    @Test
    void testFindAssignment() {
        assertEquals(null, assignmentList.findAssignment("CPSC210 Project"));
        assignmentList.addAssignment(assignment1);
        assignmentList.addAssignment(assignment2);
        assertEquals(null, assignmentList.findAssignment("MATH200 Webwork"));
        assertEquals(assignment1, assignmentList.findAssignment("CPSC210 Project"));
    }

    @Test
    void testTotalExpectedTime() {
        assertEquals(0, assignmentList.getTotalExpectedTime());
        assignmentList.addAssignment(assignment1);
        assignmentList.addAssignment(assignment2);
        assignmentList.addAssignment(assignment3);
        assertEquals(12, assignmentList.getTotalExpectedTime());
    }

    @Test
    void testTotalActualTime() {
        assertEquals(0, assignmentList.getTotalActualTime());
        assignmentList.addAssignment(assignment1);
        assignmentList.addAssignment(assignment2);
        assignmentList.addAssignment(assignment3);
        assertEquals(13, assignmentList.getTotalActualTime());
    }

    @Test
    void testGetIncompletedAssignments() {
        ArrayList<Assignment> incompletedAssignments = new ArrayList<>();
        incompletedAssignments.add(assignment3);
        assignmentList.addAssignment(assignment1);
        assignmentList.addAssignment(assignment2);
        assignmentList.addAssignment(assignment3);
        assertEquals(incompletedAssignments, assignmentList.getIncompletedAssignments());
    }

}
