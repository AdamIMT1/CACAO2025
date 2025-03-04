package abstraction.eq4Transformateur1.bourseCacao;

import abstraction.eq4Transformateur1.Transformateur1Acteur;
import abstraction.eqXRomu.bourseCacao.IAcheteurBourse;
import abstraction.eqXRomu.produits.Feve;

public class Transformateur1AcheteurBourse extends Transformateur1Acteur implements IAcheteurBourse{
	
	private Feve feve;	//Type de feve voulue
	private double T;	//Quantité de feve en tonne

	public Transformateur1AcheteurBourse() {
		super();
		this.feve = Feve.F_MQ;
		this.T = 80.0;
	}

	@Override
	public double demande(Feve f, double cours) {
		if (this.feve.equals(f)) {
			this.journal.ajouter("Je demande " + T + " tonnes de " + f + " au cours de " + cours + " euros par tonne.");
			return T;
		} else {
			return 0.0;
		}
	}

	@Override
	public void notificationAchat(Feve f, double quantiteEnT, double coursEnEuroParT) {
		this.totalStocksFeves.setValeur(this, this.totalStocksFeves.getValeur() +quantiteEnT);
	}

	@Override
	public void notificationBlackList(int dureeEnStep) {
		this.journal.ajouter("Aie... je suis blackliste... j'aurais du verifier que j'avais assez d'argent avant de passer une trop grosse commande en bourse...");
	}
}
