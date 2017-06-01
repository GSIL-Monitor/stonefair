package com.zhenhappy.ems.manager.entity.visitorfeedback;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2017-4-16.
 */
@Entity
@Table(name = "t_visitor_Feedback", schema = "dbo")
public class TVisitorFeedBack {
    private Integer id;
    private String userName;
    private String q1;
    private String a1;
    private String q2;
    private String a2;
    private String q3;
    private String a3;
    private String q4;
    private String a4;
    private String q5;
    private String a5;
    private String q6;
    private String a6;
    private String q7;
    private String a7;
    private String q8;
    private String a8;
    private String q9;
    private String a9;
    private String q10;
    private String a10;
    private String q11;
    private String a11;
    private String q12;
    private String a12;
    private String q13;
    private String a13;
    private String q14;
    private String a14;
    private String q15;
    private String a15;
    private String q16;
    private String a16;
    private String q17;
    private String a17;
    private String q18;
    private String a18;
    private String q19;
    private String a19;
    private String q20;
    private String a20;
    private String remark;
    private String createIP;
    private Date createTime;
    private Integer readed;
    private String gUID;

    public TVisitorFeedBack() {
    }

    public TVisitorFeedBack(Integer id, String userName, String q1, String a1, String q2, String a2, String q3, String a3,
                            Date createTime, Integer isReaded) {
        this.id = id;
        this.userName = userName;
        this.q1 = q1;
        this.a1 = a1;
        this.q2 = q2;
        this.a2 = a2;
        this.q3 = q3;
        this.a3 = a3;
        this.createTime = createTime;
        this.readed = isReaded;
    }

    public TVisitorFeedBack(Integer id, String userName, String q1, String a1, String q2, String a2,
                            String q3, String a3, String q4, String a4, String q5, String a5,
                            String q6, String a6, String q7, String a7, String q8, String a8,
                            String q9, String a9, String q10, String a10, String q11, String a11,
                            String q12, String a12, String q13, String a13, String q14, String a14,
                            String q15, String a15, String q16, String a16, String q17, String a17,
                            String q18, String a18, String q19, String a19, String q20, String a20,
                            String remark, String createIP, Date createTime, Integer isReaded, String GUID) {
        this.id = id;
        this.userName = userName;
        this.q1 = q1;
        this.a1 = a1;
        this.q2 = q2;
        this.a2 = a2;
        this.q3 = q3;
        this.a3 = a3;
        this.q4 = q4;
        this.a4 = a4;
        this.q5 = q5;
        this.a5 = a5;
        this.q6 = q6;
        this.a6 = a6;
        this.q7 = q7;
        this.a7 = a7;
        this.q8 = q8;
        this.a8 = a8;
        this.q9 = q9;
        this.a9 = a9;
        this.q10 = q10;
        this.a10 = a10;
        this.q11 = q11;
        this.a11 = a11;
        this.q12 = q12;
        this.a12 = a12;
        this.q13 = q13;
        this.a13 = a13;
        this.q14 = q14;
        this.a14 = a14;
        this.q15 = q15;
        this.a15 = a15;
        this.q16 = q16;
        this.a16 = a16;
        this.q17 = q17;
        this.a17 = a17;
        this.q18 = q18;
        this.a18 = a18;
        this.q19 = q19;
        this.a19 = a19;
        this.q20 = q20;
        this.a20 = a20;
        this.remark = remark;
        this.createIP = createIP;
        this.createTime = createTime;
        this.readed = isReaded;
        this.gUID = GUID;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "Q1")
    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    @Column(name = "A1")
    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    @Column(name = "Q2")
    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    @Column(name = "A2")
    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    @Column(name = "Q3")
    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    @Column(name = "A3")
    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    @Column(name = "Q4")
    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    @Column(name = "A4")
    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    @Column(name = "Q5")
    public String getQ5() {
        return q5;
    }

    public void setQ5(String q5) {
        this.q5 = q5;
    }

    @Column(name = "A5")
    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    @Column(name = "Q6")
    public String getQ6() {
        return q6;
    }

    public void setQ6(String q6) {
        this.q6 = q6;
    }

    @Column(name = "A6")
    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    @Column(name = "Q7")
    public String getQ7() {
        return q7;
    }

    public void setQ7(String q7) {
        this.q7 = q7;
    }

    @Column(name = "A7")
    public String getA7() {
        return a7;
    }

    public void setA7(String a7) {
        this.a7 = a7;
    }

    @Column(name = "Q8")
    public String getQ8() {
        return q8;
    }

    public void setQ8(String q8) {
        this.q8 = q8;
    }

    @Column(name = "A8")
    public String getA8() {
        return a8;
    }

    public void setA8(String a8) {
        this.a8 = a8;
    }

    @Column(name = "Q9")
    public String getQ9() {
        return q9;
    }

    public void setQ9(String q9) {
        this.q9 = q9;
    }

    @Column(name = "A9")
    public String getA9() {
        return a9;
    }

    public void setA9(String a9) {
        this.a9 = a9;
    }

    @Column(name = "Q10")
    public String getQ10() {
        return q10;
    }

    public void setQ10(String q10) {
        this.q10 = q10;
    }

    @Column(name = "A10")
    public String getA10() {
        return a10;
    }

    public void setA10(String a10) {
        this.a10 = a10;
    }

    @Column(name = "Q11")
    public String getQ11() {
        return q11;
    }

    public void setQ11(String q11) {
        this.q11 = q11;
    }

    @Column(name = "A11")
    public String getA11() {
        return a11;
    }

    public void setA11(String a11) {
        this.a11 = a11;
    }

    @Column(name = "Q12")
    public String getQ12() {
        return q12;
    }

    public void setQ12(String q12) {
        this.q12 = q12;
    }

    @Column(name = "A12")
    public String getA12() {
        return a12;
    }

    public void setA12(String a12) {
        this.a12 = a12;
    }

    @Column(name = "Q13")
    public String getQ13() {
        return q13;
    }

    public void setQ13(String q13) {
        this.q13 = q13;
    }

    @Column(name = "A13")
    public String getA13() {
        return a13;
    }

    public void setA13(String a13) {
        this.a13 = a13;
    }

    @Column(name = "Q14")
    public String getQ14() {
        return q14;
    }

    public void setQ14(String q14) {
        this.q14 = q14;
    }

    @Column(name = "A14")
    public String getA14() {
        return a14;
    }

    public void setA14(String a14) {
        this.a14 = a14;
    }

    @Column(name = "Q15")
    public String getQ15() {
        return q15;
    }

    public void setQ15(String q15) {
        this.q15 = q15;
    }

    @Column(name = "A15")
    public String getA15() {
        return a15;
    }

    public void setA15(String a15) {
        this.a15 = a15;
    }

    @Column(name = "Q16")
    public String getQ16() {
        return q16;
    }

    public void setQ16(String q16) {
        this.q16 = q16;
    }

    @Column(name = "A16")
    public String getA16() {
        return a16;
    }

    public void setA16(String a16) {
        this.a16 = a16;
    }

    @Column(name = "Q17")
    public String getQ17() {
        return q17;
    }

    public void setQ17(String q17) {
        this.q17 = q17;
    }

    @Column(name = "A17")
    public String getA17() {
        return a17;
    }

    public void setA17(String a17) {
        this.a17 = a17;
    }

    @Column(name = "Q18")
    public String getQ18() {
        return q18;
    }

    public void setQ18(String q18) {
        this.q18 = q18;
    }

    @Column(name = "A18")
    public String getA18() {
        return a18;
    }

    public void setA18(String a18) {
        this.a18 = a18;
    }

    @Column(name = "Q19")
    public String getQ19() {
        return q19;
    }

    public void setQ19(String q19) {
        this.q19 = q19;
    }

    @Column(name = "A19")
    public String getA19() {
        return a19;
    }

    public void setA19(String a19) {
        this.a19 = a19;
    }

    @Column(name = "Q20")
    public String getQ20() {
        return q20;
    }

    public void setQ20(String q20) {
        this.q20 = q20;
    }

    @Column(name = "A20")
    public String getA20() {
        return a20;
    }

    public void setA20(String a20) {
        this.a20 = a20;
    }

    @Column(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CreateIP")
    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    @Column(name = "CreateTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "IsReaded")
    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    @Column(name = "GUID")
    public String getgUID() {
        return gUID;
    }

    public void setgUID(String gUID) {
        this.gUID = gUID;
    }
}
