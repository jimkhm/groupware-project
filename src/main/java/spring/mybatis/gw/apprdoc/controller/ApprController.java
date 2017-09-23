package spring.mybatis.gw.apprdoc.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.apprdoc.dao.ApprAddDAO;
import spring.mybatis.gw.apprdoc.dao.ApprDeleteDAO;
import spring.mybatis.gw.apprdoc.dao.ApprDetailDAO;
import spring.mybatis.gw.apprdoc.dao.ApprListDAO;
import spring.mybatis.gw.apprdoc.dao.ApprStatusDAO;
import spring.mybatis.gw.apprdoc.dao.ApprUpdateDAO;
import spring.mybatis.gw.apprdoc.dto.ApprDetailDTO1;
import spring.mybatis.gw.apprdoc.dto.ApprDetailDTO2;
import spring.mybatis.gw.apprdoc.dto.ApprDocDTO;
import spring.mybatis.gw.apprdoc.dto.ApprHistoryDTO;
import spring.mybatis.gw.apprdoc.dto.ApprListDTO;
import spring.mybatis.gw.apprdoc.dto.ApprStepDTO;
import spring.mybatis.gw.apprdoc.dto.HistoryPopDTO;
import spring.mybatis.gw.board.dto.BoardDTO;
import spring.mybatis.gw.share.dao.FileDetailDAO;
import spring.mybatis.gw.share.dao.FileMasterDAO;

import spring.mybatis.gw.share.dto.FileDetailDTO;
import spring.mybatis.gw.share.dto.FileMasterDTO;


@Controller
public class ApprController {
	
	@Autowired
	private ApprListDAO apprListDAO;	
	@Autowired
	private ApprStatusDAO asDao;		
	@Autowired
	private ApprDetailDAO adDao;	
	@Autowired
	private ApprDeleteDAO apprDel;
	@Autowired
	private ApprAddDAO apprAddDao;	
	@Autowired
	private FileMasterDAO fileMasterDAO;	
	@Autowired
	private FileDetailDAO fileDetailDAO;
	@Autowired
	private ApprUpdateDAO auDao;
		
	//@Autowired
	//private SqlSessionTemplate sqlSession;
	
	//결재 서류 등록 페이지로 가기
	@RequestMapping("/ApprWriter.appr")
	public ModelAndView getWriteForm(){
		System.out.println("/ApprWriter.appr 도입");		
		return new ModelAndView("apprDoc/qna_apprDoc_write");
	}
	
	
	@RequestMapping("/apprFileDown.appr")
	public ModelAndView apprFileDown(@RequestParam(value="path") String path,
														HttpServletRequest request, HttpSession session) {
		
		System.out.println("apprFileDown 연동 확인 : boardFileDown()");
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			;
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
		
		request.setAttribute("path", path);
		
		return new ModelAndView("apprDoc/apprFileDown");
	}
	
	// 수정화면 (저장)
	@RequestMapping("/ApprModifyAction.appr")
	public String apprModify(
					HttpServletRequest request, HttpSession session, MultipartHttpServletRequest mRequest ){ 		
				
				
				System.out.println("ApprController 연동 확인 : apprModify()");
								
				boolean result=false;			   	
			   	int apprNo = 0;
			   	int fileMId = 0;
			 	String empNo = null;
			 	List<FileDetailDTO> pathList = null;
			 	int dResult = 0;
			 	int mResult = 0;
			 	int sResult = 0;
			 	int aResult = 0;   		
			 	int iResult = 0;
		   		boolean exist = false;
		   		
		   		String	company = null;		// 회사 코드
				String userGubun = null;
				String loginId = null;			
								
				ApprDocDTO apprDocData = new ApprDocDTO();		

				
				List<ApprStepDTO> apprStepList = new ArrayList<ApprStepDTO>();		
				
				// Session				
				String empno = null;		
				String	deptno = null;		// 부서					
				String apprTitle = null;
				String apprContent = null;
				String apprDate = null;
				String finalDate = null;
								
				session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
				if (session != null){
					company = (String)session.getAttribute("company");
					//userGubun = (String)session.getAttribute("gubun");
					empno = (String)session.getAttribute("empno");
					loginId = (String)session.getAttribute("id");
					System.out.println("session company : " + company);
					//System.out.println("session gubun : " + userGubun);
					System.out.println("session empno : " + loginId);
				} else { // 세션이 없으면 로그인 페이지로 이동
					System.out.println("session null!");
					return "redirect:/mainLogin/out_session";
				}
				
				//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
				
				
				
				if (mRequest.getParameter("apprTitle") != null) {
					apprTitle = mRequest.getParameter("apprTitle");
					System.out.println(apprTitle);
				}
				if (mRequest.getParameter("apprContent") != null) {
					apprContent = mRequest.getParameter("apprContent");
				}
				if (mRequest.getParameter("apprDate") != null) {
					apprDate = mRequest.getParameter("apprDate");
				}
				if (mRequest.getParameter("finalDate") != null) {
					finalDate = mRequest.getParameter("finalDate");
				}
				if (mRequest.getParameter("apprNo") != null) {
					apprNo = Integer.parseInt(mRequest.getParameter("apprNo"));
				}
				
				
				ApprDetailDTO1 apprDoc = adDao.getApprDoc(apprNo);
				
				int file_m_id = apprDoc.getFile_m_id();
		
				System.out.println("empNo 파라미터 확인: "+mRequest.getParameter("empNo"));
				System.out.println("apprTitle: "+apprTitle);
				System.out.println("apprContent: "+apprContent);
				System.out.println("apprDate: "+apprDate);
				System.out.println("finalDate: "+finalDate);
				System.out.println("apprNo: "+apprNo);
				
				System.out.println("file_m_id 확인"+apprDoc.getFile_m_id());
	   			
	   			////////////////////////////////////////////////////////////////////
	   			// boardDTO 만들어서 보드 테이블 저장   			
	   			
								
				if (apprNo > 0) {
					System.out.println("apprdoc");

					apprDocData.setComp_cd(company);
					apprDocData.setAppr_no(apprNo);
					apprDocData.setEmp_no(empno);
					//apprDocData.setM_YN(m_yn);
					apprDocData.setAppr_title(apprTitle);
					apprDocData.setAppr_content(apprContent);
					apprDocData.setAppr_date_appr(apprDate);
					apprDocData.setAppr_date_final(finalDate);
					apprDocData.setUpdate_id(loginId);
					// apprDocData.setFile_m_id(fileMId);

					iResult = auDao.apprDocUpdate(apprDocData);

					if (iResult > 0) {
						System.out.println("apprDocUpdate 성공 : " + apprNo);
					}

				}
	   			 
					
					
					//System.out.println("appr_step update if문 진입");
					
				sResult = apprDel.deleteApprStep(apprNo);	
				if(sResult > 0){
					System.out.println("appr_step 삭제 성공");
				}else{
					System.out.println("appr_step 삭제 실패");
				}
					
					
					
					//결재자
					String[] apprEmpNo = null;
					int apprEmpCnt = 0;
					apprEmpCnt = Integer.parseInt(mRequest.getParameter("apprEmpCnt"));
					System.out.println("apprEmpCnt: "+apprEmpCnt);
					apprEmpNo = new String[apprEmpCnt];

					if (apprEmpCnt != 0) {
						for (int i = 0; i < apprEmpCnt; i++) {
							apprEmpNo[i] = mRequest.getParameter("apprEmpNo" + i);
							System.out.println("apprEmpCnt 배열 카운트 test: "+i);				
						}
					}

					
					// 합의자
					String[] agreeEmpNo = null;
					int agreeEmpCnt = 0;
					agreeEmpCnt = Integer.parseInt(mRequest.getParameter("agreeEmpCnt"));
					System.out.println("agreeEmpCnt: "+agreeEmpCnt);
					agreeEmpNo = new String[agreeEmpCnt];

					if (agreeEmpCnt != 0) {
						for (int i = 0; i < agreeEmpCnt; i++) {
							agreeEmpNo[i] = mRequest.getParameter("agreeEmpNo" + i);
						}
					}

					// 시행자
					String[] enforceEmpNo = null;
					int enforceEmpCnt = 0;
					enforceEmpCnt = Integer.parseInt(mRequest.getParameter("enforceEmpCnt"));
					System.out.println("enforceEmpCnt: "+enforceEmpCnt);
					enforceEmpNo = new String[enforceEmpCnt];

					if (enforceEmpCnt != 0) {
						for (int i = 0; i < enforceEmpCnt; i++) {
							enforceEmpNo[i] = mRequest.getParameter("enforceEmpNo" + i);
						}
					}

					// 수신참조자
					String[] referEmpNo = null;
					int referEmpCnt = 0;
					referEmpCnt = Integer.parseInt(mRequest.getParameter("referEmpCnt"));
					System.out.println("referEmpCnt: "+referEmpCnt);
					referEmpNo = new String[referEmpCnt];

					if (referEmpCnt != 0) {
						for (int i = 0; i < referEmpCnt; i++) {
							referEmpNo[i] = mRequest.getParameter("referEmpNo" + i);
						}
					}
						
					
					
						{// 결재자
							for (int i = 0; i < apprEmpCnt; i++) {
								ApprStepDTO apprStep = new ApprStepDTO();

								apprStep.setComp_cd(company);
								apprStep.setAppr_no(apprNo);
								apprStep.setAppr_line("A"); // gubun
								apprStep.setEmp_no(apprEmpNo[i]);
								apprStep.setAppr_priority(i + 1);
								apprStep.setInsert_id(loginId);
								
								System.out.println("apprEmpNo["+i+"]"+apprEmpNo[i]);
								
								apprStepList.add(apprStep);
												
							}
						} // 결재자

						{// 합의자
							for (int i = 0; i < agreeEmpCnt; i++) {
								ApprStepDTO apprStep = new ApprStepDTO();

								apprStep.setComp_cd(company);
								apprStep.setAppr_no(apprNo);
								apprStep.setAppr_line("B");
								apprStep.setEmp_no(agreeEmpNo[i]);
								apprStep.setAppr_priority(i + 1);
								apprStep.setInsert_id(loginId);
								
								System.out.println("agreeEmpNo["+i+"]"+agreeEmpNo[i]);

								apprStepList.add(apprStep);
							}
						} // 합의자

						{// 실행자
							for (int i = 0; i < enforceEmpCnt; i++) {
								ApprStepDTO apprStep = new ApprStepDTO();

								apprStep.setComp_cd(company);
								apprStep.setAppr_no(apprNo);
								apprStep.setAppr_line("C");
								apprStep.setEmp_no(enforceEmpNo[i]);
								apprStep.setAppr_priority(i + 1);
								apprStep.setInsert_id(loginId);
								
								System.out.println("enforceEmpNo["+i+"]"+enforceEmpNo[i]);

								apprStepList.add(apprStep);
							}
						} // 실행자

						{// 수신참조자
							for (int i = 0; i < referEmpCnt; i++) {
								ApprStepDTO apprStep = new ApprStepDTO();

								apprStep.setComp_cd(company);
								apprStep.setAppr_no(apprNo);
								apprStep.setAppr_line("D");
								apprStep.setEmp_no(referEmpNo[i]);
								apprStep.setAppr_priority(i + 1);
								apprStep.setInsert_id(loginId);
								
								System.out.println("referEmpNo["+i+"]"+referEmpNo[i]);

								apprStepList.add(apprStep);
							}
						} // 수신참조자
						
						System.out.println("apprStepInsert 실행 직전");

						sResult = apprAddDao.apprStepInsert(apprStepList);

						if (sResult > 0) {
							System.out.println("apprStepInsert 성공 : " + apprNo);
						}
					
				
				
	   			////////////////////////////////////////////////////////////////////
	   			
	   			// 파일 인서트
	   		// File Upload
	   					//-----------기존 시작
	   					int fileDId = 0;
//	   					MultipartRequest multi = null;
	   					String realFolder = "";
	   					String saveFolder = "/apprUpload";		
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
	   					//if(iResult){
	   					
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
//	   									System.out.println("temp1:["+temp1+"]");
//	   									System.out.println("temp2:["+temp2+"]");
//	   									saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
	   									// 파일 저장 시, 같은 이름일 때 번호로 저장, 윗 부분 막고 하면됨
	   									//int sameFileCnt = sameFileCnt = fileDetailDAO.getSameFileCnt(originalFileName);
	   									//saveFileName = temp1 + "[" + sameFileCnt + "]" + temp2;
	   									saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
	   										   									
	   									file_rnm = saveFileName;
	   									
	   									System.out.println("file_rnm 체크"+file_rnm);
	   									
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
	   				   	   					apprAddDao.updateApprDocFileMId(company, apprNo, file_m_id);
					
	   				   	   					FileMasterDTO fileMaster = new FileMasterDTO();  				   	   					
	   				   	   					
	   				   	   					
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
	   					   	   				
	   										FileDetailDTO fileDetail = new FileDetailDTO();
	   										
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
	   							
	   							
	   						//} // while 끝
	   								
	   	//////////////////////////////////////////////////////////////////////////////////////////////		
	   			// 파일 삭제   	
	   			int delFileCnt=0;   					
	   			int[] deFile = null;
	   	   		String[] delFileYN =null;
	   	   		FileDetailDTO fileDetailDTO = null;
	   	   		String fileRnm = null;
	   	   		
		   	   	//boolean result=false;	   
			   	
			 	//String empNo = null;
			 	//List<FileDetailDTO> pathList = null;
			 	//int dResult = 0;
			 	//int mResult = 0;
			 	//int sResult = 0;
			 	//int aResult = 0;   		
		   		//boolean exist = false;
		   		int fileMid2 =0 ;
	   	   				
	   					if(mRequest.getParameter("delFileCnt") !=  null){
	   						delFileCnt = Integer.parseInt( mRequest.getParameter("delFileCnt") );
		   	   			}
	   					
	   					System.out.println( mRequest.getParameter("FILE_M_ID"));
	   					
	   					if(mRequest.getParameter("FILE_M_ID") !=  null){
	   						fileMid2 = Integer.parseInt( mRequest.getParameter("FILE_M_ID") );
		   	   			}
	   					
	   					System.out.println("delFileCnt: "+delFileCnt);
	   					System.out.println("fileMid: "+fileMid2);
	   					
	   					
	   					
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
					   					System.out.println("fileMid"+fileMid2);
					   					System.out.println("deFile[i]"+ deFile[i]);
					   					
					   					fileDetailDTO = fileDetailDAO.getFileDetail(company, fileMid2, deFile[i]);  
					   					
					   					fileRnm = fileDetailDTO.getFile_rnm();
					   									   												   		
								   					   							   
								   		
								   		realFolder = "";
						                saveFolder = "/apprUpload/";

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
								   	
								  	if(delFileYN[i].equals("Y")){
								  		System.out.println("@@@파일 디테일 테이블 삭제 if문 진입");
								  		
								  		System.out.println("company"+company);
					   					System.out.println("fileMid"+fileMid2);
					   					System.out.println("deFile[i]"+ deFile[i]);
								  		
								  		
								  		dResult = fileDetailDAO.FileDetailDelete(company, fileMid2, deFile[i]);
								  		
								  		System.out.println("dResult : "+dResult);
								  		
								  		
								   		if(dResult > 0){
								   			System.out.println("detail 삭제 성공");
								   		}else{
								   			System.out.println("detail 삭제 실패");
								   		}
								   	
								   	}
								   	
								  	 
								  				  	
//								   	if(dResult !=0 ){
//								   		mResult = fileMasterDAO.FileMasterDelete(company, fileMId);	
//									   	if(mResult != 0){
//								   			System.out.println("master 삭제 성공");
//								   		}else{
//								   			System.out.println("master 삭제 실패");
//								   		}
//								   	
//								   	}
					   					
					   					
					   				}
					   				
					   				
					   			//	System.out.printf("[%d] %s) %s \n", i, deFile[i], delFileYN[i]);
					   				
					   			// 실제 db서 그 값으로 detail 테이블에서 조회 해 와서 -- 실제파일명
					   	   			// fileDetailDAO.getFileDetail(company, fileMid, deFile[i]);            
					   				
	   				  			}
	   			
	   	   			 	   		System.out.println("delFileYN : " + delFileYN);
	   	   			 
	   	   			 	//if(delFileYN =='Y'){	 
	   	   		   	   		// 그 파일 명으로 파일 삭제
	   	   			   	   		//boolean exist = false;
	   	   			   		   	List<FileDetailDTO> fileExist=null ;
		   		   	
			   		//file_m_id의 여부 확인
			   	   	 
			   	   	 System.out.println("deleteAction fileMid:" +file_m_id);
		   		   	
	   			  ////////////////////////////////////////////////////////////////////////////// 
	   			   
	   			   
			// 파일 m id 정리 [시작]   					
   			int detailCount = fileDetailDAO.getCountFileDetail(company, file_m_id);
   			if(detailCount==0){
   				fileMasterDAO.FileMasterDelete(company, file_m_id); // 파일 마스터 테이블 삭제
   				auDao.setApprFileMIDNull(company, apprNo);  // 보드 file_m_id 를 null
   			} else {
   				apprAddDao.updateApprDocFileMId(company, apprNo, file_m_id); // 보드 file_m_id update
   			}
   			// 파일 m id 정리 [끝]
				
		return "redirect:/ApprDetail.appr?appr_no="+apprNo;
   			//return "redirect:/ApprList.appr";
				
	}
	
	@RequestMapping("/ApprDocWrite.appr")
	public ModelAndView apprDocAdd(HttpServletRequest request,HttpServletResponse response, HttpSession session, MultipartHttpServletRequest mRequest){
		
		long startTime = System.currentTimeMillis();
		// 특정 로직이나 메소드 호출
		
		boolean result = false;

		// ApprDocDTO(0)
		// ApprDocDAO(0)
		
		ApprDocDTO apprDocData = new ApprDocDTO();		

		// 결재자 appr
		// 합의자 agree
		// 시행자 enforce
		// 수신참조자 refer
		// ApprDocStepDTO(0)
		// ApprDocStepDAO
		List<ApprStepDTO> apprStepList = new ArrayList<ApprStepDTO>();		
		

		// Session
		String company = null;
		String empno = null;		
		String	deptno = null;		// 부서		
		String loginId = null;
		
		String apprTitle = null;
		String apprContent = null;
		String apprDate = null;
		String finalDate = null;
		int apprNo = 0;

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// File Upload
		//-----------기존 시작
		int fileMId = 0;
		int fileDId = 0;
//		MultipartRequest multi = null;
		String realFolder = "";
		String saveFolder = "/apprUpload";		
		//int fileSize = 5 * 1024 * 1024;// 1024는 1kbyte *1024는 1Mbyte *5 5Mbyte
		//Enumeration files = null;
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder+"/");
		//절대 경로와 tomcat이 관리하는 경로가 있는데 tomcat을 클릭해서 serve modules without publishing을 선택해야 한다. 
			
		
		System.out.println("realFolder 체크: "+realFolder);
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
		
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			deptno = (String)session.getAttribute("deptno");
			empno = (String)session.getAttribute("empno");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session deptno : " + deptno);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
			
		// 세션에서 받아오면 이 부분을 막아준다.
//		if(request.getParameter("company") != null) {
//			company = request.getParameter("company");

			// System.out.println(multi.getParameter("apprEmpNo0"));

			// file_nm = multi.getFilesystemName((String)
			// multi.getFileNames().nextElement());
			// file_rnm = multi.getOriginalFileName((String)
			// multi.getFileNames().nextElement());
			
			
			if (mRequest.getParameter("apprTitle") != null) {
				apprTitle = mRequest.getParameter("apprTitle");
				System.out.println(apprTitle);
			}
			if (mRequest.getParameter("apprContent") != null) {
				apprContent = mRequest.getParameter("apprContent");
			}
			if (mRequest.getParameter("apprDate") != null) {
				apprDate = mRequest.getParameter("apprDate");
			}
			if (mRequest.getParameter("finalDate") != null) {
				finalDate = mRequest.getParameter("finalDate");
			}
	
		System.out.println(mRequest.getParameter("empNo"));

		// <input id="apprEmpNo0" name="apprEmpNo0" type="hidden" value="E0001">
		// <span id="apprEmpNm0" class="w3-large">홍길동</span>&nbsp;&nbsp;

		// 결재자 appr
		// 합의자 agree
		// 시행자 enforce
		// 수신참조자 refer

		// 결재자
		// String[] apprEmpNo = null;
		// int apprEmpCnt = 2; //수정 필요

		String[] apprEmpNo = null;
		int apprEmpCnt = 0;
		apprEmpCnt = Integer.parseInt(mRequest.getParameter("apprEmpCnt"));
		apprEmpNo = new String[apprEmpCnt];

		if (apprEmpCnt != 0) {
			for (int i = 0; i < apprEmpCnt; i++) {
				apprEmpNo[i] = mRequest.getParameter("apprEmpNo" + i);
				System.out.println("apprEmpCnt 배열 카운트 test"+i);				
			}
		}

		// 합의자
		String[] agreeEmpNo = null;
		int agreeEmpCnt = 0;
		agreeEmpCnt = Integer.parseInt(mRequest.getParameter("agreeEmpCnt"));
		agreeEmpNo = new String[agreeEmpCnt];

		if (agreeEmpCnt != 0) {
			for (int i = 0; i < agreeEmpCnt; i++) {
				agreeEmpNo[i] = mRequest.getParameter("agreeEmpNo" + i);
			}
		}

		// 시행자
		String[] enforceEmpNo = null;
		int enforceEmpCnt = 0;
		enforceEmpCnt = Integer.parseInt(mRequest.getParameter("enforceEmpCnt"));
		enforceEmpNo = new String[enforceEmpCnt];

		if (enforceEmpCnt != 0) {
			for (int i = 0; i < enforceEmpCnt; i++) {
				enforceEmpNo[i] = mRequest.getParameter("enforceEmpNo" + i);
			}
		}

		// 수신참조자
		String[] referEmpNo = null;
		int referEmpCnt = 0;
		referEmpCnt = Integer.parseInt(mRequest.getParameter("referEmpCnt"));
		referEmpNo = new String[referEmpCnt];

		if (referEmpCnt != 0) {
			for (int i = 0; i < referEmpCnt; i++) {
				referEmpNo[i] = mRequest.getParameter("referEmpNo" + i);
			}
		}

		int iResult = 0;
		// appr_no sequence 가져오기
		apprNo = apprAddDao.getApprNo();

		// if( files != null){
		// fileMId = fileSaveDao.getFileMId();
		// fileDId = fileSaveDao.getFileDId();
		// }
		
		// appr_doc 테이블 Insert
		if (apprNo > 0) {

			apprDocData.setComp_cd(company);
			apprDocData.setAppr_no(apprNo);
			apprDocData.setEmp_no(empno);
			//apprDocData.setM_YN(m_yn);
			apprDocData.setAppr_title(apprTitle);
			apprDocData.setAppr_content(apprContent);
			apprDocData.setAppr_date_appr(apprDate);
			apprDocData.setAppr_date_final(finalDate);
			apprDocData.setInsert_id(loginId);
			// apprDocData.setFile_m_id(fileMId);

			iResult = apprAddDao.apprDocInsert(apprDocData);

			if (iResult > 0) {
				System.out.println("insertApprDoc 성공 : " + apprNo);
			}

		}

		// appr_step 테이블 Insert

		int sResult = 0;

		if (iResult > 0) {

			{// 결재자
				for (int i = 0; i < apprEmpCnt; i++) {
					ApprStepDTO apprStep = new ApprStepDTO();

					apprStep.setComp_cd(company);
					apprStep.setAppr_no(apprNo);
					apprStep.setAppr_line("A"); // gubun
					apprStep.setEmp_no(apprEmpNo[i]);
					apprStep.setAppr_priority(i + 1);
					apprStep.setInsert_id(loginId);
					
					System.out.println("결재자(apprEmpCnt) :"+i);
					
					apprStepList.add(apprStep);
									
				}
			} // 결재자

			{// 합의자
				for (int i = 0; i < agreeEmpCnt; i++) {
					ApprStepDTO apprStep = new ApprStepDTO();

					apprStep.setComp_cd(company);
					apprStep.setAppr_no(apprNo);
					apprStep.setAppr_line("B");
					apprStep.setEmp_no(agreeEmpNo[i]);
					apprStep.setAppr_priority(i + 1);
					apprStep.setInsert_id(loginId);

					apprStepList.add(apprStep);
				}
			} // 합의자

			{// 실행자
				for (int i = 0; i < enforceEmpCnt; i++) {
					ApprStepDTO apprStep = new ApprStepDTO();

					apprStep.setComp_cd(company);
					apprStep.setAppr_no(apprNo);
					apprStep.setAppr_line("C");
					apprStep.setEmp_no(enforceEmpNo[i]);
					apprStep.setAppr_priority(i + 1);
					apprStep.setInsert_id(loginId);

					apprStepList.add(apprStep);
				}
			} // 실행자

			{// 수신참조자
				for (int i = 0; i < referEmpCnt; i++) {
					ApprStepDTO apprStep = new ApprStepDTO();

					apprStep.setComp_cd(company);
					apprStep.setAppr_no(apprNo);
					apprStep.setAppr_line("D");
					apprStep.setEmp_no(referEmpNo[i]);
					apprStep.setAppr_priority(i + 1);
					apprStep.setInsert_id(loginId);

					apprStepList.add(apprStep);
				}
			} // 수신참조자
			
			System.out.println("apprStepInsert 실행 직전");

			sResult = apprAddDao.apprStepInsert(apprStepList);

			if (sResult > 0) {
				System.out.println("apprStepInsert 성공 : " + apprNo);
			}
		}

		
		// 파일 시작
		if(sResult > 0){
			
			int fileCount = 0;
			String file_nm = null;
			String file_rnm = null;
			boolean fmResult = false;				
			Iterator<String> iter=mRequest.getFileNames();   //   Iterator : 컬렉션 프레임워크에서 리스트나 셋타입의 값을 꺼내오고자할 때 Iterator타입으로 ...
			
			System.out.println("getFileNames test: "+iter);
			
			while(iter.hasNext())  //hasNext : 데이터가 있는지 없는지 판단, 데이가 있으면 트루
			{
				String uploadFileName = iter.next();   //데이터가 있을 떄 next로 ...
				MultipartFile mFile = mRequest.getFile(uploadFileName);				
				String originalFileName = mFile.getOriginalFilename();   //getOriginalFilename : 브라우저로 부터 전송되어지는 파일의 이름을 반환해준다.
				file_nm = originalFileName;
				
				System.out.println("file_nm test: "+file_nm);				
				
				String saveFileName = originalFileName;
				file_rnm = saveFileName;
				
				System.out.println("file_rnm test: "+file_rnm);
				
				if(saveFileName != null && !saveFileName.equals(""))   //널이거나 문자열에 아무것도 없으면 아무것도 처리해서는 안되게 해야한다.
				{
					if(new File(uploadPath+saveFileName).exists())   //존재하는 없는지 확인, 같은 이름이 있다면 임의의 이름으로 변경해서 저장
					{
						//saveFileName = saveFileName+"_"+System.currentTimeMillis();  //있다면... '_'를 붙여준다.   그리고 currentTimeMillis(현재시간)도 붙여준다.
						
						String temp1 = saveFileName.substring(0, saveFileName.lastIndexOf(".")); //[table]
						String temp2 = saveFileName.substring(saveFileName.lastIndexOf("."),saveFileName.length());//[.txt]
//						System.out.println("temp1:["+temp1+"]");
//						System.out.println("temp2:["+temp2+"]");
						saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
						
						file_rnm = saveFileName;
						
						System.out.println("file_rnm 체크"+file_rnm);
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
	   	   					apprAddDao.updateApprDocFileMId(company, apprNo, fileMId);

						}
						
	   	   				
	   	   				// 마스터
	   	   				if(fileCount==1){
	   	   								
	   	   					FileMasterDTO fileMaster = new FileMasterDTO();
						
	   	   					fileMaster.setComp_cd(company);
	   	   					fileMaster.setFile_m_id(fileMId);
	   	   					fileMaster.setFile_gubun("1"); // 결재서류['1']
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
		   	   				
							FileDetailDTO fileDetail = new FileDetailDTO();
							
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
		
		
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println(elapsedTime + " ms");
		
		
		return  new ModelAndView("redirect:/ApprList.appr");
		
	}
	
	@RequestMapping("/ApprDeleteAction.appr")
	public String apprDeleteAction(HttpServletRequest request,HttpServletResponse response , HttpSession session){
		
		long startTime = System.currentTimeMillis();
		// 특정 로직이나 메소드 호출
			 
	 		//System.out.println("Appr_DeleteAction 도착");
	 
			
			//FileDeleteDAO filedelete = new FileDeleteDAO();
			//ApprDocDAO boarddao=new ApprDocDAO();
			//ApprDeleteDAO apprDel = new ApprDeleteDAO();
			
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   	boolean result=false;			   	
		   	int apprNo = 0;
		   	int fileMId = 0;
		 	String empNo = null;
		 	List<FileDetailDTO> pathList = null;
		 	int dResult = 0;
		 	int mResult = 0;
		 	int sResult = 0;
		 	int aResult = 0;   		
	   		boolean exist = false;
	   		
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
		   				   	
		   	
		   	apprNo=Integer.parseInt(request.getParameter("appr_no"));
		   	//empNo = (String)request.getSession().getAttribute("empno");
		   	
		   	System.out.println("parameter apprNo 도착:"+apprNo);
		   	
		   	fileMId = apprDel.getFileMidExist(apprNo);
		   	
		   	System.out.println("파일 마스터 id 가져오기: " + fileMId);
		   	
		   	if(fileMId > 0){
		   		try {
					pathList = (List<FileDetailDTO>)fileDetailDAO.getFileDetailList(company, fileMId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		   		
		   	
			   	for(int i=0; i<pathList.size(); i++ ){
			   		
			   		//String filePath = null;
			   		String fileRnm = null;
			   		//String deletePath = null;
			   					   		
			   		//filePath = pathList.get(i).getFile_path();
			   		fileRnm = pathList.get(i).getFile_rnm();
			   		
			   		//System.out.println(filePath);
			   		//System.out.println(fileNm);
			   		
			   		//deletePath = filePath +fileNm;
			   		
			   		//System.out.println(deletePath);				   		
			   		//deletePath.replaceAll("^\\$", "^\\\\$");				   		
			   		//System.out.println(deletePath);			   		
			   		/* Path fileDelete = Paths.get(deletePath);		                
	                try {
	                        Files.deleteIfExists(fileDelete);
	                        //파일이 있는지 없는지를 살펴보고 파일이 있으면 삭제하고 없으면 아무 동작도 하지 않음
	                        
	                        System.out.println("deleteIfExists 실행");
	                } catch (IOException e) {
	                        // TODO Auto-generated catch block
	                        e.printStackTrace();
	                }*/
			   		
			   		String realFolder = "";
	                String saveFolder = "/apprUpload/";

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
			  		
			  		dResult = fileDetailDAO.FileDetailDelete(company, fileMId);
			  		
			  		System.out.println("dResult : "+dResult);
			  		
			  		
			   		if(dResult != 0){
			   			System.out.println("detail 삭제 성공");
			   		}else{
			   			System.out.println("detail 삭제 실패");
			   		}
			   	
			   	}
			   	
			  	 
			  				  	
			   	if(dResult !=0 ){
			   		mResult = fileMasterDAO.FileMasterDelete(company, fileMId);	
				   	if(mResult != 0){
			   			System.out.println("master 삭제 성공");
			   		}else{
			   			System.out.println("master 삭제 실패");
			   		}
			   	
			   	}   	
			   	   					   	
			   	
		   	}
		   	
		   	if(apprNo > 0){
			   	sResult = apprDel.deleteApprStep(apprNo);	
				   	if(sResult != 0){
			   			System.out.println("appr_step 삭제 성공");
			   		}else{
			   			System.out.println("appr_step 삭제 실패");
			   		}
			   	}
			   	if(sResult != 0){
			   	aResult = apprDel.deleteApprDoc(apprNo);
				   	if(aResult != 0){
			   			System.out.println("appr_doc 삭제 성공");
			   		}else{
			   			System.out.println("appr_doc 삭제 실패");
			   		}
			   	}
		   				   				   	
		   //	System.out.println(fileMId);			   	
		   // result=boarddao.apprDocDelete(apprNo);
		    
		   	if(aResult== 0){
		   		
		   		System.out.println("결제서류 삭제 실패");
		   		long elapsedTime = System.currentTimeMillis() - startTime;
				System.out.println(elapsedTime + " ms");
		   		return null;
		   	}
		   	
		   	long elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println(elapsedTime + " ms");
			
			System.out.println("결제서류 삭제 성공");
		
		
			return "redirect:/ApprList.appr";
	}
	
	
	@RequestMapping("/ApprHistoryAction.appr")
	public String apprHistoryAdd(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		int ret = 0;
		System.out.println("/ApprHistoryAction.appr 명령-> apprHistoryAdd 메소드 실행");
		
		ApprHistoryDTO history_Bean = new ApprHistoryDTO();
				
		String comp_cd = request.getParameter("comp_cd");
		
		String emp_no =	request.getParameter("emp_no");
		
		System.out.println("emp_no 출력 1:"+ emp_no  );
		
		String emp_id = request.getParameter("emp_id");
		int appr_no = Integer.parseInt(request.getParameter("appr_no"));
		int appr_line_no = asDao.getApprLineNo(appr_no, emp_no);
		int priority = asDao.getApprMe(emp_no, appr_no);		
		String status = (String)request.getParameter("status");
			
		System.out.println("comp_cd: "+comp_cd);
		System.out.println("status:  "+status);
		System.out.println("appr_line_no :"+appr_line_no);
		System.out.println("priority: "+priority);
		
		history_Bean.setComp_cd(comp_cd);
		history_Bean.setAppr_line_no(appr_line_no);
		history_Bean.setAppr_no(appr_no);
		history_Bean.setEmp_no(emp_no);
		history_Bean.setInsert_id(emp_id);
		history_Bean.setPriority(priority);
		history_Bean.setStatus(status);
			
		System.out.println("history_Bean에 입력 완료");
		
		ret = asDao.apprHistoryInsert(history_Bean);
		
		return "redirect:/ApprList.appr";
	}
	
	@RequestMapping("/GetHistoryAction.appr")
	public ModelAndView getHistory(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		System.out.println("ApprGetHistoryActcion에 도착");
		List<HistoryPopDTO> list = new ArrayList<HistoryPopDTO>();
		int apprNo = 0;
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		apprNo = Integer.parseInt(request.getParameter("apprNo"));
		
		String company = null;
		String loginId = null;
		String emp_no = null;
				
		session = request.getSession(true);//현재 존재하는 세션값을 얻어온다
		
		if(session != null){
		
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			emp_no = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
					
		}else{
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
		
		list = asDao.getHistory(apprNo, company);
		
		System.out.println("콘트롤러에서 리스트 수 체크:"+list.size());
		request.setAttribute("list", list);
		
		
		return new ModelAndView("apprDoc/apprHistoryPop");
	}
	
	@RequestMapping("/ApprModifyView.appr")
	public ModelAndView apprModifyView(HttpServletRequest request, HttpSession session){
		
		System.out.println("ApprController : apprModifyView()");
		
		
		String loginId = null;
		String company= null;
		String emp_no = null;
		int appr_no = 0;
		ApprDetailDTO1 apprDoc = new ApprDetailDTO1();	
		List<ApprDetailDTO2> apprStepList = new ArrayList();
		FileMasterDTO fileMasterDto = new FileMasterDTO();
		List<FileDetailDTO> fileDetaillist = new ArrayList();
				
		
		session = request.getSession(true);//현재 존재하는 세션값을 얻어온다
		if(session != null){
		
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			emp_no = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
					
		}else{
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
				
		
		if(request.getParameter("appr_no") != null){
			appr_no= Integer.parseInt(request.getParameter("appr_no"));
		}
				
		apprDoc = adDao.getApprDoc(appr_no);//appr_no 대체
				
		try {
			apprStepList = adDao.getApprStep(appr_no);//appr_no로 대체
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//appr_no대체
				
		//int myHistory = asDao.getMyApprHistory(emp_no, company, appr_no);//내 결재 이력 확인(없을 경우 0 반환)
		//int apprYN = asDao.getApprYN(appr_no);//반려 여부 확인(없을 경우 0 반환)
		//int historyMax = asDao.getApprHistoryMax(appr_no); //결재 이력 최고 순서 확인(없을 경우 0 반환)
		//int apprMe = asDao.getApprMe(emp_no, appr_no); //내 결재 순서 확인(없을 경우 0 반환)
		//String myLine = asDao.getApprMyLine(emp_no, appr_no); //내 결재 라인 확인(없을 경우 null 반환)
				
		
		if(apprDoc.getFile_m_id() != 0){
			try {                                   //apprDoc.getFile_m_id()
				fileMasterDto =fileMasterDAO.getFileMaster(company, apprDoc.getFile_m_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //
			try {
				fileDetaillist = fileDetailDAO.getFileDetailList(company, fileMasterDto.getFile_m_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		request.setAttribute("apprDoc", apprDoc);//읽어온 데이터 request 영역에 저장
		request.setAttribute("apprStepList", apprStepList);	
		
		//request.setAttribute("myHistory", myHistory );
		//request.setAttribute("apprYN", apprYN);
		//request.setAttribute("historyMax", historyMax);
		//request.setAttribute("apprMe", apprMe);
		//request.setAttribute("myLine", myLine);
		
		
		 if(apprDoc.getFile_m_id() != 0){
		   	request.setAttribute("fileMasterDto", fileMasterDto);
		   	request.setAttribute("fileDetaillist", fileDetaillist);
		 }
	
			
		
	return new ModelAndView("apprDoc/apprModify");	
				
	}
	
	
	@RequestMapping("/ApprDetail.appr")
	public ModelAndView apprDetail(HttpServletRequest request, HttpSession session){
		
		System.out.println("ApprListController : ApprDetail()");
		
		
		String loginId = null;
		String company= null;
		String emp_no = null;
		int appr_no = 0;
		ApprDetailDTO1 apprDoc = new ApprDetailDTO1();	
		List<ApprDetailDTO2> apprStepList = new ArrayList();
		FileMasterDTO fileMasterDto = new FileMasterDTO();
		List<FileDetailDTO> fileDetaillist = new ArrayList();
				
		
		session = request.getSession(true);//현재 존재하는 세션값을 얻어온다
		if(session != null){
		
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			emp_no = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
					
		}else{
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
				
		
		if(request.getParameter("appr_no") != null){
			appr_no= Integer.parseInt(request.getParameter("appr_no"));
		}
				
		apprDoc = adDao.getApprDoc(appr_no);//appr_no 대체
				
		try {
			apprStepList = adDao.getApprStep(appr_no);//appr_no로 대체
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//appr_no대체
				
		int myHistory = asDao.getMyApprHistory(emp_no, company, appr_no);//내 결재 이력 확인(없을 경우 0 반환)
		int apprYN = asDao.getApprYN(appr_no);//반려 여부 확인(없을 경우 0 반환)
		int historyMax = asDao.getApprHistoryMax(appr_no); //결재 이력 최고 순서 확인(없을 경우 0 반환)
		int apprMe = asDao.getApprMe(emp_no, appr_no); //내 결재 순서 확인(없을 경우 0 반환)
		String myLine = asDao.getApprMyLine(emp_no, appr_no); //내 결재 라인 확인(없을 경우 null 반환)
				
		
		if(apprDoc.getFile_m_id() != 0){
			try {                                   //apprDoc.getFile_m_id()
				fileMasterDto =fileMasterDAO.getFileMaster(company, apprDoc.getFile_m_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 //
			try {
				fileDetaillist = fileDetailDAO.getFileDetailList(company, fileMasterDto.getFile_m_id());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		request.setAttribute("apprDoc", apprDoc);//읽어온 데이터 request 영역에 저장
		request.setAttribute("apprStepList", apprStepList);	
		
		request.setAttribute("myHistory", myHistory );
		request.setAttribute("apprYN", apprYN);
		request.setAttribute("historyMax", historyMax);
		request.setAttribute("apprMe", apprMe);
		request.setAttribute("myLine", myLine);
		
		
		 if(apprDoc.getFile_m_id() != 0){
		   	request.setAttribute("fileMasterDto", fileMasterDto);
		   	request.setAttribute("fileDetaillist", fileDetaillist);
		 }
	
			
		
	return new ModelAndView("apprDoc/apprView");	
				
	}
	
	
	@RequestMapping("/ApprList.appr")
	public ModelAndView apprList(HttpServletRequest request, HttpSession session){
		
		System.out.println("ApprListController : apprList()");
		
		
		List<ApprListDTO> apprDoclist=new ArrayList<ApprListDTO>();
		List<ApprListDTO> apprDoclist2=new ArrayList<ApprListDTO>();
		String loginId = null;
		String company= null;
		String emp_no = null;
		
		session = request.getSession(true);//현재 존재하는 세션값을 얻어온다
		if(session != null){
		
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			emp_no = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
					
		}else{
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session");
		}
		
		
		System.out.println(company);
		System.out.println(emp_no);
		System.out.println(loginId);
		System.out.println();
				
		
		int page=1;
		int page2 =1;
		int limit=5;
		int limit2=5;
		//페이지 관련 변수
		
		
		if(request.getParameter("page")!=null){//아무 것도 보내주지 않으면 null 값이므로 false를 반환
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("page2")!=null){//아무 것도 보내주지 않으면 null 값이므로 false를 반환
			page2=Integer.parseInt(request.getParameter("page2"));
		}
				
		
		int listcount = apprListDAO.getApprDocListCount(company, emp_no);		
		int listcount2 = apprListDAO.getApprDocListCount2(company, emp_no);
		
		apprDoclist = apprListDAO.getApprDocList(page, limit, company, emp_no);		
		apprDoclist2 = apprListDAO.getApprDocList2(page2, limit2, company, emp_no);
		
				
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(((HttpServletRequest) request).getSession().getServletContext());
		
		/*총 페이지 수*/
 		int maxpage=(int)((double)listcount/limit+0.95); /*0.95를 더해서 올림 처리*/ 
 		//11(게시판 총개수)/10(limit)+0.95(9라도 상관없을 듯 함) //더 좋은 방법이 있다면 그 알고리즘을 사용하면 됨
 		int maxpage2=(int)((double)listcount2/limit2+0.95);
 		
 		
 		/*현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)*/
 		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1; //1부터 시작하게 됨
 		int startpage2 = (((int) ((double)page2 / 10 + 0.9)) - 1) * 10 + 1;
 		/*현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30 등...)*/
		int endpage = startpage+10-1;//1부터 10까지 출력
		int endpage2 = startpage2+10-1;

 		if (endpage> maxpage) endpage= maxpage;//총 페이지가 10페이지가 안 될 경우 최대 페이지가 마지막 페이지가 됨
 		if (endpage2> maxpage2) endpage2= maxpage2;
 		
 		 		
 		//리퀘스트 영역에 저장
 		request.setAttribute("page1", page); /*현재 페이지 수*/
 		request.setAttribute("page2", page2);
 		
 		request.setAttribute("maxpage1", maxpage); /*최대 페이지 수*/
 		request.setAttribute("maxpage2", maxpage2);
 		
 		request.setAttribute("startpage1", startpage); /*현재 페이지에 표시할 첫 페이지 수*/
 		request.setAttribute("startpage2", startpage2);
 		
 		request.setAttribute("endpage1", endpage); /*현재 페이지에 표시할 끝 페이지 수*/
 		request.setAttribute("endpage2", endpage2);
						
		request.setAttribute("listcount1", listcount);		
		request.setAttribute("listcount2", listcount2);
		
		request.setAttribute("apprDoclist1", apprDoclist);
		request.setAttribute("apprDoclist2", apprDoclist2);
								
		return new ModelAndView("/apprDoc/apprDocList");
	}
		

}


