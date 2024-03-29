package ui;

import model.MyPets;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadView extends ViewAbstract {
    private final String destination = ViewController.JSON_STORE;
    private final JsonReader jsonReader;

    public LoadView() {
        this.frameTitle = "Load Data";
        this.jsonReader = new JsonReader(destination);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    private void addComponents() {
        JLabel message = new JLabel("Do you want to load the previous data?");
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(message);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));

        JButton yesButton = getYesButton();
        JButton noButton = getNoButton();

        buttons.add(noButton);
        buttons.add(yesButton);

        add(Box.createVerticalGlue());
        add(messagePanel);
        add(buttons);
    }

    private JButton getNoButton() {
        JButton noButton = getButton("No");
        noButton.addActionListener(e -> {
            viewController.setMyPets(new MyPets());
            viewController.navigateTo(new MainView());
        });
        return noButton;
    }

    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            viewController.setMyPets(loadData());
            viewController.navigateTo(new MainView());
        });
        return yesButton;
    }

    private MyPets loadData() {
        MyPets myPets;
        try {
            myPets = jsonReader.read();
            System.out.println("Loaded pet information from " + destination);
        } catch (IOException e) {
            myPets = new MyPets();
            System.out.println("Unable to read from file: " + destination);
        }
        return myPets;
    }
}
