package com.zhenhappy.ems.entity.managerrole;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2016/5/12.
 */
@Entity
@Table(name = "t_user_role", schema = "dbo")
public class TUserRole implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer roleId;                 //角色ID
    private String  roleName;               //角色名称
    private String  menuIds;                //菜单ID集合，用,分开的
    private String  operationIds;           //按钮ID集合，用,分开的
    private String  roleDescription;        //角色描述

    public TUserRole() {
    }

    public TUserRole(Integer roleId, String roleName, String menuIds, String operationIds,
                     String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.menuIds = menuIds;
        this.operationIds = operationIds;
        this.roleDescription = roleDescription;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "roleId", unique = true, nullable = false)
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "roleName", length = 20)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "menuIds", length = 200)
    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    @Column(name = "operationIds", length = 200)
    public String getOperationIds() {
        return operationIds;
    }

    public void setOperationIds(String operationIds) {
        this.operationIds = operationIds;
    }

    @Column(name = "roleDescription", length = 200)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Override
    public String toString() {
        return "TUserRole [roleId=" + roleId + ", roleName=" + roleName
                + ", menuIds=" + menuIds + ", operationIds=" + operationIds
                + ", roleDescription=" + roleDescription + "]";
    }
}
