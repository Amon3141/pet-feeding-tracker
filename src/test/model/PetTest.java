package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PetTest {
    static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    Pet pet1, pet2;
    FeedingRecord r1, r2;
    Date date1, date2;

    @BeforeEach
    void setup() {
        pet1 = new Pet("pet1", 50, "g");
        pet2 = new Pet("pet2", 100, "g");

        r1 = new FeedingRecord(date1, 10);
        r2 = new FeedingRecord(date2, 10);

        try {
            date1 = SDF.parse("2024-01-01");
            date1 = SDF.parse("2024-12-31");
        } catch (ParseException e) {
            date1 = new Date();
            date2 = new Date();
        }
    }

    @Test
    void testFeedOnce() {
        pet1.feed(r1);
        assertEquals(r1, pet1.getFeedingHistory().get(0));
    }

    @Test
    void testFeedMultiple() {
        pet1.feed(r1);
        assertEquals(r1, pet1.getFeedingHistory().get(0));
        pet1.feed(r2);
        assertEquals(r1, pet1.getFeedingHistory().get(0));
        assertEquals(r2, pet1.getFeedingHistory().get(1));
    }

    @Test
    void testEditFeedingRecordWithOneElement() {
        pet1.feed(r1);
        pet1.editFeedingRecord(1, date2, 20);
        assertEquals(date2, pet1.getFeedingHistory().get(0).getDate());
        assertEquals(20, pet1.getFeedingHistory().get(0).getAmount());
    }

    @Test
    void testEditFeedingRecordWithMultipleElement() {
        pet1.feed(r1);
        pet1.feed(r2);
        pet1.editFeedingRecord(2, date1, 5);
        assertEquals(r1, pet1.getFeedingHistory().get(0));
        assertEquals(date1, pet1.getFeedingHistory().get(1).getDate());
        assertEquals(5, pet1.getFeedingHistory().get(1).getAmount());
    }

    @Test
    void testEditFeedingRecordWithMultipleElementMultipleTimes() {
        pet1.feed(r1);
        pet1.feed(r2);
        pet1.editFeedingRecord(2, date1, 10);
        pet1.editFeedingRecord(1, date2, 30);
        assertEquals(date2, pet1.getFeedingHistory().get(0).getDate());
        assertEquals(30, pet1.getFeedingHistory().get(0).getAmount());
        assertEquals(date1, pet1.getFeedingHistory().get(1).getDate());
        assertEquals(10, pet1.getFeedingHistory().get(1).getAmount());
    }

    @Test
    void testDeleteFeedingRecordOnce() {
        pet1.feed(r1);
        pet1.feed(r2);
        pet1.deleteFeedingRecord(1);
        assertEquals(1, pet1.getNumFeedingRecord());
        assertEquals(r2, pet1.getFeedingHistory().get(0));
    }

    @Test
    void testDeleteFeedingRecordMultipleTimes() {
        pet1.feed(r1);
        pet1.feed(r2);
        pet1.deleteFeedingRecord(1);
        pet1.deleteFeedingRecord(1);
        assertEquals(0, pet1.getNumFeedingRecord());
    }

    @Test
    void testGetNumFeedingRecordEmpty() {
        assertEquals(0, pet1.getNumFeedingRecord());
    }

    @Test
    void testGetNumFeedingRecordOne() {
        pet1.feed(r1);
        assertEquals(1, pet1.getNumFeedingRecord());
    }

    @Test
    void testGetNumPetsMultiple() {
        pet1.feed(r1);
        pet1.feed(r2);
        assertEquals(2, pet1.getNumFeedingRecord());
    }
}
