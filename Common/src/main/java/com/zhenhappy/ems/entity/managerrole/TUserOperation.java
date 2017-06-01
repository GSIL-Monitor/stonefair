package com.zhenhappy.ems.entity.managerrole;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2016/5/12.
 */
@Entity
@Table(name = "t_user_operation", schema = "dbo")
public class TUserOperation implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer operationId;  //按钮ID
    private Integer menuId;       //所属哪一个页面菜单的ID
    private String operationName;  //按钮名称
    private String menuName;

    public TUserOperation() {
    }

    public TUserOperation(Integer operationId, Integer menuId, String operationName, String menuName) {
        this.operationId = operationId;
        this.menuId = menuId;
        this.operationName = operationName;
        this.menuName = menuName;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "operationId", unique = true, nullable = false)
    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    @Column(name = "menuId")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Column(name = "operationName", length = 100)
    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    @Column(name = "menuName", length = 50)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String toString() {
        return "TUserOperation [operationId=" + operationId + ", menuId=" + menuId
                + ", operationName=" + operationName + ", menuName=" + menuName
                + "]";
    }
}
