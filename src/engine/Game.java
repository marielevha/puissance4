package engine;

import ia.IAMariel;
import old.Player;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private static int player, tour = 1, column;
    private static boolean win = false;
    private static Plateau plateau;
    private static IAMariel mariel;
    private static Player player1, player2;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        //plateau = new Plateau(6, 7);
        //System.out.print(plateau.toString() + "\n");
        //System.out.print(plateau.toStringIA() + "\n");
        init();
    }

    /**
     * Init : initialisation de la partie
     */
    public static void init() {
        String string = "6x7-" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000" +
                "0000000";
        plateau = new Plateau(string, 0);
        //plateau = new Plateau(6, 7);
        System.out.print(plateau.toString() + "\n");

        /**
         * Initialisation du joueur réel avec le numéro 1
         * @number : 1
         */
        player1 = new Player(1);
        player2 = new Player(2);
        /**
         * Initialisation de l'IA avec le numéro 2 et le niveau 5
         * @level : 5
         * @number : 2
         */
        //mariel = new IAMariel(5, 2);

        player = player1.getNumber();
        while (!win) {
            if (player == player1.getNumber()) {
                System.out.println("player " + player + "'s turn !");
                System.out.println("Choice column 1 to 7 : ");
                String line = scanner.nextLine();
                try {
                    column  = Integer.parseInt(line);
                    if (column >= 1 && column <= 7) {
                        // Si plateau n'est pas plein, jouer le coup
                        if (!plateau.noCheck()) {
                            if (!plateau.fullColumn(column)) {
                                win = plateau.addPoint((column - 1), player);
                                if (!win) {
                                    player = player2.getNumber();
                                }
                                System.out.println(plateau.toString());
                            }
                        }
                        // Sinon partie terminée : match null
                        else {
                            end(false);
                        }
                    }
                    else {
                        System.err.println("Illegal entry new column !");
                    }
                } catch (Exception e) {
                    System.err.println("Illegal entry new column !");
                    System.err.println(e.getMessage());
                }
            }
            else {
                System.out.println("player " + player + "'s turn !");
                System.out.println("Choice column 1 to 7 : ");
                String line = scanner.nextLine();
                try {
                    column  = Integer.parseInt(line);
                    if (column >= 1 && column <= 7) {
                        // Si plateau n'est pas pleine, jouer le coup
                        if (!plateau.noCheck()) {
                            if (!plateau.fullColumn(column)) {
                                win = plateau.addPoint((column - 1), player);
                                if (!win) {
                                    player = player1.getNumber();
                                }
                                System.out.println(plateau.toString());
                            }
                        }
                        // Sinon partie terminée : match null
                        else {
                            end(false);
                        }
                    }
                    else {
                        System.err.println("Illegal entry new column !");
                    }
                } catch (Exception e) {
                    System.err.println("Illegal entry new column !");
                    System.err.println(e.getMessage());
                }
            }
        }
        end(true);
    }

    /**
     * End : méthode de fin de partie
     * Si type = true il y a un gagnant
     * Sinon match null
     * @param type
     */
    public static void end(boolean type) {
        System.out.println("GAME OVER !!!");
        if (type) {
            player = (player % 2 == 1 ? 1 : 2);
            System.out.println("Player " + player + " win");
        }
        else {
            System.out.println("No match winner null !!!");
        }
        reload();
    }

    /**
     * Reload : méthode permettant de relancer la partie
     */
    public static void reload() {
        System.out.println("Select 1 : New Part : ");
        System.out.println("Select 2 : Leave : ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            win = false;
            plateau.empty();
            init();
        }
        System.exit(0);
    }
}
