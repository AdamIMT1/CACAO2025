package abstraction.eq3Producteur3;

import abstraction.eqXRomu.produits.Feve;

import java.util.ArrayList;
public class Producteur3Employés extends Producteur3Vente {
    
    public Producteur3Employés() {
        super();
    }
    // Paul
    private double getMasseSalariale(){
        double masseSalariale = 0;
        double[] salaires = {6,15,37.5};
        for (int i = 0; i < 3; i++){
            masseSalariale += effectifs[i] * salaires[i];
        }
        journal.ajouter("Masse salariale : " + masseSalariale);
        return masseSalariale;
        }
}

