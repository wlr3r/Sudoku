 import java.util.Scanner;

// Définition de la classe Sudoku
public class Sudoku {

    // Déclaration des constantes
    private static final int TAILLE_BOITE = 3;
    private static final int TAILLE_GRILLE = TAILLE_BOITE * TAILLE_BOITE;

    // Déclaration de la grille
    private int[][] grille;

    // Constructeur de la classe Sudoku
    public Sudoku() {
        // Initialisation de la grille avec une configuration de départ
        grille = new int[][]{
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    }

    // Méthode principale pour lancer le jeu
    public void jouer() {
        // Création d'un objet Scanner pour la saisie utilisateur
        Scanner scanner = new Scanner(System.in);
        int choix;

        // Boucle principale du jeu
        do{
            // Affichage de la grille et des options
            afficherGrille();
            afficherOptions();
            System.out.print("Entrez votre choix : ");
            choix = scanner.nextInt();

            // Traitement du choix de l'utilisateur
            switch (choix) {
                case 1:
                    jouerCase();
                    break;
                case 2:
                    afficherGrille();
                    break;
                case 3:
                    resoudreSudoku();
                    break;
                case 0:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (choix != 0);

        // Fermeture du scanner
        scanner.close();
    }

    // Méthode pour jouer un coup dans la grille
    private void jouerCase() {
        Scanner scanner = new Scanner(System.in);
    
        // Saisie de la ligne, de la colonne et de la valeur
        System.out.print("Entrez la ligne (1-9) : ");
        int ligne = scanner.nextInt();
    
        System.out.print("Entrez la colonne (1-9) : ");
        int col = scanner.nextInt();
    
        // Vérification si la case sélectionnée est vide
        if (grille[ligne - 1][col - 1] == 0) {
            System.out.print("Entrez la valeur (1-9) : ");
            int valeur = scanner.nextInt();
    
            // Vérification et mise à jour de la grille
            if (coupValide(ligne - 1, col - 1, valeur)) {
                grille[ligne - 1][col - 1] = valeur;
    
                // Vérification de la grille complète
                if (estGrilleValide()) {
                    System.out.println("Félicitations, vous avez gagné !");
                    System.exit(0);
                }
            } else {
                System.out.println("Coup invalide. Veuillez réessayer.");
            }
        } else {
            System.out.println("La case sélectionnée n'est pas vide. Veuillez réessayer.");
        }
    }
    

    // Méthode pour vérifier si un coup est valide
    private boolean coupValide(int ligne, int col, int valeur) {
        return !existeDansLigne(ligne, valeur) &&
               !existeDansColonne(col, valeur) &&
               !existeDansBoite(ligne - (ligne % TAILLE_BOITE), col - (col % TAILLE_BOITE), valeur);
    }

    // Méthode pour vérifier si une valeur existe dans la ligne
    private boolean existeDansLigne(int ligne, int valeur) {
        for (int j = 0; j < TAILLE_GRILLE; j++) {
            if (grille[ligne][j] == valeur) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour vérifier si une valeur existe dans la colonne
    private boolean existeDansColonne(int col, int valeur) {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            if (grille[i][col] == valeur) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour vérifier si une valeur existe dans la boîte 3x3
    private boolean existeDansBoite(int debutLigne, int debutCol, int valeur) {
        for (int i = debutLigne; i < debutLigne + TAILLE_BOITE; i++) {
            for (int j = debutCol; j < debutCol + TAILLE_BOITE; j++) {
                if (grille[i][j] == valeur) {
                    return true;
                }
            }
        }
        return false;
    }

    // Méthode pour vérifier si la grille est valide
    private boolean estGrilleValide() {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) {
                if (grille[i][j] != 0 && (!coupValide(i, j, grille[i][j]) || !grillePartielleValide(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode pour vérifier si la grille partielle est valide
    private boolean grillePartielleValide(int ligne, int col) {
        int valeur = grille[ligne][col];
        grille[ligne][col] = 0;

        boolean estValide = coupValide(ligne, col, valeur) &&
                grillePartielleValideDansLigne(ligne) &&
                grillePartielleValideDansColonne(col) &&
                grillePartielleValideDansBoite(ligne - (ligne % TAILLE_BOITE), col - (col % TAILLE_BOITE));

        grille[ligne][col] = valeur;
        return estValide;
    }

    // Méthode pour vérifier si la grille partielle est valide dans une ligne
    private boolean grillePartielleValideDansLigne(int ligne) {
        for (int j = 0; j < TAILLE_GRILLE; j++) {
            if (!coupValide(ligne, j, grille[ligne][j])) {
                return false;
            }
        }
        return true;
    }

    // Méthode pour vérifier si la grille partielle est valide dans une colonne
    private boolean grillePartielleValideDansColonne(int col) {
        for (int i = 0; i < TAILLE_GRILLE; i++) { 
            if (!coupValide(i, col, grille[i][col])) { 
                return false;
            }
        }
        return true;
    }

    // Méthode pour vérifier si la grille partielle est valide dans une boîte 3x3
    private boolean grillePartielleValideDansBoite(int debutLigne, int debutCol) {
        for (int i = debutLigne; i < debutLigne + TAILLE_BOITE; i++) { //parcourir la boite
            for (int j = debutCol; j < debutCol + TAILLE_BOITE; j++) {
                if (!coupValide(i, j, grille[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode pour afficher les options du jeu
    private void afficherOptions() {
        System.out.println("\nOptions :");
        System.out.println("1 : Choisir une case");
        System.out.println("2 : Afficher la grille");
        System.out.println("3 : BOT");
        System.out.println("0 : Quitter");
    }

    private boolean resoudreSudoku() {
        for (int ligne = 0; ligne < TAILLE_GRILLE; ligne++) {
            for (int col = 0; col < TAILLE_GRILLE; col++) {
                if (grille[ligne][col] == 0) {
                    for (int valeur = 1; valeur <= TAILLE_GRILLE; valeur++) {
                        if (coupValide(ligne, col, valeur)) {
                            grille[ligne][col] = valeur;
                            if (resoudreSudoku()) {
                                return true;
                            } else {
                                grille[ligne][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Méthode pour afficher la grille
    private void afficherGrille() {
        for (int i = 0; i < TAILLE_GRILLE; i++) {
            for (int j = 0; j < TAILLE_GRILLE; j++) { //parcourir la grille
                System.out.print(grille[i][j] + " "); //afficher la valeur de la case
                if ((j + 1) % TAILLE_BOITE == 0 && j < TAILLE_GRILLE - 1) { 
                    System.out.print("| ");
                }
            }
            System.out.println();
            if ((i + 1) % TAILLE_BOITE == 0 && i < TAILLE_GRILLE - 1) { //si on est à la fin d'une boite
                System.out.println("-".repeat(TAILLE_GRILLE * 2 - 1)); //répéter le caractère - pour la taille de la grille
            }
        }
    }

    // Méthode principale pour lancer le jeu
    public static void main(String[] args) {
        Sudoku jeu = new Sudoku();
        jeu.jouer();
    }
}
