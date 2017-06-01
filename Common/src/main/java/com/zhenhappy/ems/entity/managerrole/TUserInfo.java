package com.zhenhappy.ems.entity.managerrole;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2016/5/12.
 */
@Entity
@Table(name = "t_user_info", schema = "dbo")
public class TUserInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;    // 用户ID
    private String  name;
    private String userName;   // 用户名
    private String password;   // 密码
    private Integer userType;  // 用户类型
    private Integer roleId;    // 所属角色ID
    private String userDescription;  // 用户描述
    private String roleName;
    private Integer ownerId;       //资料库对应的tag id，若为null表示没有资料库
    private String shareId;         //资料库分享给对应的tag id

    public TUserInfo() {
    }

    public TUserInfo(Integer id, String  name, String userName, String password, Integer userType,
                     Integer roleId, String userDescription, String roleName, Integer ownerId, String shareId) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.roleId = roleId;
        this.userDescription = userDescription;
        this.roleName = roleName;
        this.ownerId = ownerId;
        this.shareId = shareId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    @Column(name = "name", length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "userName", length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password", length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "userType")
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Column(name = "roleId")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "userDescription", length = 200)
    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    @Column(name = "roleName", length = 200)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "ownerId")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "shareId")
    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName
                + ", password=" + password + ", userType=" + userType
                + ", roleId=" + roleId + ", userDescription=" + userDescription
                + ", roleName=" + roleName + ", ownerId=" + ownerId + ", shareId=" + shareId + "]";
    }
}
