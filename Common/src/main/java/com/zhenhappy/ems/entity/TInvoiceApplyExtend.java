package com.zhenhappy.ems.entity;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-10-31.
 */
@Entity
@Table(name = "t_invoice_apply", schema = "dbo")
public class TInvoiceApplyExtend{
    private Integer id;
    private String exhibitorNo;
    private String invoiceNo;
    private String title;
    private Integer eid;
    private Date createTime;
    private Integer invoice_flag;   //0:不开发票；1：普通发票；2：增值发票；3：无法再开发票
    private String invoice_company;
    private String invoice_taxpayer_no;
    private String invoice_bank_name;
    private String invoice_bank_account;
    private String invoice_company_address;
    private String invoice_company_tel;
    private String invoice_company_contact;
    private String invoice_general_taxpayer_flag;
    private String invoice_general_tax_type;
    private String invoice_image_address;

    public TInvoiceApplyExtend() {
		super();
	}

	public TInvoiceApplyExtend(Integer id, String exhibitorNo, String invoiceNo, String title,
                               Integer eid, Date createTime, Integer invoice_flag, String invoice_company,
                               String invoice_taxpayer_no, String invoice_bank_name, String invoice_bank_account,
                               String invoice_company_address, String invoice_company_tel, String invoice_company_contact,
                               String invoice_general_taxpayer_flag, String invoice_general_tax_type, String invoice_image_address) {
		super();
        this.id = id;
		this.exhibitorNo = exhibitorNo;
		this.invoiceNo = invoiceNo;
		this.title = title;
		this.eid = eid;
		this.createTime = createTime;
        this.invoice_flag = invoice_flag;
        this.invoice_company = invoice_company;
        this.invoice_taxpayer_no = invoice_taxpayer_no;
        this.invoice_bank_name = invoice_bank_name;
        this.invoice_bank_account = invoice_bank_account;
        this.invoice_company_address = invoice_company_address;
        this.invoice_company_tel = invoice_company_tel;
        this.invoice_company_contact = invoice_company_contact;
        this.invoice_general_taxpayer_flag = invoice_general_taxpayer_flag;
        this.invoice_general_tax_type = invoice_general_tax_type;
        this.invoice_image_address= invoice_image_address;
	}



	@Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "exhibitor_no")
    public String getExhibitorNo() {
        return exhibitorNo;
    }

    public void setExhibitorNo(String exhibitorNo) {
        this.exhibitorNo = exhibitorNo;
    }

    @Basic
    @Column(name = "invoice_no")
    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "eid")
    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "invoice_flag")
    public Integer getInvoice_flag() {
        return invoice_flag;
    }

    public void setInvoice_flag(Integer invoice_flag) {
        this.invoice_flag = invoice_flag;
    }

    @Column(name = "invoice_company")
    public String getInvoice_company() {
        return invoice_company;
    }

    public void setInvoice_company(String invoice_company) {
        this.invoice_company = invoice_company;
    }

    @Column(name = "invoice_taxpayer_no")
    public String getInvoice_taxpayer_no() {
        return invoice_taxpayer_no;
    }

    public void setInvoice_taxpayer_no(String invoice_taxpayer_no) {
        this.invoice_taxpayer_no = invoice_taxpayer_no;
    }

    @Column(name = "invoice_bank_name")
    public String getInvoice_bank_name() {
        return invoice_bank_name;
    }

    public void setInvoice_bank_name(String invoice_bank_name) {
        this.invoice_bank_name = invoice_bank_name;
    }

    @Column(name = "invoice_bank_account")
    public String getInvoice_bank_account() {
        return invoice_bank_account;
    }

    public void setInvoice_bank_account(String invoice_bank_account) {
        this.invoice_bank_account = invoice_bank_account;
    }

    @Column(name = "invoice_company_address")
    public String getInvoice_company_address() {
        return invoice_company_address;
    }

    public void setInvoice_company_address(String invoice_company_address) {
        this.invoice_company_address = invoice_company_address;
    }

    @Column(name = "invoice_company_tel")
    public String getInvoice_company_tel() {
        return invoice_company_tel;
    }

    public void setInvoice_company_tel(String invoice_company_tel) {
        this.invoice_company_tel = invoice_company_tel;
    }

    @Column(name = "invoice_company_contact")
    public String getInvoice_company_contact() {
        return invoice_company_contact;
    }

    public void setInvoice_company_contact(String invoice_company_contact) {
        this.invoice_company_contact = invoice_company_contact;
    }

    @Column(name = "invoice_general_taxpayer_flag")
    public String getInvoice_general_taxpayer_flag() {
        return invoice_general_taxpayer_flag;
    }

    public void setInvoice_general_taxpayer_flag(String invoice_general_taxpayer_flag) {
        this.invoice_general_taxpayer_flag = invoice_general_taxpayer_flag;
    }

    @Column(name = "invoice_general_tax_type")
    public String getInvoice_general_tax_type() {
        return invoice_general_tax_type;
    }

    public void setInvoice_general_tax_type(String invoice_general_tax_type) {
        this.invoice_general_tax_type = invoice_general_tax_type;
    }

    @Column(name = "invoice_image_address")
    public String getInvoice_image_address() {
        return invoice_image_address;
    }

    public void setInvoice_image_address(String invoice_image_address) {
        this.invoice_image_address = invoice_image_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TInvoiceApplyExtend that = (TInvoiceApplyExtend) o;

        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (eid != null ? !eid.equals(that.eid) : that.eid != null) return false;
        if (exhibitorNo != null ? !exhibitorNo.equals(that.exhibitorNo) : that.exhibitorNo != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (invoiceNo != null ? !invoiceNo.equals(that.invoiceNo) : that.invoiceNo != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (exhibitorNo != null ? exhibitorNo.hashCode() : 0);
        result = 31 * result + (invoiceNo != null ? invoiceNo.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (eid != null ? eid.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
