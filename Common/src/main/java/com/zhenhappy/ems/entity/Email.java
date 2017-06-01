package com.zhenhappy.ems.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Email {
	private Integer customerId;
	private String name;
	private String boothNumber;
	private String company;
	private String receivers;
	private String backupReceivers;
    private int gender;
	private String fromAddress = "service@stonefair.org.cn";
	private int flag; //0:表示展会观众；1：表示专业采购商
	private int country; //0:表示中国；1：表示国外
	private String followName; //随行人员
	private String regID; //预登记编号
	private String subject;
	private String register_subject_cn;
	private String register_content_cn;
	private String invite_subject_cn;
	private String invite_content_cn;
	private String register_subject_en;
	private String register_content_en;
	private String invite_subject_en;
	private String invite_content_en;
	private Boolean isUseTemplate;
	private String position;
	private String policeDecareCn;
	private String policeDecareEn;
	private String CheckingNo;
	private String mail_register_subject_cn_unpro;
	private String mail_register_content_cn_unpro;
	private String mail_register_subject_en_unpro;
	private String mail_register_content_en_unpro;
	private Integer emailType; //邮件种类 0：表示普通客商  1：表示专业客商

	public String getBackupReceivers() {
		return backupReceivers;
	}

	public void setBackupReceivers(String backupReceivers) {
		this.backupReceivers = backupReceivers;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getPoliceDecareEn() {
		return policeDecareEn;
	}

	public void setPoliceDecareEn(String policeDecareEn) {
		this.policeDecareEn = policeDecareEn;
	}

	public String getPoliceDecareCn() {
		return policeDecareCn;
	}

	public void setPoliceDecareCn(String policeDecareCn) {
		this.policeDecareCn = policeDecareCn;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getUseTemplate() {
		return isUseTemplate;
	}

	public void setUseTemplate(Boolean useTemplate) {
		isUseTemplate = useTemplate;
	}

	public String getCheckingNo() {
		return CheckingNo;
	}

	public void setCheckingNo(String checkingNo) {
		CheckingNo = checkingNo;
	}

	public Email() {
		super();
	}

	public Email(String name, String boothNumber, String company, String receivers, String backupReceivers, String subject,
				 int flag, int country, String followName, String RegID, String checkingNo, String mail_register_subject_cn_unpro,
				 String mail_register_content_cn_unpro, String mail_register_subject_en_unpro, String mail_register_content_en_unpro,
				 Integer emailType) {
		super();
		this.name = name;
		this.boothNumber = boothNumber;
		this.company = company;
		this.subject = subject;
		this.receivers = receivers;
		this.backupReceivers = backupReceivers;
		this.fromAddress = "service@stonefair.org.cn";
		this.flag = flag;
		this.country = country;
		this.followName = followName;
		this.regID = RegID;
		this.CheckingNo = checkingNo;
		this.mail_register_subject_cn_unpro = mail_register_subject_cn_unpro;
		this.mail_register_content_cn_unpro = mail_register_content_cn_unpro;
		this.mail_register_subject_en_unpro = mail_register_subject_en_unpro;
		this.mail_register_content_en_unpro = mail_register_content_en_unpro;
		this.emailType = emailType;
	}

	public String getFollowName() {
		return followName;
	}

	public void setFollowName(String followName) {
		this.followName = followName;
	}

	public String getRegID() {
		return regID;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoothNumber() {
		return boothNumber;
	}

	public void setBoothNumber(String boothNumber) {
		this.boothNumber = boothNumber;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getReceivers() {
		return receivers;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderStr(){
        return gender==1?"先生":"女士";
    }

    public String getGenderStrEn(){
        return gender==1?"先生":"女士";
    }

    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(new Date());
    }

    public String getTimeEn(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public Integer getEmailType() {
		return emailType;
	}

	public void setEmailType(Integer emailType) {
		this.emailType = emailType;
	}

	public String getMail_register_content_en_unpro() {
		return mail_register_content_en_unpro;
	}

	public void setMail_register_content_en_unpro(String mail_register_content_en_unpro) {
		this.mail_register_content_en_unpro = mail_register_content_en_unpro;
	}

	public String getMail_register_subject_cn_unpro() {
		return mail_register_subject_cn_unpro;
	}

	public void setMail_register_subject_cn_unpro(String mail_register_subject_cn_unpro) {
		this.mail_register_subject_cn_unpro = mail_register_subject_cn_unpro;
	}

	public String getMail_register_content_cn_unpro() {
		return mail_register_content_cn_unpro;
	}

	public void setMail_register_content_cn_unpro(String mail_register_content_cn_unpro) {
		this.mail_register_content_cn_unpro = mail_register_content_cn_unpro;
	}

	public String getMail_register_subject_en_unpro() {
		return mail_register_subject_en_unpro;
	}

	public void setMail_register_subject_en_unpro(String mail_register_subject_en_unpro) {
		this.mail_register_subject_en_unpro = mail_register_subject_en_unpro;
	}

	public String getRegister_subject_cn() {
		return register_subject_cn;
	}

	public void setRegister_subject_cn(String register_subject_cn) {
		this.register_subject_cn = register_subject_cn;
	}

	public String getRegister_content_cn() {
		return register_content_cn;
	}

	public void setRegister_content_cn(String register_content_cn) {
		this.register_content_cn = register_content_cn;
	}

	public String getInvite_subject_cn() {
		return invite_subject_cn;
	}

	public void setInvite_subject_cn(String invite_subject_cn) {
		this.invite_subject_cn = invite_subject_cn;
	}

	public String getInvite_content_cn() {
		return invite_content_cn;
	}

	public void setInvite_content_cn(String invite_content_cn) {
		this.invite_content_cn = invite_content_cn;
	}

	public String getRegister_subject_en() {
		return register_subject_en;
	}

	public void setRegister_subject_en(String register_subject_en) {
		this.register_subject_en = register_subject_en;
	}

	public String getRegister_content_en() {
		return register_content_en;
	}

	public void setRegister_content_en(String register_content_en) {
		this.register_content_en = register_content_en;
	}

	public String getInvite_subject_en() {
		return invite_subject_en;
	}

	public void setInvite_subject_en(String invite_subject_en) {
		this.invite_subject_en = invite_subject_en;
	}

	public String getInvite_content_en() {
		return invite_content_en;
	}

	public void setInvite_content_en(String invite_content_en) {
		this.invite_content_en = invite_content_en;
	}
}
