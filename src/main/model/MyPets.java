package model;

import java.util.ArrayList;

public class MyPets {
    private ArrayList<Pet> myPets;

    //EFFECTS: creates a new list of pets
    public MyPets() {
        myPets = new ArrayList<>();
    }

    //REQUIRES: !this.contains(name), targetAmount >= 0
    //MODIFIES: this
    //EFFECTS: adds a new pet with its name and targetAmount(unit) of
    //         feeding per day
    public void addPet(String name, double targetAmount, String unit) {
        if (!contains(name)) {
            Pet newPet = new Pet(name, targetAmount, unit);
            myPets.add(newPet);
        } //else {!!! throw Exception}
    }

    //REQUIRES: 1 <= petNum <= this.myPets.size(), !this.contains(newName),
    //          newTargetAmount >= 0;
    //MODIFIES: this
    //EFFECTS: edits the pet at petNum, with newName and newTargetAmount(newUnit)
    public void editPet(int petNum, String newName, double newTargetAmount,
                        String newUnit) {
        int index = petNum - 1;
        Pet petToEdit = this.myPets.get(index);
        petToEdit.setName(newName);
        petToEdit.setTargetAmount(newTargetAmount);
        petToEdit.setUnit(newUnit);
    }

    //REQUIES: there is no duplicates in myPets
    //EFFECTS: returns true if a pet with name is contained in myPets.
    //         returns false otherwise.
    public boolean contains(String name) {
        for (Pet pet: myPets) {
            if (pet.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: 0 <= i <= myPets.size() - 1
    //EFFECTS: gets the Pet at the idex in myPets
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
}
