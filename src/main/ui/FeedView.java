package ui;

import model.FeedingRecord;
import model.Pet;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Date;

public class FeedView extends RecordInfoView {
    Pet pet;

    public FeedView(Pet pet) {
        super(pet);
        this.pet = pet;
        this.dateField.setText(SDF.format(new Date()));
        this.frameTitle = "Feed";
    }

    @Override
    protected JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 40));

        JButton cancelButton = getButton("Cancel");
        cancelButton.addActionListener(e -> viewController.navigateTo(new MainView()));

        JButton addButton = getAddButton();

        footer.add(cancelButton);
        footer.add(addButton);

        return footer;
    }

    private JButton getAddButton() {
        JButton addButton = getButton("Feed");
        addButton.addActionListener(e -> {
            Date date;
            try {
                date = SDF.parse(dateField.getText());
            } catch (ParseException ex) {
                date = new Date();
                System.out.println("Couldn't parse the date string.");
            }
            double amount = Double.parseDouble(amountField.getText());
            FeedingRecord recordToAdd = new FeedingRecord(date, amount);
            pet.feed(recordToAdd);
            viewController.navigateTo(new MainView());
        });
        return addButton;
    }
}
