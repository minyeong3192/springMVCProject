package org.firstzone.myapp.dept;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("deptMybatis")
public class DeptDAOMybatis implements DeptDAOInterface {

	@Autowired
	SqlSession sqlSession;
	
	Logger logger = LoggerFactory.getLogger(DeptDAOMybatis.class);
	String namespace = "com.shinhan.dept.";
	
	// 8. 삭제(DELETE)
	public int deptDelete(int deptid) {
		logger.info("DeptDAOMybatis.....deptDelete()");
		return sqlSession.delete(namespace + "deptDelete", deptid);
	}
	
	// 7.수정(Update)
	public int deptUpdate(DeptDTO dept) {
		logger.info("DeptDAOMybatis.....deptUpdate()");
		return sqlSession.update(namespace + "deptUpdate", dept);
	}
	
	// 6. 입력(Insert)
	public int deptInsert(DeptDTO dept) {
		logger.info("DeptDAOMybatis.....deptInsert()");
		return sqlSession.insert(namespace + "deptInsert", dept);
	}
	
	// 3. 특정부서 이름으로 상세보기
	public List<DeptDTO> selectByName(String deptname) {
		logger.info("DeptDAOMybatis.....selectByName()");
		return sqlSession.selectList(namespace + "selectByName", deptname);
	}
	
	// 2. 특정부서 아이디로 상세보기
	public DeptDTO selectById(int deptid) {
		logger.info("DeptDAOMybatis....selectById");
		return sqlSession.selectOne(namespace + "selectById", deptid);
	}
	
	// 1. 부서 모두 조회
	public List<DeptDTO> selectAll() {
		System.out.println("=======");
		logger.info("DeptDAOMybatis.....selectAll()");
		return sqlSession.selectList(namespace + "selectAll");
	}


}
