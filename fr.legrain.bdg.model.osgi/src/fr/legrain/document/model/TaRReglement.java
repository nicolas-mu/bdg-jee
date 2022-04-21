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

import fr.legrain.document.dto.IRelationDoc;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaRDocument generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)

@Table(name = "ta_r_reglement")
//@SequenceGenerator(name = "gen_r_reglement", sequenceName = "num_id_r_reglement", allocationSize = 1)
@NamedQueries(value = { 
		@NamedQuery(name=TaRReglement.QN.FIND_BY_REGLEMENT_DTO, query="select new fr.legrain.document.dto.TaRReglementDTO(rr.id,f.idDocument, f.codeDocument, r.idDocument, r.codeDocument,t.idTiers,t.codeTiers,t.nomTiers,f.libelleDocument,"
				+ "f.dateDocument,f.netTtcCalc,(select coalesce(sum(r.affectation),0)from TaRReglement r where r.taFacture=f )as sommeReglement,(select coalesce(sum(r.affectation),0)from TaRAvoir r where r.taFacture=f )as sommeAvoir, rr.affectation,rr.dateExport,rr.dateVerrouillage, mad.accessibleSurCompteClient, mad.envoyeParEmail, mad.imprimePourClient) "
				+ "from TaRReglement rr join rr.taReglement r join rr.taFacture f join r.taTiers t   left join rr.taMiseADisposition mad   where r.codeDocument like :codeDoc and t.codeTiers like :codeTiers order by r.id")
})
public class TaRReglement implements java.io.Serializable,IRelationDoc {

	private static final long serialVersionUID = 1117345144579079000L;
	
	public static class QN {
		public static final String FIND_BY_REGLEMENT_DTO = "TaRReglement.findByReglementDTO";
	}
	
	private int id;
	private String version;
	private TaReglement taReglement;
	private TaFacture taFacture;
	private TaTicketDeCaisse taTicketDeCaisse;
//	private TaBoncde taBoncde;
//	private TaProforma taProforma;
//	private TaDevis taDevis;
//	private TaApporteur taApporteur;
//	private TaAvoir taAvoir;
//	private TaBonliv taBonliv;
//	private Integer export= 0;
	private BigDecimal affectation  = new BigDecimal(0);
	private String quiCree;
	private Date quandCree;
	private String quiModif;
	private Date quandModif;
	private String ipAcces;
	private Integer versionObj;
	@Transient
	private Boolean accepte=true;
		
	private Integer etat = 0;
	private TaMiseADisposition taMiseADisposition;
	private Date dateExport;
	private Date dateVerrouillage;
	
    @Transient
	private int EtatDeSuppression =0;
	public static final String TYPE_DOC = "RReglement";
	@Transient
	private String typeDocument;
	
	
	
	public TaRReglement() {
	}

	public TaRReglement(int idRAcompte) {
		this.id = idRAcompte;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id",table = "ta_r_reglement", champEntite="id", clazz = TaRReglement.class)
	public int getId() {
		return this.id;
	}

	public void setId(int idRReglement) {
		this.id = idRReglement;
	}

	@Column(name = "version", length = 20)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
//	CascadeType.PERSIST, CascadeType.MERGE, *** enlever car probleme avec remise, les rAvoir et Racompte etaient
	//déjà comme cela. Voir si pas de problème avec les modifications dans les réglements !!!
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_reglement" )
	@OrderBy("codeDocument")
	@LgrHibernateValidated(champBd = "id_reglement",table = "ta_reglement", champEntite="taReglement.idDocument", clazz = TaReglement.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRReglements")
	public TaReglement getTaReglement() {
		return this.taReglement;
	}


	
	public void setTaReglement(TaReglement taReglement) {
		this.taReglement = taReglement;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_facture")
	@LgrHibernateValidated(champBd = "id_facture",table = "ta_facture", champEntite="taFacture.idDocument", clazz = TaFacture.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRReglements")
	public TaFacture getTaFacture() {
		return this.taFacture;
	}

	public void setTaFacture(TaFacture taFacture) {
		this.taFacture = taFacture;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ticket_caisse")
	@LgrHibernateValidated(champBd = "id_ticket_caisse",table = "ta_ticket_caisse", champEntite="taTicketDeCaisse.idDocument", clazz = TaTicketDeCaisse.class)
	@XmlElement
	@XmlInverseReference(mappedBy="taRReglements")
	public TaTicketDeCaisse getTaTicketDeCaisse() {
		return taTicketDeCaisse;
	}

	public void setTaTicketDeCaisse(TaTicketDeCaisse taTicketDeCaisse) {
		this.taTicketDeCaisse = taTicketDeCaisse;
	}

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_boncde")
//	@LgrHibernateValidated(champBd = "id_boncde",table = "ta_boncde", champEntite="taBoncde.idDocument", clazz = TaBoncde.class)
//	public TaBoncde getTaBoncde() {
//		return this.taBoncde;
//	}
//
//	public void setTaBoncde(TaBoncde taBoncde) {
//		this.taBoncde = taBoncde;
//	}
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_proforma")
//	@LgrHibernateValidated(champBd = "id_proforma",table = "ta_proforma", champEntite="taProforma.idDocument", clazz = TaProforma.class)
//	public TaProforma getTaProforma() {
//		return this.taProforma;
//	}
//
//	public void setTaProforma(TaProforma taProforma) {
//		this.taProforma = taProforma;
//	}
//
//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_devis")
//	@LgrHibernateValidated(champBd = "id_devis",table = "ta_devis", champEntite="taDevis.idDocument", clazz = TaDevis.class)
//	public TaDevis getTaDevis() {
//		return this.taDevis;
//	}
//
//	public void setTaDevis(TaDevis taDevis) {
//		this.taDevis = taDevis;
//	}
//	
//	//
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_apporteur")
//	@LgrHibernateValidated(champBd = "id_apporteur",table = "ta_apporteur", champEntite="taApporteur.idDocument", clazz = TaApporteur.class)
//	public TaApporteur getTaApporteur() {
//		return this.taApporteur;
//	}
//
//	public void setTaApporteur(TaApporteur taApporteur) {
//		this.taApporteur = taApporteur;
//	}
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "id_avoir")
//	@LgrHibernateValidated(champBd = "id_avoir",table = "ta_avoir", champEntite="taAvoir.idDocument", clazz = TaAvoir.class)
//	public TaAvoir getTaAvoir() {
//		return this.taAvoir;
//	}
//
//	public void setTaAvoir(TaAvoir taAvoir) {
//		this.taAvoir = taAvoir;
//	}
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "id_bonliv")
//	@LgrHibernateValidated(champBd = "id_bonliv",table = "ta_bonliv", champEntite="taBonliv.idDocument", clazz = TaBonliv.class)
//	public TaBonliv getTaBonliv() {
//		return this.taBonliv;
//	}
//
//	public void setTaBonliv(TaBonliv taBonliv) {
//		this.taBonliv = taBonliv;
//	}


//	@Column(name = "export")
//	@LgrHibernateValidated(champBd = "export",table = "ta_r_reglement", champEntite="export", clazz = TaRReglement.class)
//	public Integer getExport() {
//		return export;
//	}
//
//	public void setExport(Integer export) {
//		this.export = export;
//	}
	
	@Column(name = "affectation", precision = 15)
	@LgrHibernateValidated(champBd = "affectation",table = "ta_r_reglement", champEntite="affectation", clazz = TaRReglement.class)
	public BigDecimal getAffectation() {
		return affectation ;
	}

	public void setAffectation(BigDecimal affectation) {
		this.affectation = affectation;
	}

//	@Column(name = "montant_reglement", precision = 15)
//	@LgrHibernateValidated(champ = "montant_reglement",table = "ta_r_acompte", champEntite="xxxxx", clazz = TaRReglement.class)
//	public BigDecimal getMontantReglement() {
//		return montantReglement;
//	}
//
//	public void setMontantReglement(BigDecimal montantReglement) {
//		this.montantReglement = montantReglement;
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
	public int getEtatDeSuppression() {
		return EtatDeSuppression;
	}
	
	@Transient
	public void setEtatDeSuppression(int etatDeSuppression) {
		EtatDeSuppression = etatDeSuppression;
	}

	@Transient
	public Boolean getAccepte() {
		return accepte;
	}

	@Transient
	public void setAccepte(Boolean accepte) {
		this.accepte = accepte;
	}
	
	@Column(name = "etat")
	@LgrHibernateValidated(champBd = "etat",table = "ta_reglement", champEntite="etat", clazz = TaReglement.class)
	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}
	
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL} , orphanRemoval=true )
	@JoinColumn(name = "id_mise_a_disposition")
	public TaMiseADisposition getTaMiseADisposition() {
		return taMiseADisposition;
	}

	public void setTaMiseADisposition(TaMiseADisposition taMiseADisposition) {
		this.taMiseADisposition = taMiseADisposition;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_export", length = 19)
	public Date getDateExport() {
		return dateExport;
	}

	public void setDateExport(Date dateExport) {
		this.dateExport = dateExport;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_verrouillage", length = 19)
	public Date getDateVerrouillage() {
		return dateVerrouillage;
	}

	public void setDateVerrouillage(Date dateVerrouillage) {
		this.dateVerrouillage = dateVerrouillage;
	}
	
//	@Transient
//	public List<TaRReglement> getTaRReglements() {
//		return taRReglements;
//	}
//
//	public void setTaRReglements(List<TaRReglement> taRReglements) {
//		this.taRReglements = taRReglements;
//	}

//	@Transient
//	public void remonteListReglements(){
//		EntityManager em= new TaRReglementDAO().getEntityManager();
//		taRReglements=new TaRReglementDAO(em).findByCode(getCodeReglement());
//	}



	@PreRemove
	public void verifRemove()throws Exception{
		if(this.getTaFacture()!=null ){
			if(this.getTaFacture().getTaTiers().getIdTiers()!=this.getTaReglement().getTaTiers().getIdTiers()){
				throw new Exception("verifRemove");
			}
		}
	}

	
	@PrePersist
	public void verifPersist() throws Exception{

//		if(this.getTaFacture()!=null ){
//			if(this.getTaFacture().getTaTiers().getIdTiers()!=this.getTaReglement().getTaTiers().getIdTiers()){
//				throw new Exception("verifPersist");
//			}
//		}
//		if(this.getTaFacture().getRegleCompletDocument(this).add(this.affectation).compareTo(this.getTaFacture().getNetAPayer())>0){
//			throw new Exception("Total affectation supérieure à la facture");
//		}
//		if(this.getTaReglement().calculAffectationTotale(this).add(this.affectation).compareTo(this.getTaReglement().getNetTtcCalc())>0){
//			throw new Exception("Total affectation supérieure au règlement");
//		}		
	}
	
//	@PreUpdate
	public void verifUpdate() throws Exception{
		if(this.getTaFacture()!=null ){
			if(this.getTaFacture().getTaTiers().getIdTiers()!=this.getTaReglement().getTaTiers().getIdTiers()){
				throw new Exception("verifUpdate");
			}
		}
		if(this.getTaFacture().getRegleCompletDocument(this).add(this.affectation).compareTo(this.getTaFacture().getNetAPayer())>0){
			throw new Exception("Total affectation supérieure à la facture");
		}
		if(this.getTaReglement().calculAffectationTotale(this).add(this.affectation).compareTo(this.getTaReglement().getNetTtcCalc())>0){
			throw new Exception("Total affectation supérieure au règlement");
		}
	}

	
	public boolean estDifferent(TaRReglement old) {
		if(old.equalsTraca(this))return true;
		return false;
	}

	@Transient
	public String getTypeDocument() {
		return TYPE_DOC;
	}


	public void setTypeDocument(String typeDocument) {
		this.typeDocument=typeDocument;
	}

	
	public boolean equalsTraca(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaRReglement other = (TaRReglement) obj;
		if (affectation == null) {
			if (other.affectation != null)
				return false;
		} else if (!affectation.equals(other.affectation))
			return false;
		if (etat == null) {
			if (other.etat != null)
				return false;
		} else if (!etat.equals(other.etat))
			return false;
		if (dateExport == null) {
			if (other.dateExport != null)
				return false;
		} else if (!dateExport.equals(other.dateExport))
			return false;
		if (taFacture == null) {
			if (other.taFacture != null)
				return false;
		} else if (!taFacture.equals(other.taFacture))
			return false;
		if (taReglement == null) {
			if (other.taReglement != null)
				return false;
		} else if (!taReglement.equals(other.taReglement))
			return false;
		return true;
	}

}