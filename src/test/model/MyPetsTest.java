package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyPetsTest {
    MyPets pets;
    Pet pet1, pet2, pet3, pet4;

    @BeforeEach
    void setup() {
        pets = new MyPets();
        pet1 = new Pet("Peter", 50, "g");
        pet2 = new Pet("Peter", 100, "mg");
        pet3 = new Pet("John", 50, "g");
        pet4 = new Pet("Pat", 1000, "mg");
    }

    @Test
    void testAddPetNotIncludedOnce() {
        pets.addPet(pet1);
        assertEquals(pet1, pets.getPetAtIndex(0));
    }

    @Test
    void testAddPetNotIncludedMultipleTimes() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        assertEquals(pet1, pets.getPetAtIndex(0));
        assertEquals(pet3, pets.getPetAtIndex(1));
    }

    @Test
    void testAddPetIncluded() {
        pets.addPet(pet1);
        pets.addPet(pet2);
        assertEquals(pet1, pets.getPetAtIndex(0));
    }

    @Test
    void testAddPetIncludedMultipleTimes() {
        pets.addPet(pet1);
        pets.addPet(pet2);
        pets.addPet(pet3);
        assertEquals(pet1, pets.getPetAtIndex(0));
        assertEquals(pet3, pets.getPetAtIndex(1));
    }

    @Test
    void testEditPetOnce() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.editPet(0, "Peter", 10, "g");
        assertEquals("Peter", pets.getPetAtIndex(0).getName());
        assertEquals(10, pets.getPetAtIndex(0).getTargetAmount());
        assertEquals("g", pets.getPetAtIndex(0).getUnit());
        assertEquals(pet3, pets.getPetAtIndex(1));
    }

    @Test
    void testEditPetOnceZeroTarget() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.editPet(0, "Peter", 0, "g");
        assertEquals("Peter", pets.getPetAtIndex(0).getName());
        assertEquals(0, pets.getPetAtIndex(0).getTargetAmount());
        assertEquals("g", pets.getPetAtIndex(0).getUnit());
        assertEquals(pet3, pets.getPetAtIndex(1));
    }

    @Test
    void testEditPetMultipleTimes() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.editPet(1, "Pet", 100, "g");
        pets.editPet(0, "Stella", 50, "mg");
        assertEquals("Stella", pets.getPetAtIndex(0).getName());
        assertEquals(50, pets.getPetAtIndex(0).getTargetAmount());
        assertEquals("mg", pets.getPetAtIndex(0).getUnit());
        assertEquals("Pet", pets.getPetAtIndex(1).getName());
        assertEquals(100, pets.getPetAtIndex(1).getTargetAmount());
        assertEquals("g", pets.getPetAtIndex(1).getUnit());
    }

    @Test
    void testDeletePetOnce() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.deletePet(0);
        assertEquals(1, pets.getNumPets());
        assertEquals(pet3, pets.getPetAtIndex(0));
    }

    @Test
    void testDeletePetMultipleTimes() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.deletePet(0);
        pets.deletePet(0);
        assertEquals(0, pets.getNumPets());
    }

    @Test
    void testContainsEmpty() {
        assertFalse(pets.contains(pet1));
    }

    @Test
    void testContainsOneElementIncluded() {
        pets.addPet(pet1);
        assertTrue(pets.contains(pet2));
    }

    @Test
    void testContainsOneElementNotIncluded() {
        pets.addPet(pet1);
        assertFalse(pets.contains(pet3));
    }

    @Test
    void testContainsMultipleElementIncluded() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        assertTrue(pets.contains(pet3));
    }

    @Test
    void testContainsMultipleElementNotIncluded() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        assertFalse(pets.contains(pet4));
    }

    @Test
    void testGetPetAtIndexOneElement() {
        pets.addPet(pet1);
        assertEquals(pet1, pets.getPetAtIndex(0));
    }

    @Test
    void testGetPetAtIndexMultipleElementsFirst() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        assertEquals(pet1, pets.getPetAtIndex(0));
    }

    @Test
    void testGetPetAtIndexMultipleElementsLast() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        pets.addPet(pet4);
        assertEquals(pet4, pets.getPetAtIndex(2));
    }

    @Test
    void testGetNumPetsEmpty() {
        assertEquals(0, pets.getNumPets());
    }

    @Test
    void testGetNumPetsOne() {
        pets.addPet(pet1);
        assertEquals(1, pets.getNumPets());
    }

    @Test
    void testGetNumPetsMultiple() {
        pets.addPet(pet1);
        pets.addPet(pet3);
        assertEquals(2, pets.getNumPets());
    }
}
