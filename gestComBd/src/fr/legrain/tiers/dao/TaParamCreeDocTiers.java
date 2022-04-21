package fr.legrain.tiers.dao;

// Generated Mar 24, 2009 4:44:04 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import fr.legrain.documents.dao.TaTDoc;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaNote generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_PARAM_CREE_DOC_TIERS")
@SequenceGenerator(name = "GEN_PARAM_CREE_DOC_TIERS", sequenceName = "NUM_ID_CREE_DOC_TIERS", allocationSize = 1)
public class TaParamCreeDocTiers implements java.io.Serializable {

	private int idParamCreeDocTiers;
	private String version;
	private TaTiers taTiers;
	private Integer tiers=0;
	private Integer document=0;
	private Integer semaine=0;
	private Integer deuxSemaines=0;
	private Integer mois=0;
	private Integer decade=0;


	private Integer xJours=0;
	private Integer nbJours=1;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;
	private String codeParam;
	
	private Set<TaTDoc> taTDoc = new HashSet<TaTDoc>(0);
	
	
	@Transient
	private Boolean appliquerATous=false; 

	public TaParamCreeDocTiers() {
	}

	public TaParamCreeDocTiers(int idParamCreeDocTiers) {
		this.idParamCreeDocTiers = idParamCreeDocTiers;
	}


//String typeDoc,
	public TaParamCreeDocTiers(int idParamCreeDocTiers, String version, TaTiers taTiers,
			 Integer tiers, Integer document, Integer semaine,
			Integer deuxSemaines, Integer mois, Integer decade, Integer xJours,
			Integer nbJours, String quiCree, Date quandCree, String quiModif,
			Date quandModif, String ipAcces, Integer versionObj) {
		super();
		this.idParamCreeDocTiers = idParamCreeDocTiers;
		this.version = version;
		this.taTiers = taTiers;
//		this.typeDoc = typeDoc;
		this.tiers = tiers;
		this.document = document;
		this.semaine = semaine;
		this.deuxSemaines = deuxSemaines;
		this.mois = mois;
		this.decade = decade;
		this.xJours = xJours;
		this.nbJours = nbJours;
		this.quiCree = quiCree;
		this.quandCree = quandCree;
		this.quiModif = quiModif;
		this.quandModif = quandModif;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_PARAM_CREE_DOC_TIERS")
	@Column(name = "ID_PARAM_CREE_DOC_TIERS", unique = true, nullable = false)
	@LgrHibernateValidated(champEntite = "", champBd = "ID_PARAM_CREE_DOC_TIERS",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public int getIdParamCreeDocTiers() {
		return this.idParamCreeDocTiers;
	}

	public void setIdParamCreeDocTiers(int idParamCreeDocTiers) {
		this.idParamCreeDocTiers = idParamCreeDocTiers;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ID_TIERS")
	@LgrHibernateValidated(champEntite = "", champBd = "ID_TIERS",table = "TA_TIERS",clazz = TaTiers.class)
	public TaTiers getTaTiers() {
		return this.taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
	}

//	@Column(name = "TYPE_DOC")
//	@LgrHibernateValidated(champEntite = "", champ = "TYPE_DOC",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
//	public String getTypeDoc() {
//		return this.typeDoc;
//	}
//
//	public void setTypeDoc(String typeDoc) {
//		this.typeDoc = typeDoc;
//	}
	
	@Column(name = "TIERS")
	@LgrHibernateValidated(champEntite = "", champBd = "TIERS",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getTiers() {
		return tiers;
	}

	public void setTiers(Integer tiers) {
		this.tiers = tiers;
	}

	@Column(name = "DOCUMENT")
	@LgrHibernateValidated(champEntite = "", champBd = "DOCUMENT",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getDocument() {
		return document;
	}

	public void setDocument(Integer document) {
		this.document = document;
	}

	@Column(name = "SEMAINE")
	@LgrHibernateValidated(champEntite = "", champBd = "SEMAINE",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getSemaine() {
		return semaine;
	}

	public void setSemaine(Integer semaine) {
		this.semaine = semaine;
	}

	@Column(name = "DEUX_SEMAINES")
	@LgrHibernateValidated(champEntite = "", champBd = "DEUX_SEMAINES",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getDeuxSemaines() {
		return deuxSemaines;
	}

	public void setDeuxSemaines(Integer deuxSemaines) {
		this.deuxSemaines = deuxSemaines;
	}

	@Column(name = "MOIS")
	@LgrHibernateValidated(champEntite = "", champBd = "MOIS",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getMois() {
		return mois;
	}

	public void setMois(Integer mois) {
		this.mois = mois;
	}

	@Column(name = "DECADE")
	@LgrHibernateValidated(champEntite = "", champBd = "DECADE",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getDecade() {
		return decade;
	}

	public void setDecade(Integer decade) {
		this.decade = decade;
	}
	
	@Column(name = "X_JOURS")
	@LgrHibernateValidated(champEntite = "", champBd = "X_JOURS",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getxJours() {
		return xJours;
	}

	public void setxJours(Integer xJours) {
		this.xJours = xJours;
	}

	@Column(name = "NB_JOURS")
	@LgrHibernateValidated(champEntite = "", champBd = "NB_JOURS",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public Integer getNbJours() {
		return nbJours;
	}

	public void setNbJours(Integer nbJours) {
		this.nbJours = nbJours;
	}
	
	@Column(name = "CODE_PARAM")
	@LgrHibernateValidated(champEntite = "", champBd = "CODE_PARAM",table = "TA_PARAM_CREE_DOC_TIERS",clazz = TaParamCreeDocTiers.class)
	public String getCodeParam() {
		return codeParam;
	}

	public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}
	

	@ManyToMany(/*cascade = CascadeType.ALL,*/ fetch = FetchType.LAZY)
	@JoinTable(name = "TA_R_PARAM_CREAT_DOC",
			joinColumns = {@JoinColumn(name = "ID_PARAM_CREE_DOC_TIERS")},inverseJoinColumns = {@JoinColumn(name = "ID_T_DOC")})
	public Set<TaTDoc> getTaTDoc(){
		return this.taTDoc;
	}

	public void setTaTDoc(Set<TaTDoc> taTDoc) {
		this.taTDoc = taTDoc;
	}

	
	@Column(name = "QUI_CREE", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCree) {
		this.quiCree = quiCree;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCree) {
		this.quandCree = quandCree;
	}

	@Column(name = "QUI_MODIF", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModif) {
		this.quiModif = quiModif;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModif) {
		this.quandModif = quandModif;
	}

	@Column(name = "IP_ACCES", length = 50)
	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String ipAcces) {
		this.ipAcces = ipAcces;
	}

	@Version
	@Column(name = "VERSION_OBJ")
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	@Transient
	public Boolean getAppliquerATous() {
		return appliquerATous;
	}
	
	@Transient
	public void setAppliquerATous(Boolean appliquerATous) {
		this.appliquerATous = appliquerATous;
	}





}