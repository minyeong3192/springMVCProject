package com.shinhan.myapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.firstzone.myapp.emp.EmpDTO;
import org.firstzone.myapp.emp.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	EmpService eService;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping("/login.do")
	public void loginDisplay() {
		logger.debug("login.do요청(debug)");
		logger.info("login.do요청(info)");
		logger.warn("login.do요청(warn)");
		logger.error("login.do요청(error)");
	}
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.do";
	}
	
	@PostMapping("/login.do")
	public String loginCheck(@RequestParam("email") String email,
			               @RequestParam("pswd") String phone,
			               HttpSession session,
			               HttpServletRequest request
			) {
		//mybatis로 변경했음(null이면 : JDBC에서는 -1 : 존재하지 안흔 ID -2 : password오류
		EmpDTO emp = eService.loginChk(email, phone);
		if(emp == null ) { //|| emp.getEmployee_id() == -1
			session.setAttribute("loginResult", "존재하지 않는 ID입니다.");
			return "redirect:login.do";
		
		}else if(emp.getEmployee_id() == -2) { //emp.getEmployee_id() == -2
			//!emp.getPhone_number().equals(phone)
			session.setAttribute("loginResult", "PASSWORD 오류입니다.");
			return "redirect:login.do";
			
		}else {
			//로그인 성공
			session.setAttribute("loginResult", "LOGIN 성공입니다.");
			session.setAttribute("emp", emp);
			
			String lastRequest = (String)session.getAttribute("lastRequest");
			
			String goPage;
			if(lastRequest == null) {
				// <처음부터 로그인 요청을 한 것>
				goPage = "../index.do";
			} else {
				// <로그인 없이 다른 페이지를 요청한 것>
				int length = request.getContextPath().length();
				goPage = lastRequest.substring(length);
				String queryString = (String)session.getAttribute("queryString");
				if(queryString!=null) goPage = goPage + "?" + queryString;
			}
			return "redirect:" + goPage;
		}
	}
	
}
