package main;


import controleur.Controleur;
import vue.Ihm;


public class Main {
    public static void main(String[] args){
        Ihm ihm = new Ihm();
        Controleur controleur=new Controleur(ihm);
        controleur.jouer();

       
        //String symbols = "\u26AA \u26AB \uD83D\uDFE9";
        //System.out.println(symbols);

        //plateau.setCase(2, 3, 1); //methode pour poser un pion
        //plateau.afficher();

        // controleur.getIhm().getPlateau().initialiserPlateau();
        //controleur.getIhm().afficher();

    }
}
