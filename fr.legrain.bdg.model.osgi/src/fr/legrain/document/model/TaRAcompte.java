package fr.legrain.document.model;

// Generated Apr 7, 2009 3:27:23 PM by Hibernate Tools 3.2.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import fr.legrain.document.dto.IRelationDoc;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaRDocument generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_r_acompte")
//@SequenceGenerator(name = "gen_r_acompte", sequenceName = "num_id_r_acompte", allocationSize = 1)
public class TaRAcompte implements java.io.Serializable {

	private static final long serialVersionUID = -1626394987143034177L;
	
	private int id;
	private String version;
	private TaFacture taFacture;
	private TaTicketDeCaisse taTicketDeCaisse;
	private TaBoncde taBoncde;
	private TaProforma taProforma;
	private TaDevis taDevis;
	private TaApporteur taApporteur;
	private TaAvoir taAvoir;
	private TaBonliv taBonliv;
	private TaAcompte taAcompte;
	private TaPrelevement taPrelevement;
	private BigDecimal affectation  = new BigDecimal(0);
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;
//	private Integer export= 0;
	public static final String TYPE_DOC = "RAcompte";
	@Transient
	private String typeDocument;
	
	private Integer Etat =0;

	private boolean EtatDeSuppression =false;

	public TaRAcompte() {
	}

	public TaRAcompte(int idRAcompte) {
		this.id = idRAcompte;
	}

//public TaRAcompte(int id, String version, TaFacture taFacture,
//			TaBoncde taBoncde, TaProforma taProforma, TaDevis taDevis,
//			TaApporteur taApporteur, TaAvoir taAvoir, TaBonliv taBonliv,
//			TaAcompte taAcompte, BigDecimal affectation, String quiCree,
//			Date quandCree, String quiModif, Date quandModif, String ipAcces,
//			Integer versionObj, boolean etatDeSuppression) {
//		super();
//		this.id = id;
//		this.version = version;
//		this.taFacture = taFacture;
//		this.taBoncde = taBoncde;
//		this.taProforma = taProforma;
//		this.taDevis = taDevis;
//		this.taApporteur = taApporteur;
//		this.taAvoir = taAvoir;
//		this.taBonliv = taBonliv;
//		this.taAcompte = taAcompte;
//		this.affectation = affectation;
//		this.quiCree = quiCree;
//		this.quandCree = quandCree;
//		this.quiModif = quiModif;
//		this.quandModif = quandModif;
//		this.ipAcces = ipAcces;
//		this.versionObj = versionObj;
//		EtatDeSuppression = etatDeSuppression;
//	}

//	public TaRAcompte(int idRAcompte, TaFacture taFacture, TaBoncde taBoncde,
//			TaProforma taProforma, 
//			TaBonliv taBonliv, TaAvoir taAvoir,TaApporteur taApporteur,
//			TaDevis taDevis, String quiCreeRDocument,
//			Date quandCreeRDocument, String quiModifRDocument,
//			Date quandModifRDocument, String ipAcces, Integer versionObj) {
//		this.id = idRAcompte;
//		this.taFacture = taFacture;
//		this.taBoncde = taBoncde;
//		this.taProforma = taProforma;
//		this.taDevis = taDevis;		
//		this.taApporteur = taApporteur;
//		this.taAvoir = taAvoir;
//		this.taBonliv = taBonliv;
//		this.quiCree = quiCreeRDocument;
//		this.quandCree = quandCreeRDocument;
//		this.quiModif = quiModifRDocument;
//		this.quandModif = quandModifRDocument;
//		this.ipAcces = ipAcces;
//		this.versionObj = versionObj;
//	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id",table = "ta_r_acompte", champEntite="id", clazz = TaRAcompte.class)
	public int getId() {
		return this.id;
	}

	public void setId(int idRAcompte) {
		this.id = idRAcompte;
	}

	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_facture")
	@LgrHibernateValidated(champBd = "id_facture",table = "ta_facture", champEntite="taFacture.idDocument", clazz = TaFacture.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRAcomptes")
	public TaFacture getTaFacture() {
		return this.taFacture;
	}

	public void setTaFacture(TaFacture taFacture) {
		this.taFacture = taFacture;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_prelevement")
	@LgrHibernateValidated(champBd = "id_prelevement",table = "ta_prelevement", champEntite="taPrelevement.idDocument", clazz = TaPrelevement.class)
	public TaPrelevement getTaPrelevement() {
		return taPrelevement;
	}

	public void setTaPrelevement(TaPrelevement taPrelevement) {
		this.taPrelevement = taPrelevement;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_boncde")
	@LgrHibernateValidated(champBd = "id_boncde",table = "ta_boncde", champEntite="taBoncde.idDocument", clazz = TaBoncde.class)
	public TaBoncde getTaBoncde() {
		return this.taBoncde;
	}

	public void setTaBoncde(TaBoncde taBoncde) {
		this.taBoncde = taBoncde;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_proforma")
	@LgrHibernateValidated(champBd = "id_proforma",table = "ta_proforma", champEntite="taProforma.idDocument", clazz = TaProforma.class)
	public TaProforma getTaProforma() {
		return this.taProforma;
	}

	public void setTaProforma(TaProforma taProforma) {
		this.taProforma = taProforma;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_devis")
	@LgrHibernateValidated(champBd = "id_devis",table = "ta_devis", champEntite="taDevis.idDocument", clazz = TaDevis.class)
	public TaDevis getTaDevis() {
		return this.taDevis;
	}

	public void setTaDevis(TaDevis taDevis) {
		this.taDevis = taDevis;
	}
	
	//
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_apporteur")
	@LgrHibernateValidated(champBd = "id_apporteur",table = "ta_apporteur", champEntite="taApporteur.idDocument", clazz = TaApporteur.class)
	public TaApporteur getTaApporteur() {
		return this.taApporteur;
	}

	public void setTaApporteur(TaApporteur taApporteur) {
		this.taApporteur = taApporteur;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_avoir")
	@LgrHibernateValidated(champBd = "id_avoir",table = "ta_avoir", champEntite="taAvoir.idDocument", clazz = TaAvoir.class)
	public TaAvoir getTaAvoir() {
		return this.taAvoir;
	}

	public void setTaAvoir(TaAvoir taAvoir) {
		this.taAvoir = taAvoir;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bonliv")
	@LgrHibernateValidated(champBd = "id_bonliv",table = "ta_bonliv", champEntite="taBonliv.idDocument", clazz = TaBonliv.class)
	public TaBonliv getTaBonliv() {
		return this.taBonliv;
	}

	public void setTaBonliv(TaBonliv taBonliv) {
		this.taBonliv = taBonliv;
	}
	//
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_acompte")
	@LgrHibernateValidated(champBd = "id_acompte",table = "ta_acompte", champEntite="taAcompte.idDocument", clazz = TaAcompte.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRAcomptes")
	public TaAcompte getTaAcompte() {
		return this.taAcompte;
	}

	public void setTaAcompte(TaAcompte taAcompte) {
		this.taAcompte = taAcompte;
	}
	
	@Column(name = "affectation", precision = 15)
	@LgrHibernateValidated(champBd = "affectation",table = "ta_r_acompte", champEntite="affectation", clazz = TaRAcompte.class)
	public BigDecimal getAffectation() {
		return affectation ;
	}

	public void setAffectation(BigDecimal montant) {
		this.affectation = montant;
	}



//	@Column(name = "export")
//	@LgrHibernateValidated(champBd = "export",table = "ta_r_acompte", champEntite="export", clazz = TaRAcompte.class)
//	public Integer getExport() {
//		return export;
//	}
//
//	public void setExport(Integer export) {
//		this.export = export;
//	}
	

	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeRDocument) {
		this.quiCree = quiCreeRDocument;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeRDocument) {
		this.quandCree = quandCreeRDocument;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifRDocument) {
		this.quiModif = quiModifRDocument;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifRDocument) {
		this.quandModif = quandModifRDocument;
	}

	@Column(name = "ip_acces", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	public BigDecimal calculAffectationEnCours(TaFacture taDocument){
		if(this.getId()==0)
			return getAffectation();
			else
		return BigDecimal.valueOf(0);
	}

	@Transient
	public boolean isEtatDeSuppression() {
		return EtatDeSuppression;
	}
	
	
	public void setEtatDeSuppression(boolean etatDeSuppression) {
		EtatDeSuppression = etatDeSuppression;
	}


	@Column(name = "etat")
	@LgrHibernateValidated(champBd = "etat",table = "ta_r_acompte", champEntite="Etat", clazz = TaRAcompte.class)
	public Integer getEtat() {
		return Etat;
	}



	public void setEtat(Integer etat) {
		Etat = etat;
	}


	@Transient
	public String getTypeDocument() {
		return TYPE_DOC;
	}


	public void setTypeDocument(String typeDocument) {
		this.typeDocument=typeDocument;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_ticket_caisse")
	@LgrHibernateValidated(champBd = "id_ticket_caisse",table = "ta_ticket_caisse", champEntite="taTicketDeCaisse.idDocument", clazz = TaTicketDeCaisse.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRAcomptes")
	public TaTicketDeCaisse getTaTicketDeCaisse() {
		return taTicketDeCaisse;
	}

	public void setTaTicketDeCaisse(TaTicketDeCaisse taTicketDeCaisse) {
		this.taTicketDeCaisse = taTicketDeCaisse;
	}



}