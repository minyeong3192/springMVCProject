package com.shinhan.myapp.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


//Service : 비즈니스 로직 작성

@Service("bService2")  // @Service = @Component + 서비스역할
public class BoardService {
	
	//BoardDAO boardDao = new BoardDAO();
	
	//*****2.적용하기 : @Autowired
	//타입으로 설정! 즉, 타입이 같으면 적용(Injection)한다.
	@Autowired
	//생략가능 @Qualifier("bDAO")
	BoardDAO boardDao;
	
	public int deleteBoard(int bno) {
		return boardDao.deleteBoard(bno);
	}
	
	public int updateBoard(BoardDTO board) { 
		return boardDao.updateBoard(board);
	}
	
	public int insertBoard(BoardDTO board) {
		return boardDao.insertBoard(board);
	}
	
	public BoardDTO selectById(int bno) { 
		return boardDao.selectById(bno);
	}
	
	public List<BoardDTO> selectAll() {
		return boardDao.selectAll();
	}

	public int deleteBoardArray(Integer[] checkBno) {
		// TODO Auto-generated method stub
		return boardDao.deleteBoardArray(checkBno);
	}

	
	
}
