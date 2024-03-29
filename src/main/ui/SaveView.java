package ui;

import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;

public class SaveView extends ViewAbstract {
    private final String destination = viewController.JSON_STORE;
    private JsonWriter jsonWriter;

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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.navigateTo(new MainView());
            }
        });
        return cancelButton;
    }

    private JButton getNoButton() {
        JButton noButton = getButton("No");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return noButton;
    }

    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
                System.exit(0);
            }
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
