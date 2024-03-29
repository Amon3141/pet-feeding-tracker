package ui;

import model.Pet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents the view with Feeding Record Info
// (FeedView & EditRecordView)
public abstract class RecordInfoView extends ViewAbstract {
    Pet pet;
    protected JTextField dateField;
    protected JTextField amountField;

    //EFFECTS: instantiates the object
    public RecordInfoView(Pet pet) {
        this.pet = pet;
        dateField = new JTextField();
        amountField = new JTextField();
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

    //EFFECTS: creates the footer
    protected abstract JPanel getFooter();

    //EFFECTS: creates the form that asks for date and amount
    private JPanel getForm() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JPanel dateRow = getRow("Date", dateField);
        JPanel amountRow = getRow("Amount", amountField);

        form.add(Box.createVerticalGlue());
        form.add(dateRow);
        form.add(amountRow);

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
