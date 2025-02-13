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

    public Grille() {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean gameStarted = false;
        boolean choice = false;

        if (!gameStarted) {
            System.out.print("Début de la partie");
            String input = scanner.nextLine().toLowerCase();
            gameStarted = true;
            ClearTerminal();
        }
        if (gameStarted) {
            if (!choice) {
                System.out.print("> ");
                String input = scanner.nextLine().toLowerCase();
                choice = true;
            }
            for (int i = 0; i < LIGNES; i++) {
                sb.append("#");
                for (int j = 0; j < COLONNES; j++) {
                    sb.append(plateau[i][j]);
                }
                sb.append("#\n");
            }
            sb.append("##########\n");
            sb.append(" abcdefgh");
        }
        return sb.toString();
    }
}
