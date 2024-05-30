package org.firstzone.myapp.dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.util.DBUtil;

@Repository
public class DeptDAO{
	
	//타입이 같으면 Injection
	//같은 타입이 여러개이면 Error
	@Autowired
	//같은 타입이 여러개있으면 이름을 비교해서 같은 이름의 Bean을 Injection
	@Qualifier("dataSource") 
	DataSource ds;
	
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;

	//8. 삭제(DELETE)
	public int deptDelete(int deptid) {
		int result  = 0;
		String sql = "delete from departments "
				+ " where department_ID =?";
		
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid);
			result = pst.executeUpdate(); //DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;
	
	}
	
	
	// 7.수정(Update)
	public int deptUpdate(DeptDTO dept) {
		int result = 0;
		String sql = "update departments "
				+ " set"
				+ "        DEPARTMENT_NAME=?,"
				+ "        MANAGER_ID=?,"
				+ "        LOCATION_ID=?"
				+ " where DEPARTMENT_ID =?";

		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(4, dept.getDepartment_id());
			pst.setString(1, dept.getDepartment_name());
			pst.setInt(2, dept.getManager_id());
			pst.setInt(3, dept.getLocation_id());
			result = pst.executeUpdate(); // DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;

	}
	
	// 6. 입력(Insert)
	public int deptInsert(DeptDTO dept) {
		int result = 0;
		String sql = "insert into departments (department_id, department_name, manager_id, location_id) values(?,?,?,?)";

		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, dept.getDepartment_id());
			pst.setString(2, dept.getDepartment_name());
			pst.setInt(3, dept.getManager_id());
			pst.setInt(4, dept.getLocation_id());
			result = pst.executeUpdate(); // DML문장은 executeUpdate, select문은 executeQuery
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return result;

	}
	
	// 3. 특정부서 이름으로 상세보기
	public List<DeptDTO> selectByName(String deptname) {
		List<DeptDTO> deptlist = new ArrayList<DeptDTO>();
		String sql = "select * from departments where lower(department_name) like lower('%'||?||'%')";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, deptname);
			while (rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		return deptlist;
	}
	
	
	// 2. 특정부서 아이디로 상세보기
	public DeptDTO selectById(int deptid) {
		DeptDTO dept = null;
		String sql = "select * from departments where department_id = " + deptid;
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				dept = makeDept(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dept;
	}

	// 1. 부서 모두 조회
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptlist = new ArrayList<DeptDTO>();
		String sql = "select * from departments";
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		return deptlist;
	}

	private DeptDTO makeDept(ResultSet rs) throws SQLException {
		DeptDTO dept = new DeptDTO();
		dept.setDepartment_id(rs.getInt("department_id"));
		dept.setDepartment_name(rs.getString("department_name"));
		dept.setManager_id(rs.getInt("manager_id"));
		dept.setLocation_id(rs.getInt("location_id"));

		return dept;
	}

}
