package persistance;

import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {
    static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    Date date1, date2;

    @BeforeEach
    void setup() {
        try {
            date1 = SDF.parse("2024-01-01");
            date2 = SDF.parse("2024-12-31");
        } catch (ParseException e) {
            date1 = new Date();
            date2 = new Date();
        }
    }

    @Test
    void testWriterInvalidFile() {
        try {
            MyPets myPets = new MyPets();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMyPets() {
        try {
            MyPets myPets = new MyPets();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyMyPets.json");
            writer.open();
            writer.write(myPets);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyMyPets.json");
            myPets = reader.read();
            assertEquals(0, myPets.getNumPets());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            MyPets myPets = new MyPets();
            myPets.addPet(new Pet("Peter", 10, "g"));
            myPets.addPet(new Pet("John", 50, "g"));
            myPets.getPetAtIndex(0).feed(new FeedingRecord(date1, 10));
            myPets.getPetAtIndex(0).feed(new FeedingRecord(date2, 100));
            myPets.getPetAtIndex(1).feed(new FeedingRecord(date1, 5));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMyPets.json");
            writer.open();
            writer.write(myPets);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralMyPets.json");
            myPets = reader.read();
            List<Pet> pets = myPets.getMyPets();
            assertEquals(2, pets.size());
            List<FeedingRecord> peterRecord = new ArrayList<>();
            peterRecord.add(new FeedingRecord(date1, 10));
            peterRecord.add(new FeedingRecord(date2, 100));
            List<FeedingRecord> johnRecord = new ArrayList<>();
            johnRecord.add(new FeedingRecord(date1, 5));
            checkPet("Peter", 10, "g", peterRecord, pets.get(0));
            checkPet("John", 50, "g", johnRecord, pets.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
