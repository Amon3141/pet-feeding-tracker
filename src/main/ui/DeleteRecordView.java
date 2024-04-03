package ui;

import model.FeedingRecord;
import model.Pet;

import java.awt.*;
import javax.swing.*;

// represents the view to delete a record
public class DeleteRecordView extends ViewAbstract {
    Pet pet;
    FeedingRecord record;

    //EFFECTS: instantiate an object
    public DeleteRecordView(Pet pet, FeedingRecord record) {
        this.pet = pet;
        this.record = record;
        this.frameTitle = "Delete a Record";
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    private void addComponents() {
        JLabel message = new JLabel("Are you sure you want to delete "
                + SDF.format(record.getDate()) + " " + record.getAmount() + pet.getUnit() + "?");
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.add(message);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));

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
            pet.deleteFeedingRecord(pet.getFeedingHistory().indexOf(record));
            viewController.navigateTo(new HistoryView(pet));
        });
        return yesButton;
    }

    // creates a cancel button
    private JButton getCancelButton() {
        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new HistoryView(pet)));
        return cancelButton;
    }
}
