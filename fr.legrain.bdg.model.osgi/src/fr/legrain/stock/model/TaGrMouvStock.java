package fr.legrain.stock.model;

// Generated 4 juin 2009 10:11:38 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.Valid;

import fr.legrain.article.model.TaFabrication;
import fr.legrain.article.model.TaTypeMouvement;
import fr.legrain.document.model.TaAvoir;
import fr.legrain.document.model.TaBonReception;
import fr.legrain.document.model.TaBonliv;
import fr.legrain.document.model.TaEtat;
import fr.legrain.document.model.TaFacture;
import fr.legrain.document.model.TaTicketDeCaisse;
import fr.legrain.general.model.TaObjetLgr;
import fr.legrain.validator.LgrHibernateValidated;

/**
 * TaStock generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_gr_mouv_stock")
@NamedQueries(value = { 

		@NamedQuery(name=TaGrMouvStock.QN.FIND_ALL_LIGHT, query="select new fr.legrain.stock.dto.GrMouvStockDTO(a.id,  a.codeGrMouvStock,a.dateGrMouvStock, a.libelleGrMouvStock, t.code) from TaGrMouvStock a join a.taTypeMouvStock t order by a.dateGrMouvStock DESC,  a.codeGrMouvStock DESC"),
		@NamedQuery(name=TaGrMouvStock.QN.FIND_BY_CODE_LIGHT, query="select new fr.legrain.stock.dto.GrMouvStockDTO(a.id,  a.codeGrMouvStock,a.dateGrMouvStock, a.libelleGrMouvStock, t.code) from TaGrMouvStock a join a.taTypeMouvStock t where a.codeGrMouvStock like :codeGrMouvStock order by a.codeGrMouvStock")
		
		
})
public class TaGrMouvStock extends TaObjetLgr   implements java.io.Serializable {

	private static final long serialVersionUID = -6491847754955008529L;
	public static class QN {
		public static final String FIND_ALL_LIGHT = "TaGrMouvStock.findAllLight";
		public static final String FIND_BY_CODE_LIGHT = "TaGrMouvStock.findByCodeLight";
	}
	
	private int idGrMouvStock;
	private String codeGrMouvStock;
	private Date dateGrMouvStock;
	private String libelleGrMouvStock;
	private TaEtat taEtat;
	private String commentaire;
	private TaFabrication taFabrication;
	private TaFacture taFacture;
	private TaAvoir taAvoir;
	private TaBonReception taBonReception;
	private TaBonliv taBonliv;
	private TaInventaire taInventaire;
	private TaTicketDeCaisse taTicketDeCaisse;
	private Boolean manuel = false;



	private TaTypeMouvement taTypeMouvStock;
	
	private Set<TaMouvementStock> taMouvementStockes = new HashSet<TaMouvementStock>(0);
	
//	private String quiCree;
//	private Date quandCree;
//	private String quiModif;
//	private Date quandModif;
//	private String ipAcces;
	private Integer versionObj;
//	private String version;

	public TaGrMouvStock() {
	}

	public TaGrMouvStock(int idGrMouvStock) {
		this.idGrMouvStock = idGrMouvStock;
	}

	public TaMouvementStock chercheLigneInverseDeplacement(TaMouvementStock ligneMouvement) {
		return chercheLigneInverseDeplacement(ligneMouvement, false);
	}

	public TaMouvementStock chercheLigneInverseDeplacement(TaMouvementStock ligneMouvement, boolean creerSiExistePas) {
		TaMouvementStock ligneInverse = null;
		if(ligneMouvement!=null) {
			for (TaMouvementStock taMouvementStock : taMouvementStockes) {
				if(ligneMouvement.getLettrageDeplacement()!=null 
						&& taMouvementStock.getLettrageDeplacement()!=null
						&& ligneMouvement.getLettrageDeplacement().equals(taMouvementStock.getLettrageDeplacement())) {
					if(ligneMouvement.getIdMouvementStock()!=taMouvementStock.getIdMouvementStock()) {
						ligneInverse = taMouvementStock;
					}
				}
			}
		}
		if(ligneInverse==null && creerSiExistePas) {
			ligneMouvement.setLettrageDeplacement(UUID.randomUUID().toString()/*.replace("-", "")*/);
			ligneInverse = creerLigneInverse(ligneMouvement);
		} else {
			//mise à jour
			ligneInverse = mappingLigneInverse(ligneMouvement,ligneInverse,false);
		}
		return ligneInverse;
	}
	
	public TaMouvementStock creerLigneInverse(TaMouvementStock ligneMouvement) {
		TaMouvementStock ligneInverse = new TaMouvementStock();
		
		ligneInverse = mappingLigneInverse(ligneMouvement,ligneInverse,true);
		
		return ligneInverse;
	}
	
	public void majLigneInverse(TaMouvementStock ligneMouvement, int idLigneAMettreAJour) {
		
		for (TaMouvementStock taMouvementStock : getTaMouvementStockes()) {
			if(taMouvementStock.getLettrageDeplacement()!=null && 
					taMouvementStock.getLettrageDeplacement().equals(taMouvementStock.getLettrageDeplacement())) {
				//mappingLigneInverse(ligneMouvement, taMouvementStock);
			}
		}

	}
	
	public TaMouvementStock mappingLigneInverse(TaMouvementStock ligneMouvement, TaMouvementStock ligneInverse, boolean creation) {
		if(ligneInverse==null) {
			ligneInverse = new TaMouvementStock();
		}
		
		//copier
		ligneInverse.setTaLot(ligneMouvement.getTaLot());
		ligneInverse.setDateStock(ligneMouvement.getDateStock());
		ligneInverse.setDescription(ligneMouvement.getDescription());
		ligneInverse.setLibelleStock(ligneMouvement.getLibelleStock());
//		if(creation) {
		if(ligneMouvement.getQte1Stock()!=null) {
			ligneInverse.setQte1Stock(ligneMouvement.getQte1Stock().abs());
		} else {
			ligneInverse.setQte1Stock(ligneMouvement.getQte1Stock());
		}
		if(ligneMouvement.getQte2Stock()!=null) {
			ligneInverse.setQte2Stock(ligneMouvement.getQte2Stock().abs());
		} else {
			ligneInverse.setQte2Stock(ligneMouvement.getQte2Stock());
		}
//		}
		ligneInverse.setUn1Stock(ligneMouvement.getUn1Stock());
		ligneInverse.setUn2Stock(ligneMouvement.getUn2Stock());
		
		ligneInverse.setUtilise(ligneMouvement.getUtilise());
		
		//affecter l'entrepot/emplacement correspondant
		
		ligneInverse.setTaEntrepot(ligneMouvement.getTaEntrepotDest());
		ligneInverse.setEmplacement(ligneMouvement.getEmplacementDest());
		
		//affecter le lettrage ??
		ligneInverse.setLettrageDeplacement(ligneMouvement.getLettrageDeplacement());
		
		//affecter le groupe de mouv ??
		ligneInverse.setTaGrMouvStock(ligneMouvement.getTaGrMouvStock());
		
		return ligneInverse;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_gr_mouv_stock", unique = true, nullable = false)
	@LgrHibernateValidated(champBd = "id_gr_mouv_stock",table = "ta_gr_mouv_stock", champEntite="idGrMouvStock", clazz = TaGrMouvStock.class)
	public int getIdGrMouvStock() {
		return this.idGrMouvStock;
	}

	public void setIdGrMouvStock(int idGrMouvStock) {
		this.idGrMouvStock = idGrMouvStock;
	}

////	@Version
//	@Column(name = "version", length = 20)
//	public String getVersion() {
//		return this.version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}



	@Temporal(TemporalType.DATE)
	@Column(name = "date_gr_mouv_stock", length = 19)
	@LgrHibernateValidated(champBd = "date_gr_mouv_stock",table = "ta_gr_mouv_stock", champEntite="dateGrMouvStock", clazz = TaGrMouvStock.class)
	public Date getDateGrMouvStock() {
		return this.dateGrMouvStock;
	}

	public void setDateGrMouvStock(Date dateGrMouvStock) {
		this.dateGrMouvStock = dateGrMouvStock;
	}

	@Column(name = "libelle_gr_mouv_stock")
	@LgrHibernateValidated(champBd = "libelle_gr_mouv_stock",table = "ta_gr_mouv_stock", champEntite="libelleGrMouvStock", clazz = TaGrMouvStock.class)
	public String getLibelleGrMouvStock() {
		return this.libelleGrMouvStock;
	}

	public void setLibelleGrMouvStock(String libelleGrMouvStock) {
		this.libelleGrMouvStock = libelleGrMouvStock;
	}




	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_type_mouv")
	@LgrHibernateValidated(champBd = "id_type_mouvement",table = "ta_type_mouvement", champEntite="taTypeMouvStock.idTypeMouvement", clazz = TaTypeMouvement.class)
	public TaTypeMouvement getTaTypeMouvStock() {
		return taTypeMouvStock;
	}

	public void setTaTypeMouvStock(TaTypeMouvement taTypeMouvStock) {
		this.taTypeMouvStock = taTypeMouvStock;
	}


	@Column(name = "code_gr_mouv_stock", length = 20)
	@LgrHibernateValidated(champBd = "code_gr_mouv_stock",table = "ta_gr_mouv_stock", champEntite="codeGrMouvStock", clazz = TaGrMouvStock.class)
	public String getCodeGrMouvStock() {
		return codeGrMouvStock;
	}

	public void setCodeGrMouvStock(String codeGrMouvStock) {
		this.codeGrMouvStock = codeGrMouvStock;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_etat")
	@LgrHibernateValidated(champBd = "id_etat",table = "ta_etat", champEntite="taEtat.idEtat", clazz = TaEtat.class)
	public TaEtat getTaEtat() {
		return this.taEtat;
	}

	public void setTaEtat(TaEtat taEtat) {
		this.taEtat = taEtat;
	}

	@Column(name = "commentaire")
	@LgrHibernateValidated(champBd = "commentaire",table = "ta_gr_mouv_stock", champEntite="commentaire", clazz = TaGrMouvStock.class)
	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "taGrMouvStock", orphanRemoval=true)
	public Set<TaMouvementStock> getTaMouvementStockes() {
		return taMouvementStockes;
	}

	public void setTaMouvementStockes(Set<TaMouvementStock> taMouvementStockes) {
		this.taMouvementStockes = taMouvementStockes;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_fabrication")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_fabrication", champEntite="taFabrication.idDocument", clazz = TaFabrication.class)
	public TaFabrication getTaFabrication() {
		return taFabrication;
	}

	public void setTaFabrication(TaFabrication taFabrication) {
		this.taFabrication = taFabrication;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bon_reception")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_bon_reception", champEntite="taBonReception.idDocument", clazz = TaBonReception.class)
	public TaBonReception getTaBonReception() {
		return taBonReception;
	}

	public void setTaBonReception(TaBonReception taBonReception) {
		this.taBonReception = taBonReception;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bonliv")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_bonliv", champEntite="taBonliv.idDocument", clazz = TaBonliv.class)
	public TaBonliv getTaBonliv() {
		return taBonliv;
	}

	public void setTaBonliv(TaBonliv taBonliv) {
		this.taBonliv = taBonliv;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_inventaire")
	@LgrHibernateValidated(champBd = "id_inventaire",table = "ta_inventaire", champEntite="taInventaire.idDocument", clazz = TaInventaire.class)
	public TaInventaire getTaInventaire() {
		return taInventaire;
	}

	public void setTaInventaire(TaInventaire taInventaire) {
		this.taInventaire = taInventaire;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_facture")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_facture", champEntite="taFacture.idDocument", clazz = TaFacture.class)
	public TaFacture getTaFacture() {
		return taFacture;
	}

	public void setTaFacture(TaFacture taFacture) {
		this.taFacture = taFacture;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_avoir")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_avoir", champEntite="taAvoir.idDocument", clazz = TaAvoir.class)
	public TaAvoir getTaAvoir() {
		return taAvoir;
	}

	public void setTaAvoir(TaAvoir taAvoir) {
		this.taAvoir = taAvoir;
	}
	
	public Boolean getManuel() {
		return manuel;
	}

	public void setManuel(Boolean manuel) {
		this.manuel = manuel;
	}
	
//	@Column(name = "qui_cree", length = 50)
//	public String getQuiCree() {
//		return this.quiCree;
//	}
//
//	public void setQuiCree(String quiCreeStock) {
//		this.quiCree = quiCreeStock;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_cree", length = 19)
//	public Date getQuandCree() {
//		return this.quandCree;
//	}
//
//	public void setQuandCree(Date quandCreeStock) {
//		this.quandCree = quandCreeStock;
//	}
//
//	@Column(name = "qui_modif", length = 50)
//	public String getQuiModif() {
//		return this.quiModif;
//	}
//
//	public void setQuiModif(String quiModifStock) {
//		this.quiModif = quiModifStock;
//	}
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "quand_modif", length = 19)
//	public Date getQuandModif() {
//		return this.quandModif;
//	}
//
//	public void setQuandModif(Date quandModifStock) {
//		this.quandModif = quandModifStock;
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



	
	public void addLigneMouvement(TaMouvementStock ligne){
		this.getTaMouvementStockes().add(ligne);
	}
	
	public void removeLigneMouvement(TaMouvementStock ligne){
		this.getTaMouvementStockes().remove(ligne);		
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ticket_caisse")
	@LgrHibernateValidated(champBd = "id_document",table = "ta_ticket_caisse", champEntite="taTicketDeCaisse.idDocument", clazz = TaTicketDeCaisse.class)
	public TaTicketDeCaisse getTaTicketDeCaisse() {
		return taTicketDeCaisse;
	}

	public void setTaTicketDeCaisse(TaTicketDeCaisse taTicketDeCaisse) {
		this.taTicketDeCaisse = taTicketDeCaisse;
	}

	public void removeLigne(TaMouvementStock ligne) throws Exception {
		if(getTaMouvementStockes().size()-1>=0 ){
			getTaMouvementStockes().remove(ligne);
		}

}

	

}