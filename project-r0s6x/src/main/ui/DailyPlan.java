package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import model.Assignment;
import model.AssignmentList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Reference: CPSC210 Lab3-FlashcardReviewer
// JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A Daily Plan applicaiton that allows the user to add and remove their plan
public class DailyPlan {
    private static final String JSON_STORE = "./data/dailyplan.json";
    private Scanner scanner;
    private AssignmentList assignmentList;
    private boolean isProgramRunning;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: creates an instance of the Daily Plan console ui application
    public DailyPlan() throws FileNotFoundException {
        assignmentList = new AssignmentList();
        scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        isProgramRunning = true;

        loadOrNot();
        while (isProgramRunning) {
            run();
        }
        System.out.println("Goodbye!");
    }

    // EFFECTS: runs the application
    public void run() {
        displayMenu();
        String input = scanner.nextLine();

        if (input.equals("6")) {
            isProgramRunning = false;
            saveWarning();
        } else {
            processMenu(input);
        }
    }
    

    // EFFECTS: displays a list of operations that can be used by the user in the
    // menu
    public void displayMenu() {
        printDivider();
        System.out.println("Please select an option:\n");
        System.out.println("1. Add a new assignment");
        System.out.println("2: Remove an assignment");
        System.out.println("3. View your daily plan");
        System.out.println("4. Record your progress");
        System.out.println("5. PlanWise Report");
        System.out.println("6: Quit");
        printDivider();
    }

    // EFFECTS: processes the user's input in the menu
    public void processMenu(String input) {
        if (input.equals("1")) {
            addAssignment();
        } else if (input.equals("2")) {
            removeAssignment(); 
        } else if (input.equals("3")) {
            planReview();
        } else if (input.equals("4")) {
            recordProgress();
        } else if (input.equals("5")) {
            showResult();
        } else {
            System.out.println("Oops! Please choose a valid option!");
        }
        printDivider();
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment into the assignment list
    public void addAssignment() {
        System.out.println("Please give your new assignment a name:");
        String assignName = this.scanner.nextLine();

        System.out.println("\nPlease give your assignment " + assignName + " a brief discription:");
        String discription = this.scanner.nextLine();

        System.out.println("\nPlease set your assignment " + assignName + " an expected completion time (in hr):");
        int expectedTime = this.scanner.nextInt();
        scanner.nextLine();

        Assignment assignment = new Assignment(assignName, discription, expectedTime);
        assignmentList.addAssignment(assignment);
        System.out.println("You have added the assignment into your daily plan!");
    }

    // MODIFIES: this
    // EFFECTS: removes an assignment from the assignment list
    public void removeAssignment() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            System.out.println("Please give the name of the assignment you wanna remove:");
            String removeName = this.scanner.nextLine();

            Assignment assignment = assignmentList.findAssignment(removeName); // try-catch?
            assignmentList.removeAssignment(assignment);

            System.out.println("You have removed the assignment from your plan!");
        } else {
            System.out.println("Sorry, your plan is empty. Try to add some new assignments!");
        }
    }

    // EFFECTS: shows the plan (list of assignments/ assignment list) to the user
    public void planReview() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            System.out.println("Great! Here is your plan:");
            System.out.println("Daily Plan:\n" + assignmentlist); // illustrate
        } else {
            System.out.println("Sorry, your plan is empty. Try to add some new assignments!");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the actual time and complete status to the assignments in the
    // plan
    public void recordProgress() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            for (Assignment next : assignmentlist) {
                String name = next.getName();
                System.out.println("\nWhat's your actual time spent on " + name + " today?");
                int actualTime = this.scanner.nextInt();
                scanner.nextLine();
                next.setActualTime(actualTime);

                System.out.println("\nHave you completed " + name + " today? (Yes or No)");
                String completeStatus = this.scanner.nextLine();
                if (completeStatus.equals("Yes")) {
                    next.markAsCompleted();
                } else {
                    next.markAsIncompleted();
                }
            }
            System.out.println("Great! You have successfully record your progress!");
        } else {
            System.out.println("Sorry, your plan is empty. Try to add some new assignments!");
        }
    }

    // EFFECTS: shows the statistics information of the plan by comparing the
    // difference between
    // actual/expected time spent and the complete status
    public void showResult() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            int totalExpectedTime = assignmentList.getTotalExpectedTime();
            int totalActualTime = assignmentList.getTotalActualTime();
            ArrayList<Assignment> incompletedAssignments = assignmentList.getIncompletedAssignments();
            System.out.println("Here is your PlanWise Report for today:");
            System.out.println(assignmentlist);
            if (totalActualTime == totalExpectedTime) {
                equalTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments);
            } else if (totalActualTime < totalExpectedTime) {
                lessTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments);
            } else {
                moreTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments);
            }
            System.out.println("That is for your PlanWise Report!");
        } else {
            System.out.println("Sorry, your plan is empty. Try to add some new assignments!");
        }
    }

    // EFFECTS: shows the messages that would appear when actual time = expected
    // time
    public void equalTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        System.out.println("You totally spent " + totalActualTime + " hours on your assignments today,");
        System.out.println("which is exactly the same as you planned.\n");
        if (incompletedAssignments.size() == 0) {
            System.out.println("Also, you have completed all your assignments. Congrats!");
        } else {
            System.out.println("However, you still have the following assignment(s) to be completed:");
            System.out.println(incompletedAssignments);
        }
    }

    // EFFECTS: shows the messages that would appear when actual time < expected
    // time
    public void lessTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        System.out.println("You totally spent " + totalActualTime + " hours on your assignments today,");
        System.out.println("which is less than your plan of " + totalExpectedTime + " hours.\n");
        if (incompletedAssignments.size() == 0) {
            System.out.println("However, you have completed all your assignments. Congrats!");
        } else {
            System.out.println("Also, you still have the following assignment(s) to be completed:");
            System.out.println(incompletedAssignments);

        }
    }

    // EFFECTS: shows the messages that would appear when actual time > expected
    // time
    public void moreTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        System.out.println("You totally spent " + totalActualTime + " hours on your assignments today,");
        System.out.println("which is more than your plan of " + totalExpectedTime + " hours.\n");
        if (incompletedAssignments.size() == 0) {
            System.out.println("Also, you have completed all your assignments. Congrats!");
            System.out.println("But PLEASE take breaks while executing your plan!");
        } else {
            System.out.println("However, you still have the following assignment(s) to be completed:");
            System.out.println(incompletedAssignments);
        }
    }

    // MODIFEIS: this
    // EFFECTS: quit the application
    public void quit() {
        System.out.println("Thanks for using PlanWise!");
        System.out.println("See you tommorrow!");
        this.isProgramRunning = false;
    }

    // EFFECTS: prints a divider line
    public void printDivider() {
        System.out.println("----------------------------------------");
    }

    // EFFECTS: ask to save the progress or not
    private void saveWarning() {
        System.out.println("Would you like to save your change(s)? (Yes/No)");
        String saveOrNot = this.scanner.nextLine();

        if (saveOrNot.equals("Yes")) {
            saveDailyPlan();
        }
    }

    // EFFECTS: ask to save the progress or not
    private void loadOrNot() {
        System.out.println("Would you like to load your saved plan? (Yes/No)");
        String loadOrNot = this.scanner.nextLine();

        if (loadOrNot.equals("Yes")) {
            loadDailyPlan();
        }
    }

    // EFFECTS: saves the assignment list to file
    private void saveDailyPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(assignmentList);
            jsonWriter.close();
            System.out.println("You have saved your Daily Plan to " + JSON_STORE + "!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads assignment list from file
    private void loadDailyPlan() {
        try {
            assignmentList = jsonReader.read();
            System.out.println("You have loaded your Daily Plan from " + JSON_STORE + "!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
