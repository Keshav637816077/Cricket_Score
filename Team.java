/***
 * This file contains the team class and adds players to team created by the user and also counts the player in the team and list players to be shown to user.
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

class Team {

    static Constant constant = new Constant();
    private String name;
    private Player[] players = new Player[200]; 
    private int playerCount = 0;

    public Team(String name) {
        this.name = name;
    }

    public boolean addPlayer(String playerName, String profession) {
        if (playerCount >= players.length) {
            return false; // Team is full
        }
        players[playerCount++] = new Player(playerName, profession);
        return true;
    }

    public String getName() {
        return name;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void listPlayers() {
        if (playerCount == 0) {
            System.out.print(constant.SPACE);
            System.out.println(constant.EMPTY);
        } else {
            for (int i = 0; i < playerCount; i++) {
                System.out.print(constant.SPACE);
                System.out.println((i + 1) + ". " + players[i].getName() + " - " + players[i].getProfession());
            }
        }
    }

    public String getPlayerName(int index) {
        if (index >= 0 && index < playerCount) {
            return players[index].getName();
        }
        return null;
    }
}
