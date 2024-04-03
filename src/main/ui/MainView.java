package ui;

import model.FeedingRecord;
import model.Pet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

// represents the main view with the list of pets
public class MainView extends ViewAbstract {
    //EFFECTS: instantiate the object
    public MainView() {
        this.frameTitle = "Pet Feeding Tracker";
        setLayout(new BorderLayout());
        addComponents();
    }

    //MODIFIES: this
    //EFFECTS: add components to the view
    protected void addComponents() {
        JPanel rows = getRows();
        JScrollPane petList = new JScrollPane(rows);
        petList.setBorder(BorderFactory.createEmptyBorder());

        JPanel addButtonPanel = getAddButtonPanel();

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.add(petList);
        body.add(addButtonPanel);

        add(body, BorderLayout.CENTER);

        JPanel footer = getFooter();
        add(footer, BorderLayout.PAGE_END);
    }

    //EFFECTS: creates the panel containing the add button
    private JPanel getAddButtonPanel() {
        JButton addButton = getButton("Add");
        addButton.addActionListener(e -> viewController.navigateTo(new AddPetView()));
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButtonPanel.add(addButton);

        return addButtonPanel;
    }

    //EFFECTS: creates the footer
    private JPanel getFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        footer.setBorder(BorderFactory.createEmptyBorder());

        JButton quitButton = getButton("Quit");
        quitButton.addActionListener(e -> viewController.navigateTo(new SaveView()));
        footer.add(quitButton);

        return footer;
    }

    //EFFECTS: creates the rows of the pet list
    private JPanel getRows() {
        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));

        rows.add(Box.createVerticalGlue());

        for (Pet pet : myPets.getMyPets()) {
            JPanel row = getRow(pet);
            rows.add(row);
        }

        //rows.add(Box.createVerticalGlue());

        return rows;
    }

    //EFFECTS: creates the row with pet info
    private JPanel getRow(Pet pet) {
        String name = pet.getName();
        double targetAmount = pet.getTargetAmount();
        double currentAmount = getTodayTotalFeeding(pet);
        String unit = pet.getUnit();

        JPanel row = new JPanel();
        row.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        row.setPreferredSize(new Dimension(30, 100));

        JButton deleteButton = getDeleteButton(pet);
        JButton editButton = getEditPet(pet);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(FONT);

        JButton feedButton = getFeedButton(pet);
        JButton historyButton = getHistoryButton(pet);

        row.add(deleteButton);
        row.add(editButton);
        row.add(nameLabel);
        addProgressUI(row, currentAmount, targetAmount, unit);
        row.add(feedButton);
        row.add(historyButton);

        return row;
    }

    //EFFECTS: creates the delete button
    private JButton getDeleteButton(Pet pet) {
        JButton deleteButton = getButton("Delete");
        deleteButton.addActionListener(e -> {
            viewController.setCurrentPet(pet);
            viewController.navigateTo(new DeletePetView()); //!!!
        });
        return deleteButton;
    }

    //EFFECTS: creates the edit button
    private JButton getEditPet(Pet pet) {
        JButton editButton = getButton("Edit");
        editButton.addActionListener(e -> {
            viewController.setCurrentPet(pet);
            viewController.navigateTo(new EditPetView()); //!!!
        });
        return editButton;
    }

    //EFFECTS: creates the feed button
    private JButton getFeedButton(Pet pet) {
        JButton feedButton = getButton("Feed");
        feedButton.addActionListener(e -> {
            viewController.setCurrentPet(pet);
            viewController.navigateTo(new FeedView()); //!!!
        });
        return feedButton;
    }

    //EFFECTS: creates the button to view the feeding history
    private JButton getHistoryButton(Pet pet) {
        JButton historyButton = getButton("History");
        historyButton.addActionListener(e -> {
            viewController.setCurrentPet(pet);
            viewController.navigateTo(new HistoryView());
        });
        return historyButton;
    }

    //EFFECTS: creates the progress bar UI
    private void addProgressUI(JPanel panel, double currentAmount, double targetAmount, String unit) {
        JProgressBar progressBar = new JProgressBar(0, (int) targetAmount);
        progressBar.setValue((int) currentAmount);
        progressBar.setPreferredSize(new Dimension(100, 50));
        UIManager.put("ProgressBar.foreground", Color.GREEN);
        SwingUtilities.updateComponentTreeUI(progressBar);

        String progressString = currentAmount + unit + " / " + targetAmount + unit;
        JLabel progressLabel = new JLabel(progressString);
        progressLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        panel.add(progressBar);
        panel.add(progressLabel);
    }

    // EFFECTS: sums up all the feeding amount given to the pet today
    private double getTodayTotalFeeding(Pet pet) {
        ArrayList<FeedingRecord> feedingHistory = pet.getFeedingHistory();
        double todayTotal = 0;
        for (FeedingRecord record : feedingHistory) {
            Date today = new Date();
            if (SDF.format(record.getDate()).equals(SDF.format(today))) {
                todayTotal += record.getAmount();
            }
        }
        return todayTotal;
    }
}