package persistence;

import model.DateFormatter;
import model.FeedingRecord;
import model.Pet;
import model.MyPets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.stream.Stream;

import org.json.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

// Represents a reader that reads workroom from JSON data stored in file
// Some of the code snippets in this class are modelled from JsonSerializationDemo JsonReader
// JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private static SimpleDateFormat SDF = DateFormatter.SDF;
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads MyPets from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MyPets read() throws IOException {
        String myPetsData = readFile(source);
        JSONObject myPetsObject = new JSONObject(myPetsData);
        return parseMyPets(myPetsObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses MyPets from JSON object and returns it
    private MyPets parseMyPets(JSONObject myPetsObject) {
        MyPets myPets = new MyPets();
        addPets(myPets, myPetsObject);
        return myPets;
    }

    // MODIFIES: myPets
    // EFFECTS: parses pets from JSON object and adds them to MyPets
    private void addPets(MyPets myPets, JSONObject myPetsObject) {
        JSONArray petList = myPetsObject.getJSONArray("pets");
        for (Object pet : petList) {
            JSONObject nextPet = (JSONObject) pet;
            addPet(myPets, nextPet);
        }
    }

    // MODIFIES: myPets
    // EFFECTS: parses a pet from JSON object and adds it to MyPets
    private void addPet(MyPets myPets, JSONObject petObject) {
        String name = petObject.getString("name");
        double targetAmount = petObject.getDouble("targetAmount");
        String unit = petObject.getString("unit");
        Pet petToAdd = new Pet(name, targetAmount, unit);

        JSONArray feedingHistory = petObject.getJSONArray("feedingHistory");
        for (Object feedingRecord : feedingHistory) {
            JSONObject nextRecord = (JSONObject) feedingRecord;
            addFeedingRecord(petToAdd, nextRecord);
        }

        myPets.addPet(petToAdd);
    }

    // MODIFIES: pet
    // EFFECTS: parses FeedingRecord from JSON object and adds it to Pet
    private void addFeedingRecord(Pet pet, JSONObject recordObject) {
        String dateInString = recordObject.getString("date");
        //String[] dateStringArray = dateInString.split("-", 0);
        //int year = Integer.parseInt(dateStringArray[0]);
        //int month = Integer.parseInt(dateStringArray[1]);
        //int day = Integer.parseInt(dateStringArray[2]);
        //Date date = new Date(2024, 3, 2);
        //System.out.println(year + " " + month + " " + day);
        Date date = new Date();
        try {
            date = SDF.parse(dateInString);
        } catch (ParseException e) {
            System.out.println("Failed to load the feeding date for " + pet.getName() + "!");
        }
        double amount = recordObject.getDouble("amount");

        FeedingRecord recordToAdd = new FeedingRecord(date, amount);
        pet.feed(recordToAdd);
    }
}