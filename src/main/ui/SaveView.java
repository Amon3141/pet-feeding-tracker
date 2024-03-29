package ui;

import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class SaveView extends ViewAbstract {
    private final String destination = ViewController.JSON_STORE;
    private final JsonWriter jsonWriter;

    public SaveView() {
        jsonWriter = new JsonWriter(destination);
        this.frameTitle = "Save Data";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    private void addComponents() {
        JLabel message = new JLabel("Do you want save the changes?");
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

        add(Box.createVerticalGlue());
        add(messagePanel);
        add(buttons);
    }

    private JButton getCancelButton() {
        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));
        return cancelButton;
    }

    private JButton getNoButton() {
        JButton noButton = getButton("No");
        noButton.addActionListener(e -> System.exit(0));
        return noButton;
    }

    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            saveData();
            System.exit(0);
        });
        return yesButton;
    }

    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(myPets);
            jsonWriter.close();
            System.out.println("Saved Pet Information to " + destination);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + destination);
        }
    }
}
