package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents the collection of pets.
public class MyPets implements Writable {
    private ArrayList<Pet> myPets;

    //EFFECTS: creates a new list of pets
    public MyPets() {
        myPets = new ArrayList<>();
    }

    //REQUIRES: !this.contains(pet)
    //MODIFIES: this
    //EFFECTS: adds a new pet to myPets
    public void addPet(Pet pet) {
        if (!contains(pet)) {
            myPets.add(pet);

            String addedPetString = pet.getName() + " (" + pet.getTargetAmount() + pet.getUnit() + " / day)";
            String logString = "Added a pet: " + addedPetString;
            EventLog.getInstance().logEvent(new Event(logString));
        } //else {!!! throw Exception}
    }

    //REQUIRES: 0 <= petNum <= this.myPets.size()-1, !this.contains(newName),
    //          newTargetAmount >= 0;
    //MODIFIES: this
    //EFFECTS: edits the pet at petNum, with newName and newTargetAmount(newUnit)
    public void editPet(int petNum, String newName, double newTargetAmount,
                        String newUnit) {
        Pet petToEdit = this.myPets.get(petNum);

        String prePetString = petToEdit.getName() + " (" + petToEdit.getTargetAmount()
                + petToEdit.getUnit() + " / day)";

        petToEdit.setName(newName);
        petToEdit.setTargetAmount(newTargetAmount);
        petToEdit.setUnit(newUnit);

        String postPetString = petToEdit.getName() + " (" + petToEdit.getTargetAmount()
                + petToEdit.getUnit() + " / day)";
        String logString = "Edited a pet: " + prePetString + " -> " + postPetString;
        EventLog.getInstance().logEvent(new Event(logString));
    }

    //REQUIRES: 0 <= petNum <= this.myPets.size()-1
    //MODIFIES: this
    //EFFECTS: deletes the Pet with petNum
    public Pet deletePet(int petNum) {
        Pet deletedPet = this.myPets.remove(petNum);

        String deletedPetString = deletedPet.getName() + " (" + deletedPet.getTargetAmount()
                + deletedPet.getUnit() + " / day)";
        String logString = "Deleted a pet: " + deletedPetString;
        EventLog.getInstance().logEvent(new Event(logString));

        return deletedPet;
    }

    //EFFECTS: returns true if a pet with the same name as the
    //         provided pet exists
    public boolean contains(Pet pet) {
        for (Pet currentPet: myPets) {
            if (currentPet.getName().equals(pet.getName())) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: 0 <= i <= myPets.size() - 1
    //EFFECTS: gets the Pet at the index in myPets
    public Pet getPetAtIndex(int i) {
        return this.myPets.get(i);
    }

    //EFFECTS: returns the number of elements in myPets
    public int getNumPets() {
        return this.myPets.size();
    }

    public ArrayList<Pet> getMyPets() {
        return this.myPets;
    }

    //EFFECTS: returns JSONObject representing this MyPets instance
    @Override
    public JSONObject toJson() {
        JSONObject petsObject = new JSONObject();
        petsObject.put("pets", petsToJson());
        return petsObject;
    }

    // EFFECTS: returns pets in this myPets as a JSON array
    private JSONArray petsToJson() {
        JSONArray petsArray = new JSONArray();

        for (Pet pet : myPets) {
            petsArray.put(pet.toJson());
        }

        return petsArray;
    }
}
