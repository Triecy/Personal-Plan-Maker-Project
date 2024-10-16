package ui;

import model.Assignment;
import model.AssignmentList;
import model.EventLog;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// DailyPlan GUI Application
public class DailyPlanGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/dailyplan.json";
    private AssignmentList assignmentList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton recordButton;
    private JButton reportButton;

    // EFFECTS: creates the GUI for the Daily Plan application
    public DailyPlanGUI() {
        super("Welcome To PlanWise!");
        mainPanel = new JPanel(new BorderLayout());

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 500);

        assignmentList = new AssignmentList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        addWindowListener(new SaveOrNotAdapter(this));

        loadOrNot();
        setupUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: sets up the user interface components
    private void setupUI() {
        setupTitleImage();

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setupTopButton(topButtonPanel);

        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setupBottomButton(bottomButtonPanel);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(topButtonPanel);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(bottomButtonPanel);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // EFFECTS: sets up the title and image of the main panel
    public void setupTitleImage() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel title = new JLabel("Welcome to PlanWise!");
        title.setFont(new Font("Bold Italic", Font.PLAIN, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(title, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon("src/main/ui/Visual.png");
        Image image = icon.getImage();
        Image logo = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon actualLogo = new ImageIcon(logo);
        JLabel appLogo = new JLabel(actualLogo);
        JPanel logoPanel = new JPanel();
        logoPanel.add(appLogo);

        titlePanel.add(logoPanel, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    }

    // EFFECTS: sets up the top buttons
    public void setupTopButton(JPanel topButtonPanel) {
        addButton = new JButton("Add Assignment");
        addButton.setPreferredSize(new Dimension(200, 40));
        addButton.setActionCommand("add");
        addButton.addActionListener(this);
        topButtonPanel.add(addButton);

        removeButton = new JButton("Remove Assignment");
        removeButton.setPreferredSize(new Dimension(200, 40));
        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);
        topButtonPanel.add(removeButton);

        viewButton = new JButton("View Your Plan");
        viewButton.setPreferredSize(new Dimension(200, 40));
        viewButton.setActionCommand("view");
        viewButton.addActionListener(this);
        topButtonPanel.add(viewButton);

    }

    // EFFECTS: sets up the bottom buttons
    public void setupBottomButton(JPanel bottomButtonPanel) {
        recordButton = new JButton("Record Progress");
        recordButton.setPreferredSize(new Dimension(200, 40));
        recordButton.setActionCommand("record");
        recordButton.addActionListener(this);
        bottomButtonPanel.add(recordButton);

        reportButton = new JButton("Get Daily Report");
        reportButton.setPreferredSize(new Dimension(200, 40));
        reportButton.setActionCommand("report");
        reportButton.addActionListener(this);
        bottomButtonPanel.add(reportButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "add":
                addAssignment();
                break;
            case "remove":
                removeAssignment();
                break;
            case "view":
                viewDailyPlan();
                break;
            case "record":
                recordProgress();
                break;
            case "report":
                showResult();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an assignment to the assignment list
    private void addAssignment() {
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setPreferredSize(new Dimension(400, 150));

        JLabel name = new JLabel("Please give your assignment a name:");
        JTextField nameAnswer = new JTextField(10);
        addPanel.add(name);
        addPanel.add(nameAnswer);

        JLabel description = new JLabel("Please give your assignment a brief description:");
        JTextField descriptionAnswer = new JTextField(10);
        addPanel.add(description);
        addPanel.add(descriptionAnswer);

        JLabel time = new JLabel("Set an expected completion time (hours):");
        JTextField timeAnswer = new JTextField(5);
        addPanel.add(time);
        addPanel.add(timeAnswer);
        int result = JOptionPane.showConfirmDialog(this, addPanel, "Add Assignment", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int expectedTime = Integer.parseInt(timeAnswer.getText());

            Assignment assignment = new Assignment(nameAnswer.getText(), descriptionAnswer.getText(), expectedTime);
            assignmentList.addAssignment(assignment);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an assignment from the assignment list
    private void removeAssignment() {
        JPanel removePanel = new JPanel();
        removePanel.setLayout(new BoxLayout(removePanel, BoxLayout.Y_AXIS));
        removePanel.setPreferredSize(new Dimension(400, 50));

        JLabel removeName = new JLabel("Which assignment would you like to remove?");
        JTextField removeAnswer = new JTextField(10);
        removePanel.add(removeName);
        removePanel.add(removeAnswer);

        int result = JOptionPane.showConfirmDialog(this, removePanel, "Remove Assignment", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String removeAssign = removeAnswer.getText();
            Assignment assignment = assignmentList.findAssignment(removeAssign);
            if (assignment != null) {
                assignmentList.removeAssignment(assignment);
            } else {
                JOptionPane.showMessageDialog(this, "Assignment not found!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: records progress for the assignments
    private void recordProgress() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            for (Assignment next : assignmentlist) {
                processRecord(next);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, your plan is empty. Try to add some new assignments!"); 
        }
    }
    
    // MODIFIES: this
    // EFFECTS: process the recording
    public void processRecord(Assignment next) {
        JPanel recordPanel = new JPanel();
        recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));
        recordPanel.setPreferredSize(new Dimension(400, 100));

        recordPanel.add(new JLabel("Assignment: " + next.getName()));

        recordPanel.add(new JLabel("What's your actual time spent on " + next.getName() + " today?"));
        JTextField actualTimeAnswer = new JTextField(5);

        recordPanel.add(actualTimeAnswer);

        recordPanel.add(new JLabel("Completed the assignment? (Click the checkbox if completed)"));
        JCheckBox completeCheckBox = new JCheckBox();
        recordPanel.add(completeCheckBox);

        int result = JOptionPane.showConfirmDialog(this, recordPanel, "Record Progress",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            next.setActualTime(Integer.parseInt(actualTimeAnswer.getText()));

            if (completeCheckBox.isSelected()) {
                next.markAsCompleted();
            } else {
                next.markAsIncompleted();
            }
        } else {
            return;
        }
    }

    // MODIFIES: this
    // EFFECTS: illustrates the current plan
    private void viewDailyPlan() {
        JFrame graphFrame = new JFrame("Daily Plan");
        graphFrame.setSize(600, 400);
        graphFrame.setLayout(new BorderLayout());

        
        JTextArea graphArea = new JTextArea();
        StringBuilder graphText = new StringBuilder();
        for (Assignment assignment : assignmentList.getAssignmentList()) {
            graphText.append("Assignment: ").append(assignment.getName()).append("\n");
            graphText.append("Description: ").append(assignment.getDescription()).append("\n");
            graphText.append("Expected Time: ").append(assignment.getExpectedTime()).append(" hours\n");
            graphText.append("Actual Time: ").append(assignment.getActualTime()).append(" hours\n");
            graphText.append("Completed?: ").append(assignment.getCompleteStatus() ? "Yes" : "No").append("\n");
            graphText.append("-----------------------------------\n");
        }
        graphArea.setText(graphText.toString());
        graphArea.setEditable(false);
        graphFrame.setLocationRelativeTo(null);

        graphFrame.add(new JScrollPane(graphArea), BorderLayout.CENTER);
        graphFrame.setVisible(true);
    }

    // EFFECTS: shows the statistics information of the plan by comparing the
    // difference between actual/expected time spent and the complete status
    public void showResult() {
        ArrayList<Assignment> assignmentlist = assignmentList.getAssignmentList();
        if (assignmentlist.size() != 0) {
            int totalExpectedTime = assignmentList.getTotalExpectedTime();
            int totalActualTime = assignmentList.getTotalActualTime();
            ArrayList<Assignment> incompletedAssignments = assignmentList.getIncompletedAssignments();

            StringBuilder report = new StringBuilder("Here is your PlanWise Report for today:\n\n");

            for (Assignment assignment : assignmentlist) {
                report.append(assignment.toString()).append("\n");
            }

            if (totalActualTime == totalExpectedTime) {
                report.append(equalTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments));
            } else if (totalActualTime < totalExpectedTime) {
                report.append(lessTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments));
            } else {
                report.append(moreTimeMessage(totalExpectedTime, totalActualTime, incompletedAssignments));
            }

            report.append("\nThat is for your PlanWise Report!");

            JOptionPane.showMessageDialog(this, report.toString(), "PlanWise Report", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, your plan is empty. Try to add some new assignments!",
                    "PlanWise Report", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: shows the messages when actual time = expected time
    public String equalTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        StringBuilder message = new StringBuilder();
        message.append("You totally spent ").append(totalActualTime).append(" hours on your assignments today,")
                .append(" which is exactly the same as you planned.\n");
        if (incompletedAssignments.size() == 0) {
            message.append("Also, you have completed all your assignments. Congrats!\n");
        } else {
            message.append("However, you still have the following assignment(s) to be completed:\n");
            for (Assignment assignment : incompletedAssignments) {
                message.append(assignment.getName()).append("\n");
            }
        }
        return message.toString();
    }

    // EFFECTS: shows the messages when actual time < expected time
    public String lessTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        StringBuilder message = new StringBuilder();
        message.append("You totally spent ").append(totalActualTime).append(" hours on your assignments today,")
                .append(" which is less than your plan of ").append(totalExpectedTime).append(" hours.\n");
        if (incompletedAssignments.size() == 0) {
            message.append("However, you have completed all your assignments. Congrats!\n");
        } else {
            message.append("Also, you still have the following assignment(s) to be completed:\n");
            for (Assignment assignment : incompletedAssignments) {
                message.append(assignment.getName()).append("\n");
            }
        }
        return message.toString();
    }

    // EFFECTS: shows the messages when actual time > expected time
    public String moreTimeMessage(int totalExpectedTime, int totalActualTime,
            ArrayList<Assignment> incompletedAssignments) {
        StringBuilder message = new StringBuilder();
        message.append("You totally spent ").append(totalActualTime).append(" hours on your assignments today,")
                .append(" which is more than your plan of ").append(totalExpectedTime).append(" hours.\n");
        if (incompletedAssignments.size() == 0) {
            message.append("Also, you have completed all your assignments. Congrats!\n")
                    .append("But PLEASE take breaks while executing your plan!\n");
        } else {
            message.append("However, you still have the following assignment(s) to be completed:\n");
            for (Assignment assignment : incompletedAssignments) {
                message.append(assignment.getName()).append("\n");
            }
        }
        return message.toString();
    }

    // EFFECTS: asks the user whether to load the saved plan
    public void saveOrNot() {
        int response = JOptionPane.showConfirmDialog(this, "Would you like to save your change(s)?", "Save Warning",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            saveDailyPlan();
        }
        printLogEvent(EventLog.getInstance());
    }

    // EFFECTS: asks the user whether to load the saved plan
    public void loadOrNot() {
        int response = JOptionPane.showConfirmDialog(this, "Would you like to load your saved plan?", "Load Plan",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loadDailyPlan();
        }
    }

    // EFFECTS: saves the assignment list to file
    private void saveDailyPlan() {
        try {
            jsonWriter.open();
            jsonWriter.write(assignmentList);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads assignment list from file
    private void loadDailyPlan() {
        try {
            assignmentList = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Loaded from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECT: print all Events in event log using a for loop
    public void printLogEvent(EventLog eventLog) {
        for (Event next : eventLog) {
            System.out.println("\n" + next);
        }
    }

    // EFFECTS: run the application
    public static void main(String[] args) {
        new DailyPlanGUI();
    }
}
