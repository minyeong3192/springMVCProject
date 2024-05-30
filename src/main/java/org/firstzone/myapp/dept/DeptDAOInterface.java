package org.firstzone.myapp.dept;

import java.util.List;

	public interface DeptDAOInterface {
		//8. 삭제(DELETE)
		public int deptDelete(int deptid);
		
		// 7.수정(Update)
		public int deptUpdate(DeptDTO dept);
		
		// 6. 입력(Insert)
		public int deptInsert(DeptDTO dept);
		
		// 3. 특정부서 이름으로 상세보기
		public List<DeptDTO> selectByName(String deptid);
		
		// 2. 특정부서 아이디로 상세보기
		public DeptDTO selectById(int deptid);

		// 1. 부서 모두 조회
		public List<DeptDTO> selectAll();
		
}
