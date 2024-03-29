package persistance;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import persistence.JsonReader;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.json.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
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
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MyPets myPets = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMyPets() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMyPets.json");
        try {
            MyPets myPets = reader.read();
            assertEquals(0, myPets.getNumPets());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralMyPets() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMyPets.json");
        try {
            MyPets myPets = reader.read();
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
            fail("Couldn't read from file");
        }
    }
}
