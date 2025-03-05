package modele;

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

    public int[][] getPlateau() {
        return cases;
    }
    /*public int[][] setPlateau(){
        //renvoyer un plateau sauvegardé à l'aide d'une id
    }*/

    public int getCase(int x , int y){
        return cases[x][y];
    }

    public void setCase(int x, int y, int valeur) { //modele jusqu'à départ
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            cases[x][y] = valeur;
        } else {
            System.out.println("Position invalide !");
        }
    }
}
