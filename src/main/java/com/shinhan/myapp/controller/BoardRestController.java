package com.shinhan.myapp.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.firstzone.myapp.dept.DeptDTO;
import org.firstzone.myapp.emp.EmpDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.shinhan.myapp.model.BoardDTO;
import com.shinhan.myapp.model.BoardService;

//Ajax로 호출하고자 한다.
//응답을 data로 View보내서 view가 data를 사용한다.

@RestController //@Controller + @ResponseBody
@RequestMapping("/board/api")
public class BoardRestController {
	
	@Autowired
	BoardService bService;
	
	Logger logger = LoggerFactory.getLogger(DeptRestController.class);
	
	//모두 조회
	@GetMapping(value = "/selectAll", produces = "application/json")
	public List<BoardDTO> selectAll(){
		List<BoardDTO> boardlist = bService.selectAll();
		return boardlist;
	}
		
	//조건 조회
	@GetMapping("/detail/{bno}") //상세보기
	public BoardDTO detail(@PathVariable Integer bno) {
		return bService.selectById(bno);
	}
	
	//입력
	@PostMapping(value = "/insert", produces = "text/plain;charset=UTF-8")	
	public String insert(MultipartHttpServletRequest multipart, HttpSession session) 
			throws UnsupportedEncodingException {
		BoardDTO board = new BoardDTO();
		HttpServletRequest request = (HttpServletRequest) multipart;
		board.setTitle(request.getParameter("title"));
		board.setContent(request.getParameter("content"));
		
		EmpDTO emp = (EmpDTO) session.getAttribute("emp");
		String writer = null;
		if(emp == null) {
			writer = "손님";
		} else {
			writer = emp.getFirst_name() + emp.getLast_name();
		}
		board.setWriter(writer);
		
		List<MultipartFile> fileList = multipart.getFiles("pic");
		String path = session.getServletContext().getRealPath("./resources/uploads");
		
		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		long time = System.currentTimeMillis(); //현재시간
		
		for (MultipartFile mf : fileList) {
			String originFileName = mf.getOriginalFilename(); //
			String saveFileName = String.format("%d_%s", time, originFileName);
			board.setPic(saveFileName);
			try {
				//upload하기
				mf.transferTo(new File(path, saveFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("board:" + board);
		int result = bService.insertBoard(board);
		return result>0 ? "입력성공":"입력실패";
	}
	
	//삭제
	@DeleteMapping(value = "/delete/{bno}", produces = "text/plain;charset=UTF-8")
	public String delete(@PathVariable("bno") Integer id) {
		int result = bService.deleteBoard(id);
		return result>0 ? "삭제성공" : "삭제실패";
	}
	
	//수정
	@PutMapping(value = "/update", produces = "text/plain;charset=UTF-8",
			consumes = "application/json")
	public String update(@RequestBody BoardDTO board) {
		int result = bService.updateBoard(board);
		return result>0 ? "수정성공" : "수정실패";
		
	}
	
}
