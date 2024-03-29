package ui;

import model.*;
import org.json.JSONException;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

// Represents the pet feeding tracker, interacting with user to display and manipulate
// the info of pets (myPets).
public class Tracker {
    private static final SimpleDateFormat SDF = DateFormatter.SDF;
    private static final String JSON_STORE = "./data/mypets.json";
    private MyPets myPets;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private boolean isRunning;
    private boolean isMain; //true if mainView, false if recordView
    private Pet currentPet;

    //EFFECTS: runs the pet feeding tracker application
    public Tracker() {
        init();
        loadDataView();
    }

    // MODIFIES: this
    // EFFECTS: let the user choose to load the data from the json file,
    //          and if so load the data to myPets
    private void loadDataView() {
        System.out.println("\nLoad the previous data? [y/n]");
        String response = input.next();
        if (response.equalsIgnoreCase("y")) {
            try {
                loadMyPets();
            } catch (JSONException e) {
                System.out.println("There was no previous data.");
            }
        }
        runTracker();
    }

    // EFFECTS: let the user choose to save the data of myPets to
    //          the json file, and if so save the data
    private void saveDataView() {
        System.out.println("\nSave the current data? [y/n]");
        String response = input.next();
        if (response.equalsIgnoreCase("y")) {
            saveMyPets();
        }
        isRunning = false;
    }

    // MODIFIES: this
    // EFFECTS: until user decides to quit the app, displays main/record views
    //          after they quit, print "Good Bye!"
    private void runTracker() {

        while (isRunning) {
            if (isMain) {
                processMainView();
            } else {
                processRecordView();
            }
        }

        System.out.println("\nGood Bye!");
    }

    // REQUIRES: isMain = true
    // MODIFIES: this
    // EFFECTS: displays all the pets. processes user input.
    private void processMainView() {
        String command;

        displayMenuMain();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            saveDataView();
        } else {
            processCommandMain(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input in the main view
    private void processCommandMain(String command) {
        switch (command) {
            case "a":
                doAddPet();
                break;
            case "e":
                doEditPet();
                break;
            case "d":
                doDeletePet();
                break;
            case "v":
                toRecordView();
                break;
            default:
                System.out.println("Invalid Operation.");
                break;
        }
    }

    // REQUIRES: isMain = false
    // MODIFIES: this
    // EFFECTS: displays all the feeding records. processes user input.
    private void processRecordView() {
        String command;

        displayMenuRecord();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            saveDataView();
        } else {
            processCommandRecord(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input in the record view
    private void processCommandRecord(String command) {
        switch (command) {
            case "a":
                doAddRecord();
                break;
            case "e":
                doEditRecord();
                break;
            case "d":
                doDeleteRecord();
                break;
            case "b":
                currentPet = null;
                isMain = true;
                break;
            default:
                System.out.println("Invalid Operation.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initiate some class fields with default values and
    //          class instances.
    private void init() {
        myPets = new MyPets();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        isRunning = true;
        isMain = true;
        currentPet = null;

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays the pet info and command menu
    private void displayMenuMain() {
        System.out.println("\nMy Pets");
        System.out.println("--------------------------------------------------");
        printPets();
        System.out.println("--------------------------------------------------");
        System.out.println("Select from:");
        System.out.println("\ta -> add a pet");
        System.out.println("\te -> edit pet info");
        System.out.println("\td -> delete pet info");
        System.out.println("\tv -> view feeding history");
        System.out.println("\tq -> quit");
    }


    // EFFECTS: displays the feeding record and command menu
    private void displayMenuRecord() {
        System.out.println("\n" + currentPet.getName() + "'s Feeding History");
        System.out.println("----------------------------");
        printFeedingHistory();
        System.out.println("----------------------------");
        System.out.println("Select from:");
        System.out.println("\ta -> add feeding record");
        System.out.println("\te -> edit feeding record");
        System.out.println("\td -> delete feeding record");
        System.out.println("\tb -> back to main view");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: interacts with the user to add a new pet
    private void doAddPet() {
        String name;
        double targetAmount;
        String unit;

        System.out.println("\nEnter pet info to add:");
        System.out.println("Name?");
        name = input.next();
        System.out.println("Target amount of feeding per day? (without unit)");
        targetAmount = Double.parseDouble(input.next());
        System.out.println("unit? (mg, g, mL, L, etc.)");
        unit = input.next();

        Pet petToAdd = new Pet(name, targetAmount, unit);
        myPets.addPet(petToAdd);
    }

    // REQUIRES: !myPets.getMyPets().isEmpty()
    // MODIFIES: this
    // EFFECTS: interacts with teh user to edit an existing pet
    private void doEditPet() {
        String name;
        double targetAmount;
        String unit;

        System.out.println("\nSelect the pet number to edit info:");

        int petNum = selectPet();
        if (petNum == -1) {
            processMainView();
        }

        System.out.println("\nEnter pet info to edit:");
        System.out.println("Name?");
        name = input.next();
        System.out.println("Target amount of feeding per day? (without unit)");
        targetAmount = Double.parseDouble(input.next());
        System.out.println("unit? (mg, g, mL, L, etc.)");
        unit = input.next();

        myPets.editPet(petNum, name, targetAmount, unit);
    }

    // REQUIRES: !myPets.getMyPets().isEmpty()
    // MODIFIES: this
    // EFFECTS: interacts with the user to delete an existing pet
    private void doDeletePet() {
        System.out.println("\nSelect the pet number to delete info:");

        int petNum = selectPet();
        if (petNum == -1) {
            processMainView();
        }

        System.out.println("Are you sure? [y/n]");
        String answer = input.next();
        if (answer.equalsIgnoreCase("y")) {
            Pet deletedPet = myPets.deletePet(petNum);
            System.out.println(deletedPet.getName() + "'s info was deleted.");
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("Action cancelled. Going back to the main screen...");
            processMainView();
        } else {
            System.out.println("Invalid Answer. Going back to the main screen...");
            processMainView();
        }
    }

    // REQUIRES: !myPets.getMyPets().isEmpty()
    // EFFECTS: let user select which pet's feeding history they want to view
    //          and go to recordView
    private void toRecordView() {
        System.out.println("\nSelect the pet number to see the feeding history:");

        int petNum = selectPet();
        if (petNum == -1) {
            processMainView();
        }

        currentPet = myPets.getPetAtIndex(petNum - 1);
        isMain = false;
        processRecordView();
    }

    // EFFECTS: selects pet number within the range of pet array
    private int selectPet() {
        int petNum = -1;

        try {
            int inp = Integer.parseInt(input.next());
            if (inp <= 0 || inp > myPets.getNumPets()) {
                // !!!
                System.out.println("Not a valid number! Enter again. Enter \"b\" to go back.");
            } else {
                petNum = inp;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number! Enter again. Enter \"b\" to go back.");
        }

        return petNum;
    }

    // MODIFIES: this
    // EFFECTS: interacts with the user to add a new feeding record
    private void doAddRecord() {
        Date date = new Date();

        System.out.println("\nEnter feeding record to add:");

        System.out.println("How much? (" + currentPet.getUnit() + ")");
        double amount = Double.parseDouble(input.next());
        System.out.println("Date:");
        System.out.println("Year? (e.g. 2024)");
        String year = input.next();
        System.out.println("Month? (e.g. 02)");
        String month = input.next();
        System.out.println("Day? (e.g. 18)");
        String day = input.next();

        String dateInString = year + "-" + month + "-" + day;

        try {
            date = SDF.parse(dateInString);
        } catch (ParseException e) {
            //!!!
            System.out.println("Invalid Date! Going back to the feeding history...");
            processRecordView();
        }

        FeedingRecord recordToAdd = new FeedingRecord(date, amount);
        currentPet.feed(recordToAdd);
    }

    // EFFECTS: selects the record number to edit
    private int preDoEditRecord() {
        int recordNum = -1;

        System.out.println("\nSelect the record number to edit:");

        recordNum = selectRecord();
        if (recordNum == -1) {
            processRecordView();
        }

        return recordNum;
    }

    // REQUIRES: !currentPet.getFeedingHistory().isEmpty()
    // MODIFIES: this
    // EFFECTS: interacts with the user to edit an existing feeding record
    private void doEditRecord() {
        Date date = new Date();

        int recordNum = preDoEditRecord();

        System.out.println("\nEnter the new feeding record:\nHow much? (" + currentPet.getUnit() + ")");

        double amount = Double.parseDouble(input.next());
        System.out.println("Date:");
        System.out.println("Year? (e.g. 2024)");
        String year = input.next();
        System.out.println("Month? (e.g. 02)");
        String month = input.next();
        System.out.println("Day? (e.g. 18)");
        String day = input.next();

        String dateInString = year + "-" + month + "-" + day;

        try {
            date = SDF.parse(dateInString);
        } catch (ParseException e) {
            //!!!
            System.out.println("Invalid Date! Going back to the feeding history...");
            processRecordView();
        }

        currentPet.editFeedingRecord(recordNum, date, amount);
    }

    // REQUIRES: !currentPet.getFeedingHistory().isEmpty()
    // MODIFIES: this
    // EFFECTS: interacts with the user to delete an existing feeding record
    private void doDeleteRecord() {
        System.out.println("\nSelect the record number to delete:");

        int recordNum = selectRecord();
        if (recordNum == -1) {
            processRecordView();
        }

        System.out.println("Are you sure? [y/n]");
        String answer = input.next();
        if (answer.equalsIgnoreCase("y")) {
            FeedingRecord deletedRecord = currentPet.deleteFeedingRecord(recordNum);
            System.out.println("Feeding Record " + recordNum + " was deleted.");
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("Action cancelled. Going back to the feeding history...");
            processRecordView();
        } else {
            System.out.println("Invalid Answer. Going bakc to the feeding history...");
            processRecordView();
        }
    }

    // EFFECTS: selects the record number within the range of the feeding history array
    private int selectRecord() {
        ArrayList<FeedingRecord> history = currentPet.getFeedingHistory();
        int petNum = -1;

        try {
            int inp = Integer.parseInt(input.next());
            if (inp <= 0 || inp > history.size()) {
                System.out.println("Not a valid number! Enter again. Going back to the feeding history...");
            } else {
                petNum = inp;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number! Enter again. Going back to the feeding history...");
        }

        return petNum;
    }

    // EFFECTS: displays all the pet names along with pet number and petInfo.
    private void printPets() {
        ArrayList<Pet> petList = myPets.getMyPets();

        if (!petList.isEmpty()) {
            for (int i = 0; i < petList.size(); i++) {
                int petNum = i + 1;
                System.out.println(petNum + "\t" + petInfo(myPets.getMyPets().get(i)));
            }
        } else {
            System.out.println("No Pets Added!");
        }
    }

    // EFFECTS: creates a string of the pet info.
    //          contains how much more feeding is needed for that pet,
    //          and how much feeding the user has done today.
    private String petInfo(Pet pet) {
        String messageToUser = "Enough for today!";
        double current = getTodayTotalFeeding(pet);
        double target = pet.getTargetAmount();
        if (current < target) {
            messageToUser = (target - current) + pet.getUnit() + " more!";
        }
        String ret = pet.getName() + "\t" + messageToUser  + "\t" + " ("
                + current + pet.getUnit() + "/" + target + pet.getUnit() + ")";
        return ret;
    }

    /*
    private String petInfo(Pet pet) {
        String ret = pet.getName() + "\t" + " ("
                + pet.getTargetAmount() + pet.getUnit() + "/day)";
        return ret;
    }
    */

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

    // EFFECTS: displays all the feeding records along with record number and recordInfo.
    private void printFeedingHistory() {
        ArrayList<FeedingRecord> history = currentPet.getFeedingHistory();

        if (!history.isEmpty()) {
            for (int i = history.size() - 1; i > -1; i--) {
                int recordNum = i + 1;
                System.out.println(recordNum + "\t"
                        + recordInfo(history.get(i), currentPet.getUnit()));
            }
        } else {
            System.out.println("No Feeding Records Added!");
        }
    }

    // EFFECTS: creates a string of the record info.
    //          contains how much feeding is done,
    //          and on what date it was done.
    private String recordInfo(FeedingRecord record, String unit) {
        String ret = record.getAmount() + unit + "\t"
                + "(" + SDF.format(record.getDate()) + ")";
        return ret;
    }

    // EFFECTS: saves myPets to file
    private void saveMyPets() {
        try {
            jsonWriter.open();
            jsonWriter.write(myPets);
            jsonWriter.close();
            System.out.println("Saved Pet Information to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads myPets from file
    private void loadMyPets() {
        try {
            myPets = jsonReader.read();
            System.out.println("Loaded pet information from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
