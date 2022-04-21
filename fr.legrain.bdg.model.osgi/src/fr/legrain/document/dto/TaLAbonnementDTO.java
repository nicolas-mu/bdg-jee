package fr.legrain.document.dto;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import fr.legrain.bdg.model.ModelObject;
import fr.legrain.document.model.TaAbonnement;
import fr.legrain.validator.LgrHibernateValidated;


/**
 * SWTLFacture generated by hbm2java
 */
public class TaLAbonnementDTO extends ModelObject implements PropertyChangeListener, java.io.Serializable, ILigneDocumentDTO{

	private static final long serialVersionUID = 5898159760972747166L;
	
	private Integer idLDocument;
	private Integer idDocument;
	private String codeTLigne;
	private Integer idArticle;
	private Integer numLigneLDocument;
	private String codeArticle;
	private String libLDocument;
	private BigDecimal qteLDocument;
	private BigDecimal qte2LDocument;
	private BigDecimal prixULDocument;
	private BigDecimal tauxTvaLDocument;
	private String codeTvaLDocument;
	private String codeTTvaLDocument;
	private BigDecimal mtHtLDocument;
	private BigDecimal remTxLDocument;
	private BigDecimal mtTtcLDocument;
	private BigDecimal remHtLDocument;
	private BigDecimal mtHtLApresRemiseGlobaleDocument;
	private BigDecimal mtTtcLApresRemiseGlobaleDocument;
	private String ipAcces;
	private String u1LDocument;
	private String u2LDocument;
	private BigDecimal txRemHtDocument;
	
	//champs venant de taStripSubscriptionItemDTO
	private String idExterne;
	private int idStripePlan;
	
	private String nickname;
	
	
	private String complement1;
	private String complement2;
	private String complement3;
	
	private String codeDocument;
	
	private String codeTiers;
	private String nomTiers;
	private String prenomTiers;
	
	private String codeEtat;
	private String liblEtat;
	
	private Date dateDebutAbonnement;
	
	private BigDecimal coefMultiplicateur;
	
	private BigDecimal commissionBancaire;
	private BigDecimal commissionPanier;
	private String nomDossier;
	
	private Integer versionObj;
	
	private BigDecimal qteSaisieLDocument;
	private String uSaisieLDocument;
	
	private BigDecimal qteTitreTransport;

	public TaLAbonnementDTO() {
	}

	public TaLAbonnementDTO(Integer ID_L_ABO, Integer ID_ABO,
			String ID_T_LIGNE, Integer ID_ARTICLE,
			Integer NUM_LIGNE_L_ABO, String CODE_ARTICLE,
			String LIB_L_ABO, BigDecimal QTE_L_ABO,
			String U1_L_ABO, String U2_L_ABO,
			BigDecimal PRIX_U_L_ABO, BigDecimal TAUX_TVA_L_ABO,
			String CODE_TVA_L_ABO, String CODE_T_TVA_L_ABO,
			BigDecimal MT_HT_L_ABO, BigDecimal MT_TTC_L_ABO,
			BigDecimal REM_TX_L_ABO, BigDecimal REM_HT_L_ABO,
			String IP_ACCES) {
		this.idLDocument = ID_L_ABO;
		this.idDocument = ID_ABO;
		this.codeTLigne = ID_T_LIGNE;
		this.idArticle = ID_ARTICLE;
		this.numLigneLDocument = NUM_LIGNE_L_ABO;
		this.codeArticle = CODE_ARTICLE;
		this.libLDocument = LIB_L_ABO;
		this.qteLDocument = QTE_L_ABO;
		this.u1LDocument = U1_L_ABO;
		this.u2LDocument = U2_L_ABO;
		this.prixULDocument = PRIX_U_L_ABO;
		this.tauxTvaLDocument = TAUX_TVA_L_ABO;
		this.codeTvaLDocument = CODE_TVA_L_ABO;
		this.codeTTvaLDocument = CODE_T_TVA_L_ABO;
		this.mtHtLDocument = MT_HT_L_ABO;
		this.mtTtcLDocument = MT_TTC_L_ABO;
		this.remTxLDocument = REM_TX_L_ABO;
		this.remHtLDocument = REM_HT_L_ABO;
		this.ipAcces = IP_ACCES;
	}
	/**Constructeur pour la méthode selectAllDTOAvecEtat (yann)**/
	public TaLAbonnementDTO(Integer ID_L_ABO, String codeDocument,
			String CODE_ARTICLE,
			String LIB_L_ABO, BigDecimal QTE_L_ABO,
			String U1_L_ABO, Date dateDebut, String codeTiers, String nomTiers, String prenomTiers,
			BigDecimal MT_HT_L_ABO, BigDecimal MT_TTC_L_ABO,
			String complement1,String complement2,String complement3,
			BigDecimal commissionBancaire, BigDecimal commissionPanier, String nomDossier,String codeTLigne, String codeEtat, String liblEtat) {
		
		this.idLDocument = ID_L_ABO;
		this.codeDocument= codeDocument;
		this.codeArticle = CODE_ARTICLE;
		this.libLDocument = LIB_L_ABO;
		this.qteLDocument = QTE_L_ABO;
		this.u1LDocument = U1_L_ABO;
		this.dateDebutAbonnement= dateDebut;
		this.codeTiers= codeTiers;
		this.nomTiers= nomTiers;
		this.prenomTiers= prenomTiers;
		this.mtHtLDocument = MT_HT_L_ABO;
		this.mtTtcLDocument = MT_TTC_L_ABO;
		this.complement1= complement1;
		this.complement2= complement2;
		this.complement3= complement3;
		this.commissionBancaire = commissionBancaire;
		this.commissionPanier = commissionPanier;
		this.nomDossier= nomDossier;
		this.codeTLigne= codeTLigne;
		this.codeEtat = codeEtat;
		this.liblEtat=liblEtat;
	}
	
	public void setIHMLABO(TaLAbonnementDTO ihmLABO) {
		setIdLDocument(ihmLABO.idLDocument);
		setIdDocument(ihmLABO.idDocument);
		setCodeTLigne(ihmLABO.codeTLigne);
		setIdArticle(ihmLABO.idArticle);
		setNumLigneLDocument(ihmLABO.numLigneLDocument);
		setCodeArticle(ihmLABO.codeArticle);
		setLibLDocument(ihmLABO.libLDocument);
		setQteLDocument(ihmLABO.qteLDocument);
		setU1LDocument(ihmLABO.u1LDocument);
		setU2LDocument(ihmLABO.u2LDocument);
		setPrixULDocument(ihmLABO.prixULDocument);
		setTauxTvaLDocument(ihmLABO.tauxTvaLDocument);
		setCodeTvaLDocument(ihmLABO.codeTvaLDocument);
		setCodeTTvaLDocument(ihmLABO.codeTTvaLDocument);
		setMtHtLDocument(ihmLABO.mtHtLDocument);
		setMtTtcLDocument(ihmLABO.mtTtcLDocument);
		setRemTxLDocument(ihmLABO.remTxLDocument);
		setRemHtLDocument(ihmLABO.remHtLDocument);
		setIpAcces(ihmLABO.ipAcces);
	}
	
	static public TaLAbonnementDTO copy(TaLAbonnementDTO ihmLABO) {
		TaLAbonnementDTO swtLABOLoc = new TaLAbonnementDTO();
		swtLABOLoc.setIdLDocument(ihmLABO.idLDocument);
		swtLABOLoc.setIdDocument(ihmLABO.idDocument);
		swtLABOLoc.setCodeTLigne(ihmLABO.codeTLigne);
		swtLABOLoc.setIdArticle(ihmLABO.idArticle);
		swtLABOLoc.setNumLigneLDocument(ihmLABO.numLigneLDocument);
		swtLABOLoc.setCodeArticle(ihmLABO.codeArticle);
		swtLABOLoc.setLibLDocument(ihmLABO.libLDocument);
		swtLABOLoc.setQteLDocument(ihmLABO.qteLDocument);
		swtLABOLoc.setU1LDocument(ihmLABO.u1LDocument);
		swtLABOLoc.setU2LDocument(ihmLABO.u2LDocument);
		swtLABOLoc.setPrixULDocument(ihmLABO.prixULDocument);
		swtLABOLoc.setTauxTvaLDocument(ihmLABO.tauxTvaLDocument);
		swtLABOLoc.setCodeTvaLDocument(ihmLABO.codeTvaLDocument);
		swtLABOLoc.setCodeTTvaLDocument(ihmLABO.codeTTvaLDocument);
		swtLABOLoc.setMtHtLDocument(ihmLABO.mtHtLDocument);
		swtLABOLoc.setMtTtcLDocument(ihmLABO.mtTtcLDocument);
		swtLABOLoc.setRemTxLDocument(ihmLABO.remTxLDocument);
		swtLABOLoc.setRemHtLDocument(ihmLABO.remHtLDocument);
		swtLABOLoc.setIpAcces(ihmLABO.ipAcces);
		swtLABOLoc.setVersionObj(ihmLABO.versionObj);
		return swtLABOLoc;
	}

	/* (non-Javadoc)
	 * @see fr.legrain.document.dto.ILigneDocumentDTO#getIdLDocument()
	 */
	@Override
	public Integer getIdLDocument() {
		return this.idLDocument;
	}

	public void setIdLDocument(Integer ID_L_ABO) {
		firePropertyChange("idLDocument", this.idLDocument, this.idLDocument = ID_L_ABO);
	}

	/* (non-Javadoc)
	 * @see fr.legrain.document.dto.ILigneDocumentDTO#getIdDocument()
	 */
	@Override
	public Integer getIdDocument() {
		return this.idDocument;
	}

	public void setIdDocument(Integer ID_ABO) {
		firePropertyChange("idDocument", this.idDocument, this.idDocument = ID_ABO);
	}

	/* (non-Javadoc)
	 * @see fr.legrain.document.dto.ILigneDocumentDTO#getCodeTLigne()
	 */
	@Override
	public String getCodeTLigne() {
		return this.codeTLigne;
	}

	public void setCodeTLigne(String codeTLigne) {
		firePropertyChange("codeTLigne", this.codeTLigne, this.codeTLigne = codeTLigne);
	}


	public Integer getIdArticle() {
		return this.idArticle;
	}

	public void setIdArticle(Integer ID_ARTICLE) {
		firePropertyChange("ID_ARTICLE", this.idArticle, this.idArticle = ID_ARTICLE);
	}

	public Integer getNumLigneLDocument() {
		return this.numLigneLDocument;
	}

	public void setNumLigneLDocument(Integer NUM_LIGNE_L_ABO) {
		firePropertyChange("numLigneLDocument", this.numLigneLDocument, this.numLigneLDocument = NUM_LIGNE_L_ABO);
	}

	/* (non-Javadoc)
	 * @see fr.legrain.document.dto.ILigneDocumentDTO#getCodeArticle()
	 */
	@Override
	@Size(min=0, max=20)
	@LgrHibernateValidated(champBd = "code_article",table = "ta_article",champEntite="codeArticle",clazz = TaLAbonnementDTO.class)
	public String getCodeArticle() {
		return this.codeArticle;
	}

	public void setCodeArticle(String CODE_ARTICLE) {
		firePropertyChange("codeArticle", this.codeArticle, this.codeArticle = CODE_ARTICLE);
	}

	@LgrHibernateValidated(champBd = "lib_l_document",table = "ta_l_bon_reception",champEntite="libLDocument",clazz = TaLAbonnementDTO.class)
	public String getLibLDocument() {
		return this.libLDocument;
	}

	public void setLibLDocument(String LIB_L_ABO) {
		firePropertyChange("libLDocument", this.libLDocument, this.libLDocument = LIB_L_ABO);
	}

	@LgrHibernateValidated(champBd = "qte_l_document",table = "ta_l_devis",champEntite="qteLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getQteLDocument() {
		return this.qteLDocument;
	}

	public void setQteLDocument(BigDecimal QTE_L_ABO) {
		firePropertyChange("qteLDocument", this.qteLDocument, this.qteLDocument = QTE_L_ABO);
	}

	@LgrHibernateValidated(champBd = "u1_l_document",table = "ta_l_devis",champEntite="u1LDocument",clazz = TaLAbonnementDTO.class)
	public String getU1LDocument() {
		return this.u1LDocument;
	}

	public void setU1LDocument(String U1_L_ABO) {
		firePropertyChange("u1LDocument", this.u1LDocument, this.u1LDocument = U1_L_ABO);
	}

	@LgrHibernateValidated(champBd = "u2_l_document",table = "ta_l_devis",champEntite="u2LDocument",clazz = TaLAbonnementDTO.class)
	public String getU2LDocument() {
		return this.u2LDocument;
	}

	public void setU2LDocument(String U2_L_ABO) {
		firePropertyChange("u2LDocument", this.u2LDocument, this.u2LDocument = U2_L_ABO);
	}

	@LgrHibernateValidated(champBd = "prix_u_l_document",table = "ta_l_devis",champEntite="prixULDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getPrixULDocument() {
		return this.prixULDocument;
	}

	public void setPrixULDocument(BigDecimal PRIX_U_L_ABO) {
		firePropertyChange("prixULDocument", this.prixULDocument, this.prixULDocument = PRIX_U_L_ABO);
	}

	@LgrHibernateValidated(champBd = "taux_tva_l_document",table = "ta_l_devis",champEntite="tauxTvaLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getTauxTvaLDocument() {
		return this.tauxTvaLDocument;
	}

	public void setTauxTvaLDocument(BigDecimal TAUX_TVA_L_ABO) {
		firePropertyChange("tauxTvaLDocument", this.tauxTvaLDocument, this.tauxTvaLDocument = TAUX_TVA_L_ABO);
	}

	@Size(min=0, max=20)
	@LgrHibernateValidated(champBd = "code_tva_l_document",table = "ta_l_devis",champEntite="codeTvaLDocument",clazz = TaLAbonnementDTO.class)
	public String getCodeTvaLDocument() {
		return this.codeTvaLDocument;
	}

	public void setCodeTvaLDocument(String CODE_TVA_L_ABO) {
		firePropertyChange("codeTvaLDocument", this.codeTvaLDocument, this.codeTvaLDocument = CODE_TVA_L_ABO);
	}

	@Size(min=0, max=20)
	@LgrHibernateValidated(champBd = "code_t_tva_l_document",table = "ta_l_devis",champEntite="codeTTvaLDocument",clazz = TaLAbonnementDTO.class)
	public String getCodeTTvaLDocument() {
		return this.codeTTvaLDocument;
	}

	public void setCodeTTvaLDocument(String CODE_T_TVA_L_ABO) {
		firePropertyChange("codeTTvaLDocument", this.codeTTvaLDocument, this.codeTTvaLDocument = CODE_T_TVA_L_ABO);
	}

	@LgrHibernateValidated(champBd = "mt_ht_l_document",table = "ta_l_devis",champEntite="mtHtLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getMtHtLDocument() {
		return this.mtHtLDocument;
	}

	public void setMtHtLDocument(BigDecimal MT_HT_L_ABO) {
		firePropertyChange("mtHtLDocument", this.mtHtLDocument, this.mtHtLDocument = MT_HT_L_ABO);
	}

	@LgrHibernateValidated(champBd = "mt_ttc_l_document",table = "ta_l_devis",champEntite="mtTtcLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getMtTtcLDocument() {
		return this.mtTtcLDocument;
	}

	public void setMtTtcLDocument(BigDecimal MT_TTC_L_ABO) {
		firePropertyChange("mtTtcLDocument", this.mtTtcLDocument, this.mtTtcLDocument = MT_TTC_L_ABO);
	}

	@LgrHibernateValidated(champBd = "rem_tx_l_document",table = "ta_l_devis",champEntite="remTxLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getRemTxLDocument() {
		return this.remTxLDocument;
	}

	public void setRemTxLDocument(BigDecimal REM_TX_L_ABO) {
		firePropertyChange("remTxLDocument", this.remTxLDocument, this.remTxLDocument = REM_TX_L_ABO);
	}

	@LgrHibernateValidated(champBd = "rem_ht_l_document",table = "ta_l_devis",champEntite="remHtLDocument",clazz = TaLAbonnementDTO.class)
	public BigDecimal getRemHtLDocument() {
		return this.remHtLDocument;
	}

	public void setRemHtLDocument(BigDecimal REM_HT_L_ABO) {
		firePropertyChange("remHtLDocument", this.remHtLDocument, this.remHtLDocument = REM_HT_L_ABO);
	}

	public String getIpAcces() {
		return this.ipAcces;
	}

	public void setIpAcces(String IP_ACCES) {
		firePropertyChange("ipAcces", this.ipAcces, this.ipAcces = IP_ACCES);
	}
	
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TaLAbonnementDTO))
			return false;
		TaLAbonnementDTO castOther = (TaLAbonnementDTO) other;

		return ((this.getIdLDocument() == castOther.getIdLDocument()) || (this
				.getIdLDocument() != null
				&& castOther.getIdLDocument() != null && this
				.getIdLDocument().equals(castOther.getIdLDocument())))
				&& ((this.getIdDocument() == castOther.getIdDocument()) || (this
						.getIdDocument() != null
						&& castOther.getIdDocument() != null && this
						.getIdDocument().equals(castOther.getIdDocument())))
				&& ((this.getCodeTLigne() == castOther.getCodeTLigne()) || (this
						.getCodeTLigne() != null
						&& castOther.getCodeTLigne() != null && this
						.getCodeTLigne().equals(castOther.getCodeTLigne())))
				&& ((this.getIdArticle() == castOther.getIdArticle()) || (this
						.getIdArticle() != null
						&& castOther.getIdArticle() != null && this
						.getIdArticle().equals(castOther.getIdArticle())))
				&& ((this.getNumLigneLDocument() == castOther
						.getNumLigneLDocument()) || (this
						.getNumLigneLDocument() != null
						&& castOther.getNumLigneLDocument() != null && this
						.getNumLigneLDocument().equals(
								castOther.getNumLigneLDocument())))
				&& ((this.getCodeArticle() == castOther.getCodeArticle()) || (this
						.getCodeArticle() != null
						&& castOther.getCodeArticle() != null && this
						.getCodeArticle().equals(castOther.getCodeArticle())))
				&& ((this.getLibLDocument() == castOther.getLibLDocument()) || (this
						.getLibLDocument() != null
						&& castOther.getLibLDocument() != null && this
						.getLibLDocument()
						.equals(castOther.getLibLDocument())))
				&& ((this.getQteLDocument() == castOther.getQteLDocument()) || (this
						.getQteLDocument() != null
						&& castOther.getQteLDocument() != null && this
						.getQteLDocument()
						.equals(castOther.getQteLDocument())))
				&& ((this.getU1LDocument() == castOther.getU1LDocument()) || (this
						.getU1LDocument() != null
						&& castOther.getU1LDocument() != null && this
						.getU1LDocument().equals(castOther.getU1LDocument())))
				&& ((this.getU2LDocument() == castOther.getU2LDocument()) || (this
						.getU2LDocument() != null
						&& castOther.getU2LDocument() != null && this
						.getU2LDocument().equals(castOther.getU2LDocument())))
				&& ((this.getPrixULDocument() == castOther
						.getPrixULDocument()) || (this.getPrixULDocument() != null
						&& castOther.getPrixULDocument() != null && this
						.getPrixULDocument().equals(
								castOther.getPrixULDocument())))
				&& ((this.getTauxTvaLDocument() == castOther
						.getTauxTvaLDocument()) || (this
						.getTauxTvaLDocument() != null
						&& castOther.getTauxTvaLDocument() != null && this
						.getTauxTvaLDocument().equals(
								castOther.getTauxTvaLDocument())))
				&& ((this.getCodeTvaLDocument() == castOther
						.getCodeTvaLDocument()) || (this
						.getCodeTvaLDocument() != null
						&& castOther.getCodeTvaLDocument() != null && this
						.getCodeTvaLDocument().equals(
								castOther.getCodeTvaLDocument())))
				&& ((this.getCodeTTvaLDocument() == castOther
						.getCodeTTvaLDocument()) || (this
						.getCodeTTvaLDocument() != null
						&& castOther.getCodeTTvaLDocument() != null && this
						.getCodeTTvaLDocument().equals(
								castOther.getCodeTTvaLDocument())))
				&& ((this.getMtHtLDocument() == castOther
						.getMtHtLDocument()) || (this.getMtHtLDocument() != null
						&& castOther.getMtHtLDocument() != null && this
						.getMtHtLDocument().equals(
								castOther.getMtHtLDocument())))
				&& ((this.getMtTtcLDocument() == castOther
						.getMtTtcLDocument()) || (this.getMtTtcLDocument() != null
						&& castOther.getMtTtcLDocument() != null && this
						.getMtTtcLDocument().equals(
								castOther.getMtTtcLDocument())))
				&& ((this.getRemTxLDocument() == castOther
						.getRemTxLDocument()) || (this.getRemTxLDocument() != null
						&& castOther.getRemTxLDocument() != null && this
						.getRemTxLDocument().equals(
								castOther.getRemTxLDocument())))
				&& ((this.getRemHtLDocument() == castOther
						.getRemHtLDocument()) || (this.getRemHtLDocument() != null
						&& castOther.getRemHtLDocument() != null && this
						.getRemHtLDocument().equals(
								castOther.getRemHtLDocument())))
				&& ((this.getIpAcces() == castOther.getIpAcces()) || (this
						.getIpAcces() != null
						&& castOther.getIpAcces() != null && this
						.getIpAcces().equals(castOther.getIpAcces())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getIdLDocument() == null ? 0 : this.getIdLDocument()
						.hashCode());
		result = 37
				* result
				+ (getIdDocument() == null ? 0 : this.getIdDocument()
						.hashCode());
		result = 37
				* result
				+ (getCodeTLigne() == null ? 0 : this.getCodeTLigne()
						.hashCode());
		result = 37
				* result
				+ (getIdArticle() == null ? 0 : this.getIdArticle()
						.hashCode());
		result = 37
				* result
				+ (getNumLigneLDocument() == null ? 0 : this
						.getNumLigneLDocument().hashCode());
		result = 37
				* result
				+ (getCodeArticle() == null ? 0 : this.getCodeArticle()
						.hashCode());
		result = 37
				* result
				+ (getLibLDocument() == null ? 0 : this.getLibLDocument()
						.hashCode());
		result = 37
				* result
				+ (getQteLDocument() == null ? 0 : this.getQteLDocument()
						.hashCode());
		result = 37
				* result
				+ (getU1LDocument() == null ? 0 : this.getU1LDocument()
						.hashCode());
		result = 37
				* result
				+ (getU2LDocument() == null ? 0 : this.getU2LDocument()
						.hashCode());
		result = 37
				* result
				+ (getPrixULDocument() == null ? 0 : this
						.getPrixULDocument().hashCode());
		result = 37
				* result
				+ (getTauxTvaLDocument() == null ? 0 : this
						.getTauxTvaLDocument().hashCode());
		result = 37
				* result
				+ (getCodeTvaLDocument() == null ? 0 : this
						.getCodeTvaLDocument().hashCode());
		result = 37
				* result
				+ (getCodeTTvaLDocument() == null ? 0 : this
						.getCodeTTvaLDocument().hashCode());
		result = 37
				* result
				+ (getMtHtLDocument() == null ? 0 : this.getMtHtLDocument()
						.hashCode());
		result = 37
				* result
				+ (getMtTtcLDocument() == null ? 0 : this
						.getMtTtcLDocument().hashCode());
		result = 37
				* result
				+ (getRemTxLDocument() == null ? 0 : this
						.getRemTxLDocument().hashCode());
		result = 37
				* result
				+ (getRemHtLDocument() == null ? 0 : this
						.getRemHtLDocument().hashCode());
		result = 37 * result
				+ (getIpAcces() == null ? 0 : this.getIpAcces().hashCode());
		return result;
	}

	public void propertyChange(PropertyChangeEvent evt) {
/*		
//		*/
//		if(evt.getPropertyName().equals("ID_L_ABO")){
//			this.setIdLDevis((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("ID_T_LIGNE")){
//			this.setCodeTLigne((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("ID_ARTICLE")){
//			this.setIdArticle((Integer)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("LIB_L_ABO")){
//			this.setLibLDevis((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("QTE_L_ABO")){
//			this.setQteLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("U1_L_ABO")){
//			this.setU1LDevis((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("U2_L_ABO")){
//			this.setU2LDevis((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("PRIX_U_L_ABO")){
//			this.setPrixULDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("TAUX_TVA_L_ABO")){
//			this.setTauxTvaLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_TVA_L_ABO")){
//			this.setCodeTvaLDevis((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_T_TVA_L_ABO")){
//			this.setCodeTTvaLDevis((String)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("MT_HT_L_ABO")){
//			this.setMtHtLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("MT_TTC_L_ABO")){
//			this.setMtTtcLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("REM_TX_L_ABO")){
//			this.setRemTxLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("REM_HT_L_ABO")){
//			this.setRemHtLDevis((BigDecimal)evt.getNewValue());
//		}		
//		if(evt.getPropertyName().equals("CODE_ARTICLE")){
//			this.setCodeArticle((String)evt.getNewValue());
//		}		
	}

	public BigDecimal getTxRemHtDocument() {
		return txRemHtDocument;
	}

	public void setTxRemHtDocument(BigDecimal txRemHtDevis) {
		firePropertyChange("txRemHtDocument", this.txRemHtDocument, this.txRemHtDocument = txRemHtDevis);
	}

	@LgrHibernateValidated(champBd = "qte2_l_document",table = "ta_l_devis",champEntite="", clazz = TaLAbonnementDTO.class)
	public BigDecimal getQte2LDocument() {
		return qte2LDocument;
	}

	public void setQte2LDocument(BigDecimal qte2LDevis) {
		firePropertyChange("qte2LDocument", this.qte2LDocument, this.qte2LDocument = qte2LDevis);
	}

	@Override
	public BigDecimal getMtHtLApresRemiseGlobaleDocument() {
		return mtHtLApresRemiseGlobaleDocument;
	}

	@Override
	public void setMtHtLApresRemiseGlobaleDocument(BigDecimal mtHtLApresRemiseGlobaleDocument) {
		this.mtHtLApresRemiseGlobaleDocument = mtHtLApresRemiseGlobaleDocument;
	}

	@Override
	public BigDecimal getMtTtcLApresRemiseGlobaleDocument() {
		return mtTtcLApresRemiseGlobaleDocument;
	}

	@Override
	public void setMtTtcLApresRemiseGlobaleDocument(BigDecimal mtTtcLApresRemiseGlobaleDocument) {
		this.mtTtcLApresRemiseGlobaleDocument = mtTtcLApresRemiseGlobaleDocument;
	}

	@Override
	public String getTypeDocument() {
		// TODO Auto-generated method stub
		return TaAbonnement.TYPE_DOC;
	}

	public String getIdExterne() {
		return idExterne;
	}

	public void setIdExterne(String idExterne) {
		this.idExterne = idExterne;
	}

	public int getIdStripePlan() {
		return idStripePlan;
	}

	public void setIdStripePlan(int idStripePlan) {
		this.idStripePlan = idStripePlan;
	}

	public String getComplement1() {
		return complement1;
	}

	public void setComplement1(String complement1) {
		this.complement1 = complement1;
	}

	public String getComplement2() {
		return complement2;
	}

	public void setComplement2(String complement2) {
		this.complement2 = complement2;
	}

	public String getComplement3() {
		return complement3;
	}

	public void setComplement3(String complement3) {
		this.complement3 = complement3;
	}

	public String getCodeEtat() {
		return codeEtat;
	}

	public void setCodeEtat(String codeEtat) {
		this.codeEtat = codeEtat;
	}

	public String getLiblEtat() {
		return liblEtat;
	}

	public void setLiblEtat(String liblEtat) {
		this.liblEtat = liblEtat;
	}

	public Date getDateDebutAbonnement() {
		return dateDebutAbonnement;
	}

	public void setDateDebutAbonnement(Date dateDebutAbonnement) {
		this.dateDebutAbonnement = dateDebutAbonnement;
	}

	public BigDecimal getCoefMultiplicateur() {
		return coefMultiplicateur;
	}

	public void setCoefMultiplicateur(BigDecimal coefMultiplicateur) {
		this.coefMultiplicateur = coefMultiplicateur;
	}

	public BigDecimal getCommissionBancaire() {
		return commissionBancaire;
	}

	public void setCommissionBancaire(BigDecimal commissionBancaire) {
		this.commissionBancaire = commissionBancaire;
	}

	public String getNomDossier() {
		return nomDossier;
	}

	public void setNomDossier(String nomDossier) {
		this.nomDossier = nomDossier;
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public String getCodeTiers() {
		return codeTiers;
	}

	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	public String getNomTiers() {
		return nomTiers;
	}

	public void setNomTiers(String nomTiers) {
		this.nomTiers = nomTiers;
	}

	public String getPrenomTiers() {
		return prenomTiers;
	}

	public void setPrenomTiers(String prenomTiers) {
		this.prenomTiers = prenomTiers;
	}

	public BigDecimal getCommissionPanier() {
		return commissionPanier;
	}

	public void setCommissionPanier(BigDecimal commissionPanier) {
		this.commissionPanier = commissionPanier;
	}
	
	public String getUSaisieLDocument() {
		return uSaisieLDocument;
	}

	public void setUSaisieLDocument(String uSaisieLDocument) {
		this.uSaisieLDocument = uSaisieLDocument;
	}

	public BigDecimal getQteSaisieLDocument() {
		return qteSaisieLDocument;
	}

	public void setQteSaisieLDocument(BigDecimal qteSaisieLDocument) {
		this.qteSaisieLDocument = qteSaisieLDocument;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public BigDecimal getQteTitreTransport() {
		return qteTitreTransport;
	}

	public void setQteTitreTransport(BigDecimal qteTitreTransport) {
		this.qteTitreTransport = qteTitreTransport;
	}

}