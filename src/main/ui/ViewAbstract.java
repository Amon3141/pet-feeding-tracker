package ui;

import model.DateFormatter;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

// Represents the abstract framework of the views (JPanel) that are used in
// the application, setting up basic fields like FONT and viewController.
public abstract class ViewAbstract extends JPanel {
    protected static final SimpleDateFormat SDF = DateFormatter.SDF;
    protected static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    protected ViewController viewController = ViewController.getInstance();
    protected MyPets myPets = viewController.getMyPets();
    protected Pet currentPet = viewController.getCurrentPet();
    protected FeedingRecord currentRecord = viewController.getCurrentRecord();
    protected String frameTitle = "";

    //EFFECTS: instantiates the object
    protected JButton getButton(String label) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(90, 45));
        button.setFont(FONT);
        return button;
    }

    //EFFECTS: returns this.frameTitle
    public String getFrameTitle() {
        return this.frameTitle;
    }
}
