package spring.mybatis.gw.main.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.admin.dao.EmployeeDAO;
import spring.mybatis.gw.admin.dto.CompanyDTO;
import spring.mybatis.gw.admin.dto.EmployeeDTO;
import spring.mybatis.gw.admin.dto.SelDTO;
import spring.mybatis.gw.main.dao.MainDAO;
import spring.mybatis.gw.main.dto.MainDTO;

@Controller
public class MainController {
	
	@Autowired
	private MainDAO mainDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	// 로그인 화면
	@RequestMapping("/MainLogin.em")
	public ModelAndView mainLogin(HttpServletRequest request, HttpSession session){
		
		System.out.println("MainController : mainLogin()");
		
		ArrayList<SelDTO> selList = null;
		
		selList = mainDAO.getCompanyList();
				
		request.setAttribute("list", selList);
		
		return new ModelAndView("mainLogin/main_login");
//		return new ModelAndView("mainLogin/Test");
	}
	
	// 메인 화면
	@RequestMapping("/MainMain.em")
	public ModelAndView mainMain(@RequestParam(value="company") String company, 
												@RequestParam(value="id") String id,
												@RequestParam(value="pw") String pw,
												@RequestParam(value="comp_nm") String companyNM,
												HttpServletRequest request, 
												HttpServletResponse response,
												HttpSession session) throws IOException {

		System.out.println("MainController : mainMain()");
		
		CompanyDTO companyDto = new CompanyDTO();
		
		boolean bReturn = false; // 사원
		boolean cReturn = false; // 관리자
		
		bReturn = mainDAO.empCheck(company, id, pw); // 사원체크
		cReturn = mainDAO.adminCheck(company, id, pw); // 관리자체크
		companyDto = mainDAO.getCompnayNm(company); // 회사명
		
		if(bReturn == true ){
			EmployeeDTO empDto = new EmployeeDTO();
			empDto = mainDAO.getEmployee(company, id);

			session.setAttribute("company", company);
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);
			session.setAttribute("gubun", "U");
			session.setAttribute("empno", empDto.getEmp_no());
			session.setAttribute("empnm", empDto.getEmp_nm());
			session.setAttribute("deptno", empDto.getDept_no());
			session.setAttribute("deptnm", empDto.getDept_nm());
			
			request.setAttribute("compnm", companyDto.getComp_nm()); // 회사명(타이틀)
			
			return new ModelAndView("mainLogin/main_main");
			
		} else if(cReturn == true) {
			session.setAttribute("company", company);
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);	
			session.setAttribute("gubun", "M");
			session.setAttribute("empno", "");
			session.setAttribute("empnm", "관리자");
			session.setAttribute("deptno", "");
			session.setAttribute("deptnm", "");
			
			request.setAttribute("compnm", companyDto.getComp_nm()); // 회사명(타이틀)
			
			return new ModelAndView("mainLogin/main_main");
			
		} else{
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}
	
	// 프로필 화면
	@RequestMapping("/MainMainProfile.em")
	public ModelAndView mainMainProfile(HttpServletRequest request, HttpSession session) throws SQLException, IOException {
		System.out.println("MainController : mainMainProfile()");
		
		// 이미지 파일 생성
		// 이미지 파일 name 넘겨주기..
		
		String	company = null;		// 회사 코드
		String	empno = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.
		if (session != null){
			company = (String)session.getAttribute("company");
			empno = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session empno : " + empno);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		boolean noImage = false;
		Map<String, Object> map = employeeDAO.getEmployeeImg(company, empno);
		Blob blob = null;
		String img_file_rnm = "";
		if(map != null) {
			blob = (Blob) map.get("IMG");
			img_file_rnm = (String) map.get("IMG_FILE_RNM");
		} else {
			noImage = true;
		}
		String saveFileName = img_file_rnm;
		String uploadPath = request.getSession().getServletContext().getRealPath("resources/tempImg/");
		System.out.println("uploadPath : "+uploadPath);
		
		File dir = new File(uploadPath);
		if(dir.isDirectory()) {	
			dir.mkdirs(); // 폴더가 없다면 폴더를 만든다.
		}
		if(new File(uploadPath+saveFileName).exists()){
			;
		} else { // 기존에 파일이 없으면 blob 파일을 image 파일로 최초 생성한다!
			if(!noImage){
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				String imgpath = uploadPath + saveFileName;
				bos = new BufferedOutputStream(new FileOutputStream(imgpath));
				bis = new BufferedInputStream(blob.getBinaryStream());
		
				byte[] buffer = new byte[2048]; 
				int read = 0;
				while((read = bis.read(buffer)) != -1) { 
					bos.write(buffer , 0 , read);
				} 
				bos.flush();
				bos.close();
			}
		}

		request.setAttribute("imgName", saveFileName);
		request.setAttribute("noImage", noImage);
		
		
		return new ModelAndView("mainLogin/main_profile");
	}
	
	// 메뉴 화면
	@RequestMapping("/MainMainMenu.em")
	public ModelAndView mainMainMenu(HttpServletRequest request, HttpSession session) {
		System.out.println("MainController : mainMainMenu()");
		
		return new ModelAndView("mainLogin/main_menu");
	}
	
	// 비밀번호 수정 팝업창
	@RequestMapping("/MainPwChangePopup.em")
	public ModelAndView mainPwChangePopUp(HttpServletRequest request, HttpSession session)  {
		System.out.println("MainController : mainPwChangePopUp()");
	
		String company = null;
		String id = null;
		
		if (session != null){
			company = (String)session.getAttribute("company");
			id = (String)session.getAttribute("id");
			System.out.println("session loginId : " + id);
		} else { // 세션이 없으면 error page 혹은 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_popup"); 
		}
		
		return new ModelAndView("mainLogin/main_pwchange");
	}

	
	// 기존 비밀번호 체크
	@RequestMapping("/mainPwCheck.em")
	public ModelAndView mainPwCheck(@RequestParam(value="now_pw") String pw,
														HttpServletRequest request, 
														HttpServletResponse response,
														HttpSession session
														) throws IOException  {
		
		
		System.out.println("MainController : mainPwCheck()");
		
		boolean bReturn = false;
		
		
		String company = null;
		String id = null;
		String gubun = null;		
		
		if (session != null){
			gubun = (String)session.getAttribute("gubun");
			company = (String)session.getAttribute("company");
			id = (String)session.getAttribute("id");
			System.out.println("session loginId : " + id);
		} else { // 세션이 없으면 error page 혹은 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); 
		}
		
		System.out.println("$company"+company+"$id"+id+"$pw"+pw);
		
		if(gubun.equals("U")){
			bReturn = mainDAO.isEmpPw(company, id, pw);
		} else if(gubun.equals("M")){
			bReturn = mainDAO.isAdminPw(company, id, pw);
		}
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"result\" : ");
		json.append(bReturn);
		json.append("}");
		
		response.setCharacterEncoding("utf-8");
		
		PrintWriter out;
		out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	
	// 비밀번호 수정
	@RequestMapping("/MainPwChange.em")
	public ModelAndView mainPwChange(@RequestParam(value="new_pw") String newpw,
														@RequestParam(value="pw_ok") String pwok,
														HttpServletRequest request,
														HttpServletResponse response,
														HttpSession session) throws IOException  {
		
		System.out.println("MainController : mainPwChange()");
		
		MainDTO mainDto = new MainDTO();
		
		boolean rOK = false;
		
		String company = null;
		String loginId = null;

		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다
		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 error page 혹은 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_popup"); // 이거 바꿔라
		}
		
//		System.out.println("$newpw : "+newpw+"$pwok : "+pwok);
		
		if(newpw.equals(pwok)){

			mainDto.setComp_cd(company);
			mainDto.setEmp_id(loginId);
			mainDto.setEmp_pw(newpw);
			
			int rtn = mainDAO.pwUpdate(mainDto); // 비밀번호 변경 메소드
			System.out.println("&&rtn :  "+rtn);

			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호 변경에 성공하였습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			
			return new ModelAndView("mainLogin/main_pwchange");
		}
		return null;
	}
	
	// 정보수정 팝업창
	@RequestMapping("/MainInfoChangePopup.em")
	public ModelAndView mainInfoChangePopUp(HttpServletRequest request, HttpSession session) throws SQLException, IOException {
		System.out.println("MainController : mainInfoChangePopUp()");
	
			// 이미지 파일 생성
			// 이미지 파일 name 넘겨주기..
			
			String	company = null;		// 회사 코드
			String	empno = null;
			
			session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.
			
			if (session != null){
				company = (String)session.getAttribute("company");
				empno = (String)session.getAttribute("empno");
				System.out.println("session company : " + company);
				System.out.println("session empno : " + empno);
			} else { // 세션이 없으면 로그인 페이지로 이동
				System.out.println("session null!");
				return new ModelAndView("mainLogin/out_popup"); // ModelAndView 일 때
			}
			
			boolean noImage = false;
			Map<String, Object> map = employeeDAO.getEmployeeImg(company, empno);
			Blob blob = null;
			String img_file_rnm = "";
			if(map != null) {
				blob = (Blob) map.get("IMG");
				img_file_rnm = (String) map.get("IMG_FILE_RNM");
			} else {
				noImage = true;
			}
			String saveFileName = img_file_rnm;
			String uploadPath = request.getSession().getServletContext().getRealPath("resources/tempImg/");
			System.out.println("uploadPath : "+uploadPath);
			
			File dir = new File(uploadPath);
			if(dir.isDirectory()) {	
				dir.mkdirs(); // 폴더가 없다면 폴더를 만든다.
			}
			if(new File(uploadPath+saveFileName).exists()){
				;
			} else { // 기존에 파일이 없으면 blob 파일을 image 파일로 최초 생성한다!
				if(!noImage){
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					String imgpath = uploadPath + saveFileName;
					bos = new BufferedOutputStream(new FileOutputStream(imgpath));
					bis = new BufferedInputStream(blob.getBinaryStream());
			
					byte[] buffer = new byte[2048]; 
					int read = 0;
					while((read = bis.read(buffer)) != -1) { 
						bos.write(buffer , 0 , read);
					} 
					bos.flush();
					bos.close();
				}
			}

			request.setAttribute("imgName", saveFileName);
			request.setAttribute("noImage", noImage);
			
		
		return new ModelAndView("mainLogin/main_infochange");
	}
	
	// 정보수정 
	@RequestMapping("/MainInfoChange.em")
	public ModelAndView mainInfoChange(@RequestParam(value="emp_phone") String phone, 
														  @RequestParam(value="emp_email") String email,
														  @RequestParam(value="emp_addr") String address,
														  HttpServletRequest request, 
														  HttpServletResponse response,
														  HttpSession session) throws IOException {
		
		System.out.println("MainController : mainInfoChange()");
		
		MainDTO mainDto = new MainDTO();
		
		boolean bReturn = false;
		
		String company = null;
		String id = null;
		
		if (session != null){
			company = (String)session.getAttribute("company");
			id = (String)session.getAttribute("id");
			System.out.println("session loginId : " + id);
		} else { // 세션이 없으면 error page 혹은 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_popup"); // 이거 바꿔라
		}
		
		mainDto.setComp_cd(company);
		mainDto.setEmp_id(id);
		mainDto.setEmp_phone(phone);
		mainDto.setEmp_email(email);
		mainDto.setEmp_addr(address);
		
		int rtn = mainDAO.infoUpdate(mainDto);
		System.out.println("$$Info"+rtn);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('정보수정에 성공하였습니다.');");
		out.println("history.go(-1);");
		out.println("</script>");
		out.close();
		
		return new ModelAndView("mainLogin/main_infochange");
	}

	// 로그아웃
	@RequestMapping("/MainLogOut.em")
	public ModelAndView mainLogout(HttpServletRequest request, HttpSession session) {
		System.out.println("MainController : mainLogout()");
		
		return new ModelAndView("mainLogin/out_session");
	}
	@RequestMapping("/MainCallOut.em") 
	public ModelAndView mainCallout(HttpServletRequest request, HttpSession session) {
		
		return new ModelAndView("mainLogin/out_callout");
	}
	
	// 팝업창 로그아웃
	@RequestMapping("/MainPopUpOut.em") 
	public ModelAndView mainPopUpOut(HttpServletRequest request, HttpSession session) {
		
		return new ModelAndView("mainLogin/out_session");
	}
	
	@RequestMapping("/MainImgChange.em") 
	public ModelAndView mainImgChange(HttpServletRequest request, HttpSession session) {
		
		return new ModelAndView("mainLogin/main_imgchange");
	}
	
	@RequestMapping("/MainImgSave.em") 
	public String mainImgSave(MultipartHttpServletRequest mRequest, HttpSession session) throws IOException {
		
		// 이미지 저장
		System.out.println("MainController : mainImgSave()");
		
		String	company = null;		// 회사 코드
		String	empno = null;
		
//		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			empno = (String)session.getAttribute("empno");
			System.out.println("session company : " + company);
			System.out.println("session empno : " + empno);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_popup"; // 리턴이 String일때
		}
		
		MultipartFile mFile = mRequest.getFile("img");
		String img_file_nm = mFile.getOriginalFilename();
		String img_file_rnm = mFile.getOriginalFilename();

		boolean hasDBFileNm = employeeDAO.isEmployeeImgFileRNM(company, empno, img_file_rnm);
		String myDBSavedFileRNM = employeeDAO.getEmployeeImgFileRNM(company, empno);
		
		System.out.println("myDBSavedFileRNM : " + myDBSavedFileRNM);
		System.out.println("hasDBFileNm : " + hasDBFileNm);
		
		String saveFileName = img_file_rnm;
		String uploadPath = session.getServletContext().getRealPath("resources/tempImg/");
		System.out.println("uploadPath : "+uploadPath);
		File dir = new File(uploadPath);	
		
		if(dir.isDirectory()) {	
			dir.mkdirs(); // 폴더가 없다면 폴더를 만든다.
		}
		
		if(new File(uploadPath+myDBSavedFileRNM).exists()) { // 기존 내 파일은 삭제하자!![실제파일말함!!] (조회하면서 파일이 생기기 때문에..)
			File file = new File(uploadPath+myDBSavedFileRNM);
			System.out.println("delete!!");
			file.delete();
		}
		if(hasDBFileNm) { //존재하는 없는지 확인, 같은 이름이 있다면 임의의 이름으로 변경해서 저장 (다른 사람 파일명하고 겹치는 경우)
			System.out.println("파일명 변경!!");
			String temp1 = saveFileName.substring(0, saveFileName.lastIndexOf(".")); //[table]	
			String temp2 = saveFileName.substring(saveFileName.lastIndexOf("."),saveFileName.length());//[.txt]	
			System.out.println("temp1:["+temp1+"]");	
			System.out.println("temp2:["+temp2+"]");	
			saveFileName = temp1 + "_" + System.currentTimeMillis() + temp2;
			img_file_rnm = saveFileName;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("img", mFile.getBytes());
        map.put("comp_cd", company);
        map.put("emp_no", empno);
        map.put("img_file_nm", img_file_nm);
        map.put("img_file_rnm", img_file_rnm);
        
		int rtn = employeeDAO.setEmployeeImg(map);
		if(rtn > 0){
			System.out.println("이미지 파일 업로드 OK");
		}
		
		return "redirect:/MainInfoChangePopup.em";
	}
}


































