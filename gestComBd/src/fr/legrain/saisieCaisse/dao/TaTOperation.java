package fr.legrain.saisieCaisse.dao;

// Generated 2 juin 2009 14:13:00 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaTOperation generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_t_operation", uniqueConstraints = @UniqueConstraint(columnNames = "code_t_operation"))
public class TaTOperation implements java.io.Serializable {

	private int idTOperation;
	private String version;
	private String codeTOperation;
	private String liblTOperation;
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;
	private Set<TaOperation> taOperations = new HashSet<TaOperation>(0);

	public TaTOperation() {
	}

	public TaTOperation(int idTOperation) {
		this.idTOperation = idTOperation;
	}

	public TaTOperation(int idTOperation,String version, String codeTOperation,
			String liblTOperation, String quiCreeTOperation,
			Date quandCreeTOperation, String quiModifTOperation,
			Date quandModifTOperation, String ipAcces, Integer versionObj,
			Set<TaOperation> taOperations) {
		this.idTOperation = idTOperation;
		this.version = version;
		this.codeTOperation = codeTOperation;
		this.liblTOperation = liblTOperation;
		this.quiCree = quiCreeTOperation;
		this.quandCree = quandCreeTOperation;
		this.quiModif = quiModifTOperation;
		this.quandModif = quandModifTOperation;
		this.ipAcces = ipAcces;
		this.versionObj = versionObj;
		this.taOperations = taOperations;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_t_operation", unique = true, nullable = false)
	@LgrHibernateValidated(champEntite = "", champBd = "id_t_operation",table = "ta_t_operation",clazz = TaTOperation.class)
	public int getIdTOperation() {
		return this.idTOperation;
	}

	public void setIdTOperation(int idTOperation) {
		this.idTOperation = idTOperation;
	}

	//@Version
	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "code_t_operation", unique = true, length = 20)
	@LgrHibernateValidated(champEntite = "", champBd = "code_t_operation",table = "ta_t_operation",clazz = TaTOperation.class)
	public String getCodeTOperation() {
		return this.codeTOperation;
	}

	public void setCodeTOperation(String codeTOperation) {
		this.codeTOperation = codeTOperation;
	}

	@Column(name = "libl_t_operation", length = 100)
	@LgrHibernateValidated(champEntite = "", champBd = "libl_t_operation",table = "ta_t_operation",clazz = TaTOperation.class)
	public String getLiblTOperation() {
		return this.liblTOperation;
	}

	public void setLiblTOperation(String liblTOperation) {
		this.liblTOperation = liblTOperation;
	}

	@Column(name = "qui_cree", length = 50)
	public String getQuiCree() {
		return this.quiCree;
	}

	public void setQuiCree(String quiCreeTOperation) {
		this.quiCree = quiCreeTOperation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_cree", length = 19)
	public Date getQuandCree() {
		return this.quandCree;
	}

	public void setQuandCree(Date quandCreeTOperation) {
		this.quandCree = quandCreeTOperation;
	}

	@Column(name = "qui_modif", length = 50)
	public String getQuiModif() {
		return this.quiModif;
	}

	public void setQuiModif(String quiModifTOperation) {
		this.quiModif = quiModifTOperation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "quand_modif", length = 19)
	public Date getQuandModif() {
		return this.quandModif;
	}

	public void setQuandModif(Date quandModifTOperation) {
		this.quandModif = quandModifTOperation;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "taTOperation")
	public Set<TaOperation> getTaOperations() {
		return this.taOperations;
	}

	public void setTaOperations(Set<TaOperation> taOperations) {
		this.taOperations = taOperations;
	}


	@Transient
	public boolean equalsPartiel(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaTOperation other = (TaTOperation) obj;
		if (codeTOperation == null) {
			if (other.codeTOperation != null)
				return false;
		} else if (!codeTOperation.equals(other.codeTOperation))
			return false;
		if (idTOperation != other.idTOperation)
			return false;
		if (liblTOperation == null) {
			if (other.liblTOperation != null)
				return false;
		} else if (!liblTOperation.equals(other.liblTOperation))
			return false;
		return true;
	}


}