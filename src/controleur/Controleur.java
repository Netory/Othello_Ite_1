package controleur;

import java.util.Random;
import vue.Ihm;


public class Controleur {

    private Ihm ihm ;
    private Joueur joueurActuel;

    private Joueur joueur1;
    private Joueur joueur2;
    private boolean PartieEnCours;

    private Random random;

    public Controleur(Ihm ihm){;

        this.random= new Random();
        this.ihm = ihm;
        this.joueur1= new Joueur();
        this.joueur2= new Joueur();
        this.joueurActuel=joueur1;
        this.PartieEnCours=false;


    }

    public Ihm getIhm(){
        return ihm;
    }

    public String determinerGagnant() {
        //method pour savoir qui gange ou ex aequo
        int scoreJoueur1 = 0; // Score du joueur 1 (pions noirs \u26AB)
        int scoreJoueur2 = 0; // Score du joueur 2 (pions blancs \u26AA)
    
        // Parcourir le plateau pour compter les pions
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ihm.getPlateau().getCase(i,j) == 2) {
                    scoreJoueur1++; // Incrementer le score du joueur 1
                } else if (ihm.getPlateau().getCase(i,j) == 1) {
                    scoreJoueur2++; // Incrementer le score du joueur 2
                }
            }
        }
    
        // Déterminer le gagnant ou si la partie est ex aequo
        if (scoreJoueur1 > scoreJoueur2) {
            return joueur1.getNom();
        } else if (scoreJoueur2 > scoreJoueur1) {
            return joueur2.getNom();
        } else {
            return "ex aequo";
        }
    }

   
    public void jouer(){
        //boucle de jeu principale qui appelle toutes les autres fonctions pour dérouler une partie ou plusieurs parties
        
        joueur1.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 1:"));
        joueur1.setTypedepion("\u26AB");
        String veutjouercontreia = ihm.demanderCaracteres("Voulez vous jouer contre une IA si oui écrire Y sinon écrire N ?");
        if(veutjouercontreia.equalsIgnoreCase("N")){

            joueur2.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 2:"));
            joueur2.setTypedepion("\u26AA");
        }else{
            joueur2.setNom("IA");
            joueur2.setTypedepion("\u26AA");
        }

        boolean ilsveulentjouer= true;

        while(ilsveulentjouer){//boucle pour rejouer une partie

            ihm.getPlateau().initialiserPlateau();
            PartieEnCours=true;
            ihm.afficher();
            while(PartieEnCours){
                //ihm.getPlateau().setCase(5, 5, 2);
                System.out.println(joueurActuel.getNom());
                if(!joueurActuel.getNom().equalsIgnoreCase("IA")){

                    //tour de jeu du joueur humain

                    ihm.afficherMessage(joueurActuel.getNom() + " à vous de jouer. Saisir une colonne entre A et H suivie d'une ligne entre 1 et 8 ou P pour passer le tour");
                    if ( !(ihm.getPlateau().aUnCoupValide(joueurActuel))){//si il n'y a pas de coup valide
                        String coup=ihm.demanderCaracteres("Aucun coup valide disponible, appuyez sur P pour passer votre tour");
                        if (coup=="P" || coup=="p"){
                            if (joueurActuel.getNom()==joueur1.getNom()){
                                joueurActuel=joueur2;
                            }
                            else{joueurActuel=joueur1;}
                        }
                    }else{//effectue le tour en demandant où le joueur veut poser le pion
                        String coup = ihm.demanderCaracteres("Entrez le chiffre de la ligne suivie d'une virgule et d'une lettre pour la ligne (3,D)");
                        //sépare la réponse en deux champs pour pouvoir prendre deux coordonnées différentes
                        String[] lescoups = coup.split(",");
                        if (lescoups.length!=2){ihm.afficherMessage("Entrée Invalide! Veuillez entrez le chiffre de la ligne suivie d'une virgule et d'une lettre pour la ligne (3,D)");}
                        int x = Integer.parseInt(lescoups[0]);//ligne 
                        int y = ihm.getPlateau().convertirLettretoChiffre(lescoups[1]);//la colonne
                        //int x = ihm.demanderEntier("Entrez la ligne (1-8) : "); 
                        //int y = ihm.demanderEntier("Entrez la colonne (1-8) : ");//ptete faire un case pour A-H
                        if (ihm.getPlateau().estCoupValide(joueurActuel, x, y)){//normalement marche
                            ihm.getPlateau().setCase(x, y, joueurActuel.getTypedepion()=="\u26AA"?1:2);//censé etre ok
                            ihm.getPlateau().retournerPions(joueurActuel, x, y);//marche pas//good aussi
                            if (joueurActuel.getNom()==joueur1.getNom()){
                                joueurActuel=joueur2;
                            }
                            else{joueurActuel=joueur1;}
                        }else{ihm.afficherMessage("Coup invalide ! Veuillez réessayer");}
                    }
                }else{//tour de jeu de l'ordinateur

                int[][] ListCoupvalide =ihm.getPlateau().ListCoupValide(joueurActuel);
                
                boolean coupEffectué=false;
                while (!coupEffectué){//recherche de coup valide aléatoire dans la matrice de coup valide
                    int i=random.nextInt(7);
                    int j=random.nextInt(7);

                    if ( ListCoupvalide[i][j]==-1){//trouve un coup valide et l'effectue
                        ihm.getPlateau().setCase(i+1,j+1,joueurActuel.getTypedepion()=="\u26AA"?1:2);
                        ihm.getPlateau().retournerPions(joueurActuel, i+1, j+1);//comme pour un tour de jeu mais sans demande
                        
                        if (joueurActuel.getNom()==joueur1.getNom()){//change de joueur actuel pour le prochain tour de jeu
                            joueurActuel=joueur2;
                        }
                        else{joueurActuel=joueur1;}
                        
                        coupEffectué=true;//à effectuer un coup valide donc fin de la méthode
                    }
                }
                }
                //affichage du plateau
                ihm.afficher();
                if (!(ihm.getPlateau().aUnCoupValide(joueur1)&&ihm.getPlateau().aUnCoupValide(joueur2))){//teste pour savoir si la partie est terminée
                    PartieEnCours=false;
                }
                //PartieEnCours=false;// sert à terminer la partie au bout d'un tour
            }
            ihm.afficherMessage("La Partie est terminée !");


            //Défilement d'une fin de partie à l'aide determinerGagnant
            if(determinerGagnant()==joueur1.getNom()){
                ihm.afficherMessage(joueur1.getNom()+" a gagné cette partie !");
                joueur1.upScore();
            }else if(determinerGagnant()==joueur2.getNom()){
                ihm.afficherMessage(joueur2.getNom()+" a gagné cette partie !");
                joueur2.upScore();
            }else{
                ihm.afficherMessage("Ex aequo");
            }
            
            String rejouer=ihm.demanderCaracteres("Voulez vous rejouer, Y or N ?");
            if (rejouer.equalsIgnoreCase("Y")){
                ilsveulentjouer=true;
            }else {
                ilsveulentjouer=false;
            }
            //System.out.println(ilsveulentjouer);  test la value de si on rejoue
            //ilsveulentjouer=((rejouer=="Y" || rejouer=="y" )?true:false);
        }
    //affichage de resultats des parties 
    ihm.afficherMessage(joueur1.getNom()+" a gagné "+joueur1.getScore()+" parties ! Et "+ joueur2.getNom() +" a gagné "+joueur2.getScore()+" parties !");
    if(joueur1.getScore()==joueur2.getScore()){
        ihm.afficherMessage("C'est ex aequo , il n'y a pas de vainqueur");
    }else if(joueur1.getScore()>=joueur2.getScore()){
        ihm.afficherMessage("C'est "+joueur1.getNom()+" qui a gagné");
    }else{
        ihm.afficherMessage("C'est "+joueur2.getNom()+" qui a gagné");
    }
    }
}

    
    
