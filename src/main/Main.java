package main;


import controleur.Controleur;
import vue.Ihm;
import vue.Plateau;

public class Main {
    public static void main(String[] args){
        Ihm ihm = new Ihm();
        //Controleur controleur=new Controleur(ihm);
       // controleur.jouer();*/
        String symbols = "\u26AA \u26AB \uD83D\uDFE9";
        //System.out.println(symbols);

        //plateau.setCase(2, 3, 1); //methode pour poser un pion
        //plateau.afficher();

        Plateau plateau = new Plateau();
        plateau.initialiserPlateau();
        System.out.println("Plateau :");
        plateau.afficher();

    }
}
