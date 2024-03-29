package ui;

import model.FeedingRecord;
import model.Pet;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

public class EditRecordView extends RecordInfoView {
    FeedingRecord record;

    public EditRecordView(Pet pet, FeedingRecord record) {
        super(pet);
        this.record = record;
        dateField.setText(SDF.format(record.getDate()));
        amountField.setText(Double.toString(record.getAmount()));
        this.frameTitle = "Edit a Feeding Record";
    }

    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new HistoryView(pet)));

        JButton saveButton = getSaveButton();

        footer.add(cancelButton);
        footer.add(saveButton);

        return footer;
    }

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
            record.setDate(date);
            record.setAmount(amount);
            viewController.navigateTo(new HistoryView(pet));
        });
        return saveButton;
    }
}
