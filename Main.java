import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        joueurs joueur1 = new joueurs();
        joueurs joueur2 = new joueurs();
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("A combien de joueurs voulez vous jouez ?");
        //int nbrDeJoueurs = Integer.parseInt(scanner.nextLine());
        //for (int i = 0; i < nbrDeJoueurs; i++) {
        //
        //}
        do{
            joueur1.setName(1);
            if (joueur1.getName().equals("")) {
                System.out.println("\u001B[36mVous devez choisir un Nom !\u001B[0m");
            }
        } while (joueur1.getName().equals(""));
        do {
            joueur2.setName(2);
            if (joueur1.getName().equals(joueur2.getName())) {
                System.out.println("\u001B[36mVous ne pouvez pas choisir le meme Nom que vos adversaire !\u001B[0m");
            }
        } while (joueur1.getName().equals(joueur2.getName()));

        do{
            joueur1.setSymbole();
            if (joueur1.getSymbole().equals("")) {
                System.out.println("\u001B[36mVous devez choisir un Symbole !\u001B[0m");
            }
        } while (joueur1.getSymbole().equals(""));
        do {
            joueur2.setSymbole();
            if (joueur2.getSymbole().equals(joueur1.getSymbole())) {
                System.out.println("\u001B[36mVous ne pouvez pas choisir le meme Symbole que vos adversaire !\u001B[0m");
            }
        } while (joueur2.getSymbole().equals(joueur1.getSymbole()));

        JoueursGlobals allJoueurs = new JoueursGlobals();
        allJoueurs.addJoueursInAllJoueurs(joueur1);
        allJoueurs.addJoueursInAllJoueurs(joueur2);
        //--------------------------------------------------------------------------
        joueurs currentPlayers = joueur1;
        Grille grille = new Grille(currentPlayers);
        Scanner scanner = new Scanner(System.in);


        while (true) {
            grille.afficherGrille(currentPlayers);
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("q")) {
                break;
            }
            if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= 'h') {
                int colonne = input.charAt(0) - 'a';
                if (colonne > 0) {
                    colonne = colonne * 2;
                }
                if (grille.placerPion(colonne, currentPlayers.getSymbole())) {
                    if (!grille.gameWin()) {
                        currentPlayers = currentPlayers.equals(joueur1) ? joueur2 : joueur1;
                    }
                } else {
                    System.out.println("\u001B[36mColonne pleine ou invalide, veuillez réessayer.\u001B[0m");
                }
            } else {
                System.out.println("\u001B[36mEntrée invalide. Utilisez les lettres a à h.\u001B[0m");
            }
        }
        scanner.close();
    }
}
