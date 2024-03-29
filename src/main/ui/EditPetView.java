package ui;

import model.*;

import java.awt.*;
import javax.swing.*;

// represents the view to edit a pet
public class EditPetView extends PetInfoView {
    private final Pet pet;

    //EFFECTS: instantiate an object
    public EditPetView(Pet pet) {
        super();
        this.pet = pet;
        this.frameTitle = "Add a New Pet";
        nameField.setText(pet.getName());
        targetField.setText(Double.toString(pet.getTargetAmount()));
        unitField.setText(pet.getUnit());
    }

    //EFFECTS: create the footer with two buttons
    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));

        JButton saveButton = getButton("Save");
        saveButton.addActionListener(e -> {
            pet.setName(nameField.getText());
            pet.setTargetAmount(Double.parseDouble(targetField.getText()));
            pet.setUnit(unitField.getText());
            viewController.navigateTo(new MainView());
        });

        footer.add(cancelButton);
        footer.add(saveButton);

        return footer;
    }
}
