import java.util.Scanner;

public class joueurs extends joueursGlobals{
    public int nombreDeVictoire;
    public String symboleDeJeu;
    public String name;

    public joueurs(){}
    public joueurs(String name, String symbole){
        this.name = name;
        this.symboleDeJeu = symbole;
    }
    public String setName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Joueur "+nbrDeJoueurs+", quel est votre nom ?");
        String name = scanner.nextLine();
        this.name = name;
        System.out.println("Joueur "+nbrDeJoueurs+", votre nom est : " + this.name);
        nbrDeJoueurs++;
        return name;
    }
    public String setSymbole(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(this.name+", quel symbole voulez vous utiliser ?");
        String symbole = scanner.nextLine();
        this.symboleDeJeu = symbole;
        if (symbole.length() > 1) {
            System.out.println("\u001B[36mVotre symbole ne doit comporter qu'un seul caractere !\u001B[0m");
            return setSymbole();
        }
        System.out.println(this.name+", votre symbole est : " + this.symboleDeJeu);
        return symbole;
    }
    
    public void addNombreDeVictoire(int nombreDeVictoire) {
        this.nombreDeVictoire += nombreDeVictoire;
    }
    public String getName() {
        return name;
    }
    public String getSymboleDeJeu() {
        return symboleDeJeu;
    }
    public int getNombreDeVictoire() {
        return nombreDeVictoire;
    }
}
