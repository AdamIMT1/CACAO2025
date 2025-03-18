package abstraction.eq6Transformateur3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.filiere.IActeur;
import abstraction.eqXRomu.general.Journal;
import abstraction.eqXRomu.general.Variable;
import abstraction.eqXRomu.produits.Chocolat;
import abstraction.eqXRomu.produits.Feve;
import abstraction.eqXRomu.produits.IProduit;
import abstraction.eq6Transformateur3.eq6Transformateur3Stock;


public class Transformateur3Acteur implements IActeur {
	
	protected int cryptogramme;
	protected int etape;
	protected double coutStockage;

	protected Journal jdb;
	protected Journal journalStock;
	protected Journal journalTransac;
	protected Journal journalCC;

	protected List<IProduit> lesFeves;
	protected List<IProduit> lesChocolats;
	protected HashMap<IProduit, Variable> dicoIndicateurFeves;
	protected eq6Transformateur3Stock stockFeves;
	protected eq6Transformateur3Stock stockChoco;

	protected Variable eq6_Q_MQ_0;
	protected Variable eq6_Q_MQ_1;
	protected Variable eq6_Q_BQ_0;
	protected Variable eq6_Q_BQ_1;
	protected Variable eq6_Q_HQ_1;
	protected Variable eq6_Q_HQ_2;
	protected Variable eq6_Q_Fraudo;
	protected Variable eq6_Q_Bollo;
	protected Variable eq6_Q_Arna;
	protected Variable eq6_Q_Hypo;
	protected Variable eq6_Q_ingre;
	protected Variable eq6_Q_machine;
	protected Variable eq6_capacite_machine;
	protected Variable eq6_nb_employe;
	protected Variable eq6_jours_decouvert;
	protected Variable eq6_cout_stockage;
	protected Variable eq6_Q_cacao_CC;
	protected Variable eq6_Q_tablette_CC;

	public Transformateur3Acteur() {
		// Initialisation des journaux
		this.jdb = new Journal("Journal de bord", this);
		this.journalStock = new Journal("Journal des stocks", this);
		this.journalTransac = new Journal("Journal des transactions", this);
		this.journalCC = new Journal("Journal des contrats cadre", this);

		// Initialisation des indicateurs
		this.eq6_Q_BQ_0 = new Variable(this.getNom()+": quantité de cacao de BQ non labellisé", this, 0);
		this.eq6_Q_BQ_1 = new Variable(this.getNom()+": quantité de cacao de BQ équitable", this, 0);
		this.eq6_Q_MQ_0 = new Variable(this.getNom()+": quantité de cacao de MQ non labellisé", this, 0);
		this.eq6_Q_MQ_1 = new Variable(this.getNom()+": quantité de cacao de MQ équitable", this, 0);
		this.eq6_Q_HQ_1 = new Variable(this.getNom()+": quantité de cacao de HQ équitable", this, 0);
		this.eq6_Q_HQ_2 = new Variable(this.getNom()+": quantité de cacao de HQ bio & équitable", this, 0);
		this.eq6_Q_Fraudo = new Variable(this.getNom()+": quantité de tablette Fraudolat", this, 0);
		this.eq6_Q_Bollo = new Variable(this.getNom()+": quantité de tablette Bollorolat", this, 0);
		this.eq6_Q_Arna = new Variable(this.getNom()+": quantité de tablette Arnaquolat", this, 0);
		this.eq6_Q_Hypo = new Variable(this.getNom()+": quantité de tablette Hypocritolat", this, 0);
		this.eq6_Q_ingre = new Variable(this.getNom()+": quantité d'ingédient secondaire", this, 0);
		this.eq6_Q_machine = new Variable(this.getNom()+": quantité de machine", this, 0);
		this.eq6_capacite_machine = new Variable(this.getNom()+": capacité de production des machines", this, 0);
		this.eq6_jours_decouvert = new Variable(this.getNom()+": nombre de jours à découvert", this, 0);
		this.eq6_nb_employe = new Variable(this.getNom()+": nombre d'employés", this, 0);
		this.eq6_cout_stockage = new Variable(this.getNom()+": coûts de stockage pour ce step", this, 0);
		this.eq6_Q_cacao_CC = new Variable(this.getNom()+": quantité de cacaco que l'on reçoit ", this, 0);
		this.eq6_Q_tablette_CC = new Variable(this.getNom()+": quantité de tablette à produire", this, 0);
	
		//Dico d'indicateur fèves
		this.dicoIndicateurFeves = new HashMap<IProduit, Variable>();
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_BQ,eq6_Q_BQ_0);
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_BQ_E,eq6_Q_BQ_1);
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_MQ,eq6_Q_MQ_0);
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_MQ_E,eq6_Q_MQ_1);
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_HQ_E,eq6_Q_HQ_1);
		this.dicoIndicateurFeves.put(abstraction.eqXRomu.produits.Feve.F_HQ_BE,eq6_Q_HQ_2);

	}
	
	public void initialiser() {
		// Lister les fèves qui existent
		this.lesFeves = new ArrayList<IProduit>();
		for (Feve f : Feve.values()) {
			this.lesFeves.add(f);
		}

		//Récupération des paramètres
		this.coutStockage = 4*Filiere.LA_FILIERE.getParametre("cout moyen stockage producteur").getValeur();
		
		//Création du stock de fèves
		stockFeves = new eq6Transformateur3Stock(this, journalStock, "fèves", 300.0, lesFeves, dicoIndicateurFeves);
	}

	public String getNom() {// NE PAS MODIFIER
		return "EQ6";
	}
	
	public String toString() {// NE PAS MODIFIER
		return this.getNom();
	}

	////////////////////////////////////////////////////////
	//         En lien avec l'interface graphique         //
	////////////////////////////////////////////////////////

	public void next() {
		etape = Filiere.LA_FILIERE.getEtape();
		jdb.ajouter("Accteur Etape " + etape);
	}

	public Color getColor() {// NE PAS MODIFIER
		return new Color(158, 242, 226); 
	}

	public String getDescription() {
		return "Bla bla bla";
	}

	// Renvoie les indicateurs
	public List<Variable> getIndicateurs() {
		List<Variable> res = new ArrayList<Variable>();
		return res;
	}

	// Renvoie les parametres
	public List<Variable> getParametres() {
		List<Variable> res=new ArrayList<Variable>();
		return res;
	}

	// Renvoie les journaux
	public List<Journal> getJournaux() {
		List<Journal> res=new ArrayList<Journal>();
		return res;
	}

	////////////////////////////////////////////////////////
	//               En lien avec la Banque               //
	////////////////////////////////////////////////////////

	// Appelee en debut de simulation pour vous communiquer 
	// votre cryptogramme personnel, indispensable pour les
	// transactions.
	public void setCryptogramme(Integer crypto) {
		this.cryptogramme = crypto;
	}

	// Appelee lorsqu'un acteur fait faillite (potentiellement vous)
	// afin de vous en informer.
	public void notificationFaillite(IActeur acteur) {
	}

	// Apres chaque operation sur votre compte bancaire, cette
	// operation est appelee pour vous en informer
	public void notificationOperationBancaire(double montant) {
	}
	
	// Renvoie le solde actuel de l'acteur
	protected double getSolde() {
		return Filiere.LA_FILIERE.getBanque().getSolde(Filiere.LA_FILIERE.getActeur(getNom()), this.cryptogramme);
	}

	////////////////////////////////////////////////////////
	//        Pour la creation de filieres de test        //
	////////////////////////////////////////////////////////

	// Renvoie la liste des filieres proposees par l'acteur
	public List<String> getNomsFilieresProposees() {
		ArrayList<String> filieres = new ArrayList<String>();
		return(filieres);
	}

	// Renvoie une instance d'une filiere d'apres son nom
	public Filiere getFiliere(String nom) {
		return Filiere.LA_FILIERE;
	}

	public double getQuantiteEnStock(IProduit p, int cryptogramme) {
		if (this.cryptogramme==cryptogramme) { // c'est donc bien un acteur assermente qui demande a consulter la quantite en stock
			return 0; // A modifier
		} else {
			return 0; // Les acteurs non assermentes n'ont pas a connaitre notre stock
		}
	}
}
