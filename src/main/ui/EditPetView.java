package ui;

import model.*;

import java.awt.*;
import javax.swing.*;

// represents the view to edit a pet
public class EditPetView extends PetInfoView {
    //EFFECTS: instantiate an object
    public EditPetView() {
        super();
        this.frameTitle = "Add a New Pet";
        nameField.setText(currentPet.getName());
        targetField.setText(Double.toString(currentPet.getTargetAmount()));
        unitField.setText(currentPet.getUnit());
    }

    //EFFECTS: create the footer with two buttons
    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));

        JButton saveButton = getButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            double targetAmount = Double.parseDouble(targetField.getText());
            String unit = unitField.getText();
            int petIndex = myPets.getMyPets().indexOf(currentPet);
            myPets.editPet(petIndex, name, targetAmount, unit);
            viewController.navigateTo(new MainView());
        });

        footer.add(cancelButton);
        footer.add(saveButton);

        return footer;
    }
}
