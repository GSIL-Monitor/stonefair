package com.zhenhappy.ems.entity;

import com.zhenhappy.ems.entity.Email;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailExtent extends Email{
	private String password;

	public EmailExtent(){

	}

	public EmailExtent(String name, String boothNumber, String company, String receivers,
					   String backupReceivers, String subject, int flag, int country, String followName, String RegID,
					   String checkingNo, String mail_register_subject_cn_unpro, String mail_register_content_cn_unpro,
					   String mail_register_subject_en_unpro, String mail_register_content_en_unpro, Integer emailType, String password) {
		super(name, boothNumber, company, receivers, backupReceivers, subject, flag, country, followName, RegID,
				checkingNo, mail_register_subject_cn_unpro, mail_register_content_cn_unpro, mail_register_subject_en_unpro,
				mail_register_content_en_unpro, emailType);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
