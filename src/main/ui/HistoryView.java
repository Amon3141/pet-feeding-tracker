package ui;

import model.*;

import java.awt.*;
import java.util.Date;
import javax.swing.*;

// Represents the view to display the feeding history
public class HistoryView extends ViewAbstract {
    private final Pet pet;

    //EFFECTS: instantiate an object
    public HistoryView(Pet pet) {
        this.pet = pet;
        this.frameTitle = pet.getName() + "'s Feeding History";
        setLayout(new BorderLayout());
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    private void addComponents() {
        JPanel rows = getRows();
        JScrollPane feedingHistory = new JScrollPane(rows);
        feedingHistory.setBorder(BorderFactory.createEmptyBorder());
        add(feedingHistory, BorderLayout.CENTER);

        JPanel footer = getFooter();
        add(footer, BorderLayout.PAGE_END);
    }

    //EFFECTS: create the footer
    private JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEADING));
        footer.setBorder(BorderFactory.createEmptyBorder());

        JButton backButton = getButton("Back");
        backButton.addActionListener(e -> viewController.navigateTo(new MainView()));

        footer.add(backButton);

        return footer;
    }

    //EFFECTS: create the rows of feeding history
    private JPanel getRows() {
        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));

        rows.add(Box.createVerticalGlue());

        for (FeedingRecord record : pet.getFeedingHistory()) {
            JPanel row = getRow(record);
            rows.add(row);
        }

        //rows.add(Box.createVerticalGlue());

        return rows;
    }

    //EFFECTS: creates the row of feeding history
    private JPanel getRow(FeedingRecord record) {
        Date date = record.getDate();
        double amount = record.getAmount();
        String unit = pet.getUnit();

        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        row.setPreferredSize(new Dimension(30, 100));

        JButton editButton = getButton("Edit");
        editButton.addActionListener(e -> viewController.navigateTo(new EditRecordView(pet, record)));

        JButton deleteButton = getButton("Delete");
        deleteButton.addActionListener(e -> viewController.navigateTo(new DeleteRecordView(pet, record)));

        String dateString = SDF.format(date);
        JLabel dateLabel = new JLabel(dateString);
        dateLabel.setFont(FONT);

        JLabel amountLabel = new JLabel(amount + unit);
        amountLabel.setFont(FONT);

        row.add(deleteButton);
        row.add(editButton);
        row.add(dateLabel);
        row.add(amountLabel);

        return row;
    }
}
