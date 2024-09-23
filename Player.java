/***
 * This file contain the player class and gets the player and their profession.
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

class Player {

    private String name;
    private String profession;

    //This method takes player name and profession and then saves it.
    public Player(String name, String profession) {
        this.name = name;
        this.profession = profession;
    }

    //This method gets name of the player.
    public String getName() {
        return name;
    }

    //This method gets the Profession of the player.
    public String getProfession() {
        return profession;
    }
}