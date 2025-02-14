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
    public String toString(joueurs currentPlayers) {
        StringBuilder sb = new StringBuilder();
        boolean choice = true;

        if (!gameStarted) {
            System.out.print("Début de la partie :");
            String input = scanner.nextLine().toLowerCase();
            gameStarted = true;
            ClearTerminal();
        }
        if (gameStarted) {
            for (int i = 0; i < LIGNES; i++) {
                sb.append("#");
                for (int j = 0; j < COLONNES; j++) {
                    sb.append(plateau[i][j]);
                }
                sb.append("#\n");
            }
            sb.append("##########\n");
            sb.append(" abcdefgh\n");
            if (gameWin() && temp == true) {
                sb.append("Le joueur " + currentPlayers.getName() + " a gagné !\n");
                sb.append("Fin de la partie !");
                temp = false;
            }else {
                sb.append("C'est à " + currentPlayers.getName() + " de jouer !\n >");
            }
            if (temp == false) {
                sb.append("Voulez-vous rejouer ? (O/N)");
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("o")) {
                    ClearTerminal();
                    temp = true;
                    gameStarted = false;
                }else {
                    System.exit(0);
                }
                
            }
            
        }
        return sb.toString();
    }
}
