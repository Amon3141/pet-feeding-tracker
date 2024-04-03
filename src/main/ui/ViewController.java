package ui;

import model.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;

// Represents the view controller that holds shared information and controls
// which panel (page) is to be displayed at the time
public class ViewController {
    private static ViewController INSTANCE;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String JSON_STORE = "./data/mypets.json";

    private final JFrame frame;
    private MyPets myPets;
    private ViewAbstract currentPanel;

    //EFFECTS: instantiates the object
    private ViewController() {
        frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                logEvents();
                System.exit(0);
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: run the application and starts from LoadView()
    public void run() {
        currentPanel = new LoadView();
        frame.setTitle(currentPanel.getFrameTitle());
        frame.add(currentPanel);
    }

    //MODIFIES: this
    //EFFECTS: the function that navigates from one view from another
    public void navigateTo(ViewAbstract newPanel) {
        frame.remove(currentPanel);
        currentPanel = newPanel;
        frame.setTitle(newPanel.getFrameTitle());
        frame.add(currentPanel);

        frame.revalidate();
        frame.repaint();
    }

    //EFFECTS: returns the singleton instance of this object
    public static ViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewController();
        }
        return INSTANCE;
    }

    //EFFECTS: logs the events using EventLog instance
    public static void logEvents() {
        for (Event ev : EventLog.getInstance()) {
            System.out.println(ev.toString());
        }
    }

    //EFFECTS: returns this.myPets
    public MyPets getMyPets() {
        return this.myPets;
    }

    //MODIFIES: this
    //EFFECTS: set this.myPets to a new value
    public void setMyPets(MyPets myPets) {
        this.myPets = myPets;
    }
}