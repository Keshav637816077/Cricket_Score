/***
 * This file contain the player class and gets the player and their profession.
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

class Player {

    private String name;
    private String profession;

    public Player(String name, String profession) {
        this.name = name;
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }
}