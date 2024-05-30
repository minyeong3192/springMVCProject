package org.firstzone.myapp.emp;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// DB와 관련없는 로직을 다룸

// Controller -> Service -> DAO
//			  <- 		 <-
// Service : 비지니스 로직을 수행한다.
@Service
public class EmpService {
	
	//type이 같으면 자동으로 Injection
	@Autowired
	EmpDAOMybatis empDAO;
	
	/* 로그인하기 */
	public EmpDTO loginChk(String email, String phone) {
		return empDAO.loginChk(email, phone);
	}
	
	/* job_id 모두 조회(+) */
	public List<JobDTO> selectAllJob() {
		return empDAO.selectAllJob();
	}
	
	
	/* 매니저 모두 조회(+) */
	public List<HashMap<String, Object>> selectAllManager(){
		return empDAO.selectAllManager();
	}
	
	
	/* email 중복체크 */
	public int selectByEmail(String email) {
		return empDAO.selectByEmail(email);
	}
	
	
	
	//8.삭제(Delete)
	public int empDelete(int empid) {
		return empDAO.empDelete(empid);
	}
	//7.수정(Update)
	public int empUpdate(EmpDTO emp) {
		return empDAO.empUpdate(emp);
	}
	
	// 6. 입력(Insert)
	public int empInsert(EmpDTO emp) {
		return empDAO.empInsert(emp);
	}
	
	// 5.다양한 조건으로 조회하기
	// 부서별(=), 직책별(=), 입사일별(>=), 급여(>=)
	public List<EmpDTO> selectByCondition(int deptid, String jobid, Date hdate, int salary) {
		return empDAO.selectByCondition(deptid, jobid, hdate, salary);
	}

	// 4.특정부서직원 상세보기
	public List<EmpDTO> selectByJob(String jobid) {
		return empDAO.selectByJob(jobid);
	}

	// 3.특정부서직원 상세보기
	public List<EmpDTO> selectBydept(int deptid) {
		return empDAO.selectBydept(deptid);
	}

	// 2.특정 직원의 상세보기
	public EmpDTO selectById(int empid) {
		return empDAO.selectById(empid);
	}

	// 1.직원 모두 조회
	public List<EmpDTO> selectAll() {

		return empDAO.selectAll();
	}
	//직원번호를 이용해서 직원의 이름과 직책과 급여를 조회한다.
	public Map<String, Object> empInfo(int empid) {
		return empDAO.empInfo(empid);
	}
	//직원번호를 이용해서 직원의 이름과 직책과 급여를 조회한다.
	public double callFunction(int empid) {
		return empDAO.callFunction(empid);
	}
}