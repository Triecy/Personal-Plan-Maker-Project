package ui;

import java.io.FileNotFoundException;

// Reference: JsonSerializationDemo, Paul Carter, 17/10/2020, java,
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Where the program gets start
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to my project!");
        try {
            new DailyPlan();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
