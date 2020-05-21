package engine;

import ia.IAMariel;
import engine.player.Player;

import java.util.Scanner;

public class Game {
    private static int player, tour = 1, column;
    private static boolean win = false, added = false;
    private static Plateau plateau;
    private static IAMariel mariel;
    private static Player player1, player2;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        init();
    }

    /**
     * Init : initialisation de la partie
     */
    public static void init() {
        plateau = new Plateau(6, 7);
        System.out.print(plateau.toString() + "\n");

        /**
         * Initialisation du joueur réel avec le numéro 1
         * @number : 1
         */
        player1 = new Player(1);

        /**
         * Initialisation du joueur réel avec le numéro 2
         * @number : 1
         */
        player2 = new Player(2);

        while (!win) {
            if (tour == 1) {
                player = player1.getNumber();
                move(player1);
                if (added) {
                    tour = 2;
                }
            }
            else {
                player = player2.getNumber();
                move(player2);
                if (added) {
                    tour = 1;
                }
            }
        }
        end(true);
    }

    /**
     * Move : cette méthode permet de à un joueur réel de jouer un coup
     * @param realPlayer
     */
    public static void move(Player realPlayer) {
        System.out.println("player " + realPlayer.getNumber() + "'s turn !");
        System.out.println("Choice column 1 to 7 : ");
        String line = scanner.nextLine();
        try {
            column  = (Integer.parseInt(line) - 1);
            if (column >= 0 && column < 7) {
                // Si plateau n'est pas plein, jouer le coup
                if (!plateau.full()) {
                    if (!plateau.fullColumn(column)) {
                        win = plateau.addPoint((column), realPlayer.getNumber());
                        added = true;
                        if (!win) {
                            player = realPlayer.getNumber();
                        }
                        System.out.println(plateau.toString());
                    }
                    else {
                        added = false;
                        System.err.println("This column (" + column + ") is full, choose another !");
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
