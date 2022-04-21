package fr.legrain.SupportAbon.dao;

// Generated 11 août 2009 15:52:23 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import fr.legrain.articles.dao.TaArticle;
import fr.legrain.articles.dao.TaTArticle;
import fr.legrain.articles.dao.TaTSupport;
import fr.legrain.validator.LgrHibernateValidated;


/**
 * TaWlgr generated by hbm2java
 */
@Entity
@Table(name = "ta_option_abon")

public  class TaOptionAbon implements java.io.Serializable {

   
	private Integer idOptionAbon;
	private String optionAbon;
    private TaArticle taArticle;
    private TaTSupport taTArticle;

    
    private Set<TaROptionAbon> taROptionAbons = new HashSet<TaROptionAbon>(0);

	
	public TaOptionAbon() {
	}

	public TaOptionAbon(Integer idOptionAbon) {
		this.idOptionAbon = idOptionAbon;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_option_abon", unique = true)
	public Integer getIdOptionAbon() {
		return this.idOptionAbon;
	}

	public void setIdOptionAbon(Integer idOptionAbon) {
		this.idOptionAbon = idOptionAbon;
	}

	@Column(name = "option_abon")
	@LgrHibernateValidated(champEntite = "", champBd = "option_abon",table = "ta_option_abon",clazz = TaOptionAbon.class)
	public String getOptionAbon() {
		return this.optionAbon;
	}

	public void setOptionAbon(String optionAbon) {
		this.optionAbon = optionAbon;
	}

	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_article")
	@LgrHibernateValidated(champEntite = "", champBd = "id_article",table = "ta_article",clazz = TaArticle.class)
	public TaArticle getTaArticle() {
		return taArticle;
	}

	public void setTaArticle(TaArticle taArticle) {
		this.taArticle = taArticle;
	}


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_t_article")
	@LgrHibernateValidated(champEntite = "", champBd = "id_t_article",table = "ta_t_article",clazz = TaTSupport.class)
	public TaTSupport getTaTArticle() {
		return taTArticle;
	}

	public void setTaTArticle(TaTSupport taTArticle) {
		this.taTArticle = taTArticle;
	}
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "taOptionAbon")
	public Set<TaROptionAbon> getTaROptionAbons() {
		return this.taROptionAbons;
	}

	public void setTaROptionAbons(Set<TaROptionAbon> taROptionAbons) {
		this.taROptionAbons = taROptionAbons;
	}


	
}