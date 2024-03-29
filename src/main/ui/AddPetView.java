package ui;

import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddPetView extends PetInfoView {
    public AddPetView() {
        super();
        this.frameTitle = "Add a New Pet";
    }

    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.navigateTo(new MainView());
            }
        });

        JButton addButton = getButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double target = Double.parseDouble(targetField.getText());
                String unit = unitField.getText();
                Pet petToAdd = new Pet(name, target, unit);
                myPets.addPet(petToAdd);
                viewController.navigateTo(new MainView());
            }
        });

        footer.add(cancelButton);
        footer.add(addButton);

        return footer;
    }

}
