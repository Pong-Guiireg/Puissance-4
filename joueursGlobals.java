import java.util.List;

public class joueursGlobals {
    public int nbrDeJoueurs = 1;
    public List<joueurs> allJoueurs;

    public List<joueurs> getAllJoueurs() {
        return allJoueurs;
    }
    public void addJoueursInAllJoueurs(joueurs Joueurs) {
        this.allJoueurs.add(Joueurs);
        nbrDeJoueurs++;
    }
}
