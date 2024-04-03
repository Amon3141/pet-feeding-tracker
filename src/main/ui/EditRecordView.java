package ui;

import model.FeedingRecord;
import model.Pet;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

// represents the view to edit a feeding record
public class EditRecordView extends RecordInfoView {
    // EFFECTS: instantiates an object
    public EditRecordView() {
        super();
        dateField.setText(SDF.format(currentRecord.getDate()));
        amountField.setText(Double.toString(currentRecord.getAmount()));
        this.frameTitle = "Edit a Feeding Record";
    }

    //EFFECTS: creates the footer
    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new HistoryView()));

        JButton saveButton = getSaveButton();

        footer.add(cancelButton);
        footer.add(saveButton);

        return footer;
    }

    //EFFECTS: creates the save button
    private JButton getSaveButton() {
        JButton saveButton = getButton("Save");
        saveButton.addActionListener(e -> {
            Date date;
            try {
                date = SDF.parse(dateField.getText());
            } catch (ParseException ex) {
                date = new Date();
                System.out.println("Couldn't parse the date string.");
            }
            double amount = Double.parseDouble(amountField.getText());
            int recordIndex = currentPet.getFeedingHistory().indexOf(currentRecord);
            currentPet.editFeedingRecord(recordIndex, date, amount);
            viewController.navigateTo(new HistoryView());
        });
        return saveButton;
    }
}
