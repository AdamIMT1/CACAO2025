package abstraction.eq7Distributeur1;
///Maxime GUY///


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import abstraction.eqXRomu.appelDOffre.SuperviseurVentesAO;
import abstraction.eqXRomu.appelDOffre.IAcheteurAO;
import abstraction.eqXRomu.appelDOffre.OffreVente;
import abstraction.eqXRomu.encheres.Enchere;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.ChocolatDeMarque;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.IProduit;



public class Distributeur1AcheteurAppelOffre extends Distributeur1AcheteurEncheres implements IAcheteurAO  {

	//protected Integer cryptogramme;
	protected IAcheteurAO identity;
	//private List<Double> requiredQuantities;
	//private Color color;
	//private String name;
	//private List<Double> stock;
	//private List<Double> priceProduct;

	public Distributeur1AcheteurAppelOffre() {
		super();

		//this.requiredQuantities = requiredQuantities;
		//this.identity = identity;
		//this.color = color;
		//this.name = name;
		//this.stock = stock;
		//this.priceProduct = priceProduct;
		//this.identity = identity;

	}

	public int getInt(Chocolat product){
        int idProduct = 0;
        switch(product.getGamme()){
            case BQ : idProduct=0;
            case MQ : idProduct=2;
            case HQ : idProduct=4;
        }
        if (product.isBio()){
            idProduct++;
        }
        if (product.isEquitable()){
            idProduct++;
        }
        return(idProduct);
    }
	
	public OffreVente choisirOV(List<OffreVente> propositions){
		int indice = -1;
		double volume = propositions.get(0).getQuantiteT();
		IProduit product = propositions.get(0).getProduit();
		if (product instanceof ChocolatDeMarque) {
        	ChocolatDeMarque chocolat = (ChocolatDeMarque) product;
			int idProduct = getInt(chocolat.getChocolat());
			double price = 1.03*this.priceProduct.get(idProduct)*volume ;
			for (int i=0; i<propositions.size(); i++){
				double priceProposed = propositions.get(i).getPrixT();
				if (priceProposed<price){
					indice = i;
					price = priceProposed;
					}
				}
				this.priceProduct.set(idProduct,price/volume);
			}
		
		if (indice == -1){
			return(null);
		}
		return(propositions.get(indice));
		}
	


	public void initialiser(){

	}

	public String getDescription(){
		return("Appelleur d'offre de l'equipe 7");
	}

	public void next(){
		SuperviseurVentesAO superviseur = new SuperviseurVentesAO();
		superviseur.acheterParAO(this.identity,this.cryptogramme, Chocolat.C_BQ , this.requiredQuantities.get(0));
		superviseur.acheterParAO(this.identity,this.cryptogramme, Chocolat.C_BQ_E , this.requiredQuantities.get(1));
		superviseur.acheterParAO(this.identity,this.cryptogramme, Chocolat.C_MQ , this.requiredQuantities.get(2));
		superviseur.acheterParAO(this.identity,this.cryptogramme, Chocolat.C_MQ_E , this.requiredQuantities.get(3));
		superviseur.acheterParAO(this.identity,this.cryptogramme, Chocolat.C_HQ_E , this.requiredQuantities.get(4));
	}

	public List<Variable> getIndicateurs(){
		List<Variable> indicateurs = new ArrayList<Variable>();
		return(indicateurs);
	}

	public List<Variable> getParametres(){
		List<Variable> parametres = new ArrayList<Variable>();
		return(parametres);
	}

	public List<Journal> getJournaux(){
		List<Journal> journaux = new ArrayList<Journal>();
		return(journaux);
	}

	public List<String> getNomsFilieresProposees(){
		List<String> noms = new ArrayList<String>();
		return(noms);
	}

	
}

