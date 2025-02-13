import java.util.Scanner;

public class main {
    
    public static void main(String[] args) {
        String[][] tableau;
        joueurs joueur1 = new joueurs();
        joueurs joueur2 = new joueurs();
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("A combien de joueurs voulez vous jouez ?");
        //int nbrDeJoueurs = Integer.parseInt(scanner.nextLine());
        //for (int i = 0; i < nbrDeJoueurs; i++) {
        //    
        //}
        joueur1.setName();
        do {
            joueur2.setName();
            if (joueur1.getName().equals(joueur2.getName())) {
                System.out.println("\u001B[36mVous ne pouvez pas choisir le meme Nom que vos adversaire !\u001B[0m");
            }
        } while (joueur1.getName().equals(joueur2.getName()));

        joueur1.setSymbole();
        do {
            joueur2.setSymbole();
            if (joueur2.getSymbole() == joueur1.getSymbole()) {
                System.out.println("\u001B[36mVous ne pouvez pas choisir le meme Symbole que vos adversaire !\u001B[0m");
            }
        } while (joueur2.getSymbole() == joueur1.getSymbole());
    }
}
