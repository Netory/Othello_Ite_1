package modele;

import controleur.Joueur;

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
        setCase(3+1, 3+1, 1);
        setCase(3+1, 4+1, 2); 
        setCase(4+1, 3+1, 2);
        setCase(4+1, 4+1, 1);
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

    public void setCase(int x, int y, int valeur) { 
        x--;
        y--;
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            cases[x][y] = valeur;
        } else {
            System.out.println("Position invalide !");
        }
    }
    public boolean estCoupValide(Joueur joueurActuel, int x, int y){ //controleur
        
        
        x--;
        y--;

        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return false;
        }
        
        if (cases[x][y] !=0){
            return false; //case est pas vide
        }
        int adversaire;
        if  (joueurActuel.getTypedepion() == "\u26AB"){
            adversaire=1;
        } else {
            adversaire=2;
        }
        int[][] directions = { //toutes les différentes possibilitées en direction
            {1,0}, {-1,0}, {0,1}, {0,-1}, 
            {1,1}, {-1,-1}, {1,-1}, {-1,1},
        };
        
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int i = x + dx;
            int j = y + dy;

            boolean pionAdverseTrouve = false;

            while (i >= 0 && i < 8 && j >=0 && j<8 && cases[i][j]==adversaire){ 
                //check si la case est dans les limites   et si cases à côté sont à l'adversaire
                pionAdverseTrouve = true;
                i+=dx;
                j+=dy;
            }
            if (pionAdverseTrouve && i >=0 && i<8 && j>=0 && j<8 && cases[i][j]==(joueurActuel.getTypedepion()=="\u26AA"?1:2)){
                return true; // coup valide
            }
        }
        return false; //coup invalide
    }

    public void retournerPions(Joueur joueurActuel, int x, int y) {
        // Déterminer le joueur adverse
        x--;
        y--;
        int adversaire;
        if  (joueurActuel.getTypedepion() == "\u26AB"){
            adversaire=1;
        } else {
            adversaire=2;
        }
    
        //toutes les différentes possibilitées en direction
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, 
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1}
        };
    
        // Parcourir chaque direction
        for (int[] dir : directions) {
            int dx = dir[0]; // mvmt ligne
            int dy = dir[1]; // mvmt ligne
            int i = x + dx;  // les cases adjacentes dans la direction
            int j = y + dy;  // les cases adjacentes dans la direction
    
            
            boolean pionAdverseTrouve = false;
            //même chose qu'au dessus
            while (i >= 0 && i < 8 && j >= 0 && j < 8 && cases[i][j] == adversaire) {
                pionAdverseTrouve = true;
                i += dx;
                j += dy;
            }
    
            // check jusqu'où ça s'arrete et si c'est encadré
            if (pionAdverseTrouve && i >= 0 && i < 8 && j >= 0 && j < 8 && cases[i][j] == (joueurActuel.getTypedepion()=="\u26AA"?1:2)) {
                // Retourner les pions adverses
                i = x + dx;
                j = y + dy;
                while (cases[i][j] == adversaire) {
                    cases[i][j] = (joueurActuel.getTypedepion()=="\u26AA"?1:2); // ça retourne le ou les pions adverses
                    i += dx;
                    j += dy;
                }
            }
        }
    }
    public boolean aUnCoupValide(Joueur joueur) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cases[i][j] == 0 && estCoupValide(joueur, i+1, j+1)) {
                    return true; // au moins un coup valide trouvé
                }
            }
        }
        return false; //0 coup valide
    }
}
