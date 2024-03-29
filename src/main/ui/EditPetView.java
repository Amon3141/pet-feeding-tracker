package ui;

import model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class EditPetView extends PetInfoView {
    private Pet pet;

    public EditPetView(Pet pet) {
        super();
        this.pet = pet;
        this.frameTitle = "Add a New Pet";
        nameField.setText(pet.getName());
        targetField.setText(Double.toString(pet.getTargetAmount()));
        unitField.setText(pet.getUnit());
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

        JButton saveButton = getButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pet.setName(nameField.getText());
                pet.setTargetAmount(Double.parseDouble(targetField.getText()));
                pet.setUnit(unitField.getText());
                viewController.navigateTo(new MainView());
            }
        });

        footer.add(cancelButton);
        footer.add(saveButton);

        return footer;
    }
}
