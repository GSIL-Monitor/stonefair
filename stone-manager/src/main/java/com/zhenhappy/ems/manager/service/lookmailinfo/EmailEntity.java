package com.zhenhappy.ems.manager.service.lookmailinfo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by Administrator on 2016/12/9.
 */
public class EmailEntity extends Authenticator {
    /**
     * 用户名（登录邮箱）
     */
    protected static String username;
    /**
     * 密码
     */
    protected static String password;

    /**
     * 初始化邮箱地址和密码
     * @param username 邮箱
     * @param password 密码
     */
    public EmailEntity(String username, String password) {
        EmailEntity.username = "exhibitor@stonefair.org.cn";
        EmailEntity.password = "Jhxsf2015~";
    }

    /**
     * 重写自我检验方法
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    String getPassword() {
        return password;
    }

    String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        EmailEntity.password = password;
    }

    public void setUsername(String username) {
        EmailEntity.username = username;
    }
}
