

public class main {
    
    public static void main(String[] args) {
        String[][] tableau;
        joueurs joueur1 = new joueurs();
        joueurs joueur2 = new joueurs();

        do{joueur1.setName();}while(joueur1.name == joueur2.name);
        do{joueur1.setSymbole();}while(joueur1.symboleDeJeu == joueur2.symboleDeJeu);
        do{joueur2.setName();}while(joueur2.name == joueur1.name);
        do{joueur2.setSymbole();}while (joueur2.symboleDeJeu == joueur1.symboleDeJeu);
        System.out.println("\u001B[36mVous ne pouvez pas rentrez la même valeur que vos adversaires !\u001B[0m");
    }
}