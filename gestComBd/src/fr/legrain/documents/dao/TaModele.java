package fr.legrain.documents.dao;

// Generated Apr 7, 2009 3:27:23 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * TaModele generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TA_MODELE")
@SequenceGenerator(name = "GEN_MODELE", sequenceName = "NUM_ID_MODELE", allocationSize = 1)
public class TaModele implements java.io.Serializable {

	private int idModele;
	private String version;
	private String quiCreeModele;
	private Date quandCreeModele;
	private String quiModifModele;
	private Date quandModifModele;
	private String ipAcces;
	private Integer versionObj;
	@Transient
	private boolean legrain = false;
	public TaModele() {
	}

	public TaModele(int idModele) {
		this.idModele = idModele;
	}

	public TaModele(int idModele, String quiCreeModele, Date quandCreeModele,
			String quiModifModele, Date quandModifModele, String ipAcces,
			Integer versionObj) {
		this.idModele = idModele;
		this.quiCreeModele = quiCreeModele;
		this.quandCreeModele = quandCreeModele;
		this.quiModifModele = quiModifModele;
		this.quandModifModele = quandModifModele;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_MODELE")
	@Column(name = "ID_MODELE", unique = true, nullable = false)
	public int getIdModele() {
		return this.idModele;
	}

	public void setIdModele(int idModele) {
		this.idModele = idModele;
	}

	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "QUI_CREE_MODELE", length = 50)
	public String getQuiCreeModele() {
		return this.quiCreeModele;
	}

	public void setQuiCreeModele(String quiCreeModele) {
		this.quiCreeModele = quiCreeModele;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_CREE_MODELE", length = 19)
	public Date getQuandCreeModele() {
		return this.quandCreeModele;
	}

	public void setQuandCreeModele(Date quandCreeModele) {
		this.quandCreeModele = quandCreeModele;
	}

	@Column(name = "QUI_MODIF_MODELE", length = 50)
	public String getQuiModifModele() {
		return this.quiModifModele;
	}

	public void setQuiModifModele(String quiModifModele) {
		this.quiModifModele = quiModifModele;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QUAND_MODIF_MODELE", length = 19)
	public Date getQuandModifModele() {
		return this.quandModifModele;
	}

	public void setQuandModifModele(Date quandModifModele) {
		this.quandModifModele = quandModifModele;
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

	public void setLegrain(boolean legrain) {
		this.legrain = legrain;
	}

}