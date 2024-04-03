package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents Pet, containing the name, targetAmount(unit) per day , and
// feeding history (= list of feeding records)
public class Pet implements Writable {
    private static final SimpleDateFormat SDF = DateFormatter.SDF;
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

    //MODIFIES: this
    //EFFECTS: add a new FeedingRecord to FeedingHistory
    public void feed(FeedingRecord record) {
        feedingHistory.add(record);

        String addedRecordString = SDF.format(record.getDate()) + " " + record.getAmount() + getUnit();
        String logString = "Added " + getName() + "'s feeding record: " + addedRecordString + "\n";
        EventLog.getInstance().logEvent(new Event(logString));
    }

    //REQUIRES:  0 <= recordNum <= feedingHistory.size()-1, amount >= 0,
    //           !this.feedingHistory.isEmpty
    //MODIFIES: this
    //EFFECTS: edit feedingHistory[recordNum-1] with newDate and
    //         newAmount
    public void editFeedingRecord(int recordNum, Date newDate, double newAmount) {
        FeedingRecord recordToEdit = this.feedingHistory.get(recordNum);

        String preRecordString = SDF.format(recordToEdit.getDate()) + " " + recordToEdit.getAmount() + getUnit();

        recordToEdit.setDate(newDate);
        recordToEdit.setAmount(newAmount);

        String postRecordString = SDF.format(recordToEdit.getDate()) + " " + recordToEdit.getAmount() + getUnit();
        String logString = "Edited " + getName() + "'s feeding record: "
                + preRecordString + " -> " + postRecordString + "\n";
        EventLog.getInstance().logEvent(new Event(logString));
    }

    //REQUIRES: 0 <= recordNum <= feedingHistory.size()-1, !this.feedingHitory.isEmpty
    //MODIFIES: this
    //EFFECTS: delete feedingHistory[recordNum-1]
    public FeedingRecord deleteFeedingRecord(int recordNum) {
        FeedingRecord deletedRecord = this.feedingHistory.remove(recordNum);

        String deletedRecordString = SDF.format(deletedRecord.getDate()) + " " + deletedRecord.getAmount() + getUnit();
        String logString = "Deleted " + getName() + "'s feeding record: " + deletedRecordString + "\n";
        EventLog.getInstance().logEvent(new Event(logString));

        return deletedRecord;
    }

    //EFFECTS: return the number of the feeding records
    public int getNumFeedingRecord() {
        return this.feedingHistory.size();
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

    //EFFECTS: returns JSONObject representing this Pet instance
    @Override
    public JSONObject toJson() {
        JSONObject petObject = new JSONObject();
        petObject.put("name", name);
        petObject.put("targetAmount", targetAmount);
        petObject.put("unit", unit);
        petObject.put("feedingHistory", historyToJson());
        return petObject;
    }

    // EFFECTS: returns feeding records in this pet as a JSON array
    private JSONArray historyToJson() {
        JSONArray feedingHistoryArray = new JSONArray();

        for (FeedingRecord record : feedingHistory) {
            feedingHistoryArray.put(record.toJson());
        }

        return feedingHistoryArray;
    }
}

