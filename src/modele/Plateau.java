package modele;

import java.util.ArrayList;
import java.util.List;

public class Plateau {
    
    private int[][] cases;

    public Plateau(){
        cases = new int[8][8];
    }
    public void initialiserPlateau() {

        //method qui initialise le plateau à l'aide de setcase 
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

    public Plateau getCopiePlateau(){
        Plateau copiePlateau = new Plateau();//initialisation du plateau
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copiePlateau.setCase(i+1, j+1, this.getCase(i, j));//copie des pions posés
            }
        }return copiePlateau;  
    }

    public int[][] getPlateau() {
        //obtient le plateau
        return cases;
    }
    /*public int[][] setPlateau(){
        //renvoyer un plateau sauvegardé à l'aide d'une id
    }*/

    public int getCase(int x , int y){
        //obtient le pion dans la case
        return cases[x][y];
    }

    public void setCase(int x, int y, int valeur) { 
        //method pour poser un pion à l'aide des coordonnées
        x--;
        y--;
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            cases[x][y] = valeur;
        } else {
            System.out.println("Position invalide !");
        }
    }
    public boolean estCoupValide(Joueur joueurActuel, int x, int y){ //controleur
        //method pour vérifié si un coup est valide en vérifiant 
        //si il y a des pions qu'on peut retourner autour de la position
        
        x--;
        y--;

        if (x < 0 || x > 8 || y < 0 || y > 8) {
            return false; //check si c'est une coordonée acceptable
        }
        
        if (cases[x][y] !=0){
            return false; //case est pas vide
        }
        int adversaire;
        if  (joueurActuel.getTypedepion() == "\u26AB"){  //obtient le type de pion
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
                //check si la case est dans les limites et si cases à côté sont à l'adversaire
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
        //method pour retourner les pions en vérifiant toutes les directions par rapport à une coordonnée

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
    
            // check jusqu'où ça s'arrête et si c'est encadré
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
        //vérifie si il y a au moins un coup valide
        return !(ListCoupValide(joueur).isEmpty());
    }

    public List<int[]> ListCoupValide(Joueur joueur) {
        //fait une matrice avec -1 pour les coups valides
        //int[][] pairList= new int[8][8];
        List<int[]> pairList= new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cases[i][j] == 0 && estCoupValide(joueur, i+1, j+1)) {
                    pairList.add(new int[]{i, j});
                    //prend les coups valide
                }
            }
        }
        return pairList; //0 coup valide
    }

    public int convertirLettretoChiffre(String lettre){
        //method pour obtenir des coordonnées à partir d'une lettre
        switch (lettre) {
            case "A":
                return 1;
            case "B":
                return 2;
            case "C":
                return 3;
            case "D":
                return 4;
            case "E":
                return 5;
            case "F":
                return 6;
            case "G":
                return 7;
            case "H":
                return 8;
            default:
                return -1;
            

        }
    }
}
