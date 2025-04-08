package modele;

public class PlateauAwalé {
    private int[][] cases;
    private int scoreJoueur1;
    private int scoreJoueur2;

    public PlateauAwalé(){
        cases = new int[2][6];
        //scoreJoueur1 = 0;
        //scoreJoueur1 = 0;
    }
    /***method qui initialise le plateau à l'aide de setcase */
    public void initialiserPlateau() {

        
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                setCase(i, j, 4); // 0 = case vide
            }
        }     
    }

    public int getCase(int x , int y){
        //obtient le pion dans la case
        return cases[x][y];
    }
    /***method pour poser un pion à l'aide des coordonnées*/
    public void setCase(int x, int y, int valeur) { 
        
        if (x >= 0 && x < 2 && y >= 0 && y < 6) {
            cases[x][y] = valeur;
        } else {
            System.out.println("Position invalide !");
        }
    }
    public boolean estCoupValide(Joueur joueuractuel,int coupJouer){
        //mettre la regle de l'affamer
        
        if(getCase(2-Integer.parseInt(joueuractuel.getTypedepion()), coupJouer)==0){
            return false;
        }
        return true;
    } 
    /*** fonction pour gérer les déplacements des grains à partir du coup du joueur*/
    public void mouvementPlateau(Joueur joueuractuel,int coupJouer){
        if(estCoupValide(joueuractuel, coupJouer)){
            int i=2-Integer.parseInt(joueuractuel.getTypedepion());
            int nbgraines = getCase(i, coupJouer);
            System.out.println(joueuractuel.getNom());
            int[] caseDepart = new int[2];//pour stocker la case de départ sans créer une nouvelle classe de stockage
            caseDepart[0]=i;
            caseDepart[1]= coupJouer;

           // int lastRow = -1;
           // int lastCol = -1;
                
            while (nbgraines!=0){
                
                if (i==1){
                    if (nbgraines+coupJouer>5){//condition si ça arrive dans le terrain adverse
                        
                        int nbgraineenhaut = nbgraines -(6-coupJouer);
                        if(nbgraineenhaut>5){
                            //System.out.println("il ya ce nombre à distribué : "+nbgraineenhaut+" et à la position "+(-nbgraineenhaut+5+1+"  et au total : "+nbgraines));
                            setCase(i, nbgraineenhaut-6, getCase(i, nbgraineenhaut-6)+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                            nbgraines-=1;
                        }else if(nbgraineenhaut<=5){
                            //System.out.println("il ya ce nombre à distribué : "+nbgraineenhaut+" et à la position "+(-nbgraineenhaut+5+1+"  et au total : "+nbgraines));
                            setCase(1-i, -nbgraineenhaut+5, getCase(1-i, -nbgraineenhaut+5)+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                            nbgraines-=1;
                        }
                        //lastRow = i;
                      //  lastCol =5-nbgraineenhaut;
                    }else{
                        setCase(i, nbgraines+coupJouer, getCase(i, nbgraines+coupJouer)+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                        nbgraines-=1;
                       // lastRow = i;
                        //lastCol =5-nbgraineenhaut;
                    }
                }else if(i==0){
                    if (0>coupJouer-nbgraines){//condition si ça arrive dans le terrain adverse
                        
                        int nbgraineEnbas = nbgraines -(coupJouer+1);
                        if (nbgraineEnbas>5){//ça revient dans ton terrain
                            //System.out.println("il ya ce nombre à distribué : "+nbgraineEnbas+" et à la position "+(nbgraineEnbas+1+"  et au total : "+nbgraines));
                            setCase(i,6-(nbgraineEnbas-5), getCase(i, 6-(nbgraineEnbas-5))+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                            nbgraines-=1;
                        }else if (nbgraineEnbas<=5){
                        //System.out.println("il ya ce nombre à distribué : "+nbgraineEnbas+" et à la position "+(nbgraineEnbas+1+"  et au total : "+nbgraines));
                        setCase(1-i, nbgraineEnbas, getCase(1-i,nbgraineEnbas)+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                        nbgraines-=1;
                        }
                    }else{
                        setCase(i,coupJouer-nbgraines, getCase(i, coupJouer-nbgraines)+1);//prend la valeur dans le slot et lui ajoute 1 grain de riz
                        nbgraines-=1;
                    }
                }
            }
            setCase(caseDepart[0], caseDepart[1], 0);
        }
    }
}
