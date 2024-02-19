package model;

import java.util.Date;

public class FeedingRecord {
    private Date date;
    private double amount;

    //EFFECTS: creates a new FeedingRecord with date and amount
    public FeedingRecord(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public double getAmount() {
        return this.amount;
    }
}
