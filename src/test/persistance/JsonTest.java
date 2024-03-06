package persistance;

import model.Pet;
import model.FeedingRecord;

import java.util.List;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPet(String name, double targetAmount, String unit,
                            List<FeedingRecord> feedingHistory, Pet pet) {
        assertEquals(name, pet.getName());
        assertEquals(targetAmount, pet.getTargetAmount());
        assertEquals(unit, pet.getUnit());
        assertEquals(feedingHistory.size(), pet.getFeedingHistory().size());
        for (int i = 0; i < Math.min(feedingHistory.size(),
                pet.getFeedingHistory().size()); i++) {
            FeedingRecord currentRecord = feedingHistory.get(i);
            checkRecord(currentRecord.getDate(), currentRecord.getAmount(),
                    pet.getFeedingHistory().get(i));
        }
    }

    private void checkRecord(Date date, double amount, FeedingRecord record) {
        assertEquals(date, record.getDate());
        assertEquals(amount, record.getAmount());
    }
}