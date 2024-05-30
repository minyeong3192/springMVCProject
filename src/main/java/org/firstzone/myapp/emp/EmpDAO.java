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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.util.DBUtil;

// DB와 업무를 보는 역할
// DAO (Data Access하는 비지니스 로직을 처리하는 Object)
@Repository
public class EmpDAO {
	
	//1.field 이용하기
	@Autowired
	//같은 타입이 여러개있으면 이름을 비교해서 같은 이름의 Bean을 Injection
	@Qualifier("dataSource") 
	DataSource ds;
	
	//2.생성자이용하기
	//@Autowired
	//public EmpDAO(DataSource ds) { this.ds = ds;}
	
	//3.setter 넣기
	//@Autowired
	//public void setDs(DataSource ds) {this.ds = ds;}
	
	Connection conn;
	Statement st;
	PreparedStatement pst; //Statement를 상속받음, 바인딩변수 지원
	ResultSet rs;
	
	
	/* 로그인하기 */
	public EmpDTO loginChk(String email, String phone) {
		EmpDTO emp = null;
		String sql = "select employee_id, first_name, last_name, phone_number "
				+ " from employees where email = ?";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			rs = pst.executeQuery(); //실행
			if (rs.next()) { //email이 같아
				if(rs.getString("phone_number").equals(phone)) {
					emp = new EmpDTO();
					emp.setEmployee_id(rs.getInt("employee_id"));
					emp.setFirst_name(rs.getString("first_name"));
					emp.setLast_name(rs.getString("last_name"));
					emp.setEmail(email);
					emp.setPhone_number(phone);
					
				}else {
					emp = new EmpDTO();
					emp.setEmployee_id(-2); //비밀번호 오류
				}
			}else {
				emp = new EmpDTO();
				emp.setEmployee_id(-1); //존재하지 않는 직원
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	
	/* job_id 모두 조회(+) */
	public List<JobDTO> selectAllJob() {
		List<JobDTO> joblist = new ArrayList<>();
		String sql = "select * from jobs";
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				JobDTO job = new JobDTO(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
				joblist.add(job);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return joblist;
	}
	
	/* 매니저 모두 조회(+) */
	public List<HashMap<String, Object>> selectAllManager() {
		List<HashMap<String, Object>> emplist = new ArrayList<>();
		String sql = "select employee_id, first_name ||'  '|| last_name fullname\r\n"
				+ " from employees\r\n"
				+ " where employee_id\r\n"
				+ " in (select distinct manager_id\r\n"
				+ " from employees where manager_id is not null)";
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("employee_id", rs.getInt(1));
				data.put("fullname", rs.getString(2));
				emplist.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}
	
	
	
	//8.삭제(Delete)
	public int empDelete(int empid) {
		int result  = 0;
		String sql = "delete from employees "
				+ " where EMPLOYEE_ID =?";
		try {
			conn = ds.getConnection();//setAutoCommit(true) 되었음
			pst = conn.prepareStatement(sql);
			pst.setInt(1, empid);
			result = pst.executeUpdate(); //DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	}
	
	
	//7.수정(Update)
	public int empUpdate(EmpDTO emp) {
		int result  = 0;
		String sql = "update employees "
				+ " set"
				+ "        FIRST_NAME=?,"
				+ "        LAST_NAME=?,"
				+ "        EMAIL=?,"
				+ "        PHONE_NUMBER=?,"
				+ "        HIRE_DATE=?,"
				+ "        JOB_ID=?,"
				+ "        SALARY=?,"
				+ "        COMMISSION_PCT=?,"
				+ "        MANAGER_ID=?,"
				+ "        DEPARTMENT_ID=?"
				+ " where EMPLOYEE_ID =?";
		
		
		try {
			conn = ds.getConnection();//setAutoCommit(true) 되었음
			pst = conn.prepareStatement(sql);
			pst.setInt(11, emp.getEmployee_id());
			pst.setString(1, emp.getFirst_name());
			pst.setString(2, emp.getLast_name());
			pst.setString(3, emp.getEmail());
			pst.setString(4, emp.getPhone_number());
			pst.setDate(5, emp.getHire_date());
			pst.setString(6, emp.getJob_id());
			pst.setInt(7, emp.getSalary());
			pst.setDouble(8, emp.getCommission_pct());
			pst.setInt(9, emp.getManager_id());
			pst.setInt(10, emp.getDepartment_id());
			result = pst.executeUpdate(); //DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	
	}
	
	//6. 입력(Insert)
	public int empInsert(EmpDTO emp) {
		int result  = 0;
		String sql = "insert into employees values(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			conn = ds.getConnection(); //setAutoCommit(true) 되었음
			pst = conn.prepareStatement(sql);
			pst.setInt(1, emp.getEmployee_id());
			pst.setString(2, emp.getFirst_name());
			pst.setString(3, emp.getLast_name());
			pst.setString(4, emp.getEmail());
			pst.setString(5, emp.getPhone_number());
			pst.setDate(6, emp.getHire_date());
			pst.setString(7, emp.getJob_id());
			pst.setInt(8, emp.getSalary());
			pst.setDouble(9, emp.getCommission_pct());
			pst.setInt(10, emp.getManager_id());
			pst.setInt(11, emp.getDepartment_id());
			result = pst.executeUpdate(); //DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	
	}
	
	
	//5.다양한 조건으로 조회하기
	//부서별(=), 직책별(=), 입사일별(>=), 급여(>=)
	public List<EmpDTO> selectByCondition(int deptid, String jobid, Date hdate, int salary) {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		String sql = "select * "
				+ " from employees"
				+ " where department_id = ?"
				+ " and job_id = ?"
				+ " and hire_date >= ?"
				+ " and salary >= ?";
		try {
			conn = ds.getConnection();// DB연결
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid); //첫번째 ?에 jobid를 넣어라.
			pst.setString(2, jobid); 
			pst.setDate(3, hdate); 
			pst.setInt(4, salary); 
			rs = pst.executeQuery(); //실행
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs); //자식이 부모에 들어갈 수 있으니까 pst
		}
		return emplist;
	}
	
	// 4-2.특정JOB인 직원모두조회
	public List<EmpDTO> selectByJob(String jobid) { // 부서아이디 들어옴
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		String sql = "select * from employees where job_id = ?"; // 문자인 경우 ? 사용
														 //like ? ||'%'
		try {
			conn = ds.getConnection();// DB연결
			pst = conn.prepareStatement(sql);
			pst.setString(1, jobid); //첫번째 ?에 jobid를 넣어라.
			rs = pst.executeQuery(); //실행
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs); //자식이 부모에 들어갈 수 있으니까 pst
		}
		return emplist;
	}

	// 4.특정JOB인 직원모두조회
	public List<EmpDTO> selectByJob2(String jobid) { // 부서아이디 들어옴
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		String sql = "select * from employees where job_id = '" + jobid + "'"; // 문자인 경우 ''사용
		try {
			conn = ds.getConnection(); // DB연결
			st = conn.createStatement(); // 통로만들기
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 3.특정부서의 직원모두조회
	public List<EmpDTO> selectBydept(int deptid) { // 부서아이디 들어옴
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		String sql = "select * from employees where department_id = ?";
		try {
			conn = ds.getConnection(); // DB연결
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid); //첫번째 부서아이디에 depid를 넣어라
			rs = pst.executeQuery();
			
//			st = conn.createStatement(); // 통로만들기
//			rs = st.executeQuery(sql);
			
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	// 2.특정직원의 상세보기
	public EmpDTO selectById(int empid) {
		EmpDTO emp = null;
		String sql = "select * from employees where employee_id = " + empid;
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return emp;
	}
	
	/* email이 있는지 없는지 중복체크!!!!!! */
	public int selectByEmail(String email) {
		String sql = "select 1 from employees where email = ?";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, email);
			rs = pst.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}

		return 0;
	}
	
	
	
	// 1.직원 모두 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<EmpDTO>();
		String sql = "select * from employees";
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return emplist;
	}

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = new EmpDTO();
		emp.setCommission_pct(rs.getDouble("commission_pct"));
		emp.setDepartment_id(rs.getInt("department_id"));
		emp.setEmail(rs.getString("email"));
		emp.setEmployee_id(rs.getInt("employee_id"));
		emp.setFirst_name(rs.getString("first_name"));
		emp.setHire_date(rs.getDate("hire_date"));
		emp.setJob_id(rs.getString("job_id"));
		emp.setLast_name(rs.getString("last_name"));
		emp.setManager_id(rs.getInt("manager_id"));
		emp.setPhone_number(rs.getString("phone_number"));
		emp.setSalary(rs.getInt("salary"));

		return emp;
	}

	//직원번호를 입력받아서 직원정보를 (이름, 직책, 급여)를 return
	public Map<String, Object> empInfo(int empid) {
		Map<String, Object> empMap = new HashMap<>();
		String fname = null, job = null;
		int salary = 0;
		String sql = "{call sp_empInfo(?, ?, ?, ?)}";
		CallableStatement cstmt = null;
		
		
		try {
			conn = ds.getConnection();
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, empid);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.registerOutParameter(4, Types.VARCHAR);
			boolean result = cstmt.execute();
			fname = cstmt.getString(2);
			job = cstmt.getString(3);
			salary = cstmt.getInt(4);
			empMap.put("fname", fname);
			empMap.put("job", job);
			empMap.put("salary", salary);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbDisconnect(conn, cstmt, rs);
		}
		return empMap;
	}
	
	//직원번호가 들어오면 직원보너스를 return하는 함수를 호출한다.
	public double callFunction(int empid) {
		double bonus = 0;
		String sql = "select f_bonus(?) from dual";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, empid);
			rs = pst.executeQuery();
			if(rs.next()) {
				bonus = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return bonus;
	}

	
}