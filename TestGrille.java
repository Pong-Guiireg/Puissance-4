import java.util.Scanner;

public class TestGrille {
    public static void main(String[] args) {
        Grille grille = new Grille();
        Scanner scanner = new Scanner(System.in);
        String currentPlayer = "X";

        while (true) {
            // Afficher la grille
            System.out.println(grille);

            // Demander au joueur de choisir une colonne
            System.out.print(" ");
            String input = scanner.nextLine().toLowerCase();

            // Vérifier si l'utilisateur veut quitter
            if (input.equals("q")) {
                break;
            }

            // Convertir la lettre en index de colonne (a=0, b=1, etc.)
            if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= 'h') {
                int colonne = input.charAt(0) - 'a';

                // Placer le pion
                if (grille.placerPion(colonne, currentPlayer)) {
                    // Alterner entre X et O
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                } else {
                    System.out.println("Colonne pleine ou invalide, veuillez réessayer.");
                }
            } else {
                System.out.println("Entrée invalide. Utilisez les lettres a à h.");
            }
        }

        scanner.close();
    }
}
