package spring.mybatis.gw.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.admin.dao.DeptDAO;
import spring.mybatis.gw.admin.dto.DeptDTO;
import spring.mybatis.gw.admin.dto.SelDeptDTO;
import spring.mybatis.gw.share.util.DocDept;

/*해야할 것들....
 * 			
1. Company
			case "/CompanyInfo.admin":
				action = new AdminCompanyListAction();
			case "/CompanyIDCheck.admin":
				action = new AdminIDCheckAction();				
			case "/CompanyNew.admin":
				action = new AdminCompanyNewAction();				
			case "/CompanySave.admin":
				action = new AdminCompanySaveAction();				
			case "/CompanyDelete.admin":
				action = new AdminCompanyDeleteAction();

2. Dept
			case "/DeptInfo.admin":
				action = new AdminDeptListAction();						
			case "/DeptSave.admin":
				action = new AdminDeptSaveAction();
			case "/DeptTree.admin": //2016.11.09. 추가
				action = new AdminDeptTreeAction();					
			case "/DeptTreeSave.admin": //2016.11.16. 추가
				action = new AdminDeptTreeSaveAction();
			case "/DeptTreeDelete.admin": /2016.11.16. 추가
				action = new AdminDeptTreeDeleteAction();
				
3. Employee
			case "/EmployeeInfo.admin":
				action = new AdminEmployeeListAction();
			case "/EmpIDCheck.admin":
				action = new EmpIDCheckAction();
			case "/EmpNoCheck.admin":
				action = new EmpNoCheckAction();
			case "/EmployeeNew.admin":
				action = new AdminEmployeeNewAction();
			case "/EmployeeSave.admin":
				action = new AdminEmployeeSaveAction();
			case "/EmployeeDelete.admin":
				action = new AdminEmployeeDeleteAction();
			case "/EmployeePop.admin":
				action = new AdminEmployeeListPopAction();

			
 * */

@Controller
public class DeptController {

	@Autowired
	private DeptDAO deptDAO;
	
	@RequestMapping("/DeptInfo.admin")
	public ModelAndView deptList(HttpServletRequest request, HttpSession session){
		
		System.out.println("DeptController : deptList()");
		
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
		
		//Map<String, Object> model = new HashMap<String, Object>();
		
		List<DeptDTO> list = deptDAO.getDeptList(company);
		
//		for(CompanyDTO company : list){
//			System.out.printf("company : %s ", 
//					company.getComp_cd());
//		}
		
		request.setAttribute("list", list);
		
		return new ModelAndView("admin/dept_list");
	}

	@RequestMapping("/DeptSave.admin")
	public String deptSave(HttpServletRequest request, HttpSession session){
		
		System.out.println("DeptController : deptSave()");
		
		String	company = null;		// 회사 코드
		String	loginId = null;
		String[]	mode = null;		// 구분
		String[]	checkYn = null;		// 삭제 여부
		String[]	dept_no = null;		// 부서 코드
		String[]	dept_nm = null;		// 부서명
		String[]	dept_manager = null;// 부서장(사번)
		String[]	m_dept_no = null;
		int		rowCount = 0;

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
		
		if(request.getParameter("rowCount") != null) {
			rowCount = Integer.parseInt(request.getParameter("rowCount"));
		}
		
		if(rowCount > 0) {
			checkYn = new String[rowCount];
			mode = new String[rowCount];
			dept_no = new String[rowCount];
			dept_nm = new String[rowCount];
			dept_manager = new String[rowCount];
			m_dept_no = new String[rowCount];
			for(int i = 0; i < rowCount; i++){
				if(request.getParameter("checkYn" + i) != null){
					checkYn[i] = request.getParameter("checkYn" + i);		
				}
				if(request.getParameter("mode" + i) != null){
					mode[i] = request.getParameter("mode" + i);		
				}
				if(request.getParameter("deptNo" + i) != null){
					dept_no[i] = request.getParameter("deptNo" + i);		
				}
				if(request.getParameter("deptNm" + i) != null) {
					dept_nm[i] = request.getParameter("deptNm" + i);	
				}
				if(request.getParameter("deptManager" + i) != null) {
					dept_manager[i] = request.getParameter("deptManager" + i);	
				}
				if(request.getParameter("mDeptNo" + i) != null) {
					m_dept_no[i] = request.getParameter("mDeptNo" + i);	
				}
				if (checkYn[i] != null && checkYn[i].equals("Y")){ // 삭제 하겠당.
					mode[i] = "delete";
				}
				System.out.printf("[%d] %s) %s) %s %s %s \n", i, checkYn[i], mode[i], dept_no[i], dept_nm[i], dept_manager[i]);
			}	
		}
		
		if(rowCount > 0) {
			// dept_list 저장 순서 삭제> update > insert 순으로 하기

			// 1) delete
			for(int i = 0; i < rowCount; i++){
				DeptDTO deptDto = new DeptDTO();
				
				deptDto.setComp_cd(company);
				deptDto.setDept_no(dept_no[i]);
				deptDto.setDept_nm(dept_nm[i]);
				deptDto.setDept_manager(dept_manager[i]);
				
				if(mode[i].equals("delete")){
					int rtn = deptDAO.deleteDept(deptDto);
					if (rtn > 0) {
						System.out.println("DeptDao."+mode[i]+"Dept 성공 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					} else {
						System.out.println("DeptDao."+mode[i]+"Dept 실패 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					}
				}		
			}
			// 2) update
			for(int i = 0; i < rowCount; i++){
				DeptDTO deptDto = new DeptDTO();
				
				deptDto.setComp_cd(company);
				deptDto.setDept_no(dept_no[i]);
				deptDto.setDept_nm(dept_nm[i]);
				deptDto.setDept_manager(dept_manager[i]);
				deptDto.setM_dept_no(m_dept_no[i]);
				deptDto.setUpdate_id(loginId);
				
				if(mode[i].equals("update")){
					int rtn = deptDAO.updateDept(deptDto);
					if (rtn > 0) {
						System.out.println("DeptDao."+mode[i]+"Dept 성공 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					} else {
						System.out.println("DeptDao."+mode[i]+"Dept 실패 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					}
				}				
			}
			// 3) insert
			for(int i = 0; i < rowCount; i++){
				DeptDTO deptDto = new DeptDTO();
				
				deptDto.setComp_cd(company);
				deptDto.setDept_no(dept_no[i]);
				deptDto.setDept_nm(dept_nm[i]);
				deptDto.setDept_manager(dept_manager[i]);
				deptDto.setM_dept_no(m_dept_no[i]);
				deptDto.setInsert_id(loginId);
				
				if(mode[i].equals("insert")){
					int rtn = deptDAO.insertDept(deptDto);
					if (rtn > 0) {
						System.out.println("DeptDao."+mode[i]+"Dept 성공 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					} else {
						System.out.println("DeptDao."+mode[i]+"Dept 실패 : (" + i + ") : " + dept_no[i] + ", "+ dept_nm[i] + ", " + dept_manager[i]);
					}
				}				
			}			
		}
		
		return "redirect:/DeptInfo.admin";
	}
	
	@RequestMapping("/DeptTree.admin")
	public ModelAndView deptTree(HttpServletRequest request, HttpSession session){
		
		System.out.println("DeptController : DeptTree()");
		
		ArrayList<SelDeptDTO> selDeptList = null;
		
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
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//company = "C001"; // TEST : 세션에서 받아오면 이 부분을 막아준다.
		
		List<DeptDTO> list = deptDAO.getDeptList(company);
		xmlStr = doc.getDeptWithManagerXml(list);		
		selDeptList = deptDAO.getSelDeptList(company);
		
		request.setAttribute("selDeptList", selDeptList);
		request.setAttribute("listCount", list.size());
		request.setAttribute("xmlStr", xmlStr);
		
		
		return new ModelAndView("admin/dept_tree");
	}
	
	@RequestMapping("/DeptTreeSave.admin")
	public String deptTreeSave(@RequestParam(value="deptNo0") String deptNo,
							@RequestParam(value="deptName0") String deptName,
							@RequestParam(value="deptManager0") String deptManager,
							@RequestParam(value="mDeptNo0") String mDeptNo,
							HttpServletRequest request, HttpSession session){
		
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
		
		System.out.println("company : " + company);		
		System.out.println("deptNo : " + deptNo);
		System.out.println("deptName : " + deptName);
		System.out.println("deptManager : " + deptManager);
		System.out.println("mDeptNo : " + mDeptNo);
		
		DeptDTO deptDto = new DeptDTO();
		deptDto.setComp_cd(company);		
		deptDto.setDept_nm(deptName);
		deptDto.setDept_manager(deptManager);
		deptDto.setM_dept_no(mDeptNo);
		
		if(deptNo.equals("NEW")){ // insert			
			// get dept no.
			deptNo = deptDAO.getDeptNo();			
			System.out.println("?deptNo?"+deptNo);
			deptDto.setDept_no(deptNo);
			deptDto.setInsert_id(loginId);
			
			int rtn = deptDAO.insertDept(deptDto);
			if (rtn > 0) {
				System.out.println("dept insert success");
			}
			
		} else { // update	
			deptDto.setDept_no(deptNo);
			deptDto.setUpdate_id(loginId);
			
			int rtn = deptDAO.updateDeptTree(deptDto);
			if (rtn > 0) {
				System.out.println("dept update success");
			}
		}
		
		return "redirect:/DeptTree.admin";
	}
		
	@RequestMapping("/DeptTreeDelete.admin")
	public ModelAndView deptTreeDelete(@RequestParam(value="deptNo0") String deptNo,
									HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		
		boolean dResult = false;
		
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
		
		System.out.println("company : " + company);		
		System.out.println("deptNo : " + deptNo);
		
		// 하위 부서 삭제
		int rtn =deptDAO.deleteSubDept(company, deptNo);
		
		if (rtn > 0) {
			System.out.println("하위 부서 삭제 성공");
		}
		
		rtn = deptDAO.deleteDept(company, deptNo);
		
		if (rtn > 0) {
			System.out.println("부서 삭제 성공");
			dResult = true;
		} else {
			dResult = false;
		}
		
		PrintWriter out;
		out = response.getWriter();
		out.print(dResult);
		out.flush();
		out.close();
		
		return null;
	}
	
}
