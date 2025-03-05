package controleur; //modele toutes les classes de données

public class Joueur {
    private int id;
    private int score;

    public Joueur(int id){
        this.id=id;
        this.score=0;
    }

    public void incrementerScore(){
        score++;  // à faire marcher par rapport aux nb de pionts renversé
    }
}