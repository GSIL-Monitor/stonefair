package com.zhenhappy.ems.entity.managerrole;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2016/5/12.
 */
@Entity
@Table(name = "t_user_menu", schema = "dbo")
public class TUserMenu implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer menuId;         // 菜单ID
    private String menuName;        // 菜单名称
    private String menuUrl;         // 菜单地址
    private Integer parentId;       // 上级菜单ID
    private String menuDescription; // 菜单描述
    private String state;           // 状态
    private String iconCls;         // 图标
    private Integer seq;            // 顺序

    public TUserMenu() {
    }

    public TUserMenu(Integer menuId, String menuName, String menuUrl, Integer parentId,
                     String menuDescription, String state, String iconCls, Integer seq) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.parentId = parentId;
        this.menuDescription = menuDescription;
        this.state = state;
        this.iconCls = iconCls;
        this.seq = seq;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "menuId", unique = true, nullable = false)
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Column(name = "menuName", length = 50)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Column(name = "menuUrl", length = 100)
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Column(name = "parentId")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Column(name = "menuDescription", length = 200)
    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    @Column(name = "state", length = 20)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "iconCls", length = 50)
    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "TUserMenu [menuId=" + menuId + ", menuName=" + menuName
                + ", menuUrl=" + menuUrl + ", parentId=" + parentId
                + ", menuDescription=" + menuDescription + ", state=" + state
                + ", iconCls=" + iconCls + ", seq=" + seq + "]";
    }
}
