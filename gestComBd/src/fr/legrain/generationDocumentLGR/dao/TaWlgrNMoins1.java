package fr.legrain.generationDocumentLGR.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import java.math.BigDecimal;
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

/**
 * TaWlgr generated by hbm2java
 */
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "ta_wlgr_2012")

public class TaWlgrNMoins1 implements java.io.Serializable {

	private Integer id;
	private String codeClient;
	private String numeroWlgr;
	private String licence;
	private String nom;
	private String ad1;
	private String ad2;
	private String cp;
	private String ville;
	private String apporteur;
	private Date finE2;
	private String typeE2;
	private String optionsE2;
	private BigDecimal reducE2;
	private Integer dureeE2Retard;
	private Integer dureeE2;
	private BigDecimal totalHtE2;
	private Date finF2;
	private String typeF2;
	private String optionsF2;
	private BigDecimal reducF2;
	private Integer dureeF2Retard;
	private Integer dureeF2;
	private BigDecimal totalHtF2;
	private Date finS2;
	private String typeS2;
	private String optionsS2;
	private BigDecimal reducS2;
	private Integer dureeS2Retard;
	private Integer dureeS2;
	private BigDecimal totalHtS2;
	private BigDecimal totalAvReduc;
	private BigDecimal participationCogere;
	private BigDecimal totalHtApReduc;
	private Integer bonus;
	private BigDecimal bonusHt;
	private BigDecimal htApresBonus;	
	private BigDecimal tva;
	private BigDecimal ttc;
	private BigDecimal ttc_du;
	private Short traite;
	private String codeDoc;
	private Short bdg;
	private String email;

	
	public TaWlgrNMoins1() {
	}

	public TaWlgrNMoins1(Integer id) {
		this.id = id;
	}

	

	

	public TaWlgrNMoins1(Integer id, String codeClient, String numeroWlgr,
			String licence, String nom, String ad1, String ad2, String cp,
			String ville, String apporteur, Date finE2, String typeE2,
			String optionsE2, BigDecimal reducE2, Integer dureeE2Retard,
			Integer dureeE2, BigDecimal totalHtE2, Date finF2, String typeF2,
			String optionsF2, BigDecimal reducF2, Integer dureeF2Retard,
			Integer dureeF2, BigDecimal totalHtF2, Date finS2, String typeS2,
			String optionsS2, BigDecimal reducS2, Integer dureeS2Retard,
			Integer dureeS2, BigDecimal totalHtS2, BigDecimal totalAvReduc,
			BigDecimal participationCogere, BigDecimal totalHtApReduc,
			Integer bonus, BigDecimal bonusHt, BigDecimal htApresBonus,
			BigDecimal tva, BigDecimal ttc, BigDecimal ttcDu, Short traite,
			String codeDoc, Short bdg, String email) {
		super();
		this.id = id;
		this.codeClient = codeClient;
		this.numeroWlgr = numeroWlgr;
		this.licence = licence;
		this.nom = nom;
		this.ad1 = ad1;
		this.ad2 = ad2;
		this.cp = cp;
		this.ville = ville;
		this.apporteur = apporteur;
		this.finE2 = finE2;
		this.typeE2 = typeE2;
		this.optionsE2 = optionsE2;
		this.reducE2 = reducE2;
		this.dureeE2Retard = dureeE2Retard;
		this.dureeE2 = dureeE2;
		this.totalHtE2 = totalHtE2;
		this.finF2 = finF2;
		this.typeF2 = typeF2;
		this.optionsF2 = optionsF2;
		this.reducF2 = reducF2;
		this.dureeF2Retard = dureeF2Retard;
		this.dureeF2 = dureeF2;
		this.totalHtF2 = totalHtF2;
		this.finS2 = finS2;
		this.typeS2 = typeS2;
		this.optionsS2 = optionsS2;
		this.reducS2 = reducS2;
		this.dureeS2Retard = dureeS2Retard;
		this.dureeS2 = dureeS2;
		this.totalHtS2 = totalHtS2;
		this.totalAvReduc = totalAvReduc;
		this.participationCogere = participationCogere;
		this.totalHtApReduc = totalHtApReduc;
		this.bonus = bonus;
		this.bonusHt = bonusHt;
		this.htApresBonus = htApresBonus;
		this.tva = tva;
		this.ttc = ttc;
		ttc_du = ttcDu;
		this.traite = traite;
		this.codeDoc = codeDoc;
		this.bdg = bdg;
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "code_client", length = 4)
	public String getCodeClient() {
		return this.codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	@Column(name = "numero_wlgr", length = 8)
	public String getNumeroWlgr() {
		return this.numeroWlgr;
	}

	public void setNumeroWlgr(String numeroWlgr) {
		this.numeroWlgr = numeroWlgr;
	}

	@Column(name = "licence", length = 12)
	public String getLicence() {
		return this.licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	@Column(name = "nom", length = 50)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name = "ad1", length = 100)
	public String getAd1() {
		return this.ad1;
	}

	public void setAd1(String ad1) {
		this.ad1 = ad1;
	}

	@Column(name = "ad2", length = 100)
	public String getAd2() {
		return this.ad2;
	}

	public void setAd2(String ad2) {
		this.ad2 = ad2;
	}

	@Column(name = "cp", length = 5)
	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	@Column(name = "ville", length = 50)
	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Column(name = "apporteur", length = 50)
	public String getApporteur() {
		return this.apporteur;
	}

	public void setApporteur(String apporteur) {
		this.apporteur = apporteur;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fin_e2", length = 10)
	public Date getFinE2() {
		return this.finE2;
	}

	public void setFinE2(Date finE2) {
		this.finE2 = finE2;
	}

	@Column(name = "type_e2", length = 1)
	public String getTypeE2() {
		return this.typeE2;
	}

	public void setTypeE2(String typeE2) {
		this.typeE2 = typeE2;
	}

	@Column(name = "options_e2", length = 10)
	public String getOptionsE2() {
		return this.optionsE2;
	}

	public void setOptionsE2(String optionsE2) {
		this.optionsE2 = optionsE2;
	}

	@Column(name = "reduc_e2", precision = 15)
	public BigDecimal getReducE2() {
		return this.reducE2;
	}

	public void setReducE2(BigDecimal reducE2) {
		this.reducE2 = reducE2;
	}

	@Column(name = "duree_e2_retard")
	public Integer getDureeE2Retard() {
		return this.dureeE2Retard;
	}

	public void setDureeE2Retard(Integer dureeE2Retard) {
		this.dureeE2Retard = dureeE2Retard;
	}

	@Column(name = "duree_e2")
	public Integer getDureeE2() {
		return this.dureeE2;
	}

	public void setDureeE2(Integer dureeE2) {
		this.dureeE2 = dureeE2;
	}

	@Column(name = "total_ht_e2", precision = 15)
	public BigDecimal getTotalHtE2() {
		return this.totalHtE2;
	}

	public void setTotalHtE2(BigDecimal totalHtE2) {
		this.totalHtE2 = totalHtE2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fin_f2", length = 10)
	public Date getFinF2() {
		return this.finF2;
	}

	public void setFinF2(Date finF2) {
		this.finF2 = finF2;
	}

	@Column(name = "type_f2", length = 1)
	public String getTypeF2() {
		return this.typeF2;
	}

	public void setTypeF2(String typeF2) {
		this.typeF2 = typeF2;
	}

	@Column(name = "options_f2", length = 10)
	public String getOptionsF2() {
		return this.optionsF2;
	}

	public void setOptionsF2(String optionsF2) {
		this.optionsF2 = optionsF2;
	}

	@Column(name = "reduc_f2", precision = 15)
	public BigDecimal getReducF2() {
		return this.reducF2;
	}

	public void setReducF2(BigDecimal reducF2) {
		this.reducF2 = reducF2;
	}

	@Column(name = "duree_f2_retard")
	public Integer getDureeF2Retard() {
		return this.dureeF2Retard;
	}

	public void setDureeF2Retard(Integer dureeF2Retard) {
		this.dureeF2Retard = dureeF2Retard;
	}

	@Column(name = "duree_f2")
	public Integer getDureeF2() {
		return this.dureeF2;
	}

	public void setDureeF2(Integer dureeF2) {
		this.dureeF2 = dureeF2;
	}

	@Column(name = "total_ht_f2", precision = 15)
	public BigDecimal getTotalHtF2() {
		return this.totalHtF2;
	}

	public void setTotalHtF2(BigDecimal totalHtF2) {
		this.totalHtF2 = totalHtF2;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fin_s2", length = 10)
	public Date getFinS2() {
		return this.finS2;
	}

	public void setFinS2(Date finS2) {
		this.finS2 = finS2;
	}

	@Column(name = "type_s2", length = 1)
	public String getTypeS2() {
		return this.typeS2;
	}

	public void setTypeS2(String typeS2) {
		this.typeS2 = typeS2;
	}

	@Column(name = "options_s2", length = 10)
	public String getOptionsS2() {
		return this.optionsS2;
	}

	public void setOptionsS2(String optionsS2) {
		this.optionsS2 = optionsS2;
	}

	@Column(name = "reduc_s2", precision = 15)
	public BigDecimal getReducS2() {
		return this.reducS2;
	}

	public void setReducS2(BigDecimal reducS2) {
		this.reducS2 = reducS2;
	}

	@Column(name = "duree_s2_retard")
	public Integer getDureeS2Retard() {
		return this.dureeS2Retard;
	}

	public void setDureeS2Retard(Integer dureeS2Retard) {
		this.dureeS2Retard = dureeS2Retard;
	}

	@Column(name = "duree_s2")
	public Integer getDureeS2() {
		return this.dureeS2;
	}

	public void setDureeS2(Integer dureeS2) {
		this.dureeS2 = dureeS2;
	}

	@Column(name = "total_ht_s2", precision = 15)
	public BigDecimal getTotalHtS2() {
		return this.totalHtS2;
	}

	public void setTotalHtS2(BigDecimal totalHtS2) {
		this.totalHtS2 = totalHtS2;
	}

	@Column(name = "participation_cogere", precision = 15)
	public BigDecimal getParticipationCogere() {
		return this.participationCogere;
	}

	public void setParticipationCogere(BigDecimal participationCogere) {
		this.participationCogere = participationCogere;
	}

	@Column(name = "total_ht_ap_redu", precision = 15)
	public BigDecimal getTotalHtApReduc() {
		return this.totalHtApReduc;
	}

	@Column(name = "total_av_reduc", precision = 15)
	public BigDecimal getTotalAvReduc() {
		return totalAvReduc;
	}

	public void setTotalAvReduc(BigDecimal totalAvReduc) {
		this.totalAvReduc = totalAvReduc;
	}

	@Column(name = "bonus", precision = 15)
	public Integer getBonus() {
		return bonus;
	}

	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}

	@Column(name = "bonus_ht", precision = 15)
	public BigDecimal getBonusHt() {
		return bonusHt;
	}

	public void setBonusHt(BigDecimal bonusHt) {
		this.bonusHt = bonusHt;
	}

	@Column(name = "ht_apres_bonus", precision = 15)
	public BigDecimal getHtApresBonus() {
		return htApresBonus;
	}

	public void setHtApresBonus(BigDecimal htApresBonus) {
		this.htApresBonus = htApresBonus;
	}

	public void setTotalHtApReduc(BigDecimal totalHt) {
		this.totalHtApReduc = totalHt;
	}

	@Column(name = "tva", precision = 15)
	public BigDecimal getTva() {
		return this.tva;
	}

	public void setTva(BigDecimal tva) {
		this.tva = tva;
	}

	@Column(name = "ttc", precision = 15)
	public BigDecimal getTtc() {
		return this.ttc;
	}

	public void setTtc(BigDecimal ttc) {
		this.ttc = ttc;
	}

	@Column(name = "traite")
	public Short getTraite() {
		return this.traite;
	}

	public void setTraite(Short traite) {
		this.traite = traite;
	}

	@Column(name = "code_doc", length = 20)
	public String getCodeDoc() {
		return this.codeDoc;
	}

	public void setCodeDoc(String codeDoc) {
		this.codeDoc = codeDoc;
	}

	@Column(name = "bdg")
	public Short getBdg() {
		return bdg;
	}

	public void setBdg(Short bdg) {
		this.bdg = bdg;
	}
	
	@Column(name = "email", length = 100)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getTtc_du() {
		return ttc_du;
	}

	public void setTtc_du(BigDecimal ttcDu) {
		ttc_du = ttcDu;
	}

}