/***
 * This file contains all the methods used in the program Cricket Score Game and there are 15 method that are in this program.
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

import java.util.*;

public class Methods {

    static Constant constant = new Constant();
    public static Team[] teams = new Team[100];
    public static int teamCount = 0;
    public static Scanner input = new Scanner(System.in);
    public static int totalOvers = 0;
    public static int targetScore = 0;

    //This method takes the team name and finds if the Team already exists or not.
    public static Team findTeam(String name) {
        for(int i = 0; i < teamCount; i++){
            if(teams[i].getName().equalsIgnoreCase(name)){
                return teams[i];
            }
        }
        return null;
    }

    //This method creates the new Team.
    public static void createTeam() {
        System.out.print(constant.CREATE_TEAM);
        String teamName = input.nextLine().trim();
        if(findTeam(teamName) != null){
            System.out.println(constant.ALREADY_PRESENT);
        }else if(teamCount >= teams.length){
            System.out.println(constant.MAX_TEAM);
        }else{
            teams[teamCount] = new Team(teamName);
            teamCount++;
            System.out.println(constant.TEAM_CREATED);
        }
    }

    //This method creates two teams that already exists in the program.
    public static void createDefaultTeams() {
        teams[teamCount++] = new Team("Empty Heads");
        teams[teamCount++] = new Team("Electric Eagles");

        for(int i = 1; i <= 11; i++){
            teams[0].addPlayer("Player A" + i, i <= 6 ? "Batsman" : "Bowler");
            teams[1].addPlayer("Player B" + i, i <= 6 ? "Batsman" : "Bowler");
        }
    }
    
    //This method adds Player or Players to the Team of your choice.
    public static void addPlayerToTeam(){
        System.out.print(constant.ENTER_TEAM);
        String teamName = input.nextLine().trim();
        Team team = findTeam(teamName);
        if (team == null) {
            System.out.println(constant.TEAM_UNKNOWN);
            return;
        }
        boolean addingPlayers = true;
        while (addingPlayers) {
            System.out.print(constant.PLAYER_NAME);
            String playerName = input.nextLine().trim();
            System.out.print(constant.PLAYER_TYPE);
            String profession = input.nextLine().trim();

            if (teams[teamCount - 1].addPlayer(playerName, profession)) {
                System.out.println(constant.PLAYER_ADDED);
            } else {
                System.out.println(constant.TEAM_FULL);
            }

            System.out.print(constant.CHOICE);
            String response = input.nextLine().trim().toLowerCase();
            if (!response.equals("y")) {
                addingPlayers = false;
            }
        }
    }

    //This method shows the existing Teams and their Players.
    public static void showTeams() {
        if (teamCount == 0) {
            System.out.println(constant.NO_TEAM);
            return;
        }
        System.out.println(constant.TEAMS);
        for (int i = 0; i < teamCount; i++) {
            System.out.println((i + 1) + ". " + teams[i].getName());
            teams[i].listPlayers();
        }
    }

    //This method conducts toss and selects playerfrom both the teams and choose who is batting and bowling also it shows results after execution of playInnings.
    private static void conductToss(Team firstTeam, Team secondTeam, String[] selectedPlayersFirstTeam, String[] selectedPlayersSecondTeam) {
        System.out.println(constant.TOSS);
        System.out.print(constant.TOSS_WINNER);
        String winningTeamName = input.nextLine().trim();
        Team winningTeam = findTeam(winningTeamName);
        if (winningTeam == null || (!winningTeam.equals(firstTeam) && !winningTeam.equals(secondTeam))) {
            System.out.println(constant.INVALID_TEAM);
            return;
        }
        System.out.print(constant.CHOOSE);
        String choice = input.nextLine().trim().toLowerCase();
        if (!choice.equals("bat") && !choice.equals("bowl")) {
            System.out.println(constant.INVALID_CHOOSE);
            return;
        }
        Team battingTeam = (choice.equals("bat")) ? winningTeam : (winningTeam.equals(firstTeam) ? secondTeam : firstTeam);
        Team bowlingTeam = (battingTeam.equals(firstTeam)) ? secondTeam : firstTeam;
        String[] battingTeamPlayers = (battingTeam.equals(firstTeam)) ? selectedPlayersFirstTeam : selectedPlayersSecondTeam;
        String[] bowlingTeamPlayers = (bowlingTeam.equals(firstTeam)) ? selectedPlayersFirstTeam : selectedPlayersSecondTeam;
        System.out.println(battingTeam.getName() + constant.BATTING);
        String[] batsmen = selectBatsmenFromSelectedPlayers(battingTeamPlayers);
        System.out.println(constant.STRIKER + batsmen[0] + constant.NON_STRIKER + batsmen[1]);
        System.out.println(bowlingTeam.getName() + constant.BOWLING);
        String bowler = selectBowlerFromSelectedPlayers(bowlingTeamPlayers);
        System.out.println(constant.BOWLER + bowler);
        System.out.print(constant.OVERS);
        totalOvers = Integer.parseInt(input.nextLine().trim());
        System.out.println(constant.MATCH_BEGIN);
        int firstInningsScore = playInnings(battingTeam, bowlingTeam, batsmen, bowler);
        targetScore = firstInningsScore + 1;   
        System.out.println(battingTeam.getName() + constant.SCORED + firstInningsScore + constant.RUNS);
        System.out.println(bowlingTeam.getName() + constant.TO_SCORE + targetScore + constant.TO_WIN);
        String[] secondInningsBatsmen = selectBatsmenFromSelectedPlayers(bowlingTeamPlayers);
        String secondInningsBowler = selectBowlerFromSelectedPlayers(battingTeamPlayers);
        int secondInningsScore = playInnings(bowlingTeam, battingTeam, secondInningsBatsmen, secondInningsBowler);   
        if (secondInningsScore >= targetScore) {
            System.out.println(bowlingTeam.getName() + constant.WIN_BY + (secondInningsScore - targetScore + 1) + constant.RUNS1);
        } else {
            System.out.println(battingTeam.getName() +  constant.WIN_BY + (targetScore - secondInningsScore) + constant.RUNS1);
        }
    }
    
    //This method starts after execution of case4 and chooses team and players. 
    public static void startMatch() {
        if (teamCount < 2) {
            System.out.println(constant.LESS_TEAM);
            return;
        }
        showTeams();
        System.out.print(constant.FIRST_TEAM);
        int firstTeamIndex = getValidTeamIndex();
        if (firstTeamIndex == -1) return;
        System.out.print(constant.SECOND_TEAM);
        int secondTeamIndex = getValidTeamIndex();
        if (secondTeamIndex == -1 || secondTeamIndex == firstTeamIndex) {
            System.out.println(constant.INVALID_TEAM1);
            return;
        }
        Team firstTeam = teams[firstTeamIndex];
        Team secondTeam = teams[secondTeamIndex];
        System.out.print(constant.SELECTION + firstTeam.getName() + "? ");
        int numPlayersFirstTeam = getNumberOfPlayers(firstTeam);
        String[] selectedPlayersFirstTeam = selectPlayers(firstTeam, numPlayersFirstTeam);
        System.out.print(constant.SELECTION + secondTeam.getName() + "? ");
        int numPlayersSecondTeam = getNumberOfPlayers(secondTeam);
        String[] selectedPlayersSecondTeam = selectPlayers(secondTeam, numPlayersSecondTeam);
        conductToss(firstTeam, secondTeam, selectedPlayersFirstTeam, selectedPlayersSecondTeam);
    }
    
    //This method Selects Batsmen from the team.
    private static String[] selectBatsmenFromSelectedPlayers(String[] selectedPlayers) {
        System.out.println(constant.BATSMAN_SELECT);
        for (int i = 0; i < selectedPlayers.length; i++) {
            System.out.println((i + 1) + ". " + selectedPlayers[i]);
        }
        return selectPlayersFromList(selectedPlayers, 2);
    }
    
    //This method selects Bowler from the team.
    private static String selectBowlerFromSelectedPlayers(String[] selectedPlayers) {
        System.out.println(constant.BOWLER_SELECT);
        for (int i = 0; i < selectedPlayers.length; i++) {
            System.out.println((i + 1) + ". " + selectedPlayers[i]);
        }
        return selectPlayersFromList(selectedPlayers, 1)[0];
    }
    
    //This method prints the player list before selecting batsmen and bowler.
    private static String[] selectPlayersFromList(String[] playerList, int count) {
        String[] selectedPlayers = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print(constant.PLAYER_NUMBER + (i + 1) + " - ");
            int playerNumber = Integer.parseInt(input.nextLine().trim());
            selectedPlayers[i] = playerList[playerNumber - 1];
        }
        return selectedPlayers;
    }
    
    //This method helps select the number of players from the selected team.
    private static int getNumberOfPlayers(Team team) {
        int count = 0;
        while (true) {
            System.out.print(constant.PLAYER_SELECTION + team.getPlayerCount() + constant.PLAYER_SELECTION1);
            try {
                count = Integer.parseInt(input.nextLine().trim());
                if (count >= 1 && count <= team.getPlayerCount()) {
                    return count;
                } else {
                    System.out.println(constant.INVALID_NUMBER + team.getPlayerCount() + ".");
                }
            } catch (Exception e) {
                System.out.println(constant.INVALID_INPUT);
            }
        }
    }

    //This method finds if the index given by the user is valid or not.
    private static int getValidTeamIndex() {
        try {
            int index = Integer.parseInt(input.nextLine().trim()) - 1;
            if (index >= 0 && index < teamCount) {
                return index;
            } else {
                System.out.println(constant.INVALID_CHOICE);
                return -1;
            }
        } catch (Exception e) {
            System.out.println(constant.INVALID_INPUT);
            return -1;
        }
    }

    //This method asks the user for runs and handles the scoring of the match.
    private static int playInnings(Team battingTeam, Team bowlingTeam, String[] batsmen, String bowler) {
        int totalRuns = 0;
        int strikerIndex = 0;
        int nonStrikerIndex = 1;
        int[] runsScored = new int[2];
        int wicketsLost = 0;
        for (int over = 1; over <= totalOvers; over++) {
            System.out.println(constant.OVER + over + constant.STARTS);
            for (int ball = 1; ball <= 6;) {
                System.out.println(constant.BALL + ball);
                System.out.println(constant.CURRENT_STRIKER + batsmen[strikerIndex]);
                System.out.println(constant.BOWLER + bowler);
                System.out.println(constant.RUN_TYPE);
                String runInput = input.nextLine().trim().toLowerCase();
                int runs = 0;
                boolean validBall = true;
                switch (runInput) {
                    case "0":
                    runs = 0;
                    System.out.println(batsmen[strikerIndex] + constant.NO_RUNS);
                    break;
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "6":
                        runs = Integer.parseInt(runInput);
                        break;
                    case "no ball":
                        runs = 1;
                        validBall = false;
                        break;
                    case "wide ball":
                        runs = 1;
                        totalRuns += runs;
                        runsScored[strikerIndex] += runs;
                        System.out.println(constant.WIDE + batsmen[strikerIndex] + constant.EXTRA_RUN);
                        validBall = false;
                        continue;
                    case "out":
                        System.out.println(batsmen[strikerIndex] + constant.OUT);
                        wicketsLost++;
                        if (wicketsLost >= 10) {
                            System.out.println(constant.ALL_OUT);
                            return totalRuns;
                        }
                        batsmen[strikerIndex] = getNextBatsman(battingTeam);
                        System.out.println(constant.NEW_BATSMAN + batsmen[strikerIndex]);
                        break;
                    default:
                        System.out.println(constant.INVALID_RUN);
                        validBall = false;
                }
                totalRuns += runs;
                runsScored[strikerIndex] += runs;
                if (runs == 1 || runs == 3) {
                    int temp = strikerIndex;
                    strikerIndex = nonStrikerIndex;
                    nonStrikerIndex = temp;
                }
                if (validBall) {
                    System.out.println(batsmen[strikerIndex] + constant.SCORED + runs + constant.RUNS);
                    ball++;
                }
            }
            System.out.println(constant.END_OVER + over + constant.SWAP);
            int temp = strikerIndex;
            strikerIndex = nonStrikerIndex;
            nonStrikerIndex = temp;
            System.out.println(batsmen[0] + constant.HAS_SCORED + runsScored[0] + constant.RUNS);
            System.out.println(batsmen[1] + constant.HAS_SCORED + runsScored[1] + constant.RUNS);
        }
        return totalRuns;
    }

    //This method selects the new batsman after the out.
    private static String getNextBatsman(Team battingTeam) {
        System.out.println(constant.NEXT_BATSMAN);
        return selectPlayers(battingTeam, 1)[0];
    }

    //This method gets the Player list to display for the user.
    private static String[] selectPlayers(Team team, int count) {
        team.listPlayers();
        String[] selectedPlayers = new String[count];
        for (int i = 0; i < count; i++) {
            System.out.print(constant.PLAYER_NUMBER + (i + 1) + " - ");
            int playerNumber = Integer.parseInt(input.nextLine().trim());
            selectedPlayers[i] = team.getPlayerName(playerNumber - 1);
        }
        return selectedPlayers;
    }    
}
