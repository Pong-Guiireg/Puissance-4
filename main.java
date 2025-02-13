

public class main {
    
    public static void main(String[] args) {
        String[][] tableau;
        joueurs joueur1 = new joueurs();
        joueurs joueur2 = new joueurs();

        //do{joueur1.setName();}while(joueur1.name == joueur2.name);
        //do{joueur1.setSymbole();}while(joueur1.symboleDeJeu == joueur2.symboleDeJeu);
        //do{joueur2.setName();}while(joueur2.name == joueur1.name);
        //do{joueur2.setSymbole();}while (joueur2.symboleDeJeu == joueur1.symboleDeJeu);
        //System.out.println("\u001B[36mVous ne pouvez pas rentrez la meme valeur que vos adversaires !\u001B[0m");

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
