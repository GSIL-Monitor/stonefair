/*
 *    Copyright 2014-2015 The Happy Network Corporation
 */
package com.zhenhappy.ems.service;

import com.zhenhappy.ems.entity.Email;
import com.zhenhappy.ems.entity.EmailExtent;
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
	 * 同步发送邮件
	 * @param email
	 * @throws Exception
	 */
	public abstract void sendMailBySynchronizationMode(Email email) throws Exception;
	/**
	 * 异步发送邮件
	 * @param email
	 */
	public abstract void sendMailByAsynchronousMode(Email email, Integer eid);
	public abstract void sendInvisitorMailByAsynchronousMode(ExhibitorInvisitorEmail exhibitorInvisitorEmail) throws Exception;

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

	public abstract void sendMailByAsyncAnnotationMode(EmailExtent email);

    public void loadDetailByEid(Page page, Integer eid);

    public TEmailSendDetail loadMailByMid(Integer mid, Integer eid);
	public abstract void sendHtmlEmails(String receivers, String subject, String content);
}
