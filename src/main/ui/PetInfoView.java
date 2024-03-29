package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents the view with Pet Info (AddPetView & EditPetView)
public abstract class PetInfoView extends ViewAbstract {
    protected JTextField nameField;
    protected JTextField targetField;
    protected JTextField unitField;

    //EFFECTS: instantiates the object
    public PetInfoView() {
        nameField = new JTextField();
        targetField = new JTextField();
        unitField = new JTextField();
        setLayout(new BorderLayout());
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    protected void addComponents() {
        JPanel form = getForm();
        JPanel footer = getFooter();

        add(form, BorderLayout.CENTER);
        add(footer, BorderLayout.PAGE_END);
        Component rightSpacer = Box.createRigidArea(new Dimension(250, 300));
        add(rightSpacer, BorderLayout.LINE_END);
    }

    //EFFECTS: creates the footer with appropriate buttons
    protected abstract JPanel getFooter();

    //EFFECTS: creates the form that asks for name, target amount, and unit
    private JPanel getForm() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel nameRow = getRow("Name", nameField);
        JPanel targetRow = getRow("Feeding amount / day", targetField);
        JPanel unitRow = getRow("Unit", unitField);

        form.add(Box.createVerticalGlue());
        form.add(nameRow);
        form.add(targetRow);
        form.add(unitRow);

        return form;
    }

    //EFFECTS: creates the row inside the form
    private JPanel getRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 0));

        JLabel label = new JLabel(labelText);
        label.setFont(FONT);

        textField.setPreferredSize(new Dimension(200, 45));
        textField.setBorder(new EmptyBorder(5, 10, 5, 10));

        row.add(label);
        row.add(textField);

        return row;
    }
}
