package vue;

import modele.Plateau;
import modele.PlateauAwalé;
import modele.Joueur;
import java.util.Scanner;


public class Ihm {

    private PlateauAwalé plateauAwalé;
    private Plateau plateau;
    private Scanner scanner;

    public Ihm(){
        this.plateauAwalé= new PlateauAwalé();
         this.plateau = new Plateau();
         this.scanner = new Scanner(System.in);
    };
    
    public Plateau getPlateau(){
        return plateau;
    }
    public PlateauAwalé getPlateauAwalé(){
        return plateauAwalé;
    }

    public void afficher() {
        //method qui affiche le plateau en regardant le type de pion sur chaque case de la matrice
        System.out.println("Plateau :");
        System.out.println("  A  B  C  D  E  F  G  H"); //indices de colonnes
        for (int i = 0; i < 8; i++) {
            System.out.print((i+1) + " "); //indices de lignes
            for (int j = 0; j < 8; j++) {
                //System.out.print(cases[i][j]);
                switch (this.plateau.getCase(i,j)) {
                    case 0:
                        
                        System.out.print("\uD83D\uDFE9"); // Case vide
                        break;
                    case 1:
                       
                        System.out.print("\u26AA "); // Pion joueur 1
                        break;
                    case 2:
                        
                        System.out.print("\u26AB "); // Pion joueur 2
                        break;
                }
            }
            System.out.println();
        }
        
    }

    public void afficherPlateauAwalé(Joueur joueur1 , Joueur joueur2){
        System.out.print(joueur1.getScore() + "  ");
        for (int i=0;i<2;i++){
            for (int j = 0; j < 6; j++) {
                System.out.print("|"+this.plateauAwalé.getCase(i, j));
            }System.out.print("|");
            if(i<1){
                
            System.out.println("  " + joueur2.getScore());
            System.out.print("   ");
            }
        }
        System.out.println();
    }
    public void afficherMessage(String message){
        //method pour afficher un message
        System.out.println(message);
    }

    public String demanderCaracteres(String message){
        //method pour afficher un message et demander une reponse en string
        System.out.println(message);
        return scanner.next();
    }

    public int demanderEntier(String message){
        //method pour afficher un message et demander une reponse d'entier
        System.out.println(message);
        return scanner.nextInt();
    }
    public Scanner getScanner(){
        return this.scanner;
    }
    public void emptyScanner(){
        this.scanner.next();
    }

}
