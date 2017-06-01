package com.zhenhappy.ems.manager.entity.backupinfo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.Date;

/**
 * WcustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exhibitor_Info_Backup", schema = "dbo")
public class TExhibitorBackupInfoTemp implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer eid;
    private String username;
    private String password;
    private Integer level;
    private Integer area;
    private String prodClass;
    private Date lastLoginTime;
    private String lastLoginIp;
    private Integer isLogout;
    private Integer createUser;
    private Date createTime;
    private Integer updateUser;
    private Date updateTime;
    private Integer tag;
    private Integer province;
    private Integer country;
    private Integer group;
    private String contractId;
    private String exhibitionArea;
    private String exhibitor_type;  //0或null：表示普通展商；1：表示素食展；2：表示泰国展
    private Integer isLogin; //是否登录过。0：表示未登录；1或null表示登录过；
    private Integer send_invitation_flag;  //是否发送展商邀请涵
    private Integer exhibitor_info_einfoid;
    private String exhibitor_info_organization_code;
    private String exhibitor_info_company;
    private String exhibitor_info_company_en;
    private String exhibitor_info_company_t;
    private String exhibitor_info_phone;
    private String exhibitor_info_fax;
    private String exhibitor_info_email;
    private String exhibitor_info_website;
    private String exhibitor_info_zipcode;
    private String exhibitor_info_main_product;
    private String exhibitor_info_main_product_en;
    private String exhibitor_info_logo;
    private String exhibitor_info_mark;
    private Integer exhibitor_info_is_delete;
    private Date exhibitor_info_create_time;
    private Date exhibitor_info_update_time;
    private Integer exhibitor_info_admin_user;
    private Date exhibitor_info_admin_update_time;
    private String exhibitor_info_address;
    private String exhibitor_info_address_en;
    private String exhibitor_info_meipai;
    private String exhibitor_info_meipai_en;
    private String exhibitor_info_emark;
    private String exhibitor_info_company_hignlight;
    private String exhibitor_info_email_address;
    private String exhibitor_info_email_contact;
    private String exhibitor_info_email_telphone;
    private Integer exhibitor_term_join_term;
    private String exhibitor_term_booth_number;
    private String exhibitor_term_mark;
    private Integer exhibitor_term_is_delete;
    private Integer exhibitor_term_create_user;
    private Date exhibitor_term_create_time;
    private Integer exhibitor_term_update_user;
    private Date exhibitor_term_update_time;
    private String exhibitor_booth_booth_number;
    private String exhibitor_booth_exhibition_area;
    private String exhibitor_booth_mark;
    private Integer exhibitor_booth_create_user;
    private Date exhibitor_booth_create_time;
    private Integer exhibitor_booth_update_user;
    private Date exhibitor_booth_update_time;
    private String exhibitor_product_type;                  //从产品列表获取
    private String exhibitor_invoice_apply_exhibitor_no;
    private String exhibitor_invoice_apply_invoice_no;
    private String exhibitor_invoice_apply_title;
    private Date exhibitor_invoice_apply_create_time;
    private Integer exhibitor_invoice_apply_invoice_flag;   //0:不开发票；1：普通发票；2：增值发票；3：无法再开发票
    private String exhibitor_invoice_apply_invoice_company;
    private String exhibitor_invoice_apply_invoice_taxpayer_no;
    private String exhibitor_invoice_apply_invoice_bank_name;
    private String exhibitor_invoice_apply_invoice_bank_account;
    private String exhibitor_invoice_apply_invoice_company_address;
    private String exhibitor_invoice_apply_invoice_company_tel;
    private String exhibitor_invoice_apply_invoice_company_contact;
    private String exhibitor_invoice_apply_invoice_general_taxpayer_flag;
    private String exhibitor_invoice_apply_invoice_general_tax_type;
    private String exhibitor_invoice_apply_invoice_image_address;
    private String exhibitor_product_name;
    private String exhibitor_product_model;
    private String exhibitor_product_origin;
    private String exhibitor_product_key_words;
    private String exhibitor_product_description;
    private String exhibitor_product_brand;
    private String exhibitor_product_spec;
    private String exhibitor_product_count;
    private String exhibitor_product_package_description;
    private String exhibitor_product_price_description;
    private Integer exhibitor_product_flag;
    private Date exhibitor_product_create_time;
    private Date exhibitor_product_update_time;
    private Integer exhibitor_product_admin;
    private Date exhibitor_product_admin_update_time;
    private String exhibitor_product_name_en;
    private String exhibitor_product_model_en;
    private String exhibitor_product_origin_en;
    private String exhibitor_product_key_words_en;
    private String exhibitor_product_description_en;
    private String exhibitor_product_brand_en;
    private String exhibitor_product_spec_en;
    private String exhibitor_product_count_en;
    private String exhibitor_product_package_description_en;
    private String exhibitor_product_price_description_en;
    private String exhibitor_contact_name;
    private String exhibitor_contact_position;
    private String exhibitor_contact_phone;
    private String exhibitor_contact_email;
    private String exhibitor_contact_address;
    private String exhibitor_contact_expressnumber;
    private Integer exhibitor_contact_is_delete;
    private String exhibitor_joiner_name;
    private String exhibitor_joiner_position;
    private String exhibitor_joiner_telphone;
    private String exhibitor_joiner_email;
    private Integer exhibitor_joiner_is_delete;
    private Date exhibitor_joiner_create_time;
    private Integer exhibitor_joiner_admin;
    private Date exhibitor_joiner_admin_update_time;
    private Integer exhibitor_joiner_is_new;//表示是否为本届新增加的参展人员。注：归档后，要重置为往届，即值为0或NULL
    private String exhibitor_visa_passport_name;
    private Integer exhibitor_visa_gender;
    private String exhibitor_visa_nationality;
    private String exhibitor_visa_job_title;
    private String exhibitor_visa_company_name;
    private String exhibitor_visa_booth_no;
    private String exhibitor_visa_detailed_address;
    private String exhibitor_visa_tel;
    private String exhibitor_visa_fax;
    private String exhibitor_visa_email;
    private String exhibitor_visa_company_website;
    private String exhibitor_visa_passport_no;
    @JSONField(format="yyyy-MM-dd")
    private Date exhibitor_visa_exp_date;
    @JSONField(format="yyyy-MM-dd")
    private Date exhibitor_visa_birth;
    private String exhibitor_visa_apply_for;
    @JSONField(format="yyyy-MM-dd")
    private Date exhibitor_visa_fromDate;
    @JSONField(format="yyyy-MM-dd")
    private Date exhibitor_visa_toDate;
    private String exhibitor_visa_passport_page;
    private String exhibitor_visa_business_license;
    private Integer exhibitor_visa_joiner_id;
    private Integer exhibitor_visa_status;  //0：表示未提交（已存在记录）；1：表示提交；2：表示未提交（新增加记录）
    private Date exhibitor_visa_create_time;
    private Date exhibitor_visa_update_time;
    private String exhibitor_visa_address;

    // Constructors

    /** default constructor */
    public TExhibitorBackupInfoTemp() {
    }

    public TExhibitorBackupInfoTemp(Integer area, String contractId, Integer country, Date createTime, Integer createUser, Integer eid,
                                    String exhibitionArea, String exhibitor_booth_booth_number, Date exhibitor_booth_create_time,
                                    Integer exhibitor_booth_create_user, String exhibitor_booth_exhibition_area, String exhibitor_booth_mark,
                                    Date exhibitor_booth_update_time, Integer exhibitor_booth_update_user, String exhibitor_contact_address,
                                    String exhibitor_contact_email, String exhibitor_contact_expressnumber, Integer exhibitor_contact_is_delete,
                                    String exhibitor_contact_name, String exhibitor_contact_phone, String exhibitor_contact_position,
                                    String exhibitor_info_address, String exhibitor_info_address_en, Date exhibitor_info_admin_update_time,
                                    Integer exhibitor_info_admin_user, String exhibitor_info_company, String exhibitor_info_company_en,
                                    String exhibitor_info_company_hignlight, String exhibitor_info_company_t, Date exhibitor_info_create_time,
                                    Integer exhibitor_info_einfoid, String exhibitor_info_email, String exhibitor_info_email_address,
                                    String exhibitor_info_email_contact, String exhibitor_info_email_telphone, String exhibitor_info_emark,
                                    String exhibitor_info_fax, Integer exhibitor_info_is_delete, String exhibitor_info_logo,
                                    String exhibitor_info_main_product, String exhibitor_info_main_product_en, String exhibitor_info_mark,
                                    String exhibitor_info_meipai, String exhibitor_info_meipai_en, String exhibitor_info_organization_code,
                                    String exhibitor_info_phone, Date exhibitor_info_update_time, String exhibitor_info_website,
                                    String exhibitor_info_zipcode, Date exhibitor_invoice_apply_create_time,
                                    String exhibitor_invoice_apply_exhibitor_no, String exhibitor_invoice_apply_invoice_bank_account,
                                    String exhibitor_invoice_apply_invoice_bank_name, String exhibitor_invoice_apply_invoice_company,
                                    String exhibitor_invoice_apply_invoice_company_address, String exhibitor_invoice_apply_invoice_company_contact,
                                    String exhibitor_invoice_apply_invoice_company_tel, Integer exhibitor_invoice_apply_invoice_flag,
                                    String exhibitor_invoice_apply_invoice_general_tax_type,
                                    String exhibitor_invoice_apply_invoice_general_taxpayer_flag,
                                    String exhibitor_invoice_apply_invoice_image_address, String exhibitor_invoice_apply_invoice_no,
                                    String exhibitor_invoice_apply_invoice_taxpayer_no, String exhibitor_invoice_apply_title,
                                    Integer exhibitor_joiner_admin, Date exhibitor_joiner_admin_update_time, Date exhibitor_joiner_create_time,
                                    String exhibitor_joiner_email, Integer exhibitor_joiner_is_delete, Integer exhibitor_joiner_is_new,
                                    String exhibitor_joiner_name, String exhibitor_joiner_position, String exhibitor_joiner_telphone,
                                    Integer exhibitor_product_admin, Date exhibitor_product_admin_update_time, String exhibitor_product_brand,
                                    String exhibitor_product_brand_en, String exhibitor_product_count, String exhibitor_product_count_en,
                                    Date exhibitor_product_create_time, String exhibitor_product_description,
                                    String exhibitor_product_description_en, Integer exhibitor_product_flag, String exhibitor_product_key_words,
                                    String exhibitor_product_key_words_en, String exhibitor_product_model, String exhibitor_product_model_en,
                                    String exhibitor_product_name, String exhibitor_product_name_en, String exhibitor_product_origin,
                                    String exhibitor_product_origin_en, String exhibitor_product_package_description,
                                    String exhibitor_product_package_description_en, String exhibitor_product_price_description,
                                    String exhibitor_product_price_description_en, String exhibitor_product_spec,
                                    String exhibitor_product_spec_en, String exhibitor_product_type, Date exhibitor_product_update_time,
                                    String exhibitor_term_booth_number, Date exhibitor_term_create_time, Integer exhibitor_term_create_user,
                                    Integer exhibitor_term_is_delete, Integer exhibitor_term_join_term, String exhibitor_term_mark,
                                    Date exhibitor_term_update_time, Integer exhibitor_term_update_user, String exhibitor_type,
                                    String exhibitor_visa_address, String exhibitor_visa_apply_for, Date exhibitor_visa_birth,
                                    String exhibitor_visa_booth_no, String exhibitor_visa_business_license, String exhibitor_visa_company_name,
                                    String exhibitor_visa_company_website, Date exhibitor_visa_create_time, String exhibitor_visa_detailed_address,
                                    String exhibitor_visa_email, Date exhibitor_visa_exp_date, String exhibitor_visa_fax,
                                    Date exhibitor_visa_fromDate, Integer exhibitor_visa_gender, String exhibitor_visa_job_title,
                                    Integer exhibitor_visa_joiner_id, String exhibitor_visa_nationality, String exhibitor_visa_passport_name,
                                    String exhibitor_visa_passport_no, String exhibitor_visa_passport_page, Integer exhibitor_visa_status,
                                    String exhibitor_visa_tel, Date exhibitor_visa_toDate, Date exhibitor_visa_update_time, Integer group,
                                    Integer isLogin, Integer isLogout, String lastLoginIp, Date lastLoginTime, Integer level, String password,
                                    String prodClass, Integer province, Integer send_invitation_flag, Integer tag, Date updateTime,
                                    Integer updateUser, String username) {
        this.area = area;
        this.contractId = contractId;
        this.country = country;
        this.createTime = createTime;
        this.createUser = createUser;
        this.eid = eid;
        this.exhibitionArea = exhibitionArea;
        this.exhibitor_booth_booth_number = exhibitor_booth_booth_number;
        this.exhibitor_booth_create_time = exhibitor_booth_create_time;
        this.exhibitor_booth_create_user = exhibitor_booth_create_user;
        this.exhibitor_booth_exhibition_area = exhibitor_booth_exhibition_area;
        this.exhibitor_booth_mark = exhibitor_booth_mark;
        this.exhibitor_booth_update_time = exhibitor_booth_update_time;
        this.exhibitor_booth_update_user = exhibitor_booth_update_user;
        this.exhibitor_contact_address = exhibitor_contact_address;
        this.exhibitor_contact_email = exhibitor_contact_email;
        this.exhibitor_contact_expressnumber = exhibitor_contact_expressnumber;
        this.exhibitor_contact_is_delete = exhibitor_contact_is_delete;
        this.exhibitor_contact_name = exhibitor_contact_name;
        this.exhibitor_contact_phone = exhibitor_contact_phone;
        this.exhibitor_contact_position = exhibitor_contact_position;
        this.exhibitor_info_address = exhibitor_info_address;
        this.exhibitor_info_address_en = exhibitor_info_address_en;
        this.exhibitor_info_admin_update_time = exhibitor_info_admin_update_time;
        this.exhibitor_info_admin_user = exhibitor_info_admin_user;
        this.exhibitor_info_company = exhibitor_info_company;
        this.exhibitor_info_company_en = exhibitor_info_company_en;
        this.exhibitor_info_company_hignlight = exhibitor_info_company_hignlight;
        this.exhibitor_info_company_t = exhibitor_info_company_t;
        this.exhibitor_info_create_time = exhibitor_info_create_time;
        this.exhibitor_info_einfoid = exhibitor_info_einfoid;
        this.exhibitor_info_email = exhibitor_info_email;
        this.exhibitor_info_email_address = exhibitor_info_email_address;
        this.exhibitor_info_email_contact = exhibitor_info_email_contact;
        this.exhibitor_info_email_telphone = exhibitor_info_email_telphone;
        this.exhibitor_info_emark = exhibitor_info_emark;
        this.exhibitor_info_fax = exhibitor_info_fax;
        this.exhibitor_info_is_delete = exhibitor_info_is_delete;
        this.exhibitor_info_logo = exhibitor_info_logo;
        this.exhibitor_info_main_product = exhibitor_info_main_product;
        this.exhibitor_info_main_product_en = exhibitor_info_main_product_en;
        this.exhibitor_info_mark = exhibitor_info_mark;
        this.exhibitor_info_meipai = exhibitor_info_meipai;
        this.exhibitor_info_meipai_en = exhibitor_info_meipai_en;
        this.exhibitor_info_organization_code = exhibitor_info_organization_code;
        this.exhibitor_info_phone = exhibitor_info_phone;
        this.exhibitor_info_update_time = exhibitor_info_update_time;
        this.exhibitor_info_website = exhibitor_info_website;
        this.exhibitor_info_zipcode = exhibitor_info_zipcode;
        this.exhibitor_invoice_apply_create_time = exhibitor_invoice_apply_create_time;
        this.exhibitor_invoice_apply_exhibitor_no = exhibitor_invoice_apply_exhibitor_no;
        this.exhibitor_invoice_apply_invoice_bank_account = exhibitor_invoice_apply_invoice_bank_account;
        this.exhibitor_invoice_apply_invoice_bank_name = exhibitor_invoice_apply_invoice_bank_name;
        this.exhibitor_invoice_apply_invoice_company = exhibitor_invoice_apply_invoice_company;
        this.exhibitor_invoice_apply_invoice_company_address = exhibitor_invoice_apply_invoice_company_address;
        this.exhibitor_invoice_apply_invoice_company_contact = exhibitor_invoice_apply_invoice_company_contact;
        this.exhibitor_invoice_apply_invoice_company_tel = exhibitor_invoice_apply_invoice_company_tel;
        this.exhibitor_invoice_apply_invoice_flag = exhibitor_invoice_apply_invoice_flag;
        this.exhibitor_invoice_apply_invoice_general_tax_type = exhibitor_invoice_apply_invoice_general_tax_type;
        this.exhibitor_invoice_apply_invoice_general_taxpayer_flag = exhibitor_invoice_apply_invoice_general_taxpayer_flag;
        this.exhibitor_invoice_apply_invoice_image_address = exhibitor_invoice_apply_invoice_image_address;
        this.exhibitor_invoice_apply_invoice_no = exhibitor_invoice_apply_invoice_no;
        this.exhibitor_invoice_apply_invoice_taxpayer_no = exhibitor_invoice_apply_invoice_taxpayer_no;
        this.exhibitor_invoice_apply_title = exhibitor_invoice_apply_title;
        this.exhibitor_joiner_admin = exhibitor_joiner_admin;
        this.exhibitor_joiner_admin_update_time = exhibitor_joiner_admin_update_time;
        this.exhibitor_joiner_create_time = exhibitor_joiner_create_time;
        this.exhibitor_joiner_email = exhibitor_joiner_email;
        this.exhibitor_joiner_is_delete = exhibitor_joiner_is_delete;
        this.exhibitor_joiner_is_new = exhibitor_joiner_is_new;
        this.exhibitor_joiner_name = exhibitor_joiner_name;
        this.exhibitor_joiner_position = exhibitor_joiner_position;
        this.exhibitor_joiner_telphone = exhibitor_joiner_telphone;
        this.exhibitor_product_admin = exhibitor_product_admin;
        this.exhibitor_product_admin_update_time = exhibitor_product_admin_update_time;
        this.exhibitor_product_brand = exhibitor_product_brand;
        this.exhibitor_product_brand_en = exhibitor_product_brand_en;
        this.exhibitor_product_count = exhibitor_product_count;
        this.exhibitor_product_count_en = exhibitor_product_count_en;
        this.exhibitor_product_create_time = exhibitor_product_create_time;
        this.exhibitor_product_description = exhibitor_product_description;
        this.exhibitor_product_description_en = exhibitor_product_description_en;
        this.exhibitor_product_flag = exhibitor_product_flag;
        this.exhibitor_product_key_words = exhibitor_product_key_words;
        this.exhibitor_product_key_words_en = exhibitor_product_key_words_en;
        this.exhibitor_product_model = exhibitor_product_model;
        this.exhibitor_product_model_en = exhibitor_product_model_en;
        this.exhibitor_product_name = exhibitor_product_name;
        this.exhibitor_product_name_en = exhibitor_product_name_en;
        this.exhibitor_product_origin = exhibitor_product_origin;
        this.exhibitor_product_origin_en = exhibitor_product_origin_en;
        this.exhibitor_product_package_description = exhibitor_product_package_description;
        this.exhibitor_product_package_description_en = exhibitor_product_package_description_en;
        this.exhibitor_product_price_description = exhibitor_product_price_description;
        this.exhibitor_product_price_description_en = exhibitor_product_price_description_en;
        this.exhibitor_product_spec = exhibitor_product_spec;
        this.exhibitor_product_spec_en = exhibitor_product_spec_en;
        this.exhibitor_product_type = exhibitor_product_type;
        this.exhibitor_product_update_time = exhibitor_product_update_time;
        this.exhibitor_term_booth_number = exhibitor_term_booth_number;
        this.exhibitor_term_create_time = exhibitor_term_create_time;
        this.exhibitor_term_create_user = exhibitor_term_create_user;
        this.exhibitor_term_is_delete = exhibitor_term_is_delete;
        this.exhibitor_term_join_term = exhibitor_term_join_term;
        this.exhibitor_term_mark = exhibitor_term_mark;
        this.exhibitor_term_update_time = exhibitor_term_update_time;
        this.exhibitor_term_update_user = exhibitor_term_update_user;
        this.exhibitor_type = exhibitor_type;
        this.exhibitor_visa_address = exhibitor_visa_address;
        this.exhibitor_visa_apply_for = exhibitor_visa_apply_for;
        this.exhibitor_visa_birth = exhibitor_visa_birth;
        this.exhibitor_visa_booth_no = exhibitor_visa_booth_no;
        this.exhibitor_visa_business_license = exhibitor_visa_business_license;
        this.exhibitor_visa_company_name = exhibitor_visa_company_name;
        this.exhibitor_visa_company_website = exhibitor_visa_company_website;
        this.exhibitor_visa_create_time = exhibitor_visa_create_time;
        this.exhibitor_visa_detailed_address = exhibitor_visa_detailed_address;
        this.exhibitor_visa_email = exhibitor_visa_email;
        this.exhibitor_visa_exp_date = exhibitor_visa_exp_date;
        this.exhibitor_visa_fax = exhibitor_visa_fax;
        this.exhibitor_visa_fromDate = exhibitor_visa_fromDate;
        this.exhibitor_visa_gender = exhibitor_visa_gender;
        this.exhibitor_visa_job_title = exhibitor_visa_job_title;
        this.exhibitor_visa_joiner_id = exhibitor_visa_joiner_id;
        this.exhibitor_visa_nationality = exhibitor_visa_nationality;
        this.exhibitor_visa_passport_name = exhibitor_visa_passport_name;
        this.exhibitor_visa_passport_no = exhibitor_visa_passport_no;
        this.exhibitor_visa_passport_page = exhibitor_visa_passport_page;
        this.exhibitor_visa_status = exhibitor_visa_status;
        this.exhibitor_visa_tel = exhibitor_visa_tel;
        this.exhibitor_visa_toDate = exhibitor_visa_toDate;
        this.exhibitor_visa_update_time = exhibitor_visa_update_time;
        this.group = group;
        this.isLogin = isLogin;
        this.isLogout = isLogout;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.level = level;
        this.password = password;
        this.prodClass = prodClass;
        this.province = province;
        this.send_invitation_flag = send_invitation_flag;
        this.tag = tag;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.username = username;
    }

    @Id
    @Column(name = "eid", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getEId() {
        return this.eid;
    }

    public void setEId(Integer eid) {
        this.eid = eid;
    }

    @Column(name = "area")
    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    @Column(name = "contractId")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Column(name = "country")
    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "createUser")
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Column(name = "exhibitionArea")
    public String getExhibitionArea() {
        return exhibitionArea;
    }

    public void setExhibitionArea(String exhibitionArea) {
        this.exhibitionArea = exhibitionArea;
    }

    @Column(name = "exhibitor_booth_booth_number")
    public String getExhibitor_booth_booth_number() {
        return exhibitor_booth_booth_number;
    }

    public void setExhibitor_booth_booth_number(String exhibitor_booth_booth_number) {
        this.exhibitor_booth_booth_number = exhibitor_booth_booth_number;
    }

    @Column(name = "exhibitor_booth_create_time")
    public Date getExhibitor_booth_create_time() {
        return exhibitor_booth_create_time;
    }

    public void setExhibitor_booth_create_time(Date exhibitor_booth_create_time) {
        this.exhibitor_booth_create_time = exhibitor_booth_create_time;
    }

    @Column(name = "exhibitor_booth_create_user")
    public Integer getExhibitor_booth_create_user() {
        return exhibitor_booth_create_user;
    }

    public void setExhibitor_booth_create_user(Integer exhibitor_booth_create_user) {
        this.exhibitor_booth_create_user = exhibitor_booth_create_user;
    }

    @Column(name = "exhibitor_booth_exhibition_area")
    public String getExhibitor_booth_exhibition_area() {
        return exhibitor_booth_exhibition_area;
    }

    public void setExhibitor_booth_exhibition_area(String exhibitor_booth_exhibition_area) {
        this.exhibitor_booth_exhibition_area = exhibitor_booth_exhibition_area;
    }

    @Column(name = "exhibitor_booth_mark")
    public String getExhibitor_booth_mark() {
        return exhibitor_booth_mark;
    }

    public void setExhibitor_booth_mark(String exhibitor_booth_mark) {
        this.exhibitor_booth_mark = exhibitor_booth_mark;
    }

    @Column(name = "exhibitor_booth_update_time")
    public Date getExhibitor_booth_update_time() {
        return exhibitor_booth_update_time;
    }

    public void setExhibitor_booth_update_time(Date exhibitor_booth_update_time) {
        this.exhibitor_booth_update_time = exhibitor_booth_update_time;
    }

    @Column(name = "exhibitor_booth_update_user")
    public Integer getExhibitor_booth_update_user() {
        return exhibitor_booth_update_user;
    }

    public void setExhibitor_booth_update_user(Integer exhibitor_booth_update_user) {
        this.exhibitor_booth_update_user = exhibitor_booth_update_user;
    }

    @Column(name = "exhibitor_contact_address")
    public String getExhibitor_contact_address() {
        return exhibitor_contact_address;
    }

    public void setExhibitor_contact_address(String exhibitor_contact_address) {
        this.exhibitor_contact_address = exhibitor_contact_address;
    }

    @Column(name = "exhibitor_contact_email")
    public String getExhibitor_contact_email() {
        return exhibitor_contact_email;
    }

    public void setExhibitor_contact_email(String exhibitor_contact_email) {
        this.exhibitor_contact_email = exhibitor_contact_email;
    }

    @Column(name = "exhibitor_contact_expressnumber")
    public String getExhibitor_contact_expressnumber() {
        return exhibitor_contact_expressnumber;
    }

    public void setExhibitor_contact_expressnumber(String exhibitor_contact_expressnumber) {
        this.exhibitor_contact_expressnumber = exhibitor_contact_expressnumber;
    }

    @Column(name = "exhibitor_contact_is_delete")
    public Integer getExhibitor_contact_is_delete() {
        return exhibitor_contact_is_delete;
    }

    public void setExhibitor_contact_is_delete(Integer exhibitor_contact_is_delete) {
        this.exhibitor_contact_is_delete = exhibitor_contact_is_delete;
    }

    @Column(name = "exhibitor_contact_name")
    public String getExhibitor_contact_name() {
        return exhibitor_contact_name;
    }

    public void setExhibitor_contact_name(String exhibitor_contact_name) {
        this.exhibitor_contact_name = exhibitor_contact_name;
    }

    @Column(name = "exhibitor_contact_phone")
    public String getExhibitor_contact_phone() {
        return exhibitor_contact_phone;
    }

    public void setExhibitor_contact_phone(String exhibitor_contact_phone) {
        this.exhibitor_contact_phone = exhibitor_contact_phone;
    }

    @Column(name = "exhibitor_contact_position")
    public String getExhibitor_contact_position() {
        return exhibitor_contact_position;
    }

    public void setExhibitor_contact_position(String exhibitor_contact_position) {
        this.exhibitor_contact_position = exhibitor_contact_position;
    }

    @Column(name = "exhibitor_info_address")
    public String getExhibitor_info_address() {
        return exhibitor_info_address;
    }

    public void setExhibitor_info_address(String exhibitor_info_address) {
        this.exhibitor_info_address = exhibitor_info_address;
    }

    @Column(name = "exhibitor_info_address_en")
    public String getExhibitor_info_address_en() {
        return exhibitor_info_address_en;
    }

    public void setExhibitor_info_address_en(String exhibitor_info_address_en) {
        this.exhibitor_info_address_en = exhibitor_info_address_en;
    }

    @Column(name = "exhibitor_info_admin_update_time")
    public Date getExhibitor_info_admin_update_time() {
        return exhibitor_info_admin_update_time;
    }

    public void setExhibitor_info_admin_update_time(Date exhibitor_info_admin_update_time) {
        this.exhibitor_info_admin_update_time = exhibitor_info_admin_update_time;
    }

    @Column(name = "exhibitor_info_admin_user")
    public Integer getExhibitor_info_admin_user() {
        return exhibitor_info_admin_user;
    }

    public void setExhibitor_info_admin_user(Integer exhibitor_info_admin_user) {
        this.exhibitor_info_admin_user = exhibitor_info_admin_user;
    }

    @Column(name = "exhibitor_info_company")
    public String getExhibitor_info_company() {
        return exhibitor_info_company;
    }

    public void setExhibitor_info_company(String exhibitor_info_company) {
        this.exhibitor_info_company = exhibitor_info_company;
    }

    @Column(name = "exhibitor_info_company_en")
    public String getExhibitor_info_company_en() {
        return exhibitor_info_company_en;
    }

    public void setExhibitor_info_company_en(String exhibitor_info_company_en) {
        this.exhibitor_info_company_en = exhibitor_info_company_en;
    }

    @Column(name = "exhibitor_info_company_hignlight")
    public String getExhibitor_info_company_hignlight() {
        return exhibitor_info_company_hignlight;
    }

    public void setExhibitor_info_company_hignlight(String exhibitor_info_company_hignlight) {
        this.exhibitor_info_company_hignlight = exhibitor_info_company_hignlight;
    }

    @Column(name = "exhibitor_info_company_t")
    public String getExhibitor_info_company_t() {
        return exhibitor_info_company_t;
    }

    public void setExhibitor_info_company_t(String exhibitor_info_company_t) {
        this.exhibitor_info_company_t = exhibitor_info_company_t;
    }

    @Column(name = "exhibitor_info_create_time")
    public Date getExhibitor_info_create_time() {
        return exhibitor_info_create_time;
    }

    public void setExhibitor_info_create_time(Date exhibitor_info_create_time) {
        this.exhibitor_info_create_time = exhibitor_info_create_time;
    }

    @Column(name = "exhibitor_info_einfoid")
    public Integer getExhibitor_info_einfoid() {
        return exhibitor_info_einfoid;
    }

    public void setExhibitor_info_einfoid(Integer exhibitor_info_einfoid) {
        this.exhibitor_info_einfoid = exhibitor_info_einfoid;
    }

    @Column(name = "exhibitor_info_email")
    public String getExhibitor_info_email() {
        return exhibitor_info_email;
    }

    public void setExhibitor_info_email(String exhibitor_info_email) {
        this.exhibitor_info_email = exhibitor_info_email;
    }

    @Column(name = "exhibitor_info_email_address")
    public String getExhibitor_info_email_address() {
        return exhibitor_info_email_address;
    }

    public void setExhibitor_info_email_address(String exhibitor_info_email_address) {
        this.exhibitor_info_email_address = exhibitor_info_email_address;
    }

    @Column(name = "exhibitor_info_email_contact")
    public String getExhibitor_info_email_contact() {
        return exhibitor_info_email_contact;
    }

    public void setExhibitor_info_email_contact(String exhibitor_info_email_contact) {
        this.exhibitor_info_email_contact = exhibitor_info_email_contact;
    }

    @Column(name = "exhibitor_info_email_telphone")
    public String getExhibitor_info_email_telphone() {
        return exhibitor_info_email_telphone;
    }

    public void setExhibitor_info_email_telphone(String exhibitor_info_email_telphone) {
        this.exhibitor_info_email_telphone = exhibitor_info_email_telphone;
    }

    @Column(name = "exhibitor_info_emark")
    public String getExhibitor_info_emark() {
        return exhibitor_info_emark;
    }

    public void setExhibitor_info_emark(String exhibitor_info_emark) {
        this.exhibitor_info_emark = exhibitor_info_emark;
    }

    @Column(name = "exhibitor_info_fax")
    public String getExhibitor_info_fax() {
        return exhibitor_info_fax;
    }

    public void setExhibitor_info_fax(String exhibitor_info_fax) {
        this.exhibitor_info_fax = exhibitor_info_fax;
    }

    @Column(name = "exhibitor_info_is_delete")
    public Integer getExhibitor_info_is_delete() {
        return exhibitor_info_is_delete;
    }

    public void setExhibitor_info_is_delete(Integer exhibitor_info_is_delete) {
        this.exhibitor_info_is_delete = exhibitor_info_is_delete;
    }

    @Column(name = "exhibitor_info_logo")
    public String getExhibitor_info_logo() {
        return exhibitor_info_logo;
    }

    public void setExhibitor_info_logo(String exhibitor_info_logo) {
        this.exhibitor_info_logo = exhibitor_info_logo;
    }

    @Column(name = "exhibitor_info_main_product")
    public String getExhibitor_info_main_product() {
        return exhibitor_info_main_product;
    }

    public void setExhibitor_info_main_product(String exhibitor_info_main_product) {
        this.exhibitor_info_main_product = exhibitor_info_main_product;
    }

    @Column(name = "exhibitor_info_main_product_en")
    public String getExhibitor_info_main_product_en() {
        return exhibitor_info_main_product_en;
    }

    public void setExhibitor_info_main_product_en(String exhibitor_info_main_product_en) {
        this.exhibitor_info_main_product_en = exhibitor_info_main_product_en;
    }

    @Column(name = "exhibitor_info_mark")
    public String getExhibitor_info_mark() {
        return exhibitor_info_mark;
    }

    public void setExhibitor_info_mark(String exhibitor_info_mark) {
        this.exhibitor_info_mark = exhibitor_info_mark;
    }

    @Column(name = "exhibitor_info_meipai")
    public String getExhibitor_info_meipai() {
        return exhibitor_info_meipai;
    }

    public void setExhibitor_info_meipai(String exhibitor_info_meipai) {
        this.exhibitor_info_meipai = exhibitor_info_meipai;
    }

    @Column(name = "exhibitor_info_meipai_en")
    public String getExhibitor_info_meipai_en() {
        return exhibitor_info_meipai_en;
    }

    public void setExhibitor_info_meipai_en(String exhibitor_info_meipai_en) {
        this.exhibitor_info_meipai_en = exhibitor_info_meipai_en;
    }

    @Column(name = "exhibitor_info_organization_code")
    public String getExhibitor_info_organization_code() {
        return exhibitor_info_organization_code;
    }

    public void setExhibitor_info_organization_code(String exhibitor_info_organization_code) {
        this.exhibitor_info_organization_code = exhibitor_info_organization_code;
    }

    @Column(name = "exhibitor_info_phone")
    public String getExhibitor_info_phone() {
        return exhibitor_info_phone;
    }

    public void setExhibitor_info_phone(String exhibitor_info_phone) {
        this.exhibitor_info_phone = exhibitor_info_phone;
    }

    @Column(name = "exhibitor_info_update_time")
    public Date getExhibitor_info_update_time() {
        return exhibitor_info_update_time;
    }

    public void setExhibitor_info_update_time(Date exhibitor_info_update_time) {
        this.exhibitor_info_update_time = exhibitor_info_update_time;
    }

    @Column(name = "exhibitor_info_website")
    public String getExhibitor_info_website() {
        return exhibitor_info_website;
    }

    public void setExhibitor_info_website(String exhibitor_info_website) {
        this.exhibitor_info_website = exhibitor_info_website;
    }

    @Column(name = "exhibitor_info_zipcode")
    public String getExhibitor_info_zipcode() {
        return exhibitor_info_zipcode;
    }

    public void setExhibitor_info_zipcode(String exhibitor_info_zipcode) {
        this.exhibitor_info_zipcode = exhibitor_info_zipcode;
    }

    @Column(name = "exhibitor_invoice_apply_create_time")
    public Date getExhibitor_invoice_apply_create_time() {
        return exhibitor_invoice_apply_create_time;
    }

    public void setExhibitor_invoice_apply_create_time(Date exhibitor_invoice_apply_create_time) {
        this.exhibitor_invoice_apply_create_time = exhibitor_invoice_apply_create_time;
    }

    @Column(name = "exhibitor_invoice_apply_exhibitor_no")
    public String getExhibitor_invoice_apply_exhibitor_no() {
        return exhibitor_invoice_apply_exhibitor_no;
    }

    public void setExhibitor_invoice_apply_exhibitor_no(String exhibitor_invoice_apply_exhibitor_no) {
        this.exhibitor_invoice_apply_exhibitor_no = exhibitor_invoice_apply_exhibitor_no;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_bank_account")
    public String getExhibitor_invoice_apply_invoice_bank_account() {
        return exhibitor_invoice_apply_invoice_bank_account;
    }

    public void setExhibitor_invoice_apply_invoice_bank_account(String exhibitor_invoice_apply_invoice_bank_account) {
        this.exhibitor_invoice_apply_invoice_bank_account = exhibitor_invoice_apply_invoice_bank_account;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_bank_name")
    public String getExhibitor_invoice_apply_invoice_bank_name() {
        return exhibitor_invoice_apply_invoice_bank_name;
    }

    public void setExhibitor_invoice_apply_invoice_bank_name(String exhibitor_invoice_apply_invoice_bank_name) {
        this.exhibitor_invoice_apply_invoice_bank_name = exhibitor_invoice_apply_invoice_bank_name;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_company")
    public String getExhibitor_invoice_apply_invoice_company() {
        return exhibitor_invoice_apply_invoice_company;
    }

    public void setExhibitor_invoice_apply_invoice_company(String exhibitor_invoice_apply_invoice_company) {
        this.exhibitor_invoice_apply_invoice_company = exhibitor_invoice_apply_invoice_company;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_company_address")
    public String getExhibitor_invoice_apply_invoice_company_address() {
        return exhibitor_invoice_apply_invoice_company_address;
    }

    public void setExhibitor_invoice_apply_invoice_company_address(String exhibitor_invoice_apply_invoice_company_address) {
        this.exhibitor_invoice_apply_invoice_company_address = exhibitor_invoice_apply_invoice_company_address;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_company_contact")
    public String getExhibitor_invoice_apply_invoice_company_contact() {
        return exhibitor_invoice_apply_invoice_company_contact;
    }

    public void setExhibitor_invoice_apply_invoice_company_contact(String exhibitor_invoice_apply_invoice_company_contact) {
        this.exhibitor_invoice_apply_invoice_company_contact = exhibitor_invoice_apply_invoice_company_contact;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_company_tel")
    public String getExhibitor_invoice_apply_invoice_company_tel() {
        return exhibitor_invoice_apply_invoice_company_tel;
    }

    public void setExhibitor_invoice_apply_invoice_company_tel(String exhibitor_invoice_apply_invoice_company_tel) {
        this.exhibitor_invoice_apply_invoice_company_tel = exhibitor_invoice_apply_invoice_company_tel;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_flag")
    public Integer getExhibitor_invoice_apply_invoice_flag() {
        return exhibitor_invoice_apply_invoice_flag;
    }

    public void setExhibitor_invoice_apply_invoice_flag(Integer exhibitor_invoice_apply_invoice_flag) {
        this.exhibitor_invoice_apply_invoice_flag = exhibitor_invoice_apply_invoice_flag;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_general_tax_type")
    public String getExhibitor_invoice_apply_invoice_general_tax_type() {
        return exhibitor_invoice_apply_invoice_general_tax_type;
    }

    public void setExhibitor_invoice_apply_invoice_general_tax_type(String exhibitor_invoice_apply_invoice_general_tax_type) {
        this.exhibitor_invoice_apply_invoice_general_tax_type = exhibitor_invoice_apply_invoice_general_tax_type;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_general_taxpayer_flag")
    public String getExhibitor_invoice_apply_invoice_general_taxpayer_flag() {
        return exhibitor_invoice_apply_invoice_general_taxpayer_flag;
    }

    public void setExhibitor_invoice_apply_invoice_general_taxpayer_flag(String exhibitor_invoice_apply_invoice_general_taxpayer_flag) {
        this.exhibitor_invoice_apply_invoice_general_taxpayer_flag = exhibitor_invoice_apply_invoice_general_taxpayer_flag;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_image_address")
    public String getExhibitor_invoice_apply_invoice_image_address() {
        return exhibitor_invoice_apply_invoice_image_address;
    }

    public void setExhibitor_invoice_apply_invoice_image_address(String exhibitor_invoice_apply_invoice_image_address) {
        this.exhibitor_invoice_apply_invoice_image_address = exhibitor_invoice_apply_invoice_image_address;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_no")
    public String getExhibitor_invoice_apply_invoice_no() {
        return exhibitor_invoice_apply_invoice_no;
    }

    public void setExhibitor_invoice_apply_invoice_no(String exhibitor_invoice_apply_invoice_no) {
        this.exhibitor_invoice_apply_invoice_no = exhibitor_invoice_apply_invoice_no;
    }

    @Column(name = "exhibitor_invoice_apply_invoice_taxpayer_no")
    public String getExhibitor_invoice_apply_invoice_taxpayer_no() {
        return exhibitor_invoice_apply_invoice_taxpayer_no;
    }

    public void setExhibitor_invoice_apply_invoice_taxpayer_no(String exhibitor_invoice_apply_invoice_taxpayer_no) {
        this.exhibitor_invoice_apply_invoice_taxpayer_no = exhibitor_invoice_apply_invoice_taxpayer_no;
    }

    @Column(name = "exhibitor_invoice_apply_title")
    public String getExhibitor_invoice_apply_title() {
        return exhibitor_invoice_apply_title;
    }

    public void setExhibitor_invoice_apply_title(String exhibitor_invoice_apply_title) {
        this.exhibitor_invoice_apply_title = exhibitor_invoice_apply_title;
    }

    @Column(name = "exhibitor_joiner_admin")
    public Integer getExhibitor_joiner_admin() {
        return exhibitor_joiner_admin;
    }

    public void setExhibitor_joiner_admin(Integer exhibitor_joiner_admin) {
        this.exhibitor_joiner_admin = exhibitor_joiner_admin;
    }

    @Column(name = "exhibitor_joiner_admin_update_time")
    public Date getExhibitor_joiner_admin_update_time() {
        return exhibitor_joiner_admin_update_time;
    }

    public void setExhibitor_joiner_admin_update_time(Date exhibitor_joiner_admin_update_time) {
        this.exhibitor_joiner_admin_update_time = exhibitor_joiner_admin_update_time;
    }

    @Column(name = "exhibitor_joiner_create_time")
    public Date getExhibitor_joiner_create_time() {
        return exhibitor_joiner_create_time;
    }

    public void setExhibitor_joiner_create_time(Date exhibitor_joiner_create_time) {
        this.exhibitor_joiner_create_time = exhibitor_joiner_create_time;
    }

    @Column(name = "exhibitor_joiner_email")
    public String getExhibitor_joiner_email() {
        return exhibitor_joiner_email;
    }

    public void setExhibitor_joiner_email(String exhibitor_joiner_email) {
        this.exhibitor_joiner_email = exhibitor_joiner_email;
    }

    @Column(name = "exhibitor_joiner_is_delete")
    public Integer getExhibitor_joiner_is_delete() {
        return exhibitor_joiner_is_delete;
    }

    public void setExhibitor_joiner_is_delete(Integer exhibitor_joiner_is_delete) {
        this.exhibitor_joiner_is_delete = exhibitor_joiner_is_delete;
    }

    @Column(name = "exhibitor_joiner_is_new")
    public Integer getExhibitor_joiner_is_new() {
        return exhibitor_joiner_is_new;
    }

    public void setExhibitor_joiner_is_new(Integer exhibitor_joiner_is_new) {
        this.exhibitor_joiner_is_new = exhibitor_joiner_is_new;
    }

    @Column(name = "exhibitor_joiner_name")
    public String getExhibitor_joiner_name() {
        return exhibitor_joiner_name;
    }

    public void setExhibitor_joiner_name(String exhibitor_joiner_name) {
        this.exhibitor_joiner_name = exhibitor_joiner_name;
    }

    @Column(name = "exhibitor_joiner_position")
    public String getExhibitor_joiner_position() {
        return exhibitor_joiner_position;
    }

    public void setExhibitor_joiner_position(String exhibitor_joiner_position) {
        this.exhibitor_joiner_position = exhibitor_joiner_position;
    }

    @Column(name = "exhibitor_joiner_telphone")
    public String getExhibitor_joiner_telphone() {
        return exhibitor_joiner_telphone;
    }

    public void setExhibitor_joiner_telphone(String exhibitor_joiner_telphone) {
        this.exhibitor_joiner_telphone = exhibitor_joiner_telphone;
    }

    @Column(name = "exhibitor_product_admin")
    public Integer getExhibitor_product_admin() {
        return exhibitor_product_admin;
    }

    public void setExhibitor_product_admin(Integer exhibitor_product_admin) {
        this.exhibitor_product_admin = exhibitor_product_admin;
    }

    @Column(name = "exhibitor_product_admin_update_time")
    public Date getExhibitor_product_admin_update_time() {
        return exhibitor_product_admin_update_time;
    }

    public void setExhibitor_product_admin_update_time(Date exhibitor_product_admin_update_time) {
        this.exhibitor_product_admin_update_time = exhibitor_product_admin_update_time;
    }

    @Column(name = "exhibitor_product_brand")
    public String getExhibitor_product_brand() {
        return exhibitor_product_brand;
    }

    public void setExhibitor_product_brand(String exhibitor_product_brand) {
        this.exhibitor_product_brand = exhibitor_product_brand;
    }

    @Column(name = "exhibitor_product_brand_en")
    public String getExhibitor_product_brand_en() {
        return exhibitor_product_brand_en;
    }

    public void setExhibitor_product_brand_en(String exhibitor_product_brand_en) {
        this.exhibitor_product_brand_en = exhibitor_product_brand_en;
    }

    @Column(name = "exhibitor_product_count")
    public String getExhibitor_product_count() {
        return exhibitor_product_count;
    }

    public void setExhibitor_product_count(String exhibitor_product_count) {
        this.exhibitor_product_count = exhibitor_product_count;
    }

    @Column(name = "exhibitor_product_count_en")
    public String getExhibitor_product_count_en() {
        return exhibitor_product_count_en;
    }

    public void setExhibitor_product_count_en(String exhibitor_product_count_en) {
        this.exhibitor_product_count_en = exhibitor_product_count_en;
    }

    @Column(name = "exhibitor_product_create_time")
    public Date getExhibitor_product_create_time() {
        return exhibitor_product_create_time;
    }

    public void setExhibitor_product_create_time(Date exhibitor_product_create_time) {
        this.exhibitor_product_create_time = exhibitor_product_create_time;
    }

    @Column(name = "exhibitor_product_description")
    public String getExhibitor_product_description() {
        return exhibitor_product_description;
    }

    public void setExhibitor_product_description(String exhibitor_product_description) {
        this.exhibitor_product_description = exhibitor_product_description;
    }

    @Column(name = "exhibitor_product_description_en")
    public String getExhibitor_product_description_en() {
        return exhibitor_product_description_en;
    }

    public void setExhibitor_product_description_en(String exhibitor_product_description_en) {
        this.exhibitor_product_description_en = exhibitor_product_description_en;
    }

    @Column(name = "exhibitor_product_flag")
    public Integer getExhibitor_product_flag() {
        return exhibitor_product_flag;
    }

    public void setExhibitor_product_flag(Integer exhibitor_product_flag) {
        this.exhibitor_product_flag = exhibitor_product_flag;
    }

    @Column(name = "exhibitor_product_key_words")
    public String getExhibitor_product_key_words() {
        return exhibitor_product_key_words;
    }

    public void setExhibitor_product_key_words(String exhibitor_product_key_words) {
        this.exhibitor_product_key_words = exhibitor_product_key_words;
    }

    @Column(name = "exhibitor_product_key_words_en")
    public String getExhibitor_product_key_words_en() {
        return exhibitor_product_key_words_en;
    }

    public void setExhibitor_product_key_words_en(String exhibitor_product_key_words_en) {
        this.exhibitor_product_key_words_en = exhibitor_product_key_words_en;
    }

    @Column(name = "exhibitor_product_model")
    public String getExhibitor_product_model() {
        return exhibitor_product_model;
    }

    public void setExhibitor_product_model(String exhibitor_product_model) {
        this.exhibitor_product_model = exhibitor_product_model;
    }

    @Column(name = "exhibitor_product_model_en")
    public String getExhibitor_product_model_en() {
        return exhibitor_product_model_en;
    }

    public void setExhibitor_product_model_en(String exhibitor_product_model_en) {
        this.exhibitor_product_model_en = exhibitor_product_model_en;
    }

    @Column(name = "exhibitor_product_name")
    public String getExhibitor_product_name() {
        return exhibitor_product_name;
    }

    public void setExhibitor_product_name(String exhibitor_product_name) {
        this.exhibitor_product_name = exhibitor_product_name;
    }

    @Column(name = "exhibitor_product_name_en")
    public String getExhibitor_product_name_en() {
        return exhibitor_product_name_en;
    }

    public void setExhibitor_product_name_en(String exhibitor_product_name_en) {
        this.exhibitor_product_name_en = exhibitor_product_name_en;
    }

    @Column(name = "exhibitor_product_origin")
    public String getExhibitor_product_origin() {
        return exhibitor_product_origin;
    }

    public void setExhibitor_product_origin(String exhibitor_product_origin) {
        this.exhibitor_product_origin = exhibitor_product_origin;
    }

    @Column(name = "exhibitor_product_origin_en")
    public String getExhibitor_product_origin_en() {
        return exhibitor_product_origin_en;
    }

    public void setExhibitor_product_origin_en(String exhibitor_product_origin_en) {
        this.exhibitor_product_origin_en = exhibitor_product_origin_en;
    }

    @Column(name = "exhibitor_product_package_description")
    public String getExhibitor_product_package_description() {
        return exhibitor_product_package_description;
    }

    public void setExhibitor_product_package_description(String exhibitor_product_package_description) {
        this.exhibitor_product_package_description = exhibitor_product_package_description;
    }

    @Column(name = "exhibitor_product_package_description_en")
    public String getExhibitor_product_package_description_en() {
        return exhibitor_product_package_description_en;
    }

    public void setExhibitor_product_package_description_en(String exhibitor_product_package_description_en) {
        this.exhibitor_product_package_description_en = exhibitor_product_package_description_en;
    }

    @Column(name = "exhibitor_product_price_description")
    public String getExhibitor_product_price_description() {
        return exhibitor_product_price_description;
    }

    public void setExhibitor_product_price_description(String exhibitor_product_price_description) {
        this.exhibitor_product_price_description = exhibitor_product_price_description;
    }

    @Column(name = "exhibitor_product_price_description_en")
    public String getExhibitor_product_price_description_en() {
        return exhibitor_product_price_description_en;
    }

    public void setExhibitor_product_price_description_en(String exhibitor_product_price_description_en) {
        this.exhibitor_product_price_description_en = exhibitor_product_price_description_en;
    }

    @Column(name = "exhibitor_product_spec")
    public String getExhibitor_product_spec() {
        return exhibitor_product_spec;
    }

    public void setExhibitor_product_spec(String exhibitor_product_spec) {
        this.exhibitor_product_spec = exhibitor_product_spec;
    }

    @Column(name = "exhibitor_product_spec_en")
    public String getExhibitor_product_spec_en() {
        return exhibitor_product_spec_en;
    }

    public void setExhibitor_product_spec_en(String exhibitor_product_spec_en) {
        this.exhibitor_product_spec_en = exhibitor_product_spec_en;
    }

    @Column(name = "exhibitor_product_type")
    public String getExhibitor_product_type() {
        return exhibitor_product_type;
    }

    public void setExhibitor_product_type(String exhibitor_product_type) {
        this.exhibitor_product_type = exhibitor_product_type;
    }

    @Column(name = "exhibitor_product_update_time")
    public Date getExhibitor_product_update_time() {
        return exhibitor_product_update_time;
    }

    public void setExhibitor_product_update_time(Date exhibitor_product_update_time) {
        this.exhibitor_product_update_time = exhibitor_product_update_time;
    }

    @Column(name = "exhibitor_term_booth_number")
    public String getExhibitor_term_booth_number() {
        return exhibitor_term_booth_number;
    }

    public void setExhibitor_term_booth_number(String exhibitor_term_booth_number) {
        this.exhibitor_term_booth_number = exhibitor_term_booth_number;
    }

    @Column(name = "exhibitor_term_create_time")
    public Date getExhibitor_term_create_time() {
        return exhibitor_term_create_time;
    }

    public void setExhibitor_term_create_time(Date exhibitor_term_create_time) {
        this.exhibitor_term_create_time = exhibitor_term_create_time;
    }

    @Column(name = "exhibitor_term_create_user")
    public Integer getExhibitor_term_create_user() {
        return exhibitor_term_create_user;
    }

    public void setExhibitor_term_create_user(Integer exhibitor_term_create_user) {
        this.exhibitor_term_create_user = exhibitor_term_create_user;
    }

    @Column(name = "exhibitor_term_is_delete")
    public Integer getExhibitor_term_is_delete() {
        return exhibitor_term_is_delete;
    }

    public void setExhibitor_term_is_delete(Integer exhibitor_term_is_delete) {
        this.exhibitor_term_is_delete = exhibitor_term_is_delete;
    }

    @Column(name = "exhibitor_term_join_term")
    public Integer getExhibitor_term_join_term() {
        return exhibitor_term_join_term;
    }

    public void setExhibitor_term_join_term(Integer exhibitor_term_join_term) {
        this.exhibitor_term_join_term = exhibitor_term_join_term;
    }

    @Column(name = "exhibitor_term_mark")
    public String getExhibitor_term_mark() {
        return exhibitor_term_mark;
    }

    public void setExhibitor_term_mark(String exhibitor_term_mark) {
        this.exhibitor_term_mark = exhibitor_term_mark;
    }

    @Column(name = "exhibitor_term_update_time")
    public Date getExhibitor_term_update_time() {
        return exhibitor_term_update_time;
    }

    public void setExhibitor_term_update_time(Date exhibitor_term_update_time) {
        this.exhibitor_term_update_time = exhibitor_term_update_time;
    }

    @Column(name = "exhibitor_term_update_user")
    public Integer getExhibitor_term_update_user() {
        return exhibitor_term_update_user;
    }

    public void setExhibitor_term_update_user(Integer exhibitor_term_update_user) {
        this.exhibitor_term_update_user = exhibitor_term_update_user;
    }

    @Column(name = "exhibitor_type")
    public String getExhibitor_type() {
        return exhibitor_type;
    }

    public void setExhibitor_type(String exhibitor_type) {
        this.exhibitor_type = exhibitor_type;
    }

    @Column(name = "exhibitor_visa_address")
    public String getExhibitor_visa_address() {
        return exhibitor_visa_address;
    }

    public void setExhibitor_visa_address(String exhibitor_visa_address) {
        this.exhibitor_visa_address = exhibitor_visa_address;
    }

    @Column(name = "exhibitor_visa_apply_for")
    public String getExhibitor_visa_apply_for() {
        return exhibitor_visa_apply_for;
    }

    public void setExhibitor_visa_apply_for(String exhibitor_visa_apply_for) {
        this.exhibitor_visa_apply_for = exhibitor_visa_apply_for;
    }

    @Column(name = "exhibitor_visa_birth")
    public Date getExhibitor_visa_birth() {
        return exhibitor_visa_birth;
    }

    public void setExhibitor_visa_birth(Date exhibitor_visa_birth) {
        this.exhibitor_visa_birth = exhibitor_visa_birth;
    }

    @Column(name = "exhibitor_visa_booth_no")
    public String getExhibitor_visa_booth_no() {
        return exhibitor_visa_booth_no;
    }

    public void setExhibitor_visa_booth_no(String exhibitor_visa_booth_no) {
        this.exhibitor_visa_booth_no = exhibitor_visa_booth_no;
    }

    @Column(name = "exhibitor_visa_business_license")
    public String getExhibitor_visa_business_license() {
        return exhibitor_visa_business_license;
    }

    public void setExhibitor_visa_business_license(String exhibitor_visa_business_license) {
        this.exhibitor_visa_business_license = exhibitor_visa_business_license;
    }

    @Column(name = "exhibitor_visa_company_name")
    public String getExhibitor_visa_company_name() {
        return exhibitor_visa_company_name;
    }

    public void setExhibitor_visa_company_name(String exhibitor_visa_company_name) {
        this.exhibitor_visa_company_name = exhibitor_visa_company_name;
    }

    @Column(name = "exhibitor_visa_company_website")
    public String getExhibitor_visa_company_website() {
        return exhibitor_visa_company_website;
    }

    public void setExhibitor_visa_company_website(String exhibitor_visa_company_website) {
        this.exhibitor_visa_company_website = exhibitor_visa_company_website;
    }

    @Column(name = "exhibitor_visa_create_time")
    public Date getExhibitor_visa_create_time() {
        return exhibitor_visa_create_time;
    }

    public void setExhibitor_visa_create_time(Date exhibitor_visa_create_time) {
        this.exhibitor_visa_create_time = exhibitor_visa_create_time;
    }

    @Column(name = "exhibitor_visa_detailed_address")
    public String getExhibitor_visa_detailed_address() {
        return exhibitor_visa_detailed_address;
    }

    public void setExhibitor_visa_detailed_address(String exhibitor_visa_detailed_address) {
        this.exhibitor_visa_detailed_address = exhibitor_visa_detailed_address;
    }

    @Column(name = "exhibitor_visa_email")
    public String getExhibitor_visa_email() {
        return exhibitor_visa_email;
    }

    public void setExhibitor_visa_email(String exhibitor_visa_email) {
        this.exhibitor_visa_email = exhibitor_visa_email;
    }

    @Column(name = "exhibitor_visa_exp_date")
    public Date getExhibitor_visa_exp_date() {
        return exhibitor_visa_exp_date;
    }

    public void setExhibitor_visa_exp_date(Date exhibitor_visa_exp_date) {
        this.exhibitor_visa_exp_date = exhibitor_visa_exp_date;
    }

    @Column(name = "exhibitor_visa_fax")
    public String getExhibitor_visa_fax() {
        return exhibitor_visa_fax;
    }

    public void setExhibitor_visa_fax(String exhibitor_visa_fax) {
        this.exhibitor_visa_fax = exhibitor_visa_fax;
    }

    @Column(name = "exhibitor_visa_fromDate")
    public Date getExhibitor_visa_fromDate() {
        return exhibitor_visa_fromDate;
    }

    public void setExhibitor_visa_fromDate(Date exhibitor_visa_fromDate) {
        this.exhibitor_visa_fromDate = exhibitor_visa_fromDate;
    }

    @Column(name = "exhibitor_visa_gender")
    public Integer getExhibitor_visa_gender() {
        return exhibitor_visa_gender;
    }

    public void setExhibitor_visa_gender(Integer exhibitor_visa_gender) {
        this.exhibitor_visa_gender = exhibitor_visa_gender;
    }

    @Column(name = "exhibitor_visa_job_title")
    public String getExhibitor_visa_job_title() {
        return exhibitor_visa_job_title;
    }

    public void setExhibitor_visa_job_title(String exhibitor_visa_job_title) {
        this.exhibitor_visa_job_title = exhibitor_visa_job_title;
    }

    @Column(name = "exhibitor_visa_joiner_id")
    public Integer getExhibitor_visa_joiner_id() {
        return exhibitor_visa_joiner_id;
    }

    public void setExhibitor_visa_joiner_id(Integer exhibitor_visa_joiner_id) {
        this.exhibitor_visa_joiner_id = exhibitor_visa_joiner_id;
    }

    @Column(name = "exhibitor_visa_nationality")
    public String getExhibitor_visa_nationality() {
        return exhibitor_visa_nationality;
    }

    public void setExhibitor_visa_nationality(String exhibitor_visa_nationality) {
        this.exhibitor_visa_nationality = exhibitor_visa_nationality;
    }

    @Column(name = "exhibitor_visa_passport_name")
    public String getExhibitor_visa_passport_name() {
        return exhibitor_visa_passport_name;
    }

    public void setExhibitor_visa_passport_name(String exhibitor_visa_passport_name) {
        this.exhibitor_visa_passport_name = exhibitor_visa_passport_name;
    }

    @Column(name = "exhibitor_visa_passport_no")
    public String getExhibitor_visa_passport_no() {
        return exhibitor_visa_passport_no;
    }

    public void setExhibitor_visa_passport_no(String exhibitor_visa_passport_no) {
        this.exhibitor_visa_passport_no = exhibitor_visa_passport_no;
    }

    @Column(name = "exhibitor_visa_passport_page")
    public String getExhibitor_visa_passport_page() {
        return exhibitor_visa_passport_page;
    }

    public void setExhibitor_visa_passport_page(String exhibitor_visa_passport_page) {
        this.exhibitor_visa_passport_page = exhibitor_visa_passport_page;
    }

    @Column(name = "exhibitor_visa_status")
    public Integer getExhibitor_visa_status() {
        return exhibitor_visa_status;
    }

    public void setExhibitor_visa_status(Integer exhibitor_visa_status) {
        this.exhibitor_visa_status = exhibitor_visa_status;
    }

    @Column(name = "exhibitor_visa_tel")
    public String getExhibitor_visa_tel() {
        return exhibitor_visa_tel;
    }

    public void setExhibitor_visa_tel(String exhibitor_visa_tel) {
        this.exhibitor_visa_tel = exhibitor_visa_tel;
    }

    @Column(name = "exhibitor_visa_toDate")
    public Date getExhibitor_visa_toDate() {
        return exhibitor_visa_toDate;
    }

    public void setExhibitor_visa_toDate(Date exhibitor_visa_toDate) {
        this.exhibitor_visa_toDate = exhibitor_visa_toDate;
    }

    @Column(name = "exhibitor_visa_update_time")
    public Date getExhibitor_visa_update_time() {
        return exhibitor_visa_update_time;
    }

    public void setExhibitor_visa_update_time(Date exhibitor_visa_update_time) {
        this.exhibitor_visa_update_time = exhibitor_visa_update_time;
    }

    @Column(name = "group")
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Column(name = "isLogin")
    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    @Column(name = "isLogout")
    public Integer getIsLogout() {
        return isLogout;
    }

    public void setIsLogout(Integer isLogout) {
        this.isLogout = isLogout;
    }

    @Column(name = "lastLoginIp")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Column(name = "lastLoginTime")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "prodClass")
    public String getProdClass() {
        return prodClass;
    }

    public void setProdClass(String prodClass) {
        this.prodClass = prodClass;
    }

    @Column(name = "province")
    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    @Column(name = "send_invitation_flag")
    public Integer getSend_invitation_flag() {
        return send_invitation_flag;
    }

    public void setSend_invitation_flag(Integer send_invitation_flag) {
        this.send_invitation_flag = send_invitation_flag;
    }

    @Column(name = "tag")
    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Column(name = "updateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "updateUser")
    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}