package model;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import persistence.Writable;

// Represents the feeding record, containing the date of amount fo the feeding
public class FeedingRecord implements Writable {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
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

    @Override
    public JSONObject toJson() {
        JSONObject recordObject = new JSONObject();
        String dateInString = SDF.format(date);
        recordObject.put("date", dateInString);
        recordObject.put("amount", amount);
        return recordObject;
    }
}
