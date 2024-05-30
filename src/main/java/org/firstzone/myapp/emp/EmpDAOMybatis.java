package org.firstzone.myapp.emp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.util.DBUtil;

@Repository
public class EmpDAOMybatis {
	
	@Autowired
	SqlSession sqlSession;
	
	String namespace = "com.shinhan.emp.";
	
	Logger logger = LoggerFactory.getLogger(EmpDAOMybatis.class);
	
	/* 로그인하기 */
	public EmpDTO loginChk(String email, String phone) {
		EmpDTO emp = sqlSession.selectOne(namespace + "loginChk", email);
		logger.info(emp == null ? "존재하지 않는 직원" : emp.toString());
		return emp;
	}
	
	/* job_id 모두 조회(+) */
	public List<JobDTO> selectAllJob() {
		List<JobDTO> jlist = sqlSession.selectList(namespace+"selectAllJob");
		logger.info(jlist.toString());
		logger.info(jlist.size() + "건 selectAllJob로 조회되었습니다.");
		return jlist;
	}
	
	/* 매니저 모두 조회(+) */
	public List<HashMap<String, Object>> selectAllManager() {
		List<HashMap<String, Object>> mlist = sqlSession.selectList(namespace+"selectAllManager");
		logger.info(mlist.toString());
		logger.info(mlist.size() + "건 selectAllManager로 조회되었습니다.");
		return mlist;
	}
	
	//8.삭제(Delete)
	public int empDelete(int empid) {
		int result = sqlSession.delete(namespace + "empDelete", empid);
		logger.info(result + "건 삭제되었습니다.");
		return result;
	}
	
	//7.수정(Update)
	public int empUpdate(EmpDTO emp) {
		int result = sqlSession.update(namespace + "empUpdate", emp);
		logger.info(result + "건 수정되었습니다.");
		return result;
	}
	
	//6. 입력(Insert)
	public int empInsert(EmpDTO emp) {
		int result = sqlSession.insert(namespace + "empInsert", emp);
		logger.info(result + "건 삽입되었습니다.");
		return result;
	}
	
	//5.다양한 조건으로 조회하기  //부서별(=), 직책별(=), 입사일별(>=), 급여(>=)
	public List<EmpDTO> selectByCondition(int deptid, String jobid, Date hdate, int salary) {
		Map<String, Object> conditionMap = new HashMap<>();
		conditionMap.put("deptid", deptid);
		conditionMap.put("jobid", jobid);
		conditionMap.put("hdate", hdate);
		conditionMap.put("salary", salary);
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectByCondition", conditionMap);
		logger.info(emplist.toString());
		logger.info(emplist.size() + "건 조회되었습니다.");
		return emplist;
	}
	
	// 4-2.특정JOB인 직원모두조회
	public List<EmpDTO> selectByJob(String jobid) { // 부서아이디 들어옴
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectByJob", "%"+jobid+"%");
		logger.info(emplist.toString());
		logger.info(emplist.size() + "건 job조건으로 조회되었습니다.");
		return emplist;
	}

	// 4.특정JOB인 직원모두조회 XXX
	public List<EmpDTO> selectByJob2(String jobid) { // 부서아이디 들어옴
		return null;
	}

	// 3.특정부서의 직원모두조회
	public List<EmpDTO> selectBydept(int deptid) { // 부서아이디 들어옴
		List<EmpDTO> emplist = sqlSession.selectList(namespace + "selectBydept", deptid);
		logger.info(emplist.toString());
		logger.info(emplist.size() + "건 deptid조건으로 조회되었습니다.");
		return emplist;
	}

	// 2.특정직원의 상세보기
	public EmpDTO selectById(int empid) {
		EmpDTO emp = sqlSession.selectOne(namespace+"selectById", empid);
		logger.info(emp != null ? emp.toString() : "data없음");
		return emp;
	}
	
	/* email이 있는지 없는지 중복체크!!!!!! */
	public int selectByEmail(String email) {
		Integer result = sqlSession.selectOne(namespace+"selectById", email);
		logger.info(result.toString());
		return result;
	}
	
	// 1.직원 모두 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = sqlSession.selectList(namespace+"selectAll");
		logger.info(emplist.toString());
		logger.info(emplist.size() + "건 조회되었습니다.");
		return emplist;
	}

	
	//직원번호를 입력받아서 직원정보를 (이름, 직책, 급여)를 return
	public Map<String, Object> empInfo(int empid) {
		return null;
	}
	
	//직원번호가 들어오면 직원보너스를 return하는 함수를 호출한다.
	public double callFunction(int empid) {
		return 0;
	}

	
}