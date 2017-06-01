/*
 *    Copyright 2014-2015 The Happy Network Corporation
 */
package com.zhenhappy.ems.service;

import com.zhenhappy.ems.entity.Email;
import com.zhenhappy.ems.entity.ExhibitorInvisitorEmail;
import com.zhenhappy.ems.entity.TEmailSendDetail;
import com.zhenhappy.util.Page;

/**
 * @project spring-mail
 * @author 吴剑斌
 * @create 2012-3-24 上午12:26:17
 */
public interface EmailMailService {
	/**
	 * 异步发送邮件
	 * @param exhibitorInvisitorEmail
	 * @param eid
	 */
	public abstract void sendExhibitorMailByAsynchronousMode(ExhibitorInvisitorEmail exhibitorInvisitorEmail, Integer eid) throws Exception;

    /**
     * 异步发送邮件
     * @param email
     */
    public abstract void retrySendMailByAsynchronousMode(Email email, Integer eid);

	/**
	 * 使用 spring 3.0 的异步注解驱动异步发送邮件
	 * @param email
	 */
	public abstract void sendMailByAsyncAnnotationMode(Email email);

    public void loadDetailByEid(Page page, Integer eid);

    public TEmailSendDetail loadMailByMid(Integer mid, Integer eid);
	public abstract void sendHtmlEmails(String receivers, String subject, String content);
}
