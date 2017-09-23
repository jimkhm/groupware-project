package spring.mybatis.gw.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.board.dao.BoardDAO;
import spring.mybatis.gw.board.dto.BoardDTO;
import spring.mybatis.gw.share.dao.FileDetailDAO;
import spring.mybatis.gw.share.dao.FileMasterDAO;
import spring.mybatis.gw.share.dto.FileDetailDTO;
import spring.mybatis.gw.share.dto.FileMasterDTO;



@Controller
public class BoardController {

		@Autowired
		private BoardDAO boardDAO;
		
		@Autowired
		private FileMasterDAO fileMasterDAO;
		
		@Autowired
		private FileDetailDAO fileDetailDAO;
		
		@Autowired
		private FileDetailDTO fileDetail;
		
		@Autowired
		private FileMasterDTO fileMaster;
		
		// 글 목록 리스트
		@RequestMapping("/BoardList.bo")
		/*public ModelAndView boardList(HttpServletRequest request, HttpSession session,
				@RequestParam(value="company") String company, @RequestParam(value="deptno") String deptno){
			*/
		public ModelAndView boardList(@RequestParam(value="page", required=false, defaultValue="1") int page,
											HttpServletRequest request, HttpSession session){
			
			//컨트롤러 연동 확인
			//System.out.println("BoardController 연동 확인 : BoardList()");
			
			String	company = null;		// 회사 코드
			String	deptno = null;		// 부서
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				deptno = (String)session.getAttribute("deptno");
				System.out.println("session company : " + company);
				System.out.println("session deptno : " + deptno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/mainLogin/out_session");
			}
			
			
//			int page = 1;
			int limit = 10;//한 페이지 당 10개의 글만 두겠다. 10개가 넘어가면 페이지가 넘어간다.
			
			System.out.println("파라미터 comp_cd 전달 : "  + company);
			System.out.println("부서 번호 전달 : "  + deptno);
			
			int listcount=boardDAO.getListCount(company, deptno); //리스트 수를 받아옴
			System.out.println("getListCount 갯수 확인 : "+listcount);  // 성공
			
			List<BoardDTO> boardlist = boardDAO.getBoardList(page, limit, company, deptno);//리스트를 받아옴
			
			
			
			/*// num 파라메터로 가져오기		
			int no = 
			   		Integer.parseInt(request.getParameter("no"));
			// 조회수 증가
			boardDAO.setReadCountUpdate(company, no);*/
			
			
			
			//총 페이지 수    //maxpage,startpage,endpage : 페이지 하단에 [이전] [1]... [다음]을 나타내준다.
	 		int maxpage=(int)((double)listcount/limit+0.95); //0.95를 더해서 올림 처리   //(double) : 실수값을 반환하기 위해, 있으면 1.1, 없으면 1
	 		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
	 		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	 		//현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30 등...)
			int endpage = startpage+10-1;

	 		if (endpage> maxpage) endpage= maxpage;
			
	 		////////////////////////////////////////////////////////////////////////
	 		
	 		
	 		request.setAttribute("page", page); //현재 페이지 수
	 		request.setAttribute("maxpage", maxpage); //최대 페이지 수
	 		request.setAttribute("startpage", startpage); //현재 페이지에 표시할 첫 페이지 수
	 		request.setAttribute("endpage", endpage); //현재 페이지에 표시할 끝 페이지 수
			request.setAttribute("listcount",listcount); //글 수
			request.setAttribute("boardlist", boardlist);

			return new ModelAndView("board/qna_board_list");		
		}
		
		// 글 등록 write 페이지
		@RequestMapping("/BoardWrite.bo")
		public ModelAndView boardWrite(HttpServletRequest request, HttpSession session) {
			
			System.out.println("BoardController 연동 확인 : boardWrite()");
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				;
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/out_session");
			}
			
			return new ModelAndView("board/qna_board_write");
		}
		
		// 글 등록
		@RequestMapping("/BoardAddAction.bo")
		public String boardAdd (@RequestParam(value="page", required=false, defaultValue="1") int page,
				HttpServletRequest request, HttpSession session,
				MultipartHttpServletRequest mRequest){
			
			System.out.println("BoardController 연동 확인 : boardAdd()");
						
			//Session
			String company = null;
			String empno = null;
			String loginId = null;
			String M_YNgubun = null; // 관리자(Y)=M or 사용자(N)=U			
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				empno = (String)session.getAttribute("empno");
				loginId = (String)session.getAttribute("id");
				M_YNgubun = (String)session.getAttribute("gubun");
				System.out.println("session company : " + company);
				System.out.println("session empno : " + empno);
				System.out.println("session id : " + loginId);
				System.out.println("session gubun : " + M_YNgubun);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return "redirect:/mainLogin/out_session";
			}
			
			
			
			// File Upload
			//-----------기존 시작
			int fileMId = 0;
			int fileDId = 0;
//			MultipartRequest multi = null;
			String realFolder = "";
			String saveFolder = "/boardUpload";		
			int fileSize = 5 * 1024 * 1024;// 1024는 1kbyte *1024는 1Mbyte *5 5Mbyte
			Enumeration files = null;
			realFolder = request.getSession().getServletContext().getRealPath(saveFolder+"/");
			
			//-----------기존 끝
			
			boolean isSuccess = false;
			//ModelAndView mav = new ModelAndView();     //ModelAndView  : 컨트롤러가 처리한 결과 정보 및 뷰 선택에 필요한 정보를 담음 
			//String uploadPath="C:/workspaces/sts/FinalProjectMe/src/main/webapp/boardUpload/";   //uploadPath : 파일이 첨부되어져온 저장할 폴더(경로) 지정, 특정 경로에 지정하고 싶을 때 이 방법으로 한다.
												//절대 경로로 지정 \는 자바가 코드로(?) 인식해버린다. /로 해야한다.
			String uploadPath = realFolder;
																	
			System.out.println("uploadPath : "+uploadPath);
			
			File dir = new File(uploadPath);
			
			if(dir.isDirectory())  //폴더가 없을 때 만들어주게하는 디버깅 코드부분 //isDirectory 트루면 업로드라는 폴더가 있다. 펄스를 반환해주면 폴더가 없다
			{
				dir.mkdirs();  //펄스일 경우 폴더가 없다면 mkdirs로 폴더를 만든다.
			}
			///////////
			
			
			boolean iResult = false;
   			String boardTitle = null;
			String boardContent = null; 
			String gubun = null; // 전체(1) or 팀(2)
			int boardNo = 0;
			BoardDTO boardDto=new BoardDTO();
			
   			if(mRequest.getParameter("boardTitle") != null){
   				boardTitle = mRequest.getParameter("boardTitle");
   			}
   			if(mRequest.getParameter("boardContent") != null){
   				boardContent = mRequest.getParameter("boardContent");
   			}
   			if(mRequest.getParameter("gubun") != null) {
   				gubun = mRequest.getParameter("gubun");
   			}
			
			boardNo = boardDAO.getBoardNo(); // boardNo sequence 가져오기
			
			// Insert
			if(boardNo > 0) {
				
				if(M_YNgubun.equals("M")){
					empno = null;
				}
				boardDto.setCOMP_CD(company);
				boardDto.setNO(boardNo);
				boardDto.setEMP_NO(empno);
				boardDto.setSUBJECT(boardTitle);
				boardDto.setCONTENT(boardContent);
				boardDto.setM_YN(M_YNgubun);
				boardDto.setGUBUN(gubun);
				
				int rtn = boardDAO.gwBoardInsert(boardDto);
				
				if(rtn > 0){
					System.out.println("insertBoard 성공 : " + boardNo);
					iResult = true;
				}
			}

			// 파일 시작
			if(iResult){
			
				int fileCount = 0;
				String file_nm = null;
				String file_rnm = null;
				boolean fmResult = false;				
				Iterator<String> iter=mRequest.getFileNames();   //   Iterator : 컬렉션 프레임워크에서 리스트나 셋타입의 값을 꺼내오고자할 때 Iterator타입으로 ...
			
				while(iter.hasNext())  //hasNext : 데이터가 있는지 없는지 판단, 데이가 있으면 트루
				{
					String uploadFileName = iter.next();   //데이터가 있을 떄 next로 ...
					MultipartFile mFile = mRequest.getFile(uploadFileName);
					String originalFileName = mFile.getOriginalFilename();   //getOriginalFilename : 브라우저로 부터 전송되어지는 파일의 이름을 반환해준다.
					file_nm = originalFileName;
					String saveFileName = originalFileName;
					file_rnm = saveFileName;
										
					if(saveFileName != null && !saveFileName.equals(""))   //널이거나 문자열에 아무것도 없으면 아무것도 처리해서는 안되게 해야한다.
					{
						if(new File(uploadPath+saveFileName).exists())   //존재하는 없는지 확인, 같은 이름이 있다면 임의의 이름으로 변경해서 저장
						{
							//saveFileName = saveFileName+"_"+System.currentTimeMillis();  //있다면... '_'를 붙여준다.   그리고 currentTimeMillis(현재시간)도 붙여준다.
							
							String temp1 = saveFileName.substring(0, saveFileName.lastIndexOf(".")); //[table]
							String temp2 = saveFileName.substring(saveFileName.lastIndexOf("."),saveFileName.length());//[.txt]
//							System.out.println("temp1:["+temp1+"]");
//							System.out.println("temp2:["+temp2+"]");
							saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
							
								/*int sameFileCnt = sameFileCnt = fileDetailDAO.getSameFileCnt(originalFileName);
								saveFileName = temp1 + "[" + sameFileCnt + "]" + temp2;*/
							
							
							file_rnm = saveFileName;
						}
					
						try {
							mFile.transferTo(new File(uploadPath+saveFileName));   //transferTo : 읽어온 순간 해당 폴더에 저장해주는 기능
							isSuccess = true;  //성공하면! 
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							isSuccess = false;//실패하면! 
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							isSuccess = false;//실패하면! 
						}   
					
						if(isSuccess)  //isSuccess이 트루이면...
						{
							fileCount++;
						// 저장 db에.../////////////////
							
							// 파일 pk 가져오기
							if(fileCount==1){
								fileMId = fileMasterDAO.getFileMasterId();
		   	   					System.out.println("파일마스터id"+fileMId);
		   	   					boardDAO.updateBoardFileMId(company, boardNo, fileMId);

							}
							
		   	   				
		   	   				// 마스터
		   	   				if(fileCount==1){
		   	   								
		   	   					//FileMasterDTO fileMaster = new FileMasterDTO();
							
		   	   					fileMaster.setComp_cd(company);
		   	   					fileMaster.setFile_m_id(fileMId);
		   	   					fileMaster.setFile_gubun("0"); // 공지사항['0']
		   	   					fileMaster.setInsert_id(loginId);
		   	   				
		   	   					int ret = fileMasterDAO.FileMasterInsert(fileMaster);	
								
		   	   					if(ret > 0){
									System.out.println("fileMInsert 성공 : " + fileMId);
									fmResult = true;
								}
		   	   					
							}
		   	   				
							// 디테일*
							boolean fdResult = false;
					
							if(fmResult){ // 마스터가 성공일 때 실행
								
								fileDId = fileDetailDAO.getFileDetailId();
			   	   				System.out.println("파일디테일id["+fileCount+"]"+fileDId);
			   	   				
								// FileDetailDTO fileDetail = new FileDetailDTO();
								
								fileDetail.setComp_cd(company);
								fileDetail.setFile_m_id(fileMId);
								fileDetail.setFile_d_id(fileDId);			
								//=파일 관련 정보 (읽어오는 부분)======================			
								fileDetail.setFile_path(saveFolder); 
								fileDetail.setFile_nm(file_nm);
								fileDetail.setFile_rnm(file_rnm);		
								//======================================
								fileDetail.setInsert_id(loginId);			
								
								int ret = fileDetailDAO.FileDetailInsert(fileDetail);
					
								if(ret > 0){
									System.out.println("fileDInsert 성공 : " + fileDId);
									fdResult = true;
								}
							} // 디테일 끝
		   	   				
						
						}else
						{
							System.out.println("파일첨부 오류 : " + saveFileName);
						}
					
					
					}
					
					
				} // while 끝
				
			}// if(iResult){ 파일 끝
			
			return "redirect:/BoardList.bo";
		} // boardAdd() 끝
		
		
		// 글 상세보기
		@RequestMapping("/BoardDetailAction.bo")
		public ModelAndView boardDetail(@RequestParam(value="no") int no,
											HttpServletRequest request, HttpSession session) {
			
			System.out.println("BoardController 연동 확인 : boardDetail()");
			
			boolean isWriter = false;
			String	company = null;		// 회사 코드
			String userGubun = null;
			String empno = null;			
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				userGubun = (String)session.getAttribute("gubun");
				empno = (String)session.getAttribute("empno");
				System.out.println("session company : " + company);
				System.out.println("session gubun : " + userGubun);
				System.out.println("session empno : " + empno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/out_session");
			}
			
			
			BoardDTO boardDTO=new BoardDTO();
			boardDTO = boardDAO.getDetail(company, no);
			
			if(userGubun == "M"  && boardDTO.getM_YN().equals("M")){
				isWriter = true; // 로그인한 시스템 관리자 가 작성한 관리자 자신의 글
			} else if(userGubun != "M" && !boardDTO.getM_YN().equals("M") && boardDTO.getEMP_NO().equals(empno)) {
				isWriter = true; // 로그인한 일반 유저가 작성한 자신의 글(empno로 비교)
			} else  {
				isWriter = false;
				int rtn = boardDAO.setReadCountUpdate(company, no);
				if(rtn > 0){
					System.out.println("조회수 업데이트 성공");
				}
			}
			
			List<FileDetailDTO> fileList = fileDetailDAO.getFileDetailList(company, boardDTO.getFILE_M_ID());
			
			request.setAttribute("boarddata", boardDTO);
			request.setAttribute("fileList", fileList);
			request.setAttribute("isWriter", isWriter);
			
			return new ModelAndView("board/qna_board_view");
		}
		
		// 파일 다운 (이클립스와는 다르게 따로 추가해줘야함)
		@RequestMapping("/BoardFileDown.bo")
		public ModelAndView boardFileDown(@RequestParam(value="path") String path,
															@RequestParam(value="originalFileName") String originalFileName,
															HttpServletRequest request, HttpSession session) {
			
			//System.out.println("BoardController 연동 확인 : boardFileDown()");
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				;
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/out_session");
			}
			
			request.setAttribute("path", path);
			request.setAttribute("originalFileName", originalFileName);
			
			
			return new ModelAndView("board/qna_board_download");
		}
		
		// 수정화면 (조회)
		@RequestMapping("/BoardModify.bo")
		public ModelAndView boardModify(@RequestParam(value="no") int no,
				HttpServletRequest request, HttpSession session)
		{
			System.out.println("BoardController 연동 확인 : BoardModify()");
			
			boolean isWriter = false;
			String	company = null;		// 회사 코드
			String userGubun = null;
			String empno = null;			
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				userGubun = (String)session.getAttribute("gubun");
				empno = (String)session.getAttribute("empno");
				System.out.println("session company : " + company);
				System.out.println("session gubun : " + userGubun);
				System.out.println("session empno : " + empno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/out_session");
			}
			
			
			BoardDTO boardDTO=new BoardDTO();
			boardDTO = boardDAO.getDetail(company, no);
			
			if(userGubun == "M"  && boardDTO.getM_YN().equals("M")){
				isWriter = true; // 로그인한 시스템 관리자 가 작성한 관리자 자신의 글
			} else if(userGubun != "M" && !boardDTO.getM_YN().equals("M") && boardDTO.getEMP_NO().equals(empno)) {
				isWriter = true; // 로그인한 일반 유저가 작성한 자신의 글(empno로 비교)
			} else {
				isWriter = false;
				int rtn = boardDAO.setReadCountUpdate(company, no);
				if(rtn > 0){
					System.out.println("조회수 업데이트 성공");
				}
			}
			
			List<FileDetailDTO> fileList = fileDetailDAO.getFileDetailList(company, boardDTO.getFILE_M_ID());
			
			request.setAttribute("boarddata", boardDTO);
			request.setAttribute("fileList", fileList);
			request.setAttribute("isWriter", isWriter);
			
			
			return new ModelAndView("board/qna_board_modify");
		}
		
		
		
		
		// 수정화면 (저장)
		@RequestMapping("/BoardModifyAction.bo")
		public String boardModify(@RequestParam(value="no") int no,
				HttpServletRequest request, HttpSession session, MultipartHttpServletRequest mRequest ){ 		
			
			
			System.out.println("BoardController 연동 확인 : BoardModifyAction()");
			
			String	company = null;		// 회사 코드
			String loginId = null;
			String userGubun = null;
			String empno = null;
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				userGubun = (String)session.getAttribute("gubun");
				empno = (String)session.getAttribute("empno");
				System.out.println("session company : " + company);
				System.out.println("session gubun : " + userGubun);
				System.out.println("session empno : " + empno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return "redirect:/mainLogin/out_session";
			}
			
			
			BoardDTO  boardDTO = boardDAO.getDetail(company, no);
			int file_m_id = boardDTO.getFILE_M_ID();
			
			String boardTitle = null;
			String boardContent = null;
			String gubun = null;
			
   			if(mRequest.getParameter("boardTitle") != null){
   				boardTitle = mRequest.getParameter("boardTitle");
   			}
   			if(mRequest.getParameter("boardContent") != null){
   				boardContent = mRequest.getParameter("boardContent");
   			}
   			
   			
   			////////////////////////////////////////////////////////////////////
   			// boardDTO 만들어서 보드 테이블 저장   			
   			int result = 0;
   			boolean iResult = false;
   			
   			boardDTO.setSUBJECT(request.getParameter("SUBJECT"));
   			boardDTO.setCONTENT(request.getParameter("CONTENT"));
   			
   			result = boardDAO.boardModify(boardDTO);
   			
			
			if(result > 0){
				System.out.println("boardModify 성공 : ");
				iResult = true;
			}
   			////////////////////////////////////////////////////////////////////
   			
   			// 파일 인서트
   		// File Upload
   					//-----------기존 시작
   					int fileDId = 0;
//   					MultipartRequest multi = null;
   					String realFolder = "";
   					String saveFolder = "/boardUpload";		
   					int fileSize = 5 * 1024 * 1024;// 1024는 1kbyte *1024는 1Mbyte *5 5Mbyte
   					Enumeration files = null;
   					realFolder = request.getSession().getServletContext().getRealPath(saveFolder+"/");
   					
   					//-----------기존 끝
   					
   					boolean isSuccess = false;
   					//ModelAndView mav = new ModelAndView();     //ModelAndView  : 컨트롤러가 처리한 결과 정보 및 뷰 선택에 필요한 정보를 담음 
   					//String uploadPath="C:/workspaces/sts/FinalProjectMe/src/main/webapp/boardUpload/";   //uploadPath : 파일이 첨부되어져온 저장할 폴더(경로) 지정, 특정 경로에 지정하고 싶을 때 이 방법으로 한다.
   														//절대 경로로 지정 \는 자바가 코드로(?) 인식해버린다. /로 해야한다.
   					String uploadPath = realFolder;
   																			
   					System.out.println("uploadPath : "+uploadPath);
   					
   					File dir = new File(uploadPath);
   					
   					if(dir.isDirectory())  //폴더가 없을 때 만들어주게하는 디버깅 코드부분 //isDirectory 트루면 업로드라는 폴더가 있다. 펄스를 반환해주면 폴더가 없다
   					{
   						dir.mkdirs();  //펄스일 경우 폴더가 없다면 mkdirs로 폴더를 만든다.
   					}
   					///////////
   					
   					//System.out.println("dir 생성 확인"+dir);
   					
   					
   					
   					// 파일 시작
   					if(iResult){
   					
   						String file_nm = null;
   						String file_rnm = null;
   						boolean fmResult = false;				
   						Iterator<String> iter=mRequest.getFileNames();   //   Iterator : 컬렉션 프레임워크에서 리스트나 셋타입의 값을 꺼내오고자할 때 Iterator타입으로 ...
   						int fileCount = 0;
   						
   						while(iter.hasNext())  //hasNext : 데이터가 있는지 없는지 판단, 데이가 있으면 트루
   						{
   							String uploadFileName = iter.next();   //데이터가 있을 떄 next로 ...
   							MultipartFile mFile = mRequest.getFile(uploadFileName);
   							String originalFileName = mFile.getOriginalFilename();   //getOriginalFilename : 브라우저로 부터 전송되어지는 파일의 이름을 반환해준다.
   							file_nm = originalFileName;
   							String saveFileName = originalFileName;
   							file_rnm = saveFileName;
   												
   							if(saveFileName != null && !saveFileName.equals(""))   //널이거나 문자열에 아무것도 없으면 아무것도 처리해서는 안되게 해야한다.
   							{
   								
   								if(new File(uploadPath+saveFileName).exists())   //존재하는 없는지 확인, 같은 이름이 있다면 임의의 이름으로 변경해서 저장
   								{
   									//saveFileName = saveFileName+"_"+System.currentTimeMillis();  //있다면... '_'를 붙여준다.   그리고 currentTimeMillis(현재시간)도 붙여준다.
   									
   									String temp1 = saveFileName.substring(0, saveFileName.lastIndexOf(".")); //[table]
   									String temp2 = saveFileName.substring(saveFileName.lastIndexOf("."),saveFileName.length());//[.txt]
//   									System.out.println("temp1:["+temp1+"]");
//   									System.out.println("temp2:["+temp2+"]");
  									saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
   									// 파일 저장 시, 같은 이름일 때 번호로 저장, 윗 부분 막고 하면됨
   									
   									System.out.println("originalFileName: "+originalFileName);
   									//int sameFileCnt  = fileDetailDAO.getSameFileCnt(originalFileName);
   									//saveFileName = temp1 + "[" + sameFileCnt + "]" + temp2;
   									
   									file_rnm = saveFileName;
   								}
   								
   								System.out.println("file_rnm 확인 :" + file_rnm);
   							
   								try {
   									mFile.transferTo(new File(uploadPath+saveFileName));   //transferTo : 읽어온 순간 해당 폴더에 저장해주는 기능
   									isSuccess = true;  //성공하면! 
   									
   									//System.out.println("mFile.transferTo :" + uploadPath+saveFileName);
   									
   								} catch (IllegalStateException e) {
   									// TODO Auto-generated catch block
   									e.printStackTrace();
   									isSuccess = false;//실패하면! 
   								} catch (IOException e) {
   									// TODO Auto-generated catch block
   									e.printStackTrace();
   									isSuccess = false;//실패하면! 
   								}   
   							
   								if(isSuccess)  //isSuccess이 트루이면...
   								{
   									
   								// 저장 db에.../////////////////
   									   									
   									// 파일 pk 가져오기
   									if( file_m_id==0){
   										
   										file_m_id = fileMasterDAO.getFileMasterId();
   				   	   					System.out.println("파일 pk 가져오기 -> 파일마스터id"+file_m_id);
   				   	   					boardDAO.updateBoardFileMId(company, no, file_m_id);
				
   				   	   					//FileMasterDTO fileMaster = new FileMasterDTO();  				   	   					
   				   	   					
   				   	   					
   				   	   					fileMaster.setComp_cd(company);
   				   	   					fileMaster.setFile_m_id(file_m_id);
   				   	   					fileMaster.setFile_gubun("0"); // 공지사항['0']
   				   	   					fileMaster.setInsert_id(loginId);
   				   	   				
   				   	   					int ret = fileMasterDAO.FileMasterInsert(fileMaster);	
   										
   				   	   					if(ret > 0){
   											System.out.println("파일 pk 가져오기 -> fileMInsert 성공 : " + file_m_id);
   											fmResult = true;
   										}
   				   	   					
   									}
   				   	   				
   									// 디테일*
   									boolean fdResult = false;
   							
   										fileDId = fileDetailDAO.getFileDetailId();
   					   	   				System.out.println("파일디테일id["+ (++fileCount) +"]"+fileDId);
   					   	   				
   										// FileDetailDTO fileDetail = new FileDetailDTO();
   										
   										fileDetail.setComp_cd(company);
   										fileDetail.setFile_m_id(file_m_id);
   										fileDetail.setFile_d_id(fileDId);			
   										//=파일 관련 정보 (읽어오는 부분)======================			
   										fileDetail.setFile_path(saveFolder); 
   										fileDetail.setFile_nm(file_nm);
   										fileDetail.setFile_rnm(file_rnm);		
   										//======================================
   										fileDetail.setInsert_id(loginId);			
   										
   										int ret = fileDetailDAO.FileDetailInsert(fileDetail);
   							
   										if(ret > 0){
   											System.out.println("fileDInsert 성공 : " + fileDId);
   											fdResult = true;
   										}
   										
   										
   										
   									} // 디테일 끝
   				   	   				
   								
   								}else
   								{
   									System.out.println("파일첨부 오류 : " + saveFileName);
   								}
   							
   							
   							}
   							
   							
   						} // while 끝
   						
   	//////////////////////////////////////////////////////////////////////////////////////////////		
   			// 파일 삭제   	
   			int delFileCnt=0;   					
   			int[] deFile = null;
   	   		String[] delFileYN =null;
   	   		FileDetailDTO fileDetailDTO = null;
   	   		String fileRnm = null;
   	   		
	   	   	//boolean result=false;	   
		   	
		 	String empNo = null;
		 	List<FileDetailDTO> pathList = null;
		 	int dResult = 0;
		 	int mResult = 0;
		 	int sResult = 0;
		 	int aResult = 0;   		
	   		boolean exist = false;
	   		int fileMid =0 ;
   	   				
   					if(mRequest.getParameter("delFileCnt") !=  null){
   						delFileCnt = Integer.parseInt( mRequest.getParameter("delFileCnt") );
	   	   			}
   					
   					System.out.println( mRequest.getParameter("FILE_M_ID"));
   					
   					if(mRequest.getParameter("FILE_M_ID") !=  null){
	   	   				fileMid = Integer.parseInt( mRequest.getParameter("FILE_M_ID") );
	   	   			}
   					
   					System.out.println("delFileCnt: "+delFileCnt);
   					System.out.println("fileMid: "+fileMid);
   					
   					
   					
   			   if(delFileCnt > 0){   	 				
   				
   				  for(int i = 0; i < delFileCnt; i++){ 
				   				// 돌면서 getParameter 해와서
   					  			System.out.println("for문 안 진입");
   					  			System.out.println("delfile: "+mRequest.getParameter("delFile"+i));
			   					
			   					deFile = new int[delFileCnt];
			   					delFileYN = new String[delFileCnt];
   					  
				   				
				   				if(mRequest.getParameter("delFile"+i) != null){
				   	   				deFile[i] =  Integer.parseInt(mRequest.getParameter("delFile"+i));
				   	   			}
				   				if(mRequest.getParameter("delFileYN"+i) != null){
				   					delFileYN[i] = mRequest.getParameter("delFileYN"+i);
				   	   			}
				   				System.out.println("delFileYN[i]: "+ delFileYN[i]);
				   				System.out.println("deFile[i]: "+deFile[i]);
				   				
				   				if(delFileYN[i].equals("Y")){
				   					System.out.println("if문 진입");
				   					
				   					System.out.println("company"+company);
				   					System.out.println("fileMid"+fileMid);
				   					System.out.println("deFile[i]"+ deFile[i]);
				   					
				   					fileDetailDTO = fileDetailDAO.getFileDetail(company, fileMid, deFile[i]);  
				   					
				   					fileRnm = fileDetailDTO.getFile_rnm();
				   									   												   		
							   					   							   
							   		
							   		realFolder = "";
					                saveFolder = "/boardUpload/";

					                realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
					                
							   		System.out.println(realFolder+"\\"+fileRnm);					                
							   		
							   		Path path = Paths.get(realFolder +"\\"+ fileRnm);			
							   		exist = Files.exists(path);	
																   		
									if(exist){ // 실제 파일 삭제
										System.out.println("실제 파일 존재");
										try {
											Files.delete(path);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}else{
										System.out.println("실제 파일 없음");
									}

									
									
							   	}
							   	
							  	if(exist){
							  		System.out.println("@@@파일 디테일 테이블 삭제 if문 진입");
							  		
							  		System.out.println("company"+company);
				   					System.out.println("fileMid"+fileMid);
				   					System.out.println("deFile[i]"+ deFile[i]);
							  		
							  		
							  		dResult = fileDetailDAO.FileDetailDelete(company, fileMid, deFile[i]);
							  		
							  		System.out.println("dResult : "+dResult);
							  		
							  		
							   		if(dResult > 0){
							   			System.out.println("detail 삭제 성공");
							   		}else{
							   			System.out.println("detail 삭제 실패");
							   		}
							   	
							   	}
							   	
			
				   					
				   				}
		
				   				
   				  			}
   			
		   		
   			   
   			  ////////////////////////////////////////////////////////////////////////////// 
   			   
   			   
   			// 파일 m id 정리 [시작]   					
   			int detailCount = fileDetailDAO.getCountFileDetail(company, file_m_id);
   			if(detailCount==0){
   				fileMasterDAO.FileMasterDelete(company, file_m_id); // 파일 마스터 테이블 삭제
   				boardDAO.setBoardFileMIDNull(company, no);  // 보드 file_m_id 를 null
   			} else {
				boardDAO.updateBoardFileMId(company, no, file_m_id); // 보드 file_m_id update
   			}
   			// 파일 m id 정리 [끝]
			
			return "redirect:BoardDetailAction.bo?no=" + no;
			
		}
		
		
		
		
		// 글 삭제
		@RequestMapping("/BoardDeleteAction.bo")
		public String boardDelete (@RequestParam(value="num") int num,
				HttpServletRequest request, HttpSession session
				){    //MultipartHttpServletRequest mRequest
			
			System.out.println("boardDelete 진입");
			
			String	company = null;		// 회사 코드
			String userGubun = null;
			String empno = null;
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
			if (session != null){
				company = (String)session.getAttribute("company");
				userGubun = (String)session.getAttribute("gubun");
				empno = (String)session.getAttribute("empno");
				System.out.println("session company : " + company);
				System.out.println("session gubun : " + userGubun);
				System.out.println("session empno : " + empno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return "redirect:/mainLogin/out_session";
			}
			
						
			// 파일 삭제   	
   			int delFileCnt=0;   					
   			int[] deFile = null;
   	   		String[] delFileYN =null;
   	   		FileDetailDTO fileDetailDTO = null;
   	   		String fileRnm = null;
   	   		
	   	   	int fileDId = 0;
	//		MultipartRequest multi = null;
			String realFolder = "";
			String saveFolder = "/boardUpload";		
			int fileSize = 5 * 1024 * 1024;// 1024는 1kbyte *1024는 1Mbyte *5 5Mbyte
			Enumeration files = null;
			realFolder = request.getSession().getServletContext().getRealPath(saveFolder+"/");
   	   		
	   	   	//boolean result=false;	   
		   	
		 	String empNo = null;
		 	List<FileDetailDTO> pathList = null;
		 	int dResult = 0;
		 	int mResult = 0;
		 	int sResult = 0;
		 	int aResult = 0;   		
	   		boolean exist = false;
	   		int fileMid =0 ;
   	   				
   					/*if(mRequest.getParameter("delFileCnt") !=  null){
   						delFileCnt = Integer.parseInt( mRequest.getParameter("delFileCnt") );
	   	   			}*/   					
   					
   					if(request.getParameter("FILE_M_ID") !=  null){
	   	   				fileMid = Integer.parseInt( request.getParameter("FILE_M_ID") );
	   	   			}
   					
   					//System.out.println( request.getParameter("FILE_M_ID"));
   					
   					//System.out.println("delFileCnt: "+delFileCnt);
   					System.out.println("fileMid: "+fileMid);
   										   						
   					
   					pathList = (List<FileDetailDTO>)fileDetailDAO.getFileDetailList(company, fileMid);
							   					   	
   					for(int i=0; i<pathList.size(); i++ ){
							   		
							   		realFolder = "";
					                saveFolder = "/boardUpload/";					               
					        		fileRnm = pathList.get(i).getFile_rnm();					        		
					                realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
					                
					                
							   		System.out.println(realFolder+"\\"+fileRnm);					                
							   		
							   		Path path = Paths.get(realFolder +"\\"+ fileRnm);			
							   		exist = Files.exists(path);	
																   		
									if(exist){ // 실제 파일 삭제
										System.out.println("실제 파일 존재");
										try {
											Files.delete(path);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}else{
										System.out.println("실제 파일 없음");
									}
														
								}
							   	
							  	/*if(exist){
							  		System.out.println("@@@파일 디테일 테이블 삭제 if문 진입");
							  		
							  		System.out.println("company"+company);
				   					System.out.println("fileMid"+fileMid);
				   											  		
							  		System.out.println("dResult : "+dResult);
							  		
							  		
							   		if(dResult > 0){
							   			System.out.println("detail 삭제 성공");
							   		}else{
							   			System.out.println("detail 삭제 실패");
							   		}
							   	
							   	}*/
							   	
			
				   					
				   			//마스터 테이블에서 삭제						 
							   	fileMasterDAO.FileMasterDelete(company, fileMid);
//							
							// 디테일 테이블에서 삭제   	
							  fileDetailDAO.FileDetailDelete(company, fileMid);
							   	
							  	
							
							// 게시글(gw_board) 삭제 메서드 불러오기
							   	boardDAO.boardDelete(company, num) ;   //    게시판(GW_BOARD)에서 파일 부분 지우기
				   				
   				  			
			
			
			
			
			return "redirect:BoardList.bo?no=" + num;
			
		}
		
}
		
