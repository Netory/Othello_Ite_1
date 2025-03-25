package controleur;

import java.util.List;
import java.util.Random;

import modele.Plateau;
import vue.Ihm;

public class Controleur {

    private Ihm ihm;
    private Joueur joueurActuel;

    private Joueur joueur1;
    private Joueur joueur2;
    private boolean PartieEnCours;

    private Random random;

    public Controleur(Ihm ihm) {
        ;

        this.random = new Random();
        this.ihm = ihm;
        this.joueur1 = new Joueur();
        this.joueur2 = new Joueur();
        this.joueurActuel = joueur1;
        this.PartieEnCours = false;

    }

    

    public int[] meilleurCoup(Plateau plateau, int profondeur, Joueur joueurActuel) {
        // checker tout les move dispo et faire par récurrence une verif des points
        // totaux obtenus en mettant les points totaux et la profondeur en argument.
        // faut utiliser la fonction évaluer de l'énoncer pour déterminer les meilleurs moves 
        // maybe à chaque fois créer un nouveau plateau à partir du précédent pour tester le coup d'après dessus ? 
        // 1 de profondeur = 2 tour un de ceux du joueur et un de l'IA
        int bestscore = Integer.MIN_VALUE;
        int[] bestMove = null;
        int limiteJ = Integer.MIN_VALUE;
        int limiteA = Integer.MAX_VALUE;


        for (int[] coupPossible : plateau.ListCoupValide(joueurActuel)) {
            /*System.out.println("Profondeur: " + profondeur );
            System.out.println("test");*/
            Plateau copiePlateau = plateau.getCopiePlateau();
            int score = minimax(copiePlateau, profondeur - 1, false, joueurActuel,limiteJ,limiteA);


            // démarrage de la récurence en le faisant sur tout les coups valides

            if (score >= bestscore) {// changement des values si un coup fait un meilleur score alors on enregistre
                                    // celui-ci
                bestMove = coupPossible;
                bestscore = score;
            }
            limiteJ = Math.max(limiteJ, bestscore);


            
        }if ( bestMove==null){
            //System.out.println("pas normal ya pb :"+plateau.ListCoupValide(joueurActuel).toArray()[0]);
            if (plateau.aUnCoupValide(joueurActuel))
            for (int[] coupPossible : plateau.ListCoupValide(joueurActuel)) {
                /*System.out.println("Profondeur: " + profondeur );
                System.out.println("test");*/
                Plateau copiePlateau = plateau.getCopiePlateau();
                int score = minimax(copiePlateau, profondeur - 1, false, joueurActuel,limiteJ,limiteA);

                if (score > bestscore) {// changement des values si un coup fait un meilleur score alors on enregistre
                    // celui-ci
                    bestMove = coupPossible;
                    bestscore = score;
                }
                limiteJ = Math.max(limiteJ, bestscore);
            
            }
        }
    return bestMove;
    }

    public int minimax(Plateau plateau, int profondeur, boolean max_min, Joueur joueurActuel,int limiteJ, int limiteA) {

        
        // condition d'arret à la récurence
        if (profondeur == 0 || (!(plateau.aUnCoupValide(joueur1)) && !(plateau.aUnCoupValide(joueur2)))) {
            return evaluerPlateau(joueurActuel, plateau);
        }

        if (max_min) {// avec true pour max et false pour min
            int bestscoremax = Integer.MIN_VALUE;// permet d'obtenir une valeur tjrs inférieur au prochain best score
            for (int[] coupPossible : plateau.ListCoupValide(joueurActuel)) {// on essaye chaque coup
                Plateau copiPlateau = plateau.getCopiePlateau();
                copiPlateau.setCase(coupPossible[0] + 1, coupPossible[1] + 1,(joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2));
                copiPlateau.retournerPions(joueurActuel, coupPossible[0] +1, coupPossible[1] + 1);

                int score = minimax(copiPlateau, profondeur - 1, false, joueurActuel,limiteJ,limiteA);
                // permet de faire l'appel avec la récurrence en mettant à false le max_min pour
                // passer au calcul du tour adverse
                bestscoremax = Math.max(bestscoremax, score);
                limiteJ = Math.max(limiteJ, score);
                // permet d'obtenirle meilleur socre en celui enregistre et celui de l'itération actuelle
            
                if (limiteA <= limiteJ) {//pour sortir d'une récurrence inutile car sers à savoir la valeur minimum qui peut etre utile
                    break;
                }
                
            }
            return bestscoremax;

            // même chose qu'en haut mais en inversant les min et max et remplacant le false
            // par true pour passer à celui d'en haut
        } else {// avec true pour max et false pour min
            int bestscoremin = Integer.MAX_VALUE;// permet d'obtenir une valeur tjrs supérieur au prochain best score
            for (int[] coupPossible : plateau.ListCoupValide(joueurActuel.getNom().equalsIgnoreCase(joueur1.getNom())?joueur2:joueur1)) {// on essaye chaque coup de l'adversaire
                Plateau copiPlateau = plateau.getCopiePlateau();
                copiPlateau.setCase(coupPossible[0] + 1, coupPossible[1] + 1,(joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 2 : 1));//1 et 2 inversé pour obtenir le type de pion de l'adversaire
                copiPlateau.retournerPions(joueurActuel.getNom().equalsIgnoreCase(joueur1.getNom())? joueur2 : joueur1, coupPossible[0] +1, coupPossible[1] + 1);
                
                int score = minimax(copiPlateau, profondeur - 1, true, joueurActuel,limiteJ,limiteA);
                // permet de faire l'appel avec la récurrence en mettant à true le max_min pour
                // passer au calcul du tour adverse
                bestscoremin = Math.min(bestscoremin, score);
                limiteA = Math.min(limiteA, score);
                // permet d'obtenirle meilleur socre en celui enregistre et celui de l'itération actuelle

                if (limiteA <= limiteJ) {//pour sortir d'une récurrence inutile car sers à savoir la valeur minimum qui peut etre utile
                    break;
                }

            }
            return bestscoremin;
        }
    }

    public int evaluerPlateau(Joueur joueurActuel, Plateau plateau) {

        int scoreJoueurActuel = 0;
        if (plateau.aUnCoupValide(joueur1) || plateau.aUnCoupValide(joueur2)) {
            //teste pour savoir si la partie est pas terminée                                                                          
                                                                                              
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (plateau.getCase(i, j) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                        scoreJoueurActuel++; // Incrementer le score du joueur actuel
                    }
                }
            } // faut checker toutes les cases qui ont comme pattern : {1,x},{6,x},{x,1},{x,6}
              // c'est les bords
            for (int x = 1; x < 7; x++) {
                if (plateau.getCase(1, x) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                    scoreJoueurActuel += 6;
                }
            }
            for (int x = 1; x < 7; x++) {
                if (plateau.getCase(6, x) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                    scoreJoueurActuel += 6;
                }
            }
            for (int x = 1; x < 7; x++) {
                if (plateau.getCase(x, 1) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                    scoreJoueurActuel += 6;
                }
            }
            for (int x = 1; x < 7; x++) {
                if (plateau.getCase(x, 6) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                    scoreJoueurActuel += 6;
                }
            }
            // on check tout les coins 1 à 1
            if (plateau.getCase(0, 7) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                scoreJoueurActuel += 11;
            }
            if (plateau.getCase(7, 7) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                scoreJoueurActuel += 11;
            }
            if (plateau.getCase(0, 0) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                scoreJoueurActuel += 11;
            }
            if (plateau.getCase(7, 0) == (joueurActuel.getTypedepion().equalsIgnoreCase("\u26AA")? 1 : 2)) {
                scoreJoueurActuel += 11;
            }
        } else {
            if (joueurActuel.getNom().equalsIgnoreCase(determinerGagnant())) {
                scoreJoueurActuel = 1000;
            } else {
                scoreJoueurActuel = -1000;
            }
        }

        return scoreJoueurActuel;
    }

    public String determinerGagnant() {
        // method pour savoir qui gange ou ex aequo
        int scoreJoueur1 = 0; // Score du joueur 1 (pions noirs \u26AB)
        int scoreJoueur2 = 0; // Score du joueur 2 (pions blancs \u26AA)

        // Parcourir le plateau pour compter les pions
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (ihm.getPlateau().getCase(i, j) == 2) {
                    scoreJoueur1++; // Incrementer le score du joueur 1
                } else if (ihm.getPlateau().getCase(i, j) == 1) {
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

    public void jouer() {
        // boucle de jeu principale qui appelle toutes les autres fonctions pour
        // dérouler une partie ou plusieurs parties

        joueur1.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 1:"));
        joueur1.setTypedepion("\u26AB");
        String veutjouercontreia = ihm.demanderCaracteres("Voulez vous jouer contre une IA si oui écrire Y sinon écrire N ?");
        
        if (veutjouercontreia.equalsIgnoreCase("N")) {

            joueur2.setNom(ihm.demanderCaracteres("Entrez le Nom du Joueur 2:"));
            joueur2.setTypedepion("\u26AA");
        } else if(veutjouercontreia.equalsIgnoreCase("Y")) {
            String veutjouercontreiaForte = ihm.demanderCaracteres("Voulez vous jouer contre une IA Forte si oui écrire Y sinon écrire N ?");
            
            if (veutjouercontreiaForte.equalsIgnoreCase("Y")){
                joueur2.setNom("Strong_IA");
                joueur2.setTypedepion("\u26AA");
                }else {
                    joueur2.setNom("Gentle_IA");
                    joueur2.setTypedepion("\u26AA");
                }
            }
            boolean ilsveulentjouer = true;

            while (ilsveulentjouer) {// boucle pour rejouer une partie

                if (joueurActuel.getNom() == joueur2.getNom()) {
                    joueurActuel = joueur1;
                }

                ihm.getPlateau().initialiserPlateau();
                PartieEnCours = true;
                ihm.afficher();
                while (PartieEnCours) {
                    // ihm.getPlateau().setCase(5, 5, 2);
                    
                    if (joueurActuel.getNom().equalsIgnoreCase("Gentle_IA")) {
                        ihm.afficherMessage(joueurActuel.getNom());
                        List<int[]> ListCoupvalide = ihm.getPlateau().ListCoupValide(joueurActuel);

                        boolean coupEffectué = false;
                        if ((ihm.getPlateau().aUnCoupValide(joueurActuel)) && !(ihm.getPlateau().ListCoupValide(joueurActuel).isEmpty())){
                            while (!coupEffectué) {// recherche de coup valide aléatoire dans la matrice de coup valide
                                int uncoupvalide = random.nextInt(ListCoupvalide.size());
                                // int j=random.nextInt(7);

                                // if ( ListCoupvalide[i][j]==-1){//trouve un coup valide et l'effectue
                                ihm.getPlateau().setCase(ListCoupvalide.get(uncoupvalide)[0] + 1,ListCoupvalide.get(uncoupvalide)[1] + 1,joueurActuel.getTypedepion() == "\u26AA" ? 1 : 2);
                                ihm.getPlateau().retournerPions(joueurActuel, ListCoupvalide.get(uncoupvalide)[0] + 1,ListCoupvalide.get(uncoupvalide)[1] + 1);// comme pour un tour de jeu mais sans demande
                                
                                

                                coupEffectué = true;// à effectuer un coup valide donc fin de la méthode
                                
                            }
                        }
                        if (joueurActuel.getNom() == joueur1.getNom()) {// change de joueur actuel pour le prochain tour de jeu
                                                                                
                            joueurActuel = joueur2;
                        } else {
                            joueurActuel = joueur1;
                        }
                    
                    }else if (joueurActuel.getNom().equalsIgnoreCase("Strong_IA")) {//tour de jeu strongia
                        ihm.afficherMessage(joueurActuel.getNom());
                        int[] meilleurCoup = meilleurCoup(ihm.getPlateau(), 8, joueurActuel);//recursion pour obtenir le coup le plus optimiser selon la profondeur
                        if(!(meilleurCoup==null)){
                            ihm.getPlateau().setCase(meilleurCoup[0] + 1, meilleurCoup[1] + 1,joueurActuel.getTypedepion() == "\u26AA" ? 1 : 2);
                            ihm.getPlateau().retournerPions(joueurActuel, meilleurCoup[0] + 1, meilleurCoup[1] + 1);
                        }
                        if (joueurActuel.getNom() == joueur1.getNom()) {// change de joueur actuel pour le prochain tour de jeu
                            joueurActuel = joueur2;
                        } else {
                            joueurActuel = joueur1;
                        }

                    } else {
                        // tour de jeu du joueur humain

                        ihm.afficherMessage(joueurActuel.getNom()
                                + " à vous de jouer. Saisir une colonne entre A et H suivie d'une ligne entre 1 et 8 ou P pour passer le tour");
                        if (!(ihm.getPlateau().aUnCoupValide(joueurActuel))) {// si il n'y a pas de coup valide
                            String coup = ihm.demanderCaracteres(
                                    "Aucun coup valide disponible, appuyez sur P pour passer votre tour");
                            if (coup == "P" || coup == "p") {
                                if (joueurActuel.getNom() == joueur1.getNom()) {
                                    joueurActuel = joueur2;
                                } else {
                                    joueurActuel = joueur1;
                                }
                            }
                        } else {// effectue le tour en demandant où le joueur veut poser le pion
                            String coup = ihm.demanderCaracteres(
                                    "Entrez le chiffre de la ligne suivie d'une virgule et d'une lettre pour la ligne (3,D)");
                            // sépare la réponse en deux champs pour pouvoir prendre deux coordonnées
                            // différentes
                            String[] lescoups = coup.split(",");
                            if (lescoups.length != 2) {
                                ihm.afficherMessage(
                                        "Entrée Invalide! Veuillez entrez le chiffre de la ligne suivie d'une virgule et d'une lettre pour la ligne (3,D)");
                            }
                            int x = Integer.parseInt(lescoups[0]);// ligne
                            int y = ihm.getPlateau().convertirLettretoChiffre(lescoups[1]);// la colonne
                            // int x = ihm.demanderEntier("Entrez la ligne (1-8) : ");
                            // int y = ihm.demanderEntier("Entrez la colonne (1-8) : ");//ptete faire un
                            // case pour A-H
                            if (ihm.getPlateau().estCoupValide(joueurActuel, x, y)) {// normalement marche
                                ihm.getPlateau().setCase(x, y, joueurActuel.getTypedepion() == "\u26AA" ? 1 : 2);
                                ihm.getPlateau().retournerPions(joueurActuel, x, y);// marche pas//good aussi
                                if (joueurActuel.getNom() == joueur1.getNom()) {
                                    joueurActuel = joueur2;
                                } else {
                                    joueurActuel = joueur1;
                                }
                            } else {
                                ihm.afficherMessage("Coup invalide ! Veuillez réessayer");
                            }
                        }
                        

                    }
                       
                        // affichage du plateau
                        ihm.afficher();
                       /* System.out.println(joueurActuel.getNom()+":");
                        System.out.println(ihm.getPlateau().aUnCoupValide(joueurActuel));
                        System.out.println(ihm.getPlateau().ListCoupValide(joueurActuel).toString());
                        if (ihm.getPlateau().ListCoupValide(joueurActuel).isEmpty() ){
                            System.out.println("coup non trouvé");
                        }*/
                        
                        if (!(ihm.getPlateau().aUnCoupValide(joueur1)) && !(ihm.getPlateau().aUnCoupValide(joueur2))) {
                            // teste pour savoir si la partie est terminée
                                                                                                                    
                            PartieEnCours = false;
                            /*System.out.println(joueur1.getNom()+":");
                            System.out.println(ihm.getPlateau().aUnCoupValide(joueur1));
                            System.out.println(joueur2.getNom()+":");
                            System.out.println(ihm.getPlateau().aUnCoupValide(joueur2));*/
                        }
                        // PartieEnCours=false;// sert à terminer la partie au bout d'un tour
                }
                    /*System.out.println(joueur1.getNom()+":");
                    System.out.println(ihm.getPlateau().aUnCoupValide(joueur1));
                    System.out.println(joueur2.getNom()+":");
                    System.out.println(ihm.getPlateau().aUnCoupValide(joueur2));*/
                    ihm.afficherMessage("La Partie est terminée !");

                    // Défilement d'une fin de partie à l'aide determinerGagnant
                    if (determinerGagnant() == joueur1.getNom()) {
                        ihm.afficherMessage(joueur1.getNom() + " a gagné cette partie !");
                        joueur1.upScore();
                    } else if (determinerGagnant() == joueur2.getNom()) {
                        ihm.afficherMessage(joueur2.getNom() + " a gagné cette partie !");
                        joueur2.upScore();
                    } else {
                        ihm.afficherMessage("Ex aequo");
                    }

                    String rejouer = ihm.demanderCaracteres("Voulez vous rejouer, Y or N ?");
                    if (rejouer.equalsIgnoreCase("Y")) {
                        ilsveulentjouer = true;
                    } else {
                        ilsveulentjouer = false;
                    }
                    // System.out.println(ilsveulentjouer); test la value de si on rejoue
                    // ilsveulentjouer=((rejouer=="Y" || rejouer=="y" )?true:false);
            }
            // affichage de resultats des parties
            ihm.afficherMessage(joueur1.getNom() + " a gagné " + joueur1.getScore() + " parties ! Et "
                    + joueur2.getNom() + " a gagné " + joueur2.getScore() + " parties !");
            if (joueur1.getScore() == joueur2.getScore()) {
                ihm.afficherMessage("C'est ex aequo , il n'y a pas de vainqueur");
            } else if (joueur1.getScore() >= joueur2.getScore()) {
                ihm.afficherMessage("C'est " + joueur1.getNom() + " qui a gagné");
            } else {
                ihm.afficherMessage("C'est " + joueur2.getNom() + " qui a gagné");
            }
        }
    }
    
