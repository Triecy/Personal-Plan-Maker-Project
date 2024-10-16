package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// A new window adapter that asks the users whether to save their changes
public class SaveOrNotAdapter extends WindowAdapter {
    private DailyPlanGUI gui;
    
    //REQUIRES: gui is not null
    //MODIFIES: this
    //EFFECTS: construct a SaveOrNotAdapter with the provided DailyPlanGUI
    //         and assigns it to the field gui.
    public SaveOrNotAdapter(DailyPlanGUI gui) {
        this.gui = gui;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        gui.saveOrNot();
    }
}

