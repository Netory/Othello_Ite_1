package vue;

public class Plateau {
    
    private int[][] cases;

    public Plateau(){
        cases = new int[8][8];
    }
    public void initialiserPlateau() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cases[i][j] = 0; // 0 = case vide
            }
            
        }
        setCase(3, 3, 1);
        setCase(3, 4, 2); 
        setCase(4, 3, 2);
        setCase(4, 4, 1);
        /*
        cases[3][3] = 1; // Joueur 1
        cases[3][4] = 2; // Joueur 2
        cases[4][3] = 2; // Joueur 2
        cases[4][4] = 1; // Joueur 1*/
        
    }

    public int[][] getCases() {
        return cases;
    }

    public void setCase(int x, int y, int valeur) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            cases[x][y] = valeur;
        } else {
            System.out.println("Position invalide !");
        }
    }

    public void afficher() {
        System.out.println("   A  B  C  D  E  F  G  H"); //indices de colonnes
        for (int i = 0; i < 8; i++) {
            System.out.print((i+1) + " "); //indices de lignes
            for (int j = 0; j < 8; j++) {
                //System.out.print(cases[i][j]);
                switch (cases[i][j]) {
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
}
