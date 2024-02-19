package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyPetsTest {
    MyPets pets1;
    String unit1, unit2;

    @BeforeEach
    void setup() {
        pets1 = new MyPets();
        unit1 = "g";
        unit2 = "mg";
    }

    @Test
    void testAddPetNotIncludedOnce() {
        pets1.addPet("Peter", 50, unit1);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(50, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
    }

    @Test
    void testAddPetNotIncludedOnceZeroTarget() {
        pets1.addPet("Peter", 0, unit1);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(0, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
    }

    @Test
    void testAddPetNotIncludedMultipleTimes() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit2);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(50, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
        assertEquals("John", pets1.getMyPets().get(1).getName());
        assertEquals(100, pets1.getMyPets().get(1).getTargetAmount());
        assertEquals(unit2, pets1.getMyPets().get(1).getUnit());
    }

    @Test
    void testAddPetIncluded() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("Peter", 100, unit2);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(50, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
    }

    @Test
    void testAddPetIncludedMultipleTimes() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("Peter", 1000, unit1);
        pets1.addPet("John", 100, unit2);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(50, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
        assertEquals("John", pets1.getMyPets().get(1).getName());
        assertEquals(100, pets1.getMyPets().get(1).getTargetAmount());
        assertEquals(unit2, pets1.getMyPets().get(1).getUnit());
    }

    @Test
    void testEditPetOnce() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit2);
        pets1.editPet(1, "Peter", 10, unit1);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(10, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
        assertEquals("John", pets1.getMyPets().get(1).getName());
        assertEquals(100, pets1.getMyPets().get(1).getTargetAmount());
        assertEquals(unit2, pets1.getMyPets().get(1).getUnit());
    }

    @Test
    void testEditPetOnceZeroTarget() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit2);
        pets1.editPet(1, "Peter", 0, unit1);
        assertEquals("Peter", pets1.getMyPets().get(0).getName());
        assertEquals(0, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(0).getUnit());
        assertEquals("John", pets1.getMyPets().get(1).getName());
        assertEquals(100, pets1.getMyPets().get(1).getTargetAmount());
        assertEquals(unit2, pets1.getMyPets().get(1).getUnit());
    }

    @Test
    void testEditPetMultipleTimes() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit2);
        pets1.editPet(2, "Pet", 100, unit1);
        pets1.editPet(1, "Stella", 50, unit2);
        assertEquals("Stella", pets1.getMyPets().get(0).getName());
        assertEquals(50, pets1.getMyPets().get(0).getTargetAmount());
        assertEquals(unit2, pets1.getMyPets().get(0).getUnit());
        assertEquals("Pet", pets1.getMyPets().get(1).getName());
        assertEquals(100, pets1.getMyPets().get(1).getTargetAmount());
        assertEquals(unit1, pets1.getMyPets().get(1).getUnit());
    }

    @Test
    void testContainsEmpty() {
        assertFalse(pets1.contains("Peter"));
    }

    @Test
    void testContainsOneElementIncluded() {
        pets1.addPet("Peter", 50, unit1);
        assertTrue(pets1.contains("Peter"));
    }

    @Test
    void testContainsOneElementNotIncluded() {
        pets1.addPet("Peter", 50, unit1);
        assertFalse(pets1.contains("John"));
    }

    @Test
    void testContainsMultipleElementIncluded() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit1);
        assertTrue(pets1.contains("John"));
    }

    @Test
    void testContainsMultipleElementNotIncluded() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit1);
        assertFalse(pets1.contains("Pat"));
    }

    @Test
    void testGetPetAtIndexOneElement() {
        pets1.addPet("Pat", 50, unit2);
        assertEquals("Pat", pets1.getPetAtIndex(0).getName());
        assertEquals(50, pets1.getPetAtIndex(0).getTargetAmount());
        assertEquals(unit2, pets1.getPetAtIndex(0).getUnit());
    }

    @Test
    void testGetPetAtIndexMultipleElementsFirst() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit1);
        assertEquals("Peter", pets1.getPetAtIndex(0).getName());
        assertEquals(50, pets1.getPetAtIndex(0).getTargetAmount());
        assertEquals(unit1, pets1.getPetAtIndex(0).getUnit());
    }

    @Test
    void testGetPetAtIndexMultipleElementsLast() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 100, unit1);
        pets1.addPet("Johny", 150, unit2);
        assertEquals("Johny", pets1.getPetAtIndex(2).getName());
        assertEquals(150, pets1.getPetAtIndex(2).getTargetAmount());
        assertEquals(unit2, pets1.getPetAtIndex(2).getUnit());
    }

    @Test
    void testGetNumPetsEmpty() {
        assertEquals(0, pets1.getNumPets());
    }

    @Test
    void testGetNumPetsOne() {
        pets1.addPet("Peter", 50, unit1);
        assertEquals(1, pets1.getNumPets());
    }

    @Test
    void testGetNumPetsMultiple() {
        pets1.addPet("Peter", 50, unit1);
        pets1.addPet("John", 150, unit1);
        assertEquals(2, pets1.getNumPets());
    }
}
