import java.util.Scanner;

public class joueurs extends JoueursGlobals{
    public int nombreDeVictoire;
    public String symboleDeJeu;
    public String name;
    Scanner scanner = new Scanner(System.in);

    public joueurs(){}
    public joueurs(String name, String symbole){
        this.name = name;
        this.symboleDeJeu = symbole;
    }
    public String setName(int nbr){
        System.out.println("Joueur "+nbr+", quel est votre nom ?");
        String name = scanner.nextLine();
        this.name = name;
        System.out.println("Joueur "+nbrDeJoueurs+", votre nom est : \u001B[32m" + this.name+"\u001B[0m");
        return name;
    }
    public String setSymbole(){
        System.out.println(this.name+", quel symbole voulez vous utiliser ?");
        String symbole = scanner.nextLine();
        this.symboleDeJeu = symbole;
        if (symbole.length() > 1) {
            System.out.println("\u001B[36mVotre symbole ne doit comporter qu'un seul caractere !\u001B[0m");
            return setSymbole();
        }
        System.out.println(this.name+", votre symbole est : \u001B[32m" + this.symboleDeJeu+"\u001B[0m");
        return symbole;
    }
    
    public void addNombreDeVictoire(int nombreDeVictoire) {
        this.nombreDeVictoire += nombreDeVictoire;
    }
    public String getName() {
        return name;
    }
    public String getSymbole() {
        return symboleDeJeu;
    }
    public int getNombreDeVictoire() {
        return nombreDeVictoire;
    }
}
