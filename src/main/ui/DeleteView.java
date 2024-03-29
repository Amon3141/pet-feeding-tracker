package ui;

import model.Pet;

import java.awt.*;
import javax.swing.*;

public class DeleteView extends ViewAbstract {
    Pet pet;

    public DeleteView(Pet pet) {
        this.pet = pet;
        this.frameTitle = "Delete a Pet";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

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

    private JButton getYesButton() {
        JButton yesButton = getButton("Yes");
        yesButton.addActionListener(e -> {
            myPets.deletePet(myPets.getMyPets().indexOf(pet) + 1);
            viewController.navigateTo(new MainView());
        });
        return yesButton;
    }

    private JButton getCancelButton() {
        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));
        return cancelButton;
    }
}
