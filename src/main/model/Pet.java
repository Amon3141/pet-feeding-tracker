package model;

import java.util.ArrayList;
import java.util.Date;

public class Pet {
    private String name;
    private double targetAmount;
    private String unit;
    private ArrayList<FeedingRecord> feedingHistory;

    //EFFECTS: create a new Pet with its name, targetAmount
    //         per day, and its unit. creates an empty FeedingRecord.
    public Pet(String name, double targetAmount, String unit) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.unit = unit;
        feedingHistory = new ArrayList<>();
    }

    //REQUIRES: amount >= 0
    //MODIFIES: this
    //EFFECTS: add a new FeedingRecord to FeedingHistory with
    //         date and amount
    public void feed(Date date, double amount) {
        FeedingRecord record = new FeedingRecord(date, amount);
        feedingHistory.add(record);
    }

    //REQUIRES:  1 <= recordNum <= feedingHistory.size(), amount >= 0,
    //           !this.feedingHistory.isEmpty
    //MODIFIES: this
    //EFFECTS: edit feedingHistory[recordNum-1] with newDate and
    //         newAmount
    public void editFeedingRecord(int recordNum, Date newDate, double newAmount) {
        int index = recordNum - 1;
        FeedingRecord recordToEdit = this.feedingHistory.get(index);
        recordToEdit.setDate(newDate);
        recordToEdit.setAmount(newAmount);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTargetAmount(double amount) {
        this.targetAmount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return this.name;
    }

    public double getTargetAmount() {
        return this.targetAmount;
    }

    public String getUnit() {
        return this.unit;
    }

    public ArrayList<FeedingRecord> getFeedingHistory() {
        return this.feedingHistory;
    }
}

