import java.util.Scanner;

public class Grille {
    private static final int LIGNES = 6;
    private static final int COLONNES = (8*2) - 1 ;
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

    public void replay(joueurs currentPlayers) {
        if (temp) {
            System.out.println("Le joueur " + currentPlayers.getName() + " a gagné !");
            temp = false;
        }
        System.out.print("Voulez-vous rejouer ? (O/N) ");
        pass = false;
        String input = scanner.nextLine().toLowerCase();
        switch (input) {
            case "o":
                ClearTerminal();
                for (int i = 0; i < LIGNES; i++) {
                    for (int j = 0; j < COLONNES; j++) {
                        plateau[i][j] = " ";
                    }
                }
                pass = true;
                temp = true;
                gameStarted = false;
                currentPlayers.nombreDeVictoire++;
            break;
            case "n":
                System.exit(0);
            default:
                System.out.println("\u001B[36mVeillez entrez quelque chose de correcte !\u001B[0m");
                replay(currentPlayers);
            break;
        }
    }

    boolean gameStarted = false;
    boolean temp = true;
    boolean pass = true;
    public void afficherGrille(joueurs currentPlayers) {
        if (!gameStarted) {
            ClearTerminal();
            System.out.println("Le joueur " + currentPlayers.getName() + " a "+currentPlayers.getNombreDeVictoire()+" victoire(s) !");
            System.out.print("Début de la partie :\n");
            gameStarted = true;
        }
        if (gameStarted) {
            for (int i = 0; i < LIGNES; i++) {
                System.out.print("#");
                for (int j = 0; j < COLONNES; j++) {
                    System.out.print(plateau[i][j]);
                }
                System.out.println("# ");
            }
            System.out.println("# # # # # # # # #");
            System.out.println(" a b c d e f g h");

            if (gameWin()) {
                replay(currentPlayers);
            }
            if (gameStarted && pass) {
                System.out.println("C'est à \u001B[31m" + currentPlayers.getName() + "\u001B[0m de jouer !");
                System.out.print("> ");
            }
        }
    }
}
