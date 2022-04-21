package fr.legrain.document.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import fr.legrain.article.model.TaArticle;
import fr.legrain.article.model.TaEntrepot;
import fr.legrain.article.model.TaLot;
import fr.legrain.document.dto.IDocumentTiers;
import fr.legrain.document.dto.TaLigneALigneSupplementDTO;
import fr.legrain.tiers.model.TaTiers;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaLFlash generated by hbm2java
 */
@Entity
//@EntityListeners(SwtLDocumentListener.class)
@Table(name = "ta_l_flash")
public class TaLFlash /*extends SWTLigneDocument*/ implements java.io.Serializable /*, ILigneDocumentTiers*/ {

	private static final long serialVersionUID = -5562741737115826023L;
	
	static Logger logger = Logger.getLogger(TaLFlash.class.getName());
//	private TaTLigne taTLigne;
	private TaArticle taArticle;
	
	private TaLot taLot;
	private TaEntrepot taEntrepot;
	private String emplacementLFlash;
	private int idLFlash ;
	private TaFlash taFlash;
	private String libLFlash;
	private BigDecimal qteLFlash;
	private BigDecimal qte2LFlash;
	private String u1LFlash;
	private String u2LFlash;
	private TaTiers taTiers;

	protected Integer versionObj;
	private String codeBarreLu;
	private String codeBarre;
	private String numLot;
	private String codeArticle;
	private String codeEntrepot;
	private String codeTiers;

	@Transient
	private boolean genereLigne=false;
	@Transient
	protected BigDecimal qteGenere;
	@Transient
	protected String uniteGenere="";
	@Transient
	protected String numlotGenere="";
	@Transient
	private Boolean accepte=false;
	
	
	private TaREtatLigneDocument taREtat;
	
	private Set<TaLigneALigne> taLigneALignes = new HashSet<TaLigneALigne>(0);
	private Set<TaREtatLigneDocument> taREtatLigneDocuments = new HashSet<TaREtatLigneDocument>(0);
	private Set<TaHistREtatLigneDocument> taHistREtatLigneDocuments = new HashSet<TaHistREtatLigneDocument>(0);
	private Set<TaLigneALigneEcheance> taLigneALignesEcheance = new HashSet<TaLigneALigneEcheance>(0);

	@Transient
	private List<TaLigneALigneSupplementDTO> listeSupplement=new LinkedList<>();
	@Transient
	private List<TaLigneALigneSupplementDTO> listeLigneAIntegrer=new LinkedList<>();
	
	private boolean abonnement;

	private String uSaisieLDocument;
	private BigDecimal qteSaisieLDocument;



	public TaLFlash() {
	}
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_l_flash", unique = true, nullable = false)
	public int getIdLFlash() {
		return this.idLFlash;
	}

	public void setIdLFlash(int idLFacture) {
		this.idLFlash = idLFacture;
	}
	
	@Version
	@Column(name = "version_obj")
	public Integer getVersionObj() {
		return this.versionObj;
	}
	
	public void setVersionObj(Integer versionObj) {
		this.versionObj = versionObj;
	}
	


//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "id_t_ligne")
//	public TaTLigne getTaTLigne() {
//		return this.taTLigne;
//	}
//
//	public void setTaTLigne(TaTLigne taTLigne) {
//		this.taTLigne = taTLigne;
//	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_article")
	@LgrHibernateValidated(champBd = "id_article",table = "ta_article", champEntite="taArticle.idArticle", clazz = TaArticle.class)
	public TaArticle getTaArticle() {
		return this.taArticle;
	}



	public void setTaArticle(TaArticle taArticle) {
		this.taArticle = taArticle;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_flash")
	public TaFlash getTaFlash() {
		return this.taFlash;
	}

	public void setTaFlash(TaFlash taFlash) {
		this.taFlash = taFlash;
	}
	
	public void setTaDocumentGeneral(IDocumentTiers taDocument) {
		this.taFlash = (TaFlash) taDocument;
	}



	@Column(name = "lib_l_flash")
	public String getLibLFlash() {
		return this.libLFlash;
	}


	public void setLibLFlash(String libLFlash) {
		this.libLFlash = libLFlash;
	}

	@Column(name = "qte_l_flash", precision = 15)
	public BigDecimal getQteLFlash() {
		return this.qteLFlash;
	}

	public void setQteLFlash(BigDecimal qteLFlash) {		
		this.qteLFlash = qteLFlash;
	}

	@Column(name = "qte2_l_flash", precision = 15)
	public BigDecimal getQte2LFlash() {
		return this.qte2LFlash;
	}

	public void setQte2LFlash(BigDecimal qte2LFlash) {
		this.qte2LFlash = qte2LFlash;
	}

	@Column(name = "u1_l_flash", length = 20)
	public String getU1LFlash() {
		return this.u1LFlash;
	}

	public void setU1LFlash(String u1LFlash) {
		this.u1LFlash = u1LFlash;
	}

	@Column(name = "u2_l_flash", length = 20)
	public String getU2LFlash() {
		return this.u2LFlash;
	}

	public void setU2LFlash(String u2LFlash) {
		this.u2LFlash = u2LFlash;
	}





	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_lot")
	@Valid
	public TaLot getTaLot() {
		return taLot;
	}

	public void setTaLot(TaLot taLot) {
		this.taLot = taLot;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_entrepot")
	public TaEntrepot getTaEntrepot() {
		return taEntrepot;
	}

	public void setTaEntrepot(TaEntrepot taEntrepot) {
		this.taEntrepot = taEntrepot;
	}

	@Column(name = "emplacement_l_flash")
	public String getEmplacementLFlash() {
		return emplacementLFlash;
	}

	public void setEmplacementLFlash(String emplacementLFlash) {
		this.emplacementLFlash = emplacementLFlash;
	}

	
	@Column(name = "code_barre_lu")
	public String getCodeBarreLu() {
		return codeBarreLu;
	}

	public void setCodeBarreLu(String codeBarreLu) {
		this.codeBarreLu = codeBarreLu;
	}
	
	
	@Column(name = "code_barre")
	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}


	@Column(name = "numlot")
	public String getNumLot() {
		return numLot;
	}


	public void setNumLot(String numLot) {
		this.numLot = numLot;
	}


	@Column(name = "code_article")
	public String getCodeArticle() {
		return codeArticle;
	}


	public void setCodeArticle(String codeArticle) {
		this.codeArticle = codeArticle;
	}


	@Column(name = "code_entrepot")
	public String getCodeEntrepot() {
		return codeEntrepot;
	}


	public void setCodeEntrepot(String codeEntrepot) {
		this.codeEntrepot = codeEntrepot;
	}


	@Column(name = "code_tiers")
	public String getCodeTiers() {
		return codeTiers;
	}


	public void setCodeTiers(String codeTiers) {
		this.codeTiers = codeTiers;
	}

	@Transient
	public boolean isGenereLigne() {
		return genereLigne;
	}

	@Transient
	public void setGenereLigne(boolean genereLigne) {
		this.genereLigne = genereLigne;
	}


	@Transient
	public Boolean getAccepte() {
		return accepte;
	}

	@Transient
	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taLFlash", orphanRemoval=true)
	@XmlTransient
	@Fetch(FetchMode.SUBSELECT)
	public Set<TaLigneALigne> getTaLigneALignes() {
		return this.taLigneALignes;
	}

	public void setTaLigneALignes(Set<TaLigneALigne> taLigneALignes) {
		this.taLigneALignes = taLigneALignes;
	}


	@Transient
	public List<TaLigneALigneSupplementDTO> getListeSupplement() {
		// TODO Auto-generated method stub
		return listeSupplement;
	}

	@Transient
	public void setListeSupplement(List<TaLigneALigneSupplementDTO> listeSupplement) {
		// TODO Auto-generated method stub
		this.listeSupplement=listeSupplement;
	}


	@Transient
	public List<TaLigneALigneSupplementDTO> getListeLigneAIntegrer() {
		// TODO Auto-generated method stub
		return listeLigneAIntegrer;
	}

	@Transient
	public void setListeLigneAIntegrer(List<TaLigneALigneSupplementDTO> listeLigneAIntegrer) {
		// TODO Auto-generated method stub
		this.listeLigneAIntegrer=listeLigneAIntegrer;
	}



	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taLFlash", orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	public Set<TaREtatLigneDocument> getTaREtatLigneDocuments() {
		return this.taREtatLigneDocuments;
	}

	public void setTaREtatLigneDocuments(Set<TaREtatLigneDocument> taREtats) {
		this.taREtatLigneDocuments = taREtats;
	}
	

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taLFlash", orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	public Set<TaHistREtatLigneDocument> getTaHistREtatLigneDocuments() {
		return this.taHistREtatLigneDocuments;
	}

	public void setTaHistREtatLigneDocuments(Set<TaHistREtatLigneDocument> taHistREtatLigneDocuments) {
		this.taHistREtatLigneDocuments = taHistREtatLigneDocuments;
	}

	
	
	@Transient
	public BigDecimal getQteGenere() {
		return qteGenere;
	}

	@Transient
	public void setQteGenere(BigDecimal qteGenere) {
		this.qteGenere = qteGenere;
	}

	@Transient
	public String getUniteGenere() {
		return uniteGenere;
	}

	@Transient
	public void setUniteGenere(String uniteGenere) {
		this.uniteGenere = uniteGenere;
	}

	@Transient
	public String getNumlotGenere() {
		return numlotGenere;
	}

	@Transient
	public void setNumlotGenere(String numlotGenere) {
		this.numlotGenere = numlotGenere;
	}



	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_r_etat")
	public TaREtatLigneDocument getTaREtatLigneDocument() {
		return this.taREtat;
	}

	public void setTaREtatLigneDocument(TaREtatLigneDocument taREtat) {
		this.taREtat = taREtat;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tiers")
	public TaTiers getTaTiers() {
		return this.taTiers;
	}

	public void setTaTiers(TaTiers taTiers) {
		this.taTiers = taTiers;
	}
	
	public void addREtatLigneDoc(TaEtat taEtat) {
		TaREtatLigneDocument rEtat=getTaREtatLigneDocument();
		if(rEtat!=null) {
			TaHistREtatLigneDocument hist=new TaHistREtatLigneDocument();
			hist.setTaEtat(getTaREtatLigneDocument().getTaEtat());
			hist.setTaLFlash(this);
			this.getTaHistREtatLigneDocuments().add(hist);
		}else rEtat=new TaREtatLigneDocument();
		
		rEtat.setTaEtat(taEtat);
		rEtat.setTaLFlash(this);
		this.setTaREtatLigneDocument(rEtat);
		this.getTaREtatLigneDocuments().add(rEtat);
	}
	
	
	@PreRemove
	private void methodInvokedBeforeRemove() {
		removeAllLigneALigne(this);
	}
	
	private void removeAllLigneALigne(TaLFlash ligne) {
		for (TaLigneALigne ll : ligne.getTaLigneALignes()) {			
			if(ll.getTaLBonliv()!=null)ll.getTaLBonliv().getTaLigneALignes().remove(ll);
			if(ll.getTaLBonReception()!=null)ll.getTaLBonReception().getTaLigneALignes().remove(ll);
			if(ll.getTaLAcompte()!=null)ll.getTaLAcompte().getTaLigneALignes().remove(ll);
			if(ll.getTaLApporteur()!=null)ll.getTaLApporteur().getTaLigneALignes().remove(ll);
			if(ll.getTaLAvisEcheance()!=null)ll.getTaLAvisEcheance().getTaLigneALignes().remove(ll);
			if(ll.getTaLBoncde()!=null)ll.getTaLBoncde().getTaLigneALignes().remove(ll);
			if(ll.getTaLBoncdeAchat()!=null)ll.getTaLBoncdeAchat().getTaLigneALignes().remove(ll);
			if(ll.getTaLDevis()!=null)ll.getTaLDevis().getTaLigneALignes().remove(ll);
			if(ll.getTaLFacture()!=null)ll.getTaLFacture().getTaLigneALignes().remove(ll);
			if(ll.getTaLPrelevement()!=null)ll.getTaLPrelevement().getTaLigneALignes().remove(ll);
			if(ll.getTaLProforma()!=null)ll.getTaLProforma().getTaLigneALignes().remove(ll);
			if(ll.getTaLTicketDeCaisse()!=null)ll.getTaLTicketDeCaisse().getTaLigneALignes().remove(ll);
			if(ll.getTaLPreparation()!=null)ll.getTaLPreparation().getTaLigneALignes().remove(ll);
		}
		ligne.getTaLigneALignes().clear();
	}
	
	@Column(name = "abonnement")
	public Boolean getAbonnement() {
		return abonnement;
	}
	
	public void setAbonnement(Boolean abonnement) {
		this.abonnement = abonnement;
	}


	@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "taLFlash", orphanRemoval=true)
	@XmlTransient
	@Fetch(FetchMode.SUBSELECT)	
	public Set<TaLigneALigneEcheance> getTaLigneALignesEcheance() {
		return taLigneALignesEcheance;
	}

	public void setTaLigneALignesEcheance(Set<TaLigneALigneEcheance> taLigneALignesEcheance) {
		this.taLigneALignesEcheance = taLigneALignesEcheance;
	}
	
	@Column(name = "u_saisie_l_document", length = 20)
	@LgrHibernateValidated(champBd = "u_saisie_l_document",table = "ta_l_flash", champEntite="uSaisieLDocument", clazz = TaLFlash.class)
	public String getUSaisieLDocument() {
		return uSaisieLDocument;
	}

	public void setUSaisieLDocument(String uSaisieLDocument) {
		this.uSaisieLDocument = uSaisieLDocument;
	}

	@Column(name = "qte_saisie_l_document", precision = 15)
	@LgrHibernateValidated(champBd = "qte_saisie_l_document",table = "ta_l_flash", champEntite="qteSaisieLDocument", clazz = TaLFlash.class)
	public BigDecimal getQteSaisieLDocument() {
		return qteSaisieLDocument;
	}

	public void setQteSaisieLDocument(BigDecimal qteSaisieLDocument) {
		this.qteSaisieLDocument = qteSaisieLDocument;
	}

}
