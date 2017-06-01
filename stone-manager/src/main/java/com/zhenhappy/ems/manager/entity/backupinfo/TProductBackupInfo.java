package com.zhenhappy.ems.manager.entity.backupinfo;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * TProduct entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "product_Info_Backup" , schema = "dbo"
)

public class TProductBackupInfo implements java.io.Serializable {
    private Integer id;
    private Integer exhibitor_info_backup_id;
    private String productName;
    private String productModel;
    private String origin;
    private String keyWords;
    private String description;
    private String productBrand;
    private String productSpec;
    private String productCount;
    private String packageDescription;
    private String priceDescription;

    //English information
    private String productNameEn;
    private String productModelEn;
    private String originEn;
    private String keyWordsEn;
    private String descriptionEn;
    private String productBrandEn;
    private String productSpecEn;
    private String productCountEn;
    private String packageDescriptionEn;
    private String priceDescriptionEn;

    private Integer flag;
    private Date createTime;
    private Date updateTime;
    private Integer admin;
    private Date adminUpdateTime;

    /**
     * default constructor
     */
    public TProductBackupInfo() {
    }

    public TProductBackupInfo(Integer admin, Date adminUpdateTime, Date createTime, String description, String descriptionEn,
                              Integer exhibitor_info_backup_id, Integer flag, Integer id, String keyWords, String keyWordsEn, String origin,
                              String originEn, String packageDescription, String packageDescriptionEn, String priceDescription,
                              String priceDescriptionEn, String productBrand, String productBrandEn, String productCount, String productCountEn,
                              String productModel, String productModelEn, String productName, String productNameEn, String productSpec,
                              String productSpecEn, Date updateTime) {
        this.admin = admin;
        this.adminUpdateTime = adminUpdateTime;
        this.createTime = createTime;
        this.description = description;
        this.descriptionEn = descriptionEn;
        this.exhibitor_info_backup_id = exhibitor_info_backup_id;
        this.flag = flag;
        this.id = id;
        this.keyWords = keyWords;
        this.keyWordsEn = keyWordsEn;
        this.origin = origin;
        this.originEn = originEn;
        this.packageDescription = packageDescription;
        this.packageDescriptionEn = packageDescriptionEn;
        this.priceDescription = priceDescription;
        this.priceDescriptionEn = priceDescriptionEn;
        this.productBrand = productBrand;
        this.productBrandEn = productBrandEn;
        this.productCount = productCount;
        this.productCountEn = productCountEn;
        this.productModel = productModel;
        this.productModelEn = productModelEn;
        this.productName = productName;
        this.productNameEn = productNameEn;
        this.productSpec = productSpec;
        this.productSpecEn = productSpecEn;
        this.updateTime = updateTime;
    }

    /**
     * id constructor
     */
    public TProductBackupInfo(Integer id) {
        this.id = id;
    }

    // Property accessors
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "exhibitor_info_backup_id")
    public Integer getExhibitor_info_backup_id() {
        return exhibitor_info_backup_id;
    }

    public void setExhibitor_info_backup_id(Integer exhibitor_info_backup_id) {
        this.exhibitor_info_backup_id = exhibitor_info_backup_id;
    }

    @Column(name = "product_name", length = 800)

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "product_model", length = 800)

    public String getProductModel() {
        return this.productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    @Column(name = "origin", length = 500)

    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Column(name = "key_words", length = 500)

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Column(name = "description")

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "product_brand", length = 800)

    public String getProductBrand() {
        return this.productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Column(name = "product_spec", length = 800)

    public String getProductSpec() {
        return this.productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    @Column(name = "product_count", length = 200)

    public String getProductCount() {
        return this.productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    @Column(name = "package_description", length = 2000)

    public String getPackageDescription() {
        return this.packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    @Column(name = "price_description", length = 1000)

    public String getPriceDescription() {
        return this.priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    @Column(name = "product_name_en", length = 1000)
    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    @Column(name = "product_model_en", length = 1000)
    public String getProductModelEn() {
        return productModelEn;
    }

    public void setProductModelEn(String productModelEn) {
        this.productModelEn = productModelEn;
    }

    @Column(name = "origin_en", length = 1000)
    public String getOriginEn() {
        return originEn;
    }

    public void setOriginEn(String originEn) {
        this.originEn = originEn;
    }

    @Column(name = "key_words_en", length = 1000)
    public String getKeyWordsEn() {
        return keyWordsEn;
    }

    public void setKeyWordsEn(String keyWordsEn) {
        this.keyWordsEn = keyWordsEn;
    }

    @Column(name = "description_en", length = 1000)
    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    @Column(name = "product_brand_en", length = 1000)
    public String getProductBrandEn() {
        return productBrandEn;
    }

    public void setProductBrandEn(String productBrandEn) {
        this.productBrandEn = productBrandEn;
    }

    @Column(name = "product_spec_en", length = 1000)
    public String getProductSpecEn() {
        return productSpecEn;
    }

    public void setProductSpecEn(String productSpecEn) {
        this.productSpecEn = productSpecEn;
    }

    @Column(name = "product_count_en", length = 1000)
    public String getProductCountEn() {
        return productCountEn;
    }

    public void setProductCountEn(String productCountEn) {
        this.productCountEn = productCountEn;
    }

    @Column(name = "package_description_en", length = 1000)
    public String getPackageDescriptionEn() {
        return packageDescriptionEn;
    }

    public void setPackageDescriptionEn(String packageDescriptionEn) {
        this.packageDescriptionEn = packageDescriptionEn;
    }

    @Column(name = "price_description_en", length = 1000)
    public String getPriceDescriptionEn() {
        return priceDescriptionEn;
    }

    public void setPriceDescriptionEn(String priceDescriptionEn) {
        this.priceDescriptionEn = priceDescriptionEn;
    }

    @Column(name = "flag")

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Column(name = "create_time", length = 23)

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_time", length = 23)

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "admin")

    public Integer getAdmin() {
        return this.admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    @Column(name = "admin_update_time", length = 23)

    public Date getAdminUpdateTime() {
        return this.adminUpdateTime;
    }

    public void setAdminUpdateTime(Date adminUpdateTime) {
        this.adminUpdateTime = adminUpdateTime;
    }

}