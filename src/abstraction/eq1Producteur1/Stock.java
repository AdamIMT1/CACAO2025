package abstraction.eq1Producteur1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eqXRomu.filiere.IActeur;

public class Stock  {
    private Map<Feve, Double> stocks; // Map pour gérer les stocks de fèves
    private Journal journalStock; // Journal pour enregistrer les opérations
    private Producteur1ContratCadre producteur1;// Référence au Producteur1

    public Stock(IActeur a) {
        this.stocks = new HashMap<>(); // Initialisation du Map
        System.out.println("classe : "+a.getClass());
        //System.exit(0);
        this.producteur1=(Producteur1ContratCadre)a;
        // Initialisation des stocks pour chaque type de fève
        this.stocks.put(Feve.F_BQ, 2 * 50000.0); // Stock initial pour fèves basse qualité
        this.stocks.put(Feve.F_MQ, 2 * 30000.0); // Stock initial pour fèves moyenne qualité
        this.stocks.put(Feve.F_HQ_E, 2 * 20000.0); // Stock initial pour fèves haute qualité
        
        this.journalStock = new Journal("Journal de Stock", producteur1); // Initialisation du journal
    }

    public Journal getJournal() {
        return journalStock;
    }

    // Ajouter une quantité pour une fève donnée
    public void ajouter(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            if (quantite < 0) {
                journalStock.ajouter("Erreur : Tentative d'ajouter une quantité négative pour " + produit);
                return;
            }
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            stocks.put(feve, actuel + quantite);
            journalStock.ajouter("Ajout " + quantite + " au stock de " + feve + ". Nouveau stock : " + (actuel + quantite));
        }
    }

    // Retirer une quantité si possible, retourne true si réussi
    public boolean retirer(IProduit produit, double quantite) {
        if (produit instanceof Feve) {
            Feve feve = (Feve) produit;
            double actuel = stocks.getOrDefault(feve, 0.0);
            if (quantite < 0) {
                journalStock.ajouter("Erreur : Tentative de retirer une quantité négative pour " + produit + ". Retrait annulé.");
                return false;
            } else if (actuel < quantite) {
                journalStock.ajouter("Erreur : Tentative de retirer plus que le stock disponible pour " + produit + ". Retrait annulé.");
                return false;
            } else {
                stocks.put(feve, actuel - quantite);
                journalStock.ajouter("Retiré " + quantite + " du stock de " + feve + ". Nouveau stock : " + (actuel - quantite));

            
                

                return true;
            }
        }
        return false;
    }

    // Obtenir le stock d'une fève donnée
    public double getStock(Feve feve) {
        return stocks.getOrDefault(feve, 0.0);
    }

    // Ajouter des quantités pour chaque type de fève
    public void ajouterStock(double quantiteFMQ, double quantiteFBQ, double quantiteFHQ) {
        ajouter(Feve.F_MQ, quantiteFMQ); // Ajoute au stock de fèves moyenne qualité
        ajouter(Feve.F_BQ, quantiteFBQ); // Ajoute au stock de fèves basse qualité
        ajouter(Feve.F_HQ_E, quantiteFHQ); // Ajoute au stock de fèves haute qualité
    }

    // Obtenir le stock total (toutes fèves confondues)
    public double getStockTotal() {
        // Somme des stocks de chaque type de fève 
        return stocks.values().stream().mapToDouble(Double::doubleValue).sum();
    }



    
}

