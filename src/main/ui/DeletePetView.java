package ui;

import model.Pet;

import java.awt.*;
import javax.swing.*;

// represents the view to delete a pet
public class DeletePetView extends ViewAbstract {
    Pet pet;

    //EFFECTS: instantiate an object
    public DeletePetView(Pet pet) {
        this.pet = pet;
        this.frameTitle = "Delete a Pet";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    private void addComponents() {
        JLabel message = new JLabel("Are you sure you want to delete " + pet.getName() + "?");
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(message);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton yesButton = getYesButton();
        JButton noButton = getCancelButton();

        buttons.add(noButton);
        buttons.add(yesButton);

        add(Box.createVerticalGlue());
        add(messagePanel);
        add(buttons);
    }

    // create a yes button
    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            myPets.deletePet(myPets.getMyPets().indexOf(pet));
            viewController.navigateTo(new MainView());
        });
        return yesButton;
    }

    // creates a cancel button
    private JButton getCancelButton() {
        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));
        return cancelButton;
    }
}
