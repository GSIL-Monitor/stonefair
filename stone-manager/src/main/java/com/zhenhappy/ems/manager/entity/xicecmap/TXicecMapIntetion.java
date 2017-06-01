package com.zhenhappy.ems.manager.entity.xicecmap;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2016-07-22.
 */
@Entity
@Table(name = "t_xicec_map_intention", schema = "dbo")
public class TXicecMapIntetion {
    private Integer id;
    private String booth_num;
    private Integer tag;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "booth_num")
    public String getBooth_num() {
        return booth_num;
    }

    public void setBooth_num(String booth_num) {
        this.booth_num = booth_num;
    }

    @Basic
    @Column(name = "tag")
    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TXicecMapIntetion that = (TXicecMapIntetion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (booth_num != null ? !booth_num.equals(that.booth_num) : that.booth_num != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (booth_num != null ? booth_num.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
