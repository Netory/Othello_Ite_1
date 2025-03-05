package vue;

import modele.Plateau;

public class Ihm {

    private Plateau plateau;

    public Ihm(){
         this.plateau = new Plateau();
    };
    


    public void afficher             () {
        System.out.println("  A B C D E F G H"); //indices de colonnes
        for (int i = 0; i < 8; i++) {
            System.out.print((i+1) + " "); //indices de lignes
            for (int j = 0; j < 8; j++) {
                //System.out.print(cases[i][j]);
                switch (this.plateau.getCase(i,j)) {
                    case 0:
                        
                        System.out.print("\uD83D\uDFE9"); // Case vide
                        break;
                    case 1:
                       
                        System.out.print("\u26AA"); // Pion joueur 1
                        break;
                    case 2:
                        
                        System.out.print("\u26AB"); // Pion joueur 2
                        break;
                }
            }
            System.out.println();
        }
        
    }

}
