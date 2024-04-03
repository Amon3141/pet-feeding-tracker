package ui;

import model.MyPets;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Represents the view to load the data
public class LoadView extends ViewAbstract {
    private String destination = ViewController.JSON_STORE;
    private JsonReader jsonReader;

    //EFFECTS: instantiate the object
    public LoadView() {
        this.frameTitle = "Load Data";
        this.jsonReader = new JsonReader(destination);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    private void addComponents() {
        JLabel message = new JLabel("Do you want to load the previous data?");
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(message);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));

        JButton yesButton = getYesButton();
        JButton noButton = getNoButton();

        buttons.add(noButton);
        buttons.add(yesButton);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("resources/turtle1.png").getImage()
                .getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);

        add(Box.createVerticalGlue());
        add(imagePanel);
        add(messagePanel);
        add(buttons);
    }

    //EFFECTS: creates the no button
    private JButton getNoButton() {
        JButton noButton = getButton("No");
        noButton.addActionListener(e -> {
            viewController.setMyPets(new MyPets());
            viewController.navigateTo(new MainView());
        });
        return noButton;
    }

    //EFFECTS: creates the yes button
    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            viewController.setMyPets(loadData());
            viewController.navigateTo(new MainView());
        });
        return yesButton;
    }

    //EFFECTS: loads the previous data
    private MyPets loadData() {
        MyPets myPets;
        try {
            myPets = jsonReader.read();
            //System.out.println("Loaded pet information from " + destination);
        } catch (IOException e) {
            myPets = new MyPets();
            //System.out.println("Unable to read from file: " + destination);
        }
        return myPets;
    }
}
