package com.zhenhappy.ems.security;

import com.zhenhappy.ems.dto.Principle;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lianghaijian on 2014-04-27.
 */
public class LoginFilter implements Filter {
	List<String> whiteList = new ArrayList<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Principle principle = (Principle) request.getSession().getAttribute(Principle.PRINCIPLE_SESSION_ATTRIBUTE);
        if (principle == null) {
            if(inWhiteList(request.getRequestURI())){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.sendRedirect(request.getContextPath() + "/login.html");
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

	/**
	 * 白名单匹配
	 * @param keyword
	 * @return
	 */
	private boolean inWhiteList(String keyword){
		for(String white : whiteList) if(white.contains(keyword)) return true;
		return false;
	}

    @Override
    public void destroy() {

    }
}
