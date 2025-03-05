package controleur;

import modele.Plateau;
import vue.Ihm;

public class Controleur {
    public Controleur(Ihm ihm){};
    public static void main(String[] args){
        Plateau plateau = new Plateau();
        plateau.initialiserPlateau();
    }
}

    
    
