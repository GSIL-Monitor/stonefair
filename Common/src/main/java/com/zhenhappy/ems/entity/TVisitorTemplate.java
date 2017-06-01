package com.zhenhappy.ems.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * TArticle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "visitor_Template"
	, schema = "dbo"
)
public class TVisitorTemplate implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private Integer id;
	private String tpl_key;
	private String tpl_value;
	private String tpl_description;

	// Constructors

	/** default constructor */
	public TVisitorTemplate() {
	}

	/** minimal constructor */
	public TVisitorTemplate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TVisitorTemplate(String tpl_key, String tpl_value, String tpl_description) {
		this.tpl_key = tpl_key;
		this.tpl_value = tpl_value;
		this.tpl_description = tpl_description;
	}

	// Property accessors
	@Id
    @GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "tpl_key", length = 200)
	public String getTpl_key() {
		return tpl_key;
	}

	public void setTpl_key(String tpl_key) {
		this.tpl_key = tpl_key;
	}

	@Column(name = "tpl_value")
	public String getTpl_value() {
		return tpl_value;
	}

	public void setTpl_value(String tpl_value) {
		this.tpl_value = tpl_value;
	}

	@Column(name = "tpl_description")
	public String getTpl_description() {
		return tpl_description;
	}

	public void setTpl_description(String tpl_description) {
		this.tpl_description = tpl_description;
	}

	@Override
	public String toString() {
		return "TVisitorTemplate [id=" + id + ", tpl_key=" + tpl_key + ", tpl_value="
				+ tpl_value + ", tpl_description=" + tpl_description + "]";
	}
	
}