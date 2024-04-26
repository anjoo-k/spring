package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

// 스프링 빈에 등록
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1" )int currentPage, Model model) {
		int boardCount = boardService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(boardCount, currentPage, 10, 5);
		ArrayList<Board> list = boardService.selectList(pi);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		return "board/boardListView";		
	}
	
	// post만 받고싶을 때
//	@RequestMapping(value = "detail.bo", method = RequestMethod.POST)
	@RequestMapping(value = "detail.bo")
	public String selectBoard(int bno, Model model) {
		
		int result  = boardService.increaseCount(bno);
		
		if(result > 0) {
			Board b = boardService.selectBoard(bno);
			model.addAttribute("b", b);
			return "board/boardDetailView";
		} else {
			model.addAttribute("errorMsg", "게시글 조회 실패");
			return "common/errorPage";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "rlist.bo", produces="application/json; charset-UTF-8")
	public String ajaxSelectReplyList(int bno) {
		ArrayList<Reply> list = boardService.selectReply(bno);
		return new Gson().toJson(list);
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		System.out.println(b);
		System.out.println(upfile);
		
		
		// 전달된 파일이 있을 경우 => 파일이름 변경 => 서버에 저장 => 원본명, 서버업로드된 경로를 board 객체에 담기
		if(!upfile.getOriginalFilename().equals("")){
			String changeName = saveFile(upfile, session);
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		int result = boardService.insertBoard(b);
		if(result > 0) { //성공 => list 페이지로 이동
			session.setAttribute("alertNsg", "게시글 작성 성공");
			return "redirect:list.bo";
		} else { // 실패 => 에러페이지
			model.addAttribute("errorMsg", "게시글 작성 실패");
			return "common/errorPage";
		}
		
	}
	
	// 이 페이지에는 컨트롤러만 있어야하나 보기 편하게 만드는것
	// 원래 아래 메소드는 common.template에 들어가야
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		//파일명 수정 후 서버에 업로드하기("imgFile.jpg => 202404231004305488.jpg")
		String originName = upfile.getOriginalFilename();
		
		//연원일 시분초 가져오기
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		//5자리 랜덤값
		int ranNum = (int)Math.random() * 90000 + 10000;
		
		//확장자
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//수정된 첨부파일명(절대 겹치지 않을)
		String changeName = currentTime + ranNum + ext;
		
		//첨부파일을 저장할 폴더의 물리적 경로(session)
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/");
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
		
	}
	
	
	// 보드넘버로 받아서 보드를 다 보내야
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Board b, Model model) {
		System.out.println(b);
		model.addAttribute("b", boardService.selectBoard(bno));
		
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo") // (@ModelAttribute가 생략되어 있는 상태
	public String updateBoard(@ModelAttribute Board b, MultipartFile reupfile, HttpSession session) {
		
		// 새로운 첨부파일이 넘어온 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			// 기존의 첨부파일이 있으면 => 기존의 파일을 삭제
			if(b.getOriginName() != null) { // 경로를 changeName에 넣어놨기 때문에
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			// 새로 넘어온 첨부파일을 서버에 업로드 시키기
			String changeName = saveFile(reupfile, session);
			
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		
		/*
		 * b에 boardTitle, boardContent
		 * 
		 * 1. 새로운 첨부파일 x, 기존첨부파일 x
		 *    => originName : null, changeName : null
		 * 2. 새로운 첨부파일 x, 기존첨부파일 o
		 *    => originName : 기존첨부파일 이름, changeName : 기존첨부파일 경로
		 * 3. 새로운 첨부파일 o, 기존첨부파일 o
		 *    => originName : 새로운 첨부파일 이름, changeName : 새로운 첨부파일 경로    
		 * 4. 새로운 첨부파일 o, 기존첨푸파일 x
		 *    => originName : 새로운 첨부 파일(기존이 없으니까), changeName : 새로운 첨부파일 경로    
		 * 
		 * 이것의 처리는 서비스에서 
		 *
		 */
		
		int result = boardService.updateBoard(b);
		
		if(result > 0) { // 성공
			session.setAttribute("alertMsg", "게시글 수정 성공");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		} else { // 실패
			return "common/errorPage";
		}
	}
	
	@ResponseBody // 리턴할 응답 바디를 직접 입력할꺼야
	@RequestMapping("rinsert.bo")
	public String ajaxInsertReply(Reply r) {
		// 성공했을 때는 success, 실패했을 때는 fail
//		int result = boardService.insertReply(r);
		return boardService.insertReply(r) > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value="topList.bo", produces="application/json; charset=UTR-8")
	public String ajaxTopBoardList() {
//		ArrayList<Board> list = boardService.selectTopBoardList();
		return new Gson().toJson(boardService.selectTopBoardList());
	}
	
}
