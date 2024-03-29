package ui;

import model.*;

import java.awt.*;
import javax.swing.*;

// represents the view to add a pet
public class AddPetView extends PetInfoView {
    //EFFECTS: instantiate an object
    public AddPetView() {
        super();
        this.frameTitle = "Add a New Pet";
    }

    //EFFECTS: create a footer with two buttons
    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));

        JButton addButton = getButton("Add");
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            double target = Double.parseDouble(targetField.getText());
            String unit = unitField.getText();
            Pet petToAdd = new Pet(name, target, unit);
            myPets.addPet(petToAdd);
            viewController.navigateTo(new MainView());
        });

        footer.add(cancelButton);
        footer.add(addButton);

        return footer;
    }

}
