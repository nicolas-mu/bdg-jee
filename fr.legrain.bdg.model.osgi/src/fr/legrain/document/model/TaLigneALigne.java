package fr.legrain.document.model;

import java.math.BigDecimal;

// Generated Apr 7, 2009 3:27:23 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaRDocument generated by hbm2java
 */
@Entity

@Table(name = "ta_ligne_a_ligne")

public class TaLigneALigne implements java.io.Serializable {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = -5861060146735764699L;
	
	
	private int id;

	private TaLFacture taLFacture;
	private TaLTicketDeCaisse taLTicketDeCaisse;
	private TaLBoncde taLBoncde;
	private TaLBoncdeAchat taLBoncdeAchat;
	private TaLProforma taLProforma;
	private TaLBonliv taLBonliv;
	private TaLBonReception taLBonReception;
	private TaLAvoir taLAvoir;
	private TaLApporteur taLApporteur;
	private TaLDevis taLDevis;
	private TaLAcompte taLAcompte;
	private TaLPrelevement taLPrelevement;
	private TaLAvisEcheance taLAvisEcheance;
	private TaLFlash taLFlash;
	private TaLPreparation taLPreparation;
	private TaLPanier taLPanier;
	private Integer idLigneSrc;
	
	//rajout yann
	private TaLEcheance taLEcheance;
	private TaLAbonnement taLAbonnement;


	private BigDecimal qte;
	private BigDecimal qteRecue;
	private String unite;
	private String uniteRecue;
	private String numLot;
	private BigDecimal prixU;
	

	private Integer versionObj;

	public TaLigneALigne() {
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id",table = "ta_ligne_a_ligne", champEntite="id", clazz = TaLigneALigne.class)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_facture")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_facture", champEntite="taLFacture.idLDocument", clazz = TaLFacture.class)
	public TaLFacture getTaLFacture() {
		return this.taLFacture;
	}

	public void setTaLFacture(TaLFacture taLFacture) {
		this.taLFacture = taLFacture;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_prelevement")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_prelevement", champEntite="taLPrelevement.idLDocument", clazz = TaLPrelevement.class)
	public TaLPrelevement getTaLPrelevement() {
		return taLPrelevement;
	}

	public void setTaLPrelevement(TaLPrelevement taLPrelevement) {
		this.taLPrelevement = taLPrelevement;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_avis_echeance")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_avis_echeance", champEntite="taLAvisEcheance.idLDocument", clazz = TaLAvisEcheance.class)
	public TaLAvisEcheance getTaLAvisEcheance() {
		return taLAvisEcheance;
	}

	public void setTaLAvisEcheance(TaLAvisEcheance taLAvisEcheance) {
		this.taLAvisEcheance = taLAvisEcheance;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_boncde")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_boncde", champEntite="taLBoncde.idLDocument", clazz = TaLBoncde.class)
	public TaLBoncde getTaLBoncde() {
		return this.taLBoncde;
	}

	public void setTaLBoncde(TaLBoncde taLBoncde) {
		this.taLBoncde = taLBoncde;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_boncde_achat")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_boncde_achat", champEntite="taBoncdeAchat.idLDocument", clazz = TaLBoncdeAchat.class)
	public TaLBoncdeAchat getTaLBoncdeAchat() {
		return this.taLBoncdeAchat;
	}

	public void setTaLBoncdeAchat(TaLBoncdeAchat taLBoncdeAchat) {
		this.taLBoncdeAchat = taLBoncdeAchat;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_proforma")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_proforma", champEntite="taLProforma.idLDocument", clazz = TaLProforma.class)
	public TaLProforma getTaLProforma() {
		return this.taLProforma;
	}

	public void setTaLProforma(TaLProforma taLProforma) {
		this.taLProforma = taLProforma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_bonliv")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_bonliv", champEntite="taLBonliv.idLDocument", clazz = TaLBonliv.class)
	public TaLBonliv getTaLBonliv() {
		return this.taLBonliv;
	}

	public void setTaLBonliv(TaLBonliv taLBonliv) {
		this.taLBonliv = taLBonliv;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_avoir")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_avoir", champEntite="taLAvoir.idLDocument", clazz = TaLAvoir.class)
	public TaLAvoir getTaLAvoir() {
		return this.taLAvoir;
	}

	public void setTaLAvoir(TaLAvoir taLAvoir) {
		this.taLAvoir = taLAvoir;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_apporteur")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_apporteur", champEntite="taLApporteur.idLDocument", clazz = TaLApporteur.class)
	public TaLApporteur getTaLApporteur() {
		return this.taLApporteur;
	}

	public void setTaLApporteur(TaLApporteur taLApporteur) {
		this.taLApporteur = taLApporteur;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_devis")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_devis", champEntite="taLDevis.idLDocument", clazz = TaLApporteur.class)
	public TaLDevis getTaLDevis() {
		return this.taLDevis;
	}

	public void setTaLDevis(TaLDevis idLDevis) {
		this.taLDevis = idLDevis;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_acompte")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_acompte", champEntite="taLAcompte.idLDocument", clazz = TaLAcompte.class)
	public TaLAcompte getTaLAcompte() {
		return taLAcompte;
	}

	public void setTaLAcompte(TaLAcompte taLAcompte) {
		this.taLAcompte = taLAcompte;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_ticket_caisse")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_ticket_caisse", champEntite="taLTicketDeCaisse.idLDocument", clazz = TaLTicketDeCaisse.class)
	public TaLTicketDeCaisse getTaLTicketDeCaisse() {
		return taLTicketDeCaisse;
	}

	public void setTaLTicketDeCaisse(TaLTicketDeCaisse taLTicketDeCaisse) {
		this.taLTicketDeCaisse = taLTicketDeCaisse;
	}
	
	
	
	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}


	@Column(name = "qte_recue", precision = 15)
	public BigDecimal getQteRecue() {
		return qteRecue ;
	}

	public void setQteRecue(BigDecimal qteRecue) {
		this.qteRecue = qteRecue;
	}


	@Column(name = "qte", precision = 15)
	public BigDecimal getQte() {
		return qte ;
	}

	public void setQte(BigDecimal qte) {
		this.qte = qte;
	}
	

	@Column(name = "unite_recue")
	public String getUniteRecue() {
		return uniteRecue;
	}



	public void setUniteRecue(String uniteRecue) {
		this.uniteRecue = uniteRecue;
	}

	@Column(name = "unite")
	public String getUnite() {
		return unite;
	}



	public void setUnite(String unite) {
		this.unite = unite;
	}

	@Column(name = "num_lot")
	public String getNumLot() {
		return numLot;
	}



	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}


	@Column(name = "prix_unite", precision = 15)
	public BigDecimal getPrixU() {
		return prixU;
	}



	public void setPrixU(BigDecimal prixU) {
		this.prixU = prixU;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_bon_reception")
	@LgrHibernateValidated(champBd = "id_l_document",table = "ta_l_bon_reception", champEntite="taLBonReception.idLDocument", clazz = TaLBonReception.class)
	public TaLBonReception getTaLBonReception() {
		return taLBonReception;
	}



	public void setTaLBonReception(TaLBonReception taLBonReception) {
		this.taLBonReception = taLBonReception;
	}



	@Column(name = "id_ligne_src")
	public Integer getIdLigneSrc() {
		return idLigneSrc;
	}



	public void setIdLigneSrc(Integer idLigneSrc) {
		this.idLigneSrc = idLigneSrc;
	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_flash")
	public TaLFlash getTaLFlash() {
		return taLFlash;
	}



	public void setTaLFlash(TaLFlash taLFlash) {
		this.taLFlash = taLFlash;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_preparation")
	public TaLPreparation getTaLPreparation() {
		return taLPreparation;
	}



	public void setTaLPreparation(TaLPreparation taLPreparation) {
		this.taLPreparation = taLPreparation;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_echeance")
	public TaLEcheance getTaLEcheance() {
		return taLEcheance;
	}



	public void setTaLEcheance(TaLEcheance taLEcheance) {
		this.taLEcheance = taLEcheance;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_panier")
	public TaLPanier getTaLPanier() {
		return taLPanier;
	}



	public void setTaLPanier(TaLPanier taLPanier) {
		this.taLPanier = taLPanier;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_l_abonnement")
	public TaLAbonnement getTaLAbonnement() {
		return taLAbonnement;
	}



	public void setTaLAbonnement(TaLAbonnement taLAbonnement) {
		this.taLAbonnement = taLAbonnement;
	}






	

	
}