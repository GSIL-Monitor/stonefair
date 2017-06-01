package com.zhenhappy.ems.manager.dto;

import com.zhenhappy.ems.dto.BaseRequest;

/**
 * Created by wangxd on 2016-11-28.
 */
public class ExhibitorInvitationTransaction extends BaseRequest {
	private String boothNumber;
	private String companyZh;
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

	public String getBoothNumber() {
		return boothNumber;
	}

	public void setBoothNumber(String boothNumber) {
		this.boothNumber = boothNumber;
	}

	public String getCompanyZh() {
		return companyZh;
	}

	public void setCompanyZh(String companyZh) {
		this.companyZh = companyZh;
	}

	public String getInvoice_bank_account() {
		return invoice_bank_account;
	}

	public void setInvoice_bank_account(String invoice_bank_account) {
		this.invoice_bank_account = invoice_bank_account;
	}

	public String getInvoice_bank_name() {
		return invoice_bank_name;
	}

	public void setInvoice_bank_name(String invoice_bank_name) {
		this.invoice_bank_name = invoice_bank_name;
	}

	public String getInvoice_company() {
		return invoice_company;
	}

	public void setInvoice_company(String invoice_company) {
		this.invoice_company = invoice_company;
	}

	public String getInvoice_company_address() {
		return invoice_company_address;
	}

	public void setInvoice_company_address(String invoice_company_address) {
		this.invoice_company_address = invoice_company_address;
	}

	public String getInvoice_company_contact() {
		return invoice_company_contact;
	}

	public void setInvoice_company_contact(String invoice_company_contact) {
		this.invoice_company_contact = invoice_company_contact;
	}

	public String getInvoice_company_tel() {
		return invoice_company_tel;
	}

	public void setInvoice_company_tel(String invoice_company_tel) {
		this.invoice_company_tel = invoice_company_tel;
	}

	public String getInvoice_general_tax_type() {
		return invoice_general_tax_type;
	}

	public void setInvoice_general_tax_type(String invoice_general_tax_type) {
		this.invoice_general_tax_type = invoice_general_tax_type;
	}

	public String getInvoice_general_taxpayer_flag() {
		return invoice_general_taxpayer_flag;
	}

	public void setInvoice_general_taxpayer_flag(String invoice_general_taxpayer_flag) {
		this.invoice_general_taxpayer_flag = invoice_general_taxpayer_flag;
	}

	public String getInvoice_image_address() {
		return invoice_image_address;
	}

	public void setInvoice_image_address(String invoice_image_address) {
		this.invoice_image_address = invoice_image_address;
	}

	public String getInvoice_taxpayer_no() {
		return invoice_taxpayer_no;
	}

	public void setInvoice_taxpayer_no(String invoice_taxpayer_no) {
		this.invoice_taxpayer_no = invoice_taxpayer_no;
	}
}
