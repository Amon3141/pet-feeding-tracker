package ui;

// The main method called by the java program that instantiates the viewController
public class Main {
    // EFFECTS: instantiate the pet feeding tracker application
    public static void main(String[] args) {
        ViewController.getInstance().run();
        // new Tracker(); (for console-based application)
    }
}
