/***
 * Week-3 Assignment - Cricket Score Game
 * 
 * This Project has 5 files i.e. Constant.java, Cricket_score_game.java, Methods.java, Player.java, Team.java .
 * This program creates the team and creates the score for every over and creates the scoreboard for who wins and who looses in the cricket match.
 * 
 * Program Owner - Keshav Kumar
 * Date - 17/09/2024
 */

import java.util.*;

public class Cricket_score_game{

    static Methods methods = new Methods();
    static Constant constant = new Constant();
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Methods.createDefaultTeams();

        while (true) {
            System.out.println(constant.WELCOME);
            System.out.println(constant.OPTIONS);
            System.out.print(constant.ENTER_CHOICE);
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1":
                    Methods.createTeam();
                    break;
                case "2":
                    Methods.addPlayerToTeam();
                    break;
                case "3":
                    Methods.showTeams();
                    break;
                case "4":
                    Methods.startMatch();
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println(constant.INVALID);
            }
        }
    }    
}