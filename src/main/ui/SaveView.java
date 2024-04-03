package ui;

import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

// Represents the view to save the current data
public class SaveView extends ViewAbstract {
    private String destination = ViewController.JSON_STORE;
    private JsonWriter jsonWriter;

    //EFFECTS: instantiates the object
    public SaveView() {
        jsonWriter = new JsonWriter(destination);
        this.frameTitle = "Save Data";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    private void addComponents() {
        JLabel message = new JLabel("Do you want to save the changes?");
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(message);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton yesButton = getYesButton();
        JButton noButton = getNoButton();
        JButton cancelButton = getCancelButton();

        buttons.add(cancelButton);
        buttons.add(noButton);
        buttons.add(yesButton);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("resources/turtle2.png")
                .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);

        add(Box.createVerticalGlue());
        add(imagePanel);
        add(messagePanel);
        add(buttons);
    }

    //EFFECTS: creates the cancel button
    private JButton getCancelButton() {
        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));
        return cancelButton;
    }

    //EFFECTS: creates the no button
    private JButton getNoButton() {
        JButton noButton = getButton("No");
        noButton.addActionListener(e -> {
            ViewController.logEvents();
            System.exit(0);
        });
        return noButton;
    }

    //EFFECTS: creates the yes button
    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            saveData();
            ViewController.logEvents();
            System.exit(0);
        });
        return yesButton;
    }

    //EFFECTS: saves the current data
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(myPets);
            jsonWriter.close();
            //System.out.println("Saved Pet Information to " + destination);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + destination);
        }
    }
}
