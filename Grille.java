import java.util.Scanner;

public class Grille {
    private static final int LIGNES = 6;
    private static final int COLONNES = 8;
    private String[][] plateau;
    private Scanner scanner = new Scanner(System.in);

    public void ClearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Grille(joueurs currentPlayers) {
        plateau = new String[LIGNES][COLONNES];
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                plateau[i][j] = " ";
            }
        }
    }

    public boolean placerPion(int colonne, String symbole) {
        if (colonne < 0 || colonne >= COLONNES) {
            return false;
        }
        for (int ligne = LIGNES - 1; ligne >= 0; ligne--) {
            if (plateau[ligne][colonne].equals(" ")) {
                plateau[ligne][colonne] = symbole;
                return true;
            }
        }
        return false;
    }

    public boolean gameWin() {
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                if (!plateau[i][j].equals(" ")) {
                    if (j + 3 < COLONNES && plateau[i][j].equals(plateau[i][j + 1]) && plateau[i][j].equals(plateau[i][j + 2]) && plateau[i][j].equals(plateau[i][j + 3])) {
                        return true;
                    }
                    if (i + 3 < LIGNES) {
                        if (plateau[i][j].equals(plateau[i + 1][j]) && plateau[i][j].equals(plateau[i + 2][j]) && plateau[i][j].equals(plateau[i + 3][j])) {
                            return true;
                        }
                        if (j + 3 < COLONNES && plateau[i][j].equals(plateau[i + 1][j + 1]) && plateau[i][j].equals(plateau[i + 2][j + 2]) && plateau[i][j].equals(plateau[i + 3][j + 3])) {
                            return true;
                        }
                        if (j - 3 >= 0 && plateau[i][j].equals(plateau[i + 1][j - 1]) && plateau[i][j].equals(plateau[i + 2][j - 2]) && plateau[i][j].equals(plateau[i + 3][j - 3])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean gameStarted = false;
    boolean temp = true;
    public void afficherGrille(joueurs currentPlayers) {
        if (!gameStarted) {
            System.out.print("Début de la partie :");
            String input = scanner.nextLine().toLowerCase();
            gameStarted = true;
            ClearTerminal();
        }

        if (gameStarted) {
            for (int i = 0; i < LIGNES; i++) {
                System.out.print("#");
                for (int j = 0; j < COLONNES; j++) {
                    System.out.print(plateau[i][j]);
                }
                System.out.println("#");
            }
            System.out.println("##########");
            System.out.println(" abcdefgh");

            if (gameWin()) {
                if (temp) {
                    System.out.println("Le joueur " + currentPlayers.getName() + " a gagné !");
                    System.out.print("Voulez-vous rejouer ? (O/N) ");
                    temp = false;
                }
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("o")) {
                    ClearTerminal();
                    for (int i = 0; i < LIGNES; i++) {
                        for (int j = 0; j < COLONNES; j++) {
                            plateau[i][j] = " ";
                        }
                    }
                    temp = true;
                    gameStarted = false;
                    currentPlayers.nombreDeVictoire++;
                } else if (input.equals("n")) {
                    System.exit(0);
                }
            } else {
                System.out.println("C'est à " + currentPlayers.getName() + " de jouer !");
                System.out.print("> ");
            }
        }
    }
}
