package spring.mybatis.gw.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.admin.dao.DeptDAO;
import spring.mybatis.gw.admin.dao.EmployeeDAO;
import spring.mybatis.gw.admin.dto.DeptDTO;
import spring.mybatis.gw.admin.dto.EmployeeDTO;
import spring.mybatis.gw.admin.dto.SelDeptDTO;
import spring.mybatis.gw.admin.dto.SelRoleDTO;
import spring.mybatis.gw.share.util.DocDept;

@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@RequestMapping("/EmployeeImgTestUp.admin")
	public ModelAndView employeeImgTestUp(HttpServletRequest request, HttpSession session,
										MultipartHttpServletRequest mRequest) throws IOException, SQLException{
		System.out.println("EmployeeController : employeeImgTestUp()");
		
		String comp_cd = (String) mRequest.getParameter("compCd");
		String emp_no = (String) mRequest.getParameter("empNo");
		
		MultipartFile mFile = mRequest.getFile("img");
		String img_file_nm = mFile.getOriginalFilename();
		String img_file_rnm = mFile.getOriginalFilename();

		boolean hasDBFileNm = employeeDAO.isEmployeeImgFileRNM(comp_cd, emp_no, img_file_rnm);
		String myDBSavedFileRNM = employeeDAO.getEmployeeImgFileRNM(comp_cd, emp_no);
		
		System.out.println("myDBSavedFileRNM : " + myDBSavedFileRNM);
		System.out.println("hasDBFileNm : " + hasDBFileNm);
		
		String saveFileName = img_file_rnm;
		String uploadPath = request.getSession().getServletContext().getRealPath("resources/tempImg/");
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
        map.put("comp_cd", comp_cd);
        map.put("emp_no", emp_no);
        map.put("img_file_nm", img_file_nm);
        map.put("img_file_rnm", img_file_rnm);
        
		int rtn = employeeDAO.setEmployeeImg(map);
		if(rtn > 0){
			System.out.println("이미지 파일 업로드 OK");
		}
		
		return null;
	}
	
	@RequestMapping("/EmployeeImgTest.admin")
	public ModelAndView employeeImgTest(HttpServletRequest request, HttpSession session) throws SQLException, IOException{
		
		System.out.println("EmployeeController : employeeImgTest()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
				
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		//loginId = "id03"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		//loginId = "i002"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		EmployeeDTO employee = employeeDAO.getEmployee(company, loginId);
		employee.setComp_cd(company);
		
		boolean noImage = false;
		Map<String, Object> map = employeeDAO.getEmployeeImg(company, employee.getEmp_no());
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
		request.setAttribute("employee", employee);
		request.setAttribute("imgName", saveFileName);
		request.setAttribute("noImage", noImage);
		
		return new ModelAndView("admin/employee_img_test");
	}
	
	@RequestMapping("/EmployeeInfo.admin")
	public ModelAndView employeeList(@RequestParam(value="searchGubun", required=false, defaultValue="") String searchGubun,
									@RequestParam(value="searchInput", required=false, defaultValue="") String searchInput,
									HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeList()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("$company : " + company + " , searchGubun : " + searchGubun + " , searchInput : " + searchInput);
		
		List<EmployeeDTO> list = null;
		
		if (searchGubun == null || (searchGubun.equals("ALL") && searchInput.length() == 0)){ // 검색어가 없는 경우
			list = employeeDAO.getEmployeeList(company);
		} else { // 검색어가 있는 경우
			list = employeeDAO.getEmployeeList(company, searchGubun, searchInput);
		}
		
		ArrayList<SelDeptDTO> selDeptList = deptDAO.getSelDeptList(company);
		ArrayList<SelRoleDTO> selRoleList = employeeDAO.getSelRoleList(company);
		
		request.setAttribute("list", list);
		request.setAttribute("selDeptList", selDeptList);
		request.setAttribute("selRoleList", selRoleList);
		
		request.setAttribute("searchGubun", searchGubun);
		request.setAttribute("searchInput", searchInput);
		
		return new ModelAndView("admin/employee_list");
	}
	
	@RequestMapping("/EmpIDCheck.admin")
	public ModelAndView empIDCheck(@RequestParam(value="emp_id") String emp_id,
									HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		
		System.out.println("EmployeeController : empIDCheck()");
		
		boolean bReturn = false;
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("$company:"+company+",$id:"+emp_id);
		
		bReturn = employeeDAO.isEmpId(company, emp_id);
		System.out.println("$bReturn:"+bReturn);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"result\" : ");
		json.append(bReturn);
		json.append("}");

		PrintWriter out;
		out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	
	@RequestMapping("/EmpNoCheck.admin")
	public ModelAndView empNoCheck(@RequestParam(value="emp_no") String emp_no,
									HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		
		System.out.println("EmployeeController : empNoCheck()");
		
		boolean bReturn = false;
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("$company:"+company+",$emp:"+emp_no);

		bReturn = employeeDAO.isEmpNo(company, emp_no);
		System.out.println("$bReturn:"+bReturn);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"result\" : ");
		json.append(bReturn);
		json.append("}");

		PrintWriter out;
		out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	
	@RequestMapping("/EmployeeNew.admin")
	public String employeeNew(@RequestParam(value="emp_no") String emp_no,
								@RequestParam(value="emp_id") String emp_id,
								@RequestParam(value="emp_pw") String emp_pw,
								@RequestParam(value="emp_nm") String emp_nm,
								@RequestParam(value="dept_no") String dept_no,
								@RequestParam(value="emp_role_cd") String emp_role_cd,
								@RequestParam(value="emp_phone") String emp_phone,
								@RequestParam(value="emp_email") String emp_email,
								@RequestParam(value="emp_addr") String emp_addr,
								HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeNew()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session"; // 리턴이 String일때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("----company :" + company);
		System.out.println("----emp_no :" + emp_no);
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		
		employeeDto.setComp_cd(company);			//	회사 코드
		employeeDto.setEmp_no(emp_no);				//	사번
		employeeDto.setEmp_id(emp_id);				//	아이디
		employeeDto.setEmp_pw(emp_pw);				//	비밀번호
		employeeDto.setEmp_nm(emp_nm);				//	이름
		employeeDto.setDept_no(dept_no);			//	부서코드
		employeeDto.setEmp_role_cd(emp_role_cd);	//	직급코드
		employeeDto.setEmp_phone(emp_phone);		//	전화번호
		employeeDto.setEmp_email(emp_email);		//	이메일
		employeeDto.setEmp_addr(emp_addr);			//	주소
		employeeDto.setInsert_id(loginId);

		int rtn = employeeDAO.insertEmployee(employeeDto);
		
		if (rtn > 0) {
			System.out.println("employeeDao.insertEmployee 성공");
		} else {
			System.out.println("employeeDao.insertEmployee 실패");
		}
		
		return "redirect:/EmployeeInfo.admin";
	}
	
	@RequestMapping("/EmployeeSave.admin")
	public String employeeSave(@RequestParam(value="emp_no") String emp_no,
									@RequestParam(value="emp_id") String emp_id,
									@RequestParam(value="emp_pw") String emp_pw,
									@RequestParam(value="emp_nm") String emp_nm,
									@RequestParam(value="dept_no") String dept_no,
									@RequestParam(value="emp_role_cd") String emp_role_cd,
									@RequestParam(value="emp_phone") String emp_phone,
									@RequestParam(value="emp_email") String emp_email,
									@RequestParam(value="emp_addr") String emp_addr,
									HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeSave()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session"; // 리턴이 String일때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		EmployeeDTO employeeDto = new EmployeeDTO();
		
		employeeDto.setComp_cd(company);			//	회사 코드
		employeeDto.setEmp_no(emp_no);				//	사번
		employeeDto.setEmp_id(emp_id);				//	아이디
		employeeDto.setEmp_pw(emp_pw);				//	비밀번호
		employeeDto.setEmp_nm(emp_nm);				//	이름
		employeeDto.setDept_no(dept_no);			//	부서코드
		employeeDto.setEmp_role_cd(emp_role_cd);	//	직급코드
		employeeDto.setEmp_phone(emp_phone);		//	전화번호
		employeeDto.setEmp_email(emp_email);		//	이메일
		employeeDto.setEmp_addr(emp_addr);			//	주소
		employeeDto.setUpdate_id(loginId);

		int rtn = employeeDAO.updateEmployee(employeeDto);
		
		if (rtn > 0) {
			System.out.println("employeeDao.updateEmployee 성공");
		} else {
			System.out.println("employeeDao.updateEmployee 실패");
		}
		
		if(request.getParameter("defaultRow") != null) {
			request.setAttribute("defaultRow", request.getParameter("defaultRow"));
		}
		
		return "redirect:/EmployeeInfo.admin";
	}
	
	@RequestMapping("/EmployeeDelete.admin")
	public String employeeDelete(HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeDelete()");
		
		int			listCount = 0;
		String[]	emp_no = null;		// 사번
		String[]	check_yn = null;	// 삭제 여부
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session"; // 리턴이 String일때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		if(request.getParameter("listCount") != null) {
			listCount = Integer.parseInt(request.getParameter("listCount"));
		}
		
		if(listCount > 0) {
			emp_no = new String[listCount];
			check_yn = new String[listCount];
			for(int i = 0; i < listCount; i++){
				if(request.getParameter("emp_no" + i) != null){
					emp_no[i] = request.getParameter("emp_no" + i);		
				}
				if(request.getParameter("check_yn" + i) != null) {
					check_yn[i] = request.getParameter("check_yn" + i);	
				}
				System.out.printf("[%d] %s %s \n", i, emp_no[i], check_yn[i]);
			}
			
			int rtn = employeeDAO.deleteEmployee(company, emp_no, check_yn);
			
			if (rtn > 0) {
				System.out.println("companyDao.deleteCompany 성공");
			} else {
				System.out.println("companyDao.deleteCompany 실패");
			}
		}
		
		return "redirect:/EmployeeInfo.admin";
	}
	
	@RequestMapping("/EmployeePop.admin")
	public ModelAndView employeeListPop(@RequestParam(value="searchGubun", required=false, defaultValue="") String searchGubun,
										@RequestParam(value="searchInput", required=false, defaultValue="") String searchInput,
										HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeListPop()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_popup");     // 팝업 사용시 리턴이 ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("$company : " + company + " , searchGubun : " + searchGubun + " , searchInput : " + searchInput);
		List<EmployeeDTO> list = null;
		if (searchGubun == null || (searchGubun.equals("ALL") && searchInput.length() == 0)){ // 검색어가 없는 경우
			list = employeeDAO.getEmployeeList(company);
		} else { // 검색어가 있는 경우
			list = employeeDAO.getEmployeeList(company, searchGubun, searchInput);
		}
		
		request.setAttribute("list", list);
		
		request.setAttribute("searchGubun", searchGubun);
		request.setAttribute("searchInput", searchInput);
		
		return new ModelAndView("admin/employee_pop");
	}

	//StepPop.appr >> EmployeeStepPop.admin
	@RequestMapping("EmployeeStepPop.admin")
	public ModelAndView employeeStepPop(HttpServletRequest request, HttpSession session){
		
		System.out.println("EmployeeController : employeeStepPop()");
		
		DocDept doc = new DocDept();
		String xmlStr = "";
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			company = (String)session.getAttribute("company");
			loginId = (String)session.getAttribute("id");
			System.out.println("session company : " + company);
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_popup");     // 팝업 사용시 리턴이 ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		System.out.println("$company : " + company);

		List<DeptDTO> deptList = deptDAO.getDeptList(company);
		
		xmlStr = doc.getDeptXml(deptList);
		
		List<EmployeeDTO> empList = employeeDAO.getEmployeeList(company);
		
		request.setAttribute("xmlStr", xmlStr);
		request.setAttribute("empList", empList);
		
		return new ModelAndView("apprDoc/appr_step_pop");
	}
	
}
