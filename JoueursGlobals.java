import java.util.ArrayList;
import java.util.List;

public class JoueursGlobals {
    public int nbrDeJoueurs = 1;
    public List<joueurs> allJoueurs = new ArrayList<>();

    public joueurs getJoueursByIndex(int index) {
        return allJoueurs.get(index);
    }
    public void addJoueursInAllJoueurs(joueurs Joueurs) {
        this.allJoueurs.add(Joueurs);
        nbrDeJoueurs++;
    }
}
