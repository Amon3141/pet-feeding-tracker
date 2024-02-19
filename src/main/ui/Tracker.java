package ui;

import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class Tracker {
    private MyPets myPets;
    private Scanner input;
    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private boolean isRunning;
    private boolean isMain; //true if mainView, false if recordView
    private Pet currentPet;

    //EFFECTS: runs the pet feeding tracker application
    public Tracker() {
        init();
        runTracker();
    }

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

    //MODIFIES: this
    //EFFECTS: displays all the pets. processes user input.
    private void processMainView() {
        String command = null;

        displayMenuMain();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            isRunning = false;
        } else {
            processCommandMain(command);
        }
    }

    private void processCommandMain(String command) {
        if (command.equals("a")) {
            doAddPet();
        } else if (command.equals("e")) {
            doEditPet();
        } else if (command.equals("v")) {
            toRecordView();
        } else {
            System.out.println("Invalid Operation.");
        }
    }

    private void processRecordView() {
        String command = null;

        displayMenuRecord();
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("q")) {
            isRunning = false;
        } else {
            processCommandRecord(command);
        }
    }

    private void processCommandRecord(String command) {
        if (command.equals("a")) {
            doAddRecord();
        } else if (command.equals("e")) {
            doEditRecord();
        } else if (command.equals("b")) {
            currentPet = null;
            isMain = true;
        } else {
            System.out.println("Invalid Operation.");
        }
    }


    private void init() {
        myPets = new MyPets();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        isRunning = true;
        isMain = true;
        currentPet = null;
    }

    private void displayMenuMain() {
        System.out.println("\nMy Pets");
        System.out.println("____________________________");
        printPets();
        System.out.println("____________________________");
        System.out.println("Select from:");
        System.out.println("\ta -> add a pet");
        System.out.println("\te -> edit pet info");
        System.out.println("\tv -> view feeding history");
        System.out.println("\tq -> quit");
    }

    private void displayMenuRecord() {
        System.out.println("\n" + currentPet.getName() + "'s Feeding History");
        System.out.println("____________________________");
        printFeedingHistory();
        System.out.println("____________________________");
        System.out.println("Select from:");
        System.out.println("\ta -> add feeding record");
        System.out.println("\te -> edit feeding record");
        System.out.println("\tb -> back to main view");
        System.out.println("\tq -> quit");
    }

    private void doAddPet() {
        String name = null;
        double targetAmount = -1;
        String unit = null;

        System.out.println("\nEnter pet info to add:");
        System.out.println("Name?");
        name = input.next();
        System.out.println("Target amount of feeding per day? (without unit)");
        targetAmount = Double.parseDouble(input.next());
        System.out.println("unit? (mg, g, mL, L, etc.)");
        unit = input.next();

        myPets.addPet(name, targetAmount, unit);
    }

    private void doEditPet() {
        String name = null;
        double targetAmount = -1;
        String unit = null;

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

    private int selectPet() {
        int petNum = -1;

        try {
            int inp = Integer.parseInt(input.next());
            if (inp <= 0 || inp > myPets.getNumPets()) {
                System.out.println("Not a valid number! Enter again. Enter \"b\" to go back.");
            } else {
                petNum = inp;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number! Enter again. Enter \"b\" to go back.");
        }

        return petNum;
    }

    private void doAddRecord() {
        double amount = -1.0;

        Date date = new Date();
        String year = null;
        String month = null;
        String day = null;

        System.out.println("\nEnter feeding record to add:");

        System.out.println("How much? (" + currentPet.getUnit() + ")");
        amount = Double.parseDouble(input.next());
        System.out.println("Date:");
        System.out.println("Year? (e.g. 2024)");
        year = input.next();
        System.out.println("Month? (e.g. 02)");
        month = input.next();
        System.out.println("Day? (e.g. 18)");
        day = input.next();

        String dateInString = year + "-" + month + "-" + day;

        try {
            date = SDF.parse(dateInString);
        } catch (ParseException e) {
            //!!!
            System.out.println("Invalid Date! Going back to feeding history...");
            processRecordView();
        }

        currentPet.feed(date, amount);
    }

    private int preDoEditRecord() {
        int recordNum = -1;

        System.out.println("\nSelect the record number to edit:");

        recordNum = selectRecord();
        if (recordNum == -1) {
            processRecordView();
        }

        return recordNum;
    }

    private void doEditRecord() {
        double amount = -1.0;

        Date date = new Date();
        String year = null;
        String month = null;
        String day = null;

        int recordNum = preDoEditRecord();

        System.out.println("\nEnter feeding record to edit:\nHow much? (" + currentPet.getUnit() + ")");

        amount = Double.parseDouble(input.next());
        System.out.println("Date:");
        System.out.println("Year? (e.g. 2024)");
        year = input.next();
        System.out.println("Month? (e.g. 02)");
        month = input.next();
        System.out.println("Day? (e.g. 18)");
        day = input.next();

        String dateInString = year + "-" + month + "-" + day;

        try {
            date = SDF.parse(dateInString);
        } catch (ParseException e) {
            //!!!
            System.out.println("Invalid Date! Going back to feeding history...");
            processRecordView();
        }

        currentPet.editFeedingRecord(recordNum, date, amount);
    }

    private int selectRecord() {
        ArrayList<FeedingRecord> history = currentPet.getFeedingHistory();
        int petNum = -1;

        try {
            int inp = Integer.parseInt(input.next());
            if (inp <= 0 || inp > history.size()) {
                System.out.println("Not a valid number! Enter again. Going back to feeding history...");
            } else {
                petNum = inp;
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number! Enter again. Going back to feeding history...");
        }

        return petNum;
    }

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

    /*
    private String petInfo(Pet pet) {
        String ret = pet.getName() + "\t" + " (" + 0 + pet.getUnit() + "/"
                + pet.getTargetAmount() + pet.getUnit() + ")";
        return ret;
    }
    */

    private String petInfo(Pet pet) {
        String ret = pet.getName() + "\t" + " ("
                + pet.getTargetAmount() + pet.getUnit() + "/day)";
        return ret;
    }

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

    private String recordInfo(FeedingRecord record, String unit) {
        String ret = record.getAmount() + unit + "\t"
                + "(" + SDF.format(record.getDate()) + ")";
        return ret;
    }
}
