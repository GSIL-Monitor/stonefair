package com.zhenhappy.ems.manager.entity.backupinfo;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhenhappy.ems.entity.TContact;
import com.zhenhappy.ems.entity.TExhibitorJoiner;
import com.zhenhappy.ems.entity.TProduct;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * WcustomerInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "exhibitor_Info_Backup", schema = "dbo")
public class TExhibitorBackupInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
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
    private Date exhibitor_backup_date;

    // Constructors

    /** default constructor */
    public TExhibitorBackupInfo() {
    }

    public TExhibitorBackupInfo(Integer id, Integer area, String contractId, Integer country, Date createTime, Integer createUser, Integer eid,
                                String exhibitionArea, String exhibitor_booth_booth_number, Date exhibitor_booth_create_time,
                                Integer exhibitor_booth_create_user, String exhibitor_booth_exhibition_area, String exhibitor_booth_mark,
                                Date exhibitor_booth_update_time, Integer exhibitor_booth_update_user, String exhibitor_info_address,
                                String exhibitor_info_address_en, Date exhibitor_info_admin_update_time, Integer exhibitor_info_admin_user,
                                String exhibitor_info_company, String exhibitor_info_company_en, String exhibitor_info_company_hignlight,
                                String exhibitor_info_company_t, Date exhibitor_info_create_time, Integer exhibitor_info_einfoid,
                                String exhibitor_info_email, String exhibitor_info_email_address, String exhibitor_info_email_contact,
                                String exhibitor_info_email_telphone, String exhibitor_info_emark, String exhibitor_info_fax,
                                Integer exhibitor_info_is_delete, String exhibitor_info_logo, String exhibitor_info_main_product,
                                String exhibitor_info_main_product_en, String exhibitor_info_mark, String exhibitor_info_meipai,
                                String exhibitor_info_meipai_en, String exhibitor_info_organization_code, String exhibitor_info_phone,
                                Date exhibitor_info_update_time, String exhibitor_info_website, String exhibitor_info_zipcode,
                                Date exhibitor_invoice_apply_create_time, String exhibitor_invoice_apply_exhibitor_no,
                                String exhibitor_invoice_apply_invoice_bank_account, String exhibitor_invoice_apply_invoice_bank_name,
                                String exhibitor_invoice_apply_invoice_company, String exhibitor_invoice_apply_invoice_company_address,
                                String exhibitor_invoice_apply_invoice_company_contact, String exhibitor_invoice_apply_invoice_company_tel,
                                Integer exhibitor_invoice_apply_invoice_flag, String exhibitor_invoice_apply_invoice_general_tax_type,
                                String exhibitor_invoice_apply_invoice_general_taxpayer_flag,
                                String exhibitor_invoice_apply_invoice_image_address, String exhibitor_invoice_apply_invoice_no,
                                String exhibitor_invoice_apply_invoice_taxpayer_no, String exhibitor_invoice_apply_title,
                                String exhibitor_product_type, String exhibitor_term_booth_number, Date exhibitor_term_create_time,
                                Integer exhibitor_term_create_user, Integer exhibitor_term_is_delete, Integer exhibitor_term_join_term,
                                String exhibitor_term_mark, Date exhibitor_term_update_time, Integer exhibitor_term_update_user,
                                String exhibitor_type, Integer group, Integer isLogin, Integer isLogout, String lastLoginIp,
                                Date lastLoginTime, Integer level, String password, String prodClass, Integer province,
                                Integer send_invitation_flag, Integer tag, Date updateTime, Integer updateUser, String username,
                                Date exhibitor_backup_date) {
        this.id = id;
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
        this.exhibitor_product_type = exhibitor_product_type;
        this.exhibitor_term_booth_number = exhibitor_term_booth_number;
        this.exhibitor_term_create_time = exhibitor_term_create_time;
        this.exhibitor_term_create_user = exhibitor_term_create_user;
        this.exhibitor_term_is_delete = exhibitor_term_is_delete;
        this.exhibitor_term_join_term = exhibitor_term_join_term;
        this.exhibitor_term_mark = exhibitor_term_mark;
        this.exhibitor_term_update_time = exhibitor_term_update_time;
        this.exhibitor_term_update_user = exhibitor_term_update_user;
        this.exhibitor_type = exhibitor_type;
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
        this.exhibitor_backup_date = exhibitor_backup_date;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "eid")
    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    @Column(name = "area")
    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    @Column(name = "contract_id")
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

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "create_user")
    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    @Column(name = "exhibition_area")
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

    @Column(name = "exhibitor_product_type")
    public String getExhibitor_product_type() {
        return exhibitor_product_type;
    }

    public void setExhibitor_product_type(String exhibitor_product_type) {
        this.exhibitor_product_type = exhibitor_product_type;
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

    @Column(name = "[group]")
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Column(name = "is_login")
    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    @Column(name = "is_logout")
    public Integer getIsLogout() {
        return isLogout;
    }

    public void setIsLogout(Integer isLogout) {
        this.isLogout = isLogout;
    }

    @Column(name = "last_login_ip")
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    @Column(name = "last_login_time")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "[level]")
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

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "update_user")
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

    @Column(name = "exhibitor_backup_date")
    public Date getExhibitor_backup_date() {
        return exhibitor_backup_date;
    }

    public void setExhibitor_backup_date(Date exhibitor_backup_date) {
        this.exhibitor_backup_date = exhibitor_backup_date;
    }
}