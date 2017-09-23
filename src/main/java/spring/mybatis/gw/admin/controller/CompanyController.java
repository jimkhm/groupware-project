package spring.mybatis.gw.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.admin.dao.CompanyDAO;
import spring.mybatis.gw.admin.dto.CompanyDTO;

@Controller
public class CompanyController {

	@Autowired
	private CompanyDAO companyDAO;
	
	@RequestMapping("/CompanyInfo.admin")
	public ModelAndView companyList(HttpServletRequest request, HttpSession session){
		
		System.out.println("CompanyController : companyList()");
		
//		request.setCharacterEncoding("utf-8");
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			;
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return new ModelAndView("mainLogin/out_session"); // ModelAndView 일 때
		}
		
		//Map<String, Object> model = new HashMap<String, Object>();
		
		List<CompanyDTO> list = companyDAO.getCompanyList();
		
//		for(CompanyDTO company : list){
//			System.out.printf("company : %s ", 
//					company.getComp_cd());
//		}
		
		//model.put("list", list);
		//mav.addObject("list", list); //request 영역에 해당 데이터를 저장해줌
		
		request.setAttribute("list", list);
		
		//return new ModelAndView("company_list", model);
		return new ModelAndView("admin/company_list");
	}
	
	@RequestMapping("/CompanyIDCheck.admin")
	public ModelAndView adminIdCheck(@RequestParam(value="comp_cd") String comp_cd,
									@RequestParam(value="admin_id") String admin_id,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		boolean bReturn = false;
		
		//comp_cd = "C001";
//		System.out.println("$company:"+comp_cd+",$id:"+admin_id);
		
		bReturn = companyDAO.isAdminId(comp_cd, admin_id);
//		System.out.println("$bReturn:"+bReturn);
		
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
	
	@RequestMapping("/CompanyNew.admin")
	public String companyNew(@RequestParam(value="comp_nm") String comp_nm,
								@RequestParam(value="comp_no1") String comp_no1,
								@RequestParam(value="comp_no2") String comp_no2,
								@RequestParam(value="comp_ceo_nm") String comp_ceo_nm,
								@RequestParam(value="comp_addr") String comp_addr,
								@RequestParam(value="comp_phone") String comp_phone,
								@RequestParam(value="admin_id") String admin_id,
								@RequestParam(value="admin_phone") String admin_phone,
								@RequestParam(value="admin_pw") String admin_pw,
								HttpServletRequest request, HttpSession session){

		String	loginId = null;
		String	comp_cd = null;		// 회사 코드
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			loginId = (String)session.getAttribute("id");
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session";
		}
		
		// 데이터가 하나도 없음. -> 신규로 회사코드 가져오기		
		comp_cd = companyDAO.getCompanyCd();
		
		CompanyDTO companyDto = new CompanyDTO();
		
		companyDto.setComp_cd(comp_cd);
		companyDto.setComp_nm(comp_nm);
		companyDto.setComp_no1(comp_no1);
		companyDto.setComp_no2(comp_no2);
		companyDto.setComp_ceo_nm(comp_ceo_nm);
		companyDto.setComp_addr(comp_addr);
		companyDto.setComp_phone(comp_phone);
		companyDto.setAdmin_id(admin_id);
		companyDto.setAdmin_phone(admin_phone);
		companyDto.setAdmin_pw(admin_pw);
		companyDto.setInsert_id(loginId);

		int rtn = companyDAO.insertCompany(companyDto);
		
		if (rtn > 0) {
			System.out.println("companyNew 성공");
		} else {
			System.out.println("companyNew 실패");
		}
		
		return "redirect:/CompanyInfo.admin";
	}
	
	@RequestMapping("/CompanySave.admin")
	public String companySave(@RequestParam(value="comp_cd") String comp_cd,
			@RequestParam(value="comp_nm") String comp_nm,
			@RequestParam(value="comp_no1") String comp_no1,
			@RequestParam(value="comp_no2") String comp_no2,
			@RequestParam(value="comp_ceo_nm") String comp_ceo_nm,
			@RequestParam(value="comp_addr") String comp_addr,
			@RequestParam(value="comp_phone") String comp_phone,
			@RequestParam(value="admin_id") String admin_id,
			@RequestParam(value="admin_phone") String admin_phone,
			@RequestParam(value="admin_pw") String admin_pw,
			@RequestParam(value="defaultComp") String defaultComp,
			HttpServletRequest request, HttpSession session){
		
		String	loginId = null;
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			loginId = (String)session.getAttribute("id");
			System.out.println("session loginId : " + loginId);
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session"; // 리턴이 String일때
		}
		
		CompanyDTO companyDto = new CompanyDTO();
		
		companyDto.setComp_cd(comp_cd);
		companyDto.setComp_nm(comp_nm);
		companyDto.setComp_no1(comp_no1);
		companyDto.setComp_no2(comp_no2);
		companyDto.setComp_ceo_nm(comp_ceo_nm);
		companyDto.setComp_addr(comp_addr);
		companyDto.setComp_phone(comp_phone);
		companyDto.setAdmin_id(admin_id);
		companyDto.setAdmin_phone(admin_phone);
		companyDto.setAdmin_pw(admin_pw);
		companyDto.setInsert_id(loginId);
		
		int rtn = companyDAO.updateCompany(companyDto);
		
		if (rtn > 0) {
			System.out.println("companySave 성공");
		} else {
			System.out.println("companySave 실패");
		}
		
		request.setAttribute("defaultComp", defaultComp);
		
		return "redirect:/CompanyInfo.admin";
	}

	@RequestMapping("/CompanyDelete.admin")
	public String companyDelete(@RequestParam(value="comp_cd") String comp_cd,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		boolean dResult = false;		
		
		session = request.getSession(true); //현재 존재하는 세션값을 얻어온다.		
		if (session != null){
			;
		} else { // 세션이 없으면 로그인 페이지로 이동
			System.out.println("session null!");
			return "mainLogin/out_session"; // 리턴이 String일때
		}
		
		int rtn = companyDAO.deleteCompany(comp_cd);
		
		if (rtn > 0) {
			System.out.println("companyDelete 성공");
			dResult = true;
		} else {
			System.out.println("companyDelete 실패");
			dResult = false;
		}

//		PrintWriter out;
//		out = response.getWriter();
//		out.print(dResult);
//		out.flush();
//		out.close();
		
		return "redirect:/CompanyInfo.admin";
	}
}
