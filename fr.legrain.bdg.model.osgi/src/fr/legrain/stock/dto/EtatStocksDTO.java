package fr.legrain.stock.dto;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

import fr.legrain.article.model.TaArticle;
import fr.legrain.article.model.TaEntrepot;
import fr.legrain.article.model.TaLot;
import fr.legrain.bdg.model.ModelObject;

/**
 * SWTPrix generated by hbm2java
 */
public class EtatStocksDTO extends ModelObject implements java.io.Serializable {

	private static final long serialVersionUID = -8017113900430214905L;

	private Integer id;	

	private Date dateEtatStock;
	private String libelleEtatStock;
	private BigDecimal qte1EtatStock;
	private String un1EtatStock;
	private BigDecimal qte2EtatStock;
	private String un2EtatStock;	
	private Integer idEntrepotOrg;
	private String codeEntrepotOrg;
	private Integer idLotArticle;
	private String codeLotArticle;
	private String emplacementOrg;
	private String numLot;
	private Date dluo;
	private Boolean lotTermine;
	private String codeFamille;
	private Integer versionObj;
	
	private BigDecimal qteRef;
	private String unRef;

	private TaLot taLot;
	private TaEntrepot taEntrepot;
	private TaArticle taArticle;
	
	
	public EtatStocksDTO() {	
	}

	public EtatStocksDTO(Integer id, Date dateEtatStock,
			String libelleEtatStock, BigDecimal qte1EtatStock,
			String un1EtatStock, BigDecimal qte2EtatStock, String un2EtatStock,
			String codeEntrepotOrg, String codeLotArticle,String numLot,Boolean lotTermine,
			String emplacementOrg, Integer versionObj) {
		super();
		this.id = id;
		this.dateEtatStock = dateEtatStock;
		this.libelleEtatStock = libelleEtatStock;
		this.qte1EtatStock = qte1EtatStock;
		this.un1EtatStock = un1EtatStock;
		this.qte2EtatStock = qte2EtatStock;
		this.un2EtatStock = un2EtatStock;
		this.codeEntrepotOrg = codeEntrepotOrg;
		this.codeLotArticle = codeLotArticle;
		this.numLot = numLot;
		this.lotTermine = lotTermine;
		this.emplacementOrg = emplacementOrg;
		this.versionObj = versionObj;
	}



	public EtatStocksDTO(Integer id, Date dateEtatStock,
			String libelleEtatStock, BigDecimal qte1EtatStock,
			String un1EtatStock, BigDecimal qte2EtatStock, String un2EtatStock,
			String codeEntrepotOrg, String codeLotArticle,String numLot,Boolean lotTermine,
			String emplacementOrg, String codeFamille, Integer versionObj) {
		super();
		this.id = id;
		this.dateEtatStock = dateEtatStock;
		this.libelleEtatStock = libelleEtatStock;
		this.qte1EtatStock = qte1EtatStock;
		this.un1EtatStock = un1EtatStock;
		this.qte2EtatStock = qte2EtatStock;
		this.un2EtatStock = un2EtatStock;
		this.codeEntrepotOrg = codeEntrepotOrg;
		this.codeLotArticle = codeLotArticle;
		this.numLot = numLot;
		this.lotTermine = lotTermine;
		this.emplacementOrg = emplacementOrg;
		this.codeFamille = codeFamille;
		this.versionObj = versionObj;
	}

	public EtatStocksDTO(Integer id, Date dateEtatStock,
			String libelleEtatStock, BigDecimal qte1EtatStock,
			String un1EtatStock, BigDecimal qte2EtatStock, String un2EtatStock,
			String codeEntrepotOrg, String codeLotArticle,String numLot,Boolean lotTermine,
			String emplacementOrg, String codeFamille, Integer versionObj ,String unRef,BigDecimal qteRef) {
		super();
		this.id = id;
		this.dateEtatStock = dateEtatStock;
		this.libelleEtatStock = libelleEtatStock;
		this.qte1EtatStock = qte1EtatStock;
		this.un1EtatStock = un1EtatStock;
		this.qte2EtatStock = qte2EtatStock;
		this.un2EtatStock = un2EtatStock;
		this.codeEntrepotOrg = codeEntrepotOrg;
		this.codeLotArticle = codeLotArticle;
		this.numLot = numLot;
		this.lotTermine = lotTermine;
		this.emplacementOrg = emplacementOrg;
		this.codeFamille = codeFamille;
		this.versionObj = versionObj;
		this.unRef = unRef;
		this.qteRef = qteRef;
	}

	public EtatStocksDTO(Integer id, Date dateEtatStock,
			String libelleEtatStock, BigDecimal qte1EtatStock,
			String un1EtatStock, BigDecimal qte2EtatStock, String un2EtatStock,
			String codeEntrepotOrg, String codeLotArticle,String numLot, Date dluo, Boolean lotTermine,//+champ dluo
			String emplacementOrg, String codeFamille, Integer versionObj ,String unRef,BigDecimal qteRef) {
		super();
		this.id = id;
		this.dateEtatStock = dateEtatStock;
		this.libelleEtatStock = libelleEtatStock;
		this.qte1EtatStock = qte1EtatStock;
		this.un1EtatStock = un1EtatStock;
		this.qte2EtatStock = qte2EtatStock;
		this.un2EtatStock = un2EtatStock;
		this.codeEntrepotOrg = codeEntrepotOrg;
		this.codeLotArticle = codeLotArticle;
		this.numLot = numLot;
		this.dluo = dluo;
		this.lotTermine = lotTermine;
		this.emplacementOrg = emplacementOrg;
		this.codeFamille = codeFamille;
		this.versionObj = versionObj;
		this.unRef = unRef;
		this.qteRef = qteRef;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer idEtatStock) {
		firePropertyChange("idEtatStock", this.id, this.id = idEtatStock);
	}


	public Date getDateEtatStock() {
		return dateEtatStock;
	}


	public void setDateEtatStock(Date dateEtatStock) {
		firePropertyChange("dateEtatStock", this.dateEtatStock, this.dateEtatStock = dateEtatStock);
	}


	public String getLibelleEtatStock() {
		return libelleEtatStock;
	}


	public void setLibelleEtatStock(String libelleEtatStock) {
		firePropertyChange("libelleEtatStock", this.libelleEtatStock, this.libelleEtatStock = libelleEtatStock);
	}


	public BigDecimal getQte1EtatStock() {
		return qte1EtatStock;
	}


	public void setQte1EtatStock(BigDecimal qte1EtatStock) {
		firePropertyChange("qte1EtatStock", this.qte1EtatStock, this.qte1EtatStock = qte1EtatStock);
	}


	public String getUn1EtatStock() {
		return un1EtatStock;
	}


	public void setUn1EtatStock(String un1EtatStock) {
		firePropertyChange("un1EtatStock", this.un1EtatStock, this.un1EtatStock = un1EtatStock);
	}


	public BigDecimal getQte2EtatStock() {
		return qte2EtatStock;
	}


	public void setQte2EtatStock(BigDecimal qte2EtatStock) {
		firePropertyChange("qte2EtatStock", this.qte2EtatStock, this.qte2EtatStock = qte2EtatStock);
	}


	public String getUn2EtatStock() {
		return un2EtatStock;
	}


	public void setUn2EtatStock(String un2EtatStock) {
		firePropertyChange("un2EtatStock", this.un2EtatStock, this.un2EtatStock = un2EtatStock);
	}


	public Integer getIdEntrepotOrg() {
		return idEntrepotOrg;
	}


	public void setIdEntrepotOrg(Integer idEntrepotOrg) {
		firePropertyChange("idEntrepotOrg", this.idEntrepotOrg, this.idEntrepotOrg = idEntrepotOrg);
	}


	public String getCodeEntrepotOrg() {
		return codeEntrepotOrg;
	}


	public void setCodeEntrepotOrg(String codeEntrepotOrg) {
		firePropertyChange("codeEntrepotOrg", this.codeEntrepotOrg, this.codeEntrepotOrg = codeEntrepotOrg);
	}


	public Integer getIdLotArticle() {
		return idLotArticle;
	}


	public void setIdLotArticle(Integer idLotArticle) {
		firePropertyChange("idLotArticle", this.idLotArticle, this.idLotArticle = idLotArticle);
	}


	public String getCodeLotArticle() {
		return codeLotArticle;
	}


	public void setCodeLotArticle(String codeLotArticle) {
		firePropertyChange("codeLotArticle", this.codeLotArticle, this.codeLotArticle = codeLotArticle);
	}


	public String getEmplacementOrg() {
		return emplacementOrg;
	}


	public void setEmplacementOrg(String emplacementOrg) {
		firePropertyChange("emplacementOrg", this.emplacementOrg, this.emplacementOrg = emplacementOrg);
	}



	public String getNumLot() {
		return numLot;
	}





	public void setNumLot(String numLot) {
		firePropertyChange("numLot", this.numLot, this.numLot = numLot);
	}


	public TaLot getTaLot() {
		return taLot;
	}





	public void setTaLot(TaLot taLot) {
		firePropertyChange("taLot", this.taLot, this.taLot = taLot);
	}





	public TaEntrepot getTaEntrepot() {
		return taEntrepot;
	}





	public void setTaEntrepot(TaEntrepot taEntrepot) {
		firePropertyChange("taEntrepot", this.taEntrepot, this.taEntrepot = taEntrepot);
	}





	public String getCodeFamille() {
		return codeFamille;
	}





	public void setCodeFamille(String codeFamille) {
		firePropertyChange("codeFamille", this.codeFamille, this.codeFamille = codeFamille);
	}


	public TaArticle getTaArticle() {
		return taArticle;
	}





	public void setTaArticle(TaArticle taArticle) {
		firePropertyChange("taArticle", this.taArticle, this.taArticle = taArticle);
	}

	
	
	public Integer getVersionObj() {
		return this.versionObj;
	}

	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}


	public Boolean getLotTermine() {
		return lotTermine;
	}


	public void setLotTermine(Boolean lotTermine) {
		this.lotTermine = lotTermine;
	}


	public BigDecimal getQteRef() {
		return qteRef;
	}


	public void setQteRef(BigDecimal qteRef) {
		this.qteRef = qteRef;
	}


	public String getUnRef() {
		return unRef;
	}


	public void setUnRef(String unRef) {
		this.unRef = unRef;
	}


	public Date getDluo() {
		return dluo;
	}


	public void setDluo(Date dluo) {
		this.dluo = dluo;
	}















	
}