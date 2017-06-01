package com.zhenhappy.ems.manager.entity.visitorfeedback;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by wangxd on 2017-5-25.
 */
@Entity
@Table(name = "t_visitor_feedback_type", schema = "dbo")
public class TVisitorFeedBackType {
    private Integer id;
    private String question_name;       //问卷题目
    private Integer question_type;       //题目答案类型    1：用户输入； 2：单选题；  3：多选题
    private String question_alian;      //题目对应的表t_visitor_feedback里的字段名
    private Integer bakfield;

    public TVisitorFeedBackType() {
    }

    public TVisitorFeedBackType(Integer id, String question_name, Integer question_type, String question_alian, Integer bakfield) {
        this.id = id;
        this.question_name = question_name;
        this.question_type = question_type;
        this.question_alian = question_alian;
        this.bakfield = bakfield;
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

    @Column(name = "question_name")
    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    @Column(name = "question_type")
    public Integer getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(Integer question_type) {
        this.question_type = question_type;
    }

    @Column(name = "bakfield")
    public Integer getBakfield() {
        return bakfield;
    }

    public void setBakfield(Integer bakfield) {
        this.bakfield = bakfield;
    }

    @Column(name = "question_alian")
    public String getQuestion_alian() {
        return question_alian;
    }

    public void setQuestion_alian(String question_alian) {
        this.question_alian = question_alian;
    }
}
