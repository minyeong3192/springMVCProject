package org.firstzone.myapp.dept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeptService {
	
	@Autowired
	@Qualifier("deptMybatis")
	DeptDAOInterface deptDAO;

	// 8. 삭제(DELETE)
	public int deptDelete(int deptid) {
		return deptDAO.deptDelete(deptid);
	}
	
	// 7.수정(Update)
	public int deptUpdate(DeptDTO dept) {
		return deptDAO.deptUpdate(dept);
	}
	
	// 6. 입력(Insert)
	public int deptInsert(DeptDTO dept) {
		return deptDAO.deptInsert(dept);
	}
	
	// 3. 특정부서 이름으로 상세보기
	public List<DeptDTO> selectByName(String deptname) { 
		return deptDAO.selectByName(deptname); 
	}
	
	// 2. 특정부서의 상세보기
	public DeptDTO selectById(int deptid) {
		return deptDAO.selectById(deptid);
	}

	// 1. 부서 모두 조회
	public List<DeptDTO> selectAll() {
		return deptDAO.selectAll();
	}
	
}
