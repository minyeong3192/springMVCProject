package com.shinhan.myapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

//global exception처리 : @ControllerAdvice
@ControllerAdvice
public class ExceptionHandlerController {
	
	Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);
	
	@ExceptionHandler(Exception.class)
	public String errorProcess500(Exception ex) {
		
		logger.info("====== 500 error. ======");
		logger.info(ex.getClass().getPackageName());
		logger.info(ex.getClass().getSimpleName());
		logger.info(ex.getMessage());
		ex.printStackTrace();
		return "error/errorPage500";
	}
	
	//요청주소가 잘못되었을 때 문제 발생! : static자원과 충돌
	//*.do요청 => DispatcherServlet 받아서 handler가 없는 주소이면 오류를 일으킨다.
	//static자원과 충돌안되고 잘 수행한다.
	// /* => 문제 발생! : static자원과 충돌
	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handlerError404(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMessage", "====== 404 error. ======");
		mv.addObject("url", request.getRequestURL());
		mv.setViewName("error/errorPage404");
		return mv;
	}

}

/*
 * 
 * @ExceptionHandler(Exception.class) public String aa(Exception ex) {
 * System.out.println("=500=============="); System.out.println(ex.getClass());
 * System.out.println(ex.getMessage()); return "error/errorPage500"; }
 * 
 * @ExceptionHandler(NoHandlerFoundException.class)
 * //@ResponseStatus(HttpStatus.NOT_FOUND) public ModelAndView
 * handlerError404(HttpServletRequest request) {
 * System.out.println("=404=============="); ModelAndView mv = new
 * ModelAndView(); mv.addObject("errorMessage", "404����"); mv.addObject("url",
 * request.getRequestURL()); mv.setViewName("error/errorPage404"); return mv; }
 * 
 */
