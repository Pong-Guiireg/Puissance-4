import java.util.ArrayList;
import java.util.List;

public class joueursGlobals {
    public int nbrDeJoueurs = 1;
    public List<joueurs> allJoueurs = new ArrayList<>();

    public List<joueurs> getAllJoueurs() {
        return allJoueurs;
    }
    public void addJoueursInAllJoueurs(joueurs Joueurs) {
        this.allJoueurs.add(Joueurs);
        nbrDeJoueurs++;
    }
}
