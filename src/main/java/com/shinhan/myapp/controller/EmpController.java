package com.shinhan.myapp.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.firstzone.myapp.dept.DeptService;
import org.firstzone.myapp.emp.EmpDTO;
import org.firstzone.myapp.emp.EmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.myapp.util.DateUtil;

@Controller
@RequestMapping("/emp")
public class EmpController {
	
	//@Autowired는 타입이 같으면 자동으로 Injection
	@Autowired
	EmpService eService;
	
	@Autowired
	DeptService dService;
	
	Logger logger = LoggerFactory.getLogger(EmpController.class);
	
	//deptSelect=50&jobSelect=FI_ACCOUNT&hdate=2005-01-01&salary=5000
	//deptSelect=0 : 모든부서를 의미
	//jobSelect=all : 모든직책을 의미
	
	// new [4가지 조건조회]
	@RequestMapping("/empAll2.do")
	public String empCondition(Model model, HttpSession session
			, Integer deptSelect, String jobSelect
			, @RequestParam(value = "hdate", required = false, defaultValue="1900-01-01") Date hdate, Integer salary) {
		logger.info(hdate.toLocalDate().toString());
		//@RequestParam(value = "hdate", required = false)
		
		
		//선택한 값들을 session에 저장하기
		session.setAttribute("deptSelect", deptSelect);
		session.setAttribute("jobSelect", jobSelect);
		session.setAttribute("hdate", hdate);
		session.setAttribute("salary", salary);
		if(salary == null) salary = 0;
		//Date startDate = DateUtil.getSQLDate(hdate);
		List<EmpDTO> emplist2 = eService
				.selectByCondition(deptSelect, jobSelect, hdate, salary);
		
		logger.info(emplist2.size() + "건 조회됨");
		model.addAttribute("emplist", emplist2);
		model.addAttribute("deptlist", dService.selectAll());//5/22
		model.addAttribute("joblist", eService.selectAllJob());//5/22
		
		return "emp/emplist";
	}
	
	
	@RequestMapping("/empAll.do")
	public String empAll(Model model, Integer deptid, String jobid) {
		List<EmpDTO> emplist2 = null;

		/*if(deptid == null && jobid == null) {
			emplist2 = eService.selectAll(); //없을때만 전부조회 
		} else {
			//부서조회하기
			if(deptid != null && jobid == null) {
				if(deptid == 0) {
					emplist2 = eService.selectAll();
				} else {
					emplist2 = eService.selectBydept(deptid);
				}
			} else {
				//직책조회하기
				if(jobid.equals("all")){
					emplist2 = eService.selectAll(); 
				} else {
					emplist2 = eService.selectByJob(jobid); 
				}
			}
		}*/
		
		if(deptid == null) deptid = 0;
		emplist2 = eService.selectByCondition(deptid, jobid, null, 0);
		
		 
		
		model.addAttribute("emplist", emplist2);
		model.addAttribute("deptlist", dService.selectAll());//5/22
		model.addAttribute("joblist", eService.selectAllJob());//5/22
		//view이름이 return된다.
		//ViewResolver가 접두사 + view이름 + 접미사
		//view로 forword된다.(주소는 바뀌지 않음)
		return "emp/emplist";
	}
	
	@GetMapping("/empDetail.do")
	public void detail(Model model, @RequestParam("empid") Integer empid2) {
		model.addAttribute("empInfo", eService.selectById(empid2));
		model.addAttribute("deptlist", dService.selectAll());
		model.addAttribute("mlist", eService.selectAllManager());
		model.addAttribute("joblist", eService.selectAllJob());
		
	}
	
	//입력창 보여주기
	@GetMapping("/empInsert.do")
	public void insertDisplay(Model model) {
		model.addAttribute("deptlist", dService.selectAll());
		model.addAttribute("mlist", eService.selectAllManager());
		model.addAttribute("joblist", eService.selectAllJob());
	}
	
	//DB에 입력하기
	@PostMapping("/empInsert.do")
	public String insertDB(EmpDTO emp) {
		System.out.println("insert확인(JavaBean) : " + emp);
		eService.empInsert(emp);
		
		//forward : 요청을 위임
		//redirect : 재요청
		return "redirect:empAll.do";
	}
	
	//DB에 수정하기
	@PostMapping("/empDetail.do")
	public String updateDB(EmpDTO emp) {
		System.out.println("update확인(JavaBean) : " + emp);
		eService.empUpdate(emp);
			
		//forward : 요청을 위임
		//redirect : 재요청
		return "redirect:empAll.do";
	}
	
	@GetMapping("/empIdCheck.do")
	@ResponseBody // => response.getWriter().append("1")
	public String test(Integer empid) {
		EmpDTO emp =  eService.selectById(empid);
		if(emp == null) return "0";
		return "1";
	}
	
	//@RequestMapping(value = "/boardDelete.do", method = RequestMethod.GET)
	@GetMapping("/empDelete.do")
	public String delete(Integer empid) {
		eService.empDelete(empid);
		return "redirect:empAll.do";
	}
}
