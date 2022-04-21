package fr.legrain.tiers.model;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

import org.apache.log4j.Logger;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import fr.legrain.general.model.TaObjetLgr;
import fr.legrain.tiers.model.TaTBanque;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaCompteBanque generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_compte_banque")
//@SequenceGenerator(name = "gen_compte_banque", sequenceName = "num_id_compte_banque", allocationSize = 1)
public class TaCompteBanque extends TaObjetLgr   implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 768657441856886888L;
	
	@Transient
	static Logger logger = Logger.getLogger(TaCompteBanque.class.getName());
	
	
	private int idCompteBanque;
//	private String version;
	private TaTBanque taTBanque;
	private TaTiers taTiers;
	private String nomBanque;
	private String compte;
	private String codeBanque;
	private String codeGuichet;
	private String cleRib;
	private String adresse1Banque;
	private String adresse2Banque;
	private String cpBanque;
	private String villeBanque;
	private String iban;
	private String codeBIC;
	private String nomCompte;
	private String titulaire;
//	private String quiCree;
//	private Date quandCree;
//	private String quiModif;
//	private Date quandModif;
//	private String ipAcces;
	private Integer versionObj;
	private String cptcomptable;
	
	private String bankCode;//: 12406
	private String branchCode;//: 00023
	private String country;//: FR
	private String last4;//: 0977
	
	private TaMandatPrelevement taMandatPrelevement;

	public TaCompteBanque() {
	}

	public TaCompteBanque(int idCompteBanque, TaTiers taTiers) {
		this.idCompteBanque = idCompteBanque;
		this.taTiers = taTiers;
	}

	public TaCompteBanque(int idCompteBanque, TaTBanque taTBanque,
			TaTiers taTiers, String nomBanque, String compte,
			String codeBanque, String codeGuichet, String cleRib,
			String adresse1Banque, String adresse2Banque, String cpBanque,
			String villeBanque, String iban, String codeBIC,String nomCompte, String titulaire,
			String quiCreeCompteBanque, Date quandCreeCompteBanque,
			String quiModifCompteBanque, Date quandModifCompteBanque,
			String ipAcces, Integer versionObj) {
		this.idCompteBanque = idCompteBanque;
		this.taTBanque = taTBanque;
		this.taTiers = taTiers;
		this.nomBanque = nomBanque;
		this.compte = compte;
		this.codeBanque = codeBanque;
		this.codeGuichet = codeGuichet;
		this.cleRib = cleRib;
		this.adresse1Banque = adresse1Banque;
		this.adresse2Banque = adresse2Banque;
		this.cpBanque = cpBanque;
		this.villeBanque = villeBanque;
		this.iban = iban;
		this.codeBIC = codeBIC;
		this.nomCompte = nomCompte;
		this.titulaire = titulaire;
		this.quiCree = quiCreeCompteBanque;
		this.quandCree = quandCreeCompteBanque;
		this.quiModif = quiModifCompteBanque;
		this.quandModif = quandModifCompteBanque;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compte_banque", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id_compte_banque",table = "ta_compte_banque",champEntite="idCompteBanque", clazz = TaCompteBanque.class)
	public int getIdCompteBanque() {
		return this.idCompteBanque;
	}

	public void setIdCompteBanque(int idCompteBanque) {
		this.idCompteBanque = idCompteBanque;
	}

//	@Column(name = "version", length = 20)
//	public String getVersion() {
//		return this.version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}

//	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH})
	@JoinColumn(name = "id_t_banque")
	@LgrHibernateValidated(champBd = "id_t_banque",table = "ta_t_banque",champEntite="idTBanque",clazz = TaTBanque.class)
	public TaTBanque getTaTBanque() {
		return this.taTBanque;
	}

	public void setTaTBanque(TaTBanque taTBanque) {
		this.taTBanque = taTBanque;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "id_tiers", nullable = false)
	@LgrHibernateValidated(champBd = "id_tiers",table = "ta_tiers",champEntite="idTiers",clazz = TaTiers.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taCompteBanques")
	public TaTiers getTaTiers() {
		return this.taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
	}

	@Column(name = "nom_banque", length = 20)
	@LgrHibernateValidated(champBd = "nom_banque",table = "ta_compte_banque",champEntite="nomBanque",clazz = TaCompteBanque.class)
	public String getNomBanque() {
		return this.nomBanque;
	}

	public void setNomBanque(String nomBanque) {
		this.nomBanque = nomBanque;
	}

	@Column(name = "compte", length = 20)
	@LgrHibernateValidated(champBd = "compte",table = "ta_compte_banque",champEntite="compte",clazz = TaCompteBanque.class)
	public String getCompte() {
		return this.compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	@Column(name = "code_banque", length = 20)
	@LgrHibernateValidated(champBd = "code_banque",table = "ta_compte_banque",champEntite="codeBanque",clazz = TaCompteBanque.class)
	public String getCodeBanque() {
		return this.codeBanque;
	}

	public void setCodeBanque(String codeBanque) {
		this.codeBanque = codeBanque;
	}

	@Column(name = "code_guichet", length = 20)
	@LgrHibernateValidated(champBd = "code_guichet",table = "ta_compte_banque",champEntite="codeGuichet",clazz = TaCompteBanque.class)
	public String getCodeGuichet() {
		return this.codeGuichet;
	}

	public void setCodeGuichet(String codeGuichet) {
		this.codeGuichet = codeGuichet;
	}

	@Column(name = "cle_rib", length = 2)
	@LgrHibernateValidated(champBd = "cle_rib",table = "ta_compte_banque",champEntite="cleRib",clazz = TaCompteBanque.class)
	public String getCleRib() {
		return this.cleRib;
	}

	public void setCleRib(String cleRib) {
		this.cleRib = cleRib;
	}

	@Column(name = "adresse1_banque", length = 100)
	@LgrHibernateValidated(champBd = "adresse1_banque",table = "ta_compte_banque",champEntite="adresse1Banque",clazz = TaCompteBanque.class)
	public String getAdresse1Banque() {
		return this.adresse1Banque;
	}

	public void setAdresse1Banque(String adresse1Banque) {
		this.adresse1Banque = adresse1Banque;
	}

	@Column(name = "adresse2_banque", length = 100)
	@LgrHibernateValidated(champBd = "adresse2_banque",table = "ta_compte_banque",champEntite="adresse2Banque",clazz = TaCompteBanque.class)
	public String getAdresse2Banque() {
		return this.adresse2Banque;
	}

	public void setAdresse2Banque(String adresse2Banque) {
		this.adresse2Banque = adresse2Banque;
	}

	@Column(name = "cp_banque", length = 25)
	@LgrHibernateValidated(champBd = "cp_banque",table = "ta_compte_banque",champEntite="cpBanque",clazz = TaCompteBanque.class)
	public String getCpBanque() {
		return this.cpBanque;
	}

	public void setCpBanque(String cpBanque) {
		this.cpBanque = cpBanque;
	}

	@Column(name = "ville_banque", length = 100)
	@LgrHibernateValidated(champBd = "ville_banque",table = "ta_compte_banque",champEntite="villeBanque",clazz = TaCompteBanque.class)
	public String getVilleBanque() {
		return this.villeBanque;
	}

	public void setVilleBanque(String villeBanque) {
		this.villeBanque = villeBanque;
	}

	@Column(name = "iban", length = 100)
	@LgrHibernateValidated(champBd = "iban",table = "ta_compte_banque",champEntite="iban",clazz = TaCompteBanque.class)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@Column(name = "code_b_i_c", length = 20)
	@LgrHibernateValidated(champBd = "code_b_i_c",table = "ta_compte_banque",champEntite="codeBIC",clazz = TaCompteBanque.class)
	public String getCodeBIC() {
		return this.codeBIC;
	}

	public void setCodeBIC(String codeBIC) {
		this.codeBIC = codeBIC;
	}

	@Column(name = "titulaire", length = 100)
	@LgrHibernateValidated(champBd = "titulaire",table = "ta_compte_banque",champEntite="titulaire",clazz = TaCompteBanque.class)
	public String getTitulaire() {
		return this.titulaire;
	}

	public void setTitulaire(String titulaire) {
		this.titulaire = titulaire;
	}

//	@Column(name = "qui_cree", length = 50)
//	public String getQuiCree() {
//		return this.quiCree;
//	}
//
//	public void setQuiCree(String quiCreeCompteBanque) {
//		this.quiCree = quiCreeCompteBanque;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_cree", length = 19)
//	public Date getQuandCree() {
//		return this.quandCree;
//	}
//
//	public void setQuandCree(Date quandCreeCompteBanque) {
//		this.quandCree = quandCreeCompteBanque;
//	}
//
//	@Column(name = "qui_modif", length = 50)
//	public String getQuiModif() {
//		return this.quiModif;
//	}
//
//	public void setQuiModif(String quiModifCompteBanque) {
//		this.quiModif = quiModifCompteBanque;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_modif", length = 19)
//	public Date getQuandModif() {
//		return this.quandModif;
//	}
//
//	public void setQuandModif(Date quandModifCompteBanque) {
//		this.quandModif = quandModifCompteBanque;
//	}
//
//	@Column(name = "ip_acces", length = 50)
//	public String getIpAcces() {
//		return this.ipAcces;
//	}
//
//	public void setIpAcces(String ipAcces) {
//		this.ipAcces = ipAcces;
//	}

	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	@Column(name = "cptcomptable", length = 100)
	@LgrHibernateValidated(champBd = "cptcomptable",table = "ta_compte_banque",champEntite="cptcomptable",clazz = TaCompteBanque.class)
	public String getCptcomptable() {
		return cptcomptable;
	}

	public void setCptcomptable(String cptcomptable) {
		this.cptcomptable = cptcomptable;
	}
	
	@Column(name = "nom_compte", length = 50)
	@LgrHibernateValidated(champBd = "nom_compte",table = "ta_compte_banque",champEntite="nomCompte",clazz = TaCompteBanque.class)
	public String getNomCompte() {
		return nomCompte;
	}

	public void setNomCompte(String nomCompte) {
		this.nomCompte = nomCompte;
	}
	
	/**************************************************************************************/
	@Column(name = "bank_code")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "branch_code")
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "last4")
	public String getLast4() {
		return last4;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL} , orphanRemoval=true )
	@JoinColumn(name = "id_mandat_prelevement")
	public TaMandatPrelevement getTaMandatPrelevement() {
		return taMandatPrelevement;
	}

	public void setTaMandatPrelevement(TaMandatPrelevement taMandatPrelevement) {
		this.taMandatPrelevement = taMandatPrelevement;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		TaCompteBanque objet = new TaCompteBanque();
		try {
			objet.setAdresse1Banque(adresse1Banque);
			objet.setAdresse2Banque(adresse2Banque);
			objet.setCleRib(cleRib);
			objet.setCodeBanque(codeBanque);
			objet.setCodeBIC(codeBIC);
			objet.setCodeGuichet(codeGuichet);
			objet.setCompte(compte);
			objet.setCptcomptable(cptcomptable);
			objet.setIban(iban);
			objet.setNomBanque(nomBanque);
			objet.setNomCompte(nomCompte);
			objet.setTaTBanque(taTBanque);
			objet.setTitulaire(titulaire);
			objet.setVilleBanque(villeBanque);

		} catch (Exception e) {
			logger.error("", e);
		}
		// on renvoie le clone
		return objet;
	}
	
	
	
	
}