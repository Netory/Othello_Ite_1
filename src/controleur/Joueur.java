package controleur; //modele toutes les classes de données

public class Joueur {
    private String nom;
    private int score;
    private String typedepion;

    public Joueur(){
        this.nom="";
        this.score=0;
        this.typedepion="";
    }
    
    //getteur et setteur des différents champs de Joueur
   
    public void setScore(int score){
        this.score+=score;
    }
    public void upScore(){
        this.score+=1;
    }
    public int getScore(){
        return this.score;
    }

    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return this.nom;
    }
    public void setTypedepion(String typedepion){
        this.typedepion= typedepion;
    }
    public String getTypedepion(){
        return this.typedepion;
    }

    public void incrementerScore(){
        //method pour augment le score de parties gagner
        score++;  // à faire marcher par rapport aux nb de pionts renversé
    }
}