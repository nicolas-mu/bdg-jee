package fr.legrain.tiers.model;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import fr.legrain.article.model.TaPrix;
import fr.legrain.document.dto.IRelationDoc;
import fr.legrain.document.model.TaFacture;
import fr.legrain.general.model.TaObjetLgr;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaRDocument generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)

@Table(name = "ta_r_prix_tiers")

public class TaRPrixTiers extends TaObjetLgr   implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2656590961817826180L;
	
	
	private int id;
//	private String version;
	private TaTTarif taTTarif;
	private TaTiers taTiers;
	private TaPrix taPrix;
	
	private BigDecimal coef  = new BigDecimal(0);
//	private String quiCree;
//	private Date quandCree;
//	private String quiModif;
//	private Date quandModif;
//	private String ipAcces;
	private Integer versionObj;

	
	
	
	public TaRPrixTiers() {
	}

	public TaRPrixTiers(int id) {
		this.id = id;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id",table = "ta_r_prix", champEntite="id", clazz = TaRPrixTiers.class)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	@Column(name = "version", length = 20)
//	public String getVersion() {
//		return this.version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}



	
	
	@Column(name = "coef", precision = 15)
	@LgrHibernateValidated(champBd = "coef",table = "ta_r_prix", champEntite="coef", clazz = TaRPrixTiers.class)
	public BigDecimal getCoef() {
		return coef ;
	}

	public void setCoef(BigDecimal coef) {
		this.coef = coef;
	}


//	@Column(name = "qui_cree", length = 50)
//	public String getQuiCree() {
//		return this.quiCree;
//	}
//
//	public void setQuiCree(String quiCreeRDocument) {
//		this.quiCree = quiCreeRDocument;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_cree", length = 19)
//	public Date getQuandCree() {
//		return this.quandCree;
//	}
//
//	public void setQuandCree(Date quandCreeRDocument) {
//		this.quandCree = quandCreeRDocument;
//	}
//
//	@Column(name = "qui_modif", length = 50)
//	public String getQuiModif() {
//		return this.quiModif;
//	}
//
//	public void setQuiModif(String quiModifRDocument) {
//		this.quiModif = quiModifRDocument;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_modif", length = 19)
//	public Date getQuandModif() {
//		return this.quandModif;
//	}
//
//	public void setQuandModif(Date quandModifRDocument) {
//		this.quandModif = quandModifRDocument;
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

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_t_tarif")
	public TaTTarif getTaTTarif() {
		return taTTarif;
	}

	public void setTaTTarif(TaTTarif tTarif) {
		taTTarif = tTarif;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tiers")
	public TaTiers getTaTiers() {
		return taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prix")
	@XmlElement
	@XmlInverseReference(mappedBy="taRPrixesTiers")
	public TaPrix getTaPrix() {
		return taPrix;
	}

	public void setTaPrix(TaPrix taPrix) {
		this.taPrix = taPrix;
	}
	
	
}