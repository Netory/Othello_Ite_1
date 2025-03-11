package controleur;

import vue.Ihm;

public class Controleur {

    private Ihm ihm ;
    private Joueur joueurActuel;

    private Joueur joueur1;
    private Joueur joueur2;
    private boolean PartieEnCours;
    public Controleur(Ihm ihm){;

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
        
        joueur1.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 1:"));
        joueur1.setTypedepion("\u26AB");
        joueur2.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 2:"));
        joueur2.setTypedepion("\u26AA");

        boolean ilsveulentjouer= true;

        while(ilsveulentjouer){//boucle pour rejouer une partie

        ihm.getPlateau().initialiserPlateau();
        PartieEnCours=true;
        ihm.afficher();
        while(PartieEnCours){
            //ihm.getPlateau().setCase(5, 5, 2);
            
            ihm.afficherMessage(joueurActuel.getNom() + " à vous de jouer. Saisir une colonne entre A et H suivie d'une ligne entre 1 et 8 ou P pour passer le tour");
            if ( !(ihm.getPlateau().aUnCoupValide(joueurActuel))){
                String coup=ihm.demanderCaracteres("Aucun coup valide disponible, appuyez sur P pour passer votre tour");
                if (coup=="P" || coup=="p"){
                    if (joueurActuel.getNom()==joueur1.getNom()){
                        joueurActuel=joueur2;
                    }
                    else{joueurActuel=joueur1;}
                }
            }else{
                String coup = ihm.demanderCaracteres("Entrez le chiffre de la ligne suivie d'une virgule et d'une lettre pour la ligne (3,D)");
                
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
            ihm.afficher();
            if (!(ihm.getPlateau().aUnCoupValide(joueur1)&&ihm.getPlateau().aUnCoupValide(joueur2))){//à voir mais normalement c'est ok
                PartieEnCours=false;
            }
            //PartieEnCours=false;// à enlever
        }
        ihm.afficherMessage("La Partie est terminée !");

        if(determinerGagnant()==joueur1.getNom()){
            ihm.afficherMessage(joueur1.getNom()+" a gagné cette partie !");
            joueur1.upScore();
        }else if(determinerGagnant()==joueur2.getNom()){
            ihm.afficherMessage(joueur2.getNom()+" a gagné cette partie !");
            joueur2.upScore();
        }else{ihm.afficherMessage("Ex aequo");}
           
        String rejouer=ihm.demanderCaracteres("Voulez vous rejouer, Y or N ?");
        if (rejouer.equalsIgnoreCase("Y")){
            ilsveulentjouer=true;
        }else {ilsveulentjouer=false;}
        //System.out.println(ilsveulentjouer);  test la value de si on rejoue
        //ilsveulentjouer=((rejouer=="Y" || rejouer=="y" )?true:false);
    }

    ihm.afficherMessage(joueur1.getNom()+" a gagné "+joueur1.getScore()+" parties ! Et "+ joueur2.getNom() +" a gagné "+joueur2.getScore()+" parties !");
    if(joueur1.getScore()==joueur2.getScore()){ihm.afficherMessage("C'est ex aequo , il n'y a pas de vainqueur");
    }else if(joueur1.getScore()>=joueur2.getScore()){ihm.afficherMessage("C'est "+joueur1.getNom()+" qui a gagné");
    }else{ihm.afficherMessage("C'est "+joueur2.getNom()+" qui a gagné");}
    
    }
}

    
    
