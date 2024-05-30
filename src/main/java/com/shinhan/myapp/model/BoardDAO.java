package com.shinhan.myapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.shinhan.myapp.util.DBUtil;

@Repository("bDAO") //@Repository = @Component(Bean,객체)인데 + DAO 역할을 한다.
public class BoardDAO {
	
	//*****1.적용하기 : @Repository("bDAO")
	//타입으로 설정! 즉, 타입이 같으면 적용(Injection)한다.
	@Autowired
	//같은 타입이 여러개있으면 이름을 비교해서 같은 이름의 Bean을 Injection
	@Qualifier("dataSource") 
	DataSource ds;
	
	Connection conn;
	Statement st;
	PreparedStatement pst;
	ResultSet rs; //조회
	int result; //insert등등의 건수 조회
	
	public int deleteBoard(int bno) {
		
		String sql = "delete from TBL_BOARD where bno=? ";
		try {
			conn = ds.getConnection(); 
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bno);
			
			result = pst.executeUpdate(); //update
			
			//rs = pst.executeQuery(); 조회이기때문에XX
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	
	public int updateBoard(BoardDTO board) { 
		
		String sql = "update TBL_BOARD set title=?, content=?, pic=? "
				+ " where bno=? ";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, board.getTitle());
			pst.setString(2, board.getContent());
			//pst.setString(3, board.getWriter());
			pst.setString(3, board.getPic());
			pst.setInt(4, board.getBno());
			
			result = pst.executeUpdate(); //update
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	public int insertBoard(BoardDTO board) { 
		
		String sql = "insert into TBL_BOARD(bno, title, content, writer, pic) "
				+ " values(seq_board_bno.nextval, ?, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1, board.getTitle());
			pst.setString(2, board.getContent());
			pst.setString(3, board.getWriter());
			pst.setString(4, board.getPic());
			result = pst.executeUpdate(); //insert
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return result;
	}
	
	
	
	public BoardDTO selectById(int bno) { 
		BoardDTO board = null;
		
		String sql = "select * from TBL_BOARD where bno = ?";
		try {
			conn = ds.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, bno);
			rs = pst.executeQuery();
			while(rs.next()) {
				board = makeBoard(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		return board;
	}
	
	
	public List<BoardDTO> selectAll() { //모두 조회
		List<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		String sql = "select * from TBL_BOARD";
		try {
			conn = ds.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				BoardDTO board = makeBoard(rs);
				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, rs);
		}
		
		
		return boardList;
	}

	private BoardDTO makeBoard(ResultSet rs) throws SQLException {
		BoardDTO board = new BoardDTO();
		
		board.setBno(rs.getInt("bno"));
		board.setContent(rs.getString("content")); //이름을 쓰는게 좋아(칼럼의)
		board.setCreate_date(rs.getDate("create_date"));
		board.setPic(rs.getString("pic"));
		board.setTitle(rs.getString("title"));
		board.setWriter(rs.getString("writer"));
		
		return board;
	}
	
	public int deleteBoardArray(Integer[] checkBno) {
		// (java)[10, 20, 30] => (SQL)where bno in (10,20,30)
		String sql = " delete from TBL_BOARD where bno in (%s) ";
		
		sql = String.format(sql, Arrays.stream(checkBno).map(String::valueOf)
				.collect(Collectors.joining(","))); //10, 20, 30
		
		try {
			conn = ds.getConnection(); 
			st = conn.createStatement();
			result = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return result;
	}
	
}
