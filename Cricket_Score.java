/***
 * Cricket Score Game
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

import java.util.*;

public class Cricket_Score {

    private static final int MAX_PLAYERS = 11;
    private static Team[] teams = new Team[100];
    private static int teamCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create a new team");
            System.out.println("2. Add player to a team");
            System.out.println("3. Show existing teams");
            System.out.println("4. Choose 2 teams and select 11 players from each");
            System.out.println("5. Exit");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    createTeam();
                    break;
                case "2": 
                    addPlayerToTeam();
                    break;
                case "3":
                    showTeams();
                    break;
                case "4":
                    chooseAndSelectPlayers();
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose between 1 and 5.");
            }
        }
    }

    private static void createTeam() {
        System.out.print("Enter the team name: ");
        String teamName = scanner.nextLine().trim();
        if (findTeam(teamName) != null) {
            System.out.println("Team already exists.");
        } else if (teamCount >= teams.length) {
            System.out.println("Maximum number of teams reached.");
        } else {
            teams[teamCount] = new Team(teamName);
            teamCount++;
            System.out.println("Team created successfully.");

            boolean addingPlayers = true;
            while (addingPlayers) {
                System.out.print("Enter the player's name: ");
                String playerName = scanner.nextLine().trim();
                System.out.print("Enter the player's profession: ");
                String profession = scanner.nextLine().trim();

                if (teams[teamCount - 1].addPlayer(playerName, profession)) {
                    System.out.println("Player added successfully.");
                } else {
                    System.out.println("Failed to add player. Team might be full.");
                }

                System.out.print("Do you want to add another player? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();
                if (!response.equals("yes")) {
                    addingPlayers = false;
                }
            }
        }
    }

    private static void addPlayerToTeam() {
        System.out.print("Enter the team name to add a player to: ");
        String teamName = scanner.nextLine().trim();
        Team team = findTeam(teamName);
        if (team == null) {
            System.out.println("Team does not exist.");
            return;
        }

        System.out.print("Enter the player's name: ");
        String playerName = scanner.nextLine().trim();
        System.out.print("Enter the player's profession: ");
        String profession = scanner.nextLine().trim();

        if (team.addPlayer(playerName, profession)) {
            System.out.println("Player added successfully.");
        } else {
            System.out.println("Failed to add player. Team might be full.");
        }
    }

    private static void showTeams() {
        if (teamCount == 0) {
            System.out.println("No teams available.");
            return;
        }
        System.out.println("Existing teams:");
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teams[i].getName());
        }
    }

    private static void chooseAndSelectPlayers() {
        if (teamCount < 2) {
            System.out.println("Not enough teams to choose from.");
            return;
        }

        showTeams();

        System.out.print("Select the first team by number: ");
        int firstTeamIndex = getValidTeamIndex();
        if (firstTeamIndex == -1) return;

        System.out.print("Select the second team by number: ");
        int secondTeamIndex = getValidTeamIndex();
        if (secondTeamIndex == -1 || secondTeamIndex == firstTeamIndex) {
            System.out.println("Invalid choice. Teams should be different.");
            return;
        }

        Team firstTeam = teams[firstTeamIndex];
        Team secondTeam = teams[secondTeamIndex];

        System.out.println("Selected Teams:");
        System.out.println("Team 1: " + firstTeam.getName());
        firstTeam.listPlayers();
        System.out.println("Team 2: " + secondTeam.getName());
        secondTeam.listPlayers();

        System.out.println("Select 11 players from Team 1:");
        String[] selectedPlayersFirstTeam = selectPlayers(firstTeam);

        System.out.println("Select 11 players from Team 2:");
        String[] selectedPlayersSecondTeam = selectPlayers(secondTeam);

        System.out.println("Selected players from Team 1:");
        for (String player : selectedPlayersFirstTeam) {
            System.out.println(player);
        }

        System.out.println("Selected players from Team 2:");
        for (String player : selectedPlayersSecondTeam) {
            System.out.println(player);
        }
    }

    private static int getValidTeamIndex() {
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index >= 0 && index < teamCount) {
                return index;
            } else {
                System.out.println("Invalid choice. Try again.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number.");
            return -1;
        }
    }

    private static String[] selectPlayers(Team team) {
        if (team.getPlayerCount() < MAX_PLAYERS) {
            System.out.println("Not enough players in the team. You have " + team.getPlayerCount() + " players.");
            return new String[0];
        }

        team.listPlayers();

        String[] selectedPlayers = new String[MAX_PLAYERS];
        int count = 0;
        while (count < MAX_PLAYERS) {
            System.out.print("Select player " + (count + 1) + " by number (1-" + team.getPlayerCount() + "): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (choice >= 0 && choice < team.getPlayerCount()) {
                    String playerName = team.getPlayerName(choice);
                    boolean alreadySelected = false;
                    for (int i = 0; i < count; i++) {
                        if (selectedPlayers[i].equals(playerName)) {
                            alreadySelected = true;
                            break;
                        }
                    }
                    if (!alreadySelected) {
                        selectedPlayers[count++] = playerName;
                    } else {
                        System.out.println("Player already selected.");
                    }
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
        return selectedPlayers;
    }

    private static Team findTeam(String teamName) {
        for (int i = 0; i < teamCount; i++) {
            if (teams[i].getName().equals(teamName)) {
                return teams[i];
            }
        }
        return null;
    }

    private static class Team {
        private String name;
        private Player[] players = new Player[20];
        private int playerCount = 0;

        Team(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        boolean addPlayer(String name, String profession) {
            if (playerCount < players.length) {
                players[playerCount++] = new Player(name, profession);
                return true;
            }
            return false;
        }

        int getPlayerCount() {
            return playerCount;
        }

        String getPlayerName(int index) {
            if (index >= 0 && index < playerCount) {
                return players[index].getName();
            }
            return null;
        }

        void listPlayers() {
            System.out.println("Available players:");
            for (int i = 0; i < playerCount; i++) {
                System.out.println((i + 1) + ". " + players[i].getName() + " (" + players[i].getProfession() + ")");
            }
        }
    }

    private static class Player {
        private String name;
        private String profession;

        Player(String name, String profession) {
            this.name = name;
            this.profession = profession;
        }

        String getName() {
            return name;
        }

        String getProfession() {
            return profession;
        }
    }
}
