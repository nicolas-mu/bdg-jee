package fr.legrain.pointLgr.dto;

import java.math.BigDecimal;
import java.util.Date;

import fr.legrain.bdg.model.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTAdresse generated by hbm2java
 */
public class ComptePointDTO extends ModelObject  implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1145248992837593776L;
	
	private Integer id;
	private BigDecimal point;
	private Date dateAcquisition;
	private Date datePeremption;
	private Date dateUtilisation;
	private Integer idTiers;
	private String codeTiers;
	private String nomTiers;
	private String codeDocument;
	private String typeMouv;
	private Integer idTypeMouv;
	private String libelleMouv;
	private String commentaire;
	private String libelleEtat;
	private Integer versionObj;



	public ComptePointDTO() {
	}

	public void setSWTAdresse(ComptePointDTO swtAdresse) {
		this.id = swtAdresse.id;

	}

	
	public static ComptePointDTO copy(ComptePointDTO swtAdresse){
		ComptePointDTO swtAdresseLoc = new ComptePointDTO();
		swtAdresseLoc.setId(swtAdresse.getId());                //1
		swtAdresseLoc.setIdTiers(swtAdresse.getIdTiers()); 
		swtAdresseLoc.setCodeDocument(swtAdresse.getCodeDocument()); 
		swtAdresseLoc.setCodeTiers(swtAdresse.getCodeTiers()); 
		swtAdresseLoc.setCommentaire(swtAdresse.getCommentaire()); 
		swtAdresseLoc.setDatePeremption(swtAdresse.getDatePeremption()); 
		swtAdresseLoc.setDateAcquisition(swtAdresse.getDateAcquisition()); 
		swtAdresseLoc.setDateUtilisation(swtAdresse.getDateUtilisation()); 
		swtAdresseLoc.setPoint(swtAdresse.getPoint()); 
		swtAdresseLoc.setTypeMouv(swtAdresse.getTypeMouv()); 
		swtAdresseLoc.setIdTypeMouv(swtAdresse.getIdTypeMouv()); 
		swtAdresseLoc.setLibelleMouv(swtAdresse.getLibelleMouv());
		swtAdresseLoc.setLibelleEtat(swtAdresse.getLibelleEtat());
		return swtAdresseLoc;
	}
	
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public BigDecimal getPoint() {
		return point;
	}

	public void setPoint(BigDecimal point) {
		firePropertyChange("point", this.point, this.point = point);
	}

	public Date getDatePeremption() {
		return datePeremption;
	}

	public void setDatePeremption(Date datePeremption) {
		firePropertyChange("datePeremption", this.datePeremption, this.datePeremption = datePeremption);
	}

	public Integer getIdTiers() {
		return idTiers;
	}

	public void setIdTiers(Integer idTiers) {
		firePropertyChange("idTiers", this.idTiers, this.idTiers = idTiers);
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		firePropertyChange("codeTiers", this.codeTiers, this.codeTiers = codeTiers);
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		firePropertyChange("codeDocument", this.codeDocument, this.codeDocument = codeDocument);
	}

	public String getTypeMouv() {
		return typeMouv;
	}

	public void setTypeMouv(String typeMouv) {
		firePropertyChange("typeMouv", this.typeMouv, this.typeMouv = typeMouv);
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		firePropertyChange("commentaire", this.commentaire, this.commentaire = commentaire);
	}


	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		firePropertyChange("nomTiers", this.nomTiers, this.nomTiers = nomTiers);
	}

	public Integer getIdTypeMouv() {
		return idTypeMouv;
	}

	public void setIdTypeMouv(Integer idTypeMouv) {
		firePropertyChange("idTypeMouv", this.idTypeMouv, this.idTypeMouv = idTypeMouv);
	}

	public String getLibelleMouv() {
		return libelleMouv;
	}

	public void setLibelleMouv(String libelleMouv) {
		firePropertyChange("libelleMouv", this.libelleMouv, this.libelleMouv = libelleMouv);
	}


	public String getLibelleEtat() {
		return libelleEtat;
	}

	public void setLibelleEtat(String libelleEtat) {
		firePropertyChange("libelleEtat", this.libelleEtat, this.libelleEtat = libelleEtat);
	}
	

	public Date getDateAcquisition() {
		return dateAcquisition;
	}

	public void setDateAcquisition(Date dateAcquisition) {
		firePropertyChange("dateAcquisition", this.dateAcquisition, this.dateAcquisition = dateAcquisition);
	}

	public Date getDateUtilisation() {
		return dateUtilisation;
	}

	public void setDateUtilisation(Date dateUtilisation) {
		firePropertyChange("dateUtilisation", this.dateUtilisation, this.dateUtilisation = dateUtilisation);
	}

	
	public Integer getVersionObj() {
		return versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeDocument == null) ? 0 : codeDocument.hashCode());
		result = prime * result
				+ ((codeTiers == null) ? 0 : codeTiers.hashCode());
		result = prime * result
				+ ((commentaire == null) ? 0 : commentaire.hashCode());
		result = prime * result
				+ ((dateAcquisition == null) ? 0 : dateAcquisition.hashCode());
		result = prime * result
				+ ((datePeremption == null) ? 0 : datePeremption.hashCode());
		result = prime * result
				+ ((dateUtilisation == null) ? 0 : dateUtilisation.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idTiers == null) ? 0 : idTiers.hashCode());
		result = prime * result
				+ ((idTypeMouv == null) ? 0 : idTypeMouv.hashCode());
		result = prime * result
				+ ((libelleEtat == null) ? 0 : libelleEtat.hashCode());
		result = prime * result
				+ ((libelleMouv == null) ? 0 : libelleMouv.hashCode());
		result = prime * result
				+ ((nomTiers == null) ? 0 : nomTiers.hashCode());
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result
				+ ((typeMouv == null) ? 0 : typeMouv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComptePointDTO other = (ComptePointDTO) obj;
		if (codeDocument == null) {
			if (other.codeDocument != null)
				return false;
		} else if (!codeDocument.equals(other.codeDocument))
			return false;
		if (codeTiers == null) {
			if (other.codeTiers != null)
				return false;
		} else if (!codeTiers.equals(other.codeTiers))
			return false;
		if (commentaire == null) {
			if (other.commentaire != null)
				return false;
		} else if (!commentaire.equals(other.commentaire))
			return false;
		if (dateAcquisition == null) {
			if (other.dateAcquisition != null)
				return false;
		} else if (!dateAcquisition.equals(other.dateAcquisition))
			return false;
		if (datePeremption == null) {
			if (other.datePeremption != null)
				return false;
		} else if (!datePeremption.equals(other.datePeremption))
			return false;
		if (dateUtilisation == null) {
			if (other.dateUtilisation != null)
				return false;
		} else if (!dateUtilisation.equals(other.dateUtilisation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idTiers == null) {
			if (other.idTiers != null)
				return false;
		} else if (!idTiers.equals(other.idTiers))
			return false;
		if (idTypeMouv == null) {
			if (other.idTypeMouv != null)
				return false;
		} else if (!idTypeMouv.equals(other.idTypeMouv))
			return false;
		if (libelleEtat == null) {
			if (other.libelleEtat != null)
				return false;
		} else if (!libelleEtat.equals(other.libelleEtat))
			return false;
		if (libelleMouv == null) {
			if (other.libelleMouv != null)
				return false;
		} else if (!libelleMouv.equals(other.libelleMouv))
			return false;
		if (nomTiers == null) {
			if (other.nomTiers != null)
				return false;
		} else if (!nomTiers.equals(other.nomTiers))
			return false;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		if (typeMouv == null) {
			if (other.typeMouv != null)
				return false;
		} else if (!typeMouv.equals(other.typeMouv))
			return false;
		return true;
	}

	public ComptePointDTO(Integer idPoint, BigDecimal point,
			Date dateAcquisition, Date datePeremption, Date dateUtilisation,
			Integer idTiers, String codeTiers, String nomTiers,
			String codeDocument, String typeMouv, Integer idTypeMouv,
			String libelleMouv, String commentaire, String libelleEtat) {
		super();
		this.id = idPoint;
		this.point = point;
		this.dateAcquisition = dateAcquisition;
		this.datePeremption = datePeremption;
		this.dateUtilisation = dateUtilisation;
		this.idTiers = idTiers;
		this.codeTiers = codeTiers;
		this.nomTiers = nomTiers;
		this.codeDocument = codeDocument;
		this.typeMouv = typeMouv;
		this.idTypeMouv = idTypeMouv;
		this.libelleMouv = libelleMouv;
		this.commentaire = commentaire;
		this.libelleEtat = libelleEtat;
	}




	
}