package com.shinhan.myapp.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.firstzone.myapp.emp.EmpDTO;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */

public class LoginCheckFilter implements Filter {
    
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		//로그인하지 않으면 업무로직 수행못함
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep= (HttpServletResponse)response;
		HttpSession session = req.getSession();
		
		//webshop22/auth/login.do
		//endWith() : ~로 끝나면
		if(!req.getRequestURI().endsWith("login.do")) {
			
			System.out.println("!! getQueryString : " + req.getQueryString());
			session.setAttribute("lastRequest", req.getRequestURI());
			session.setAttribute("queryString", req.getQueryString());
			
			EmpDTO emp = (EmpDTO) session.getAttribute("emp");
			if(emp == null) { 
				//로그인되어있지 않으면 브라우저로 내려가서 로그인으로 재요청(=sendRedirect)
				String path = req.getContextPath();
				//System.out.println("filter...path => " + path);
				rep.sendRedirect(path + "/auth/login.do");
				return ;
			}	
		}
		// place your code here
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
