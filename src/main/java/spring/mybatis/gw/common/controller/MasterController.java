package spring.mybatis.gw.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.mybatis.gw.common.dao.CodeMasterDAO;
import spring.mybatis.gw.common.dto.CodeMasterDTO;

@Controller
public class MasterController {
	@Autowired
	private CodeMasterDAO masterDAO;
	
	@RequestMapping("/CodeMgr.cd")
	public ModelAndView CodeMasterList(HttpServletRequest request, HttpSession session){
		System.out.println("CodeMgr.cd");
		
		ArrayList<CodeMasterDTO> masterList = null;
		String comp_cd = (String) session.getAttribute("company");
		masterList = masterDAO.getCodeMasterList(comp_cd);
				
		request.setAttribute("masterList", masterList);
		
		return new ModelAndView("/common/codeMain");
	}
	
	@RequestMapping("/insert_Mcode.cd")
	public ModelAndView CodeMasterInsert(
			@RequestParam(value="cd_m") String cd_m,
			@RequestParam(value="cd_nm") String cd_nm,
			@RequestParam(value="remark") String remark,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		
		CodeMasterDTO masterInsert = new CodeMasterDTO();
		masterInsert.setComp_cd((String) session.getAttribute("company"));
		masterInsert.setCd_m(cd_m);
		masterInsert.setCd_nm(cd_nm);
		masterInsert.setRemark(remark);
		masterInsert.setInsert_id((String)session.getAttribute("id"));
		masterInsert.setUpdate_id((String)session.getAttribute("id"));
		masterDAO.insertCodeMaster(masterInsert);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"cd_m\" : \""+masterInsert.getCd_m()+"\", ");
		json.append("\"cd_nm\" : \""+masterInsert.getCd_nm()+"\", ");
		json.append("\"remark\" : \""+masterInsert.getRemark()+"\", ");
		json.append("\"result\" : \"ok\" ");
		json.append("}");
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
	@RequestMapping("/update_Mcode.cd")
	public ModelAndView CodeMasterUpdate(
			@RequestParam(value="ocd_m") String ocd_m,
			@RequestParam(value="cd_m") String cd_m,
			@RequestParam(value="cd_nm") String cd_nm,
			@RequestParam(value="remark") String remark,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		
		CodeMasterDTO masterInsert = new CodeMasterDTO();
		masterInsert.setComp_cd((String) session.getAttribute("company"));
		masterInsert.setCd_m(cd_m);
		masterInsert.setCd_nm(cd_nm);
		masterInsert.setRemark(remark);
		masterInsert.setUpdate_id((String)session.getAttribute("id"));
		masterDAO.updateCodeMaster(masterInsert, ocd_m);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"cd_m\" : \""+masterInsert.getCd_m()+"\", ");
		json.append("\"cd_nm\" : \""+masterInsert.getCd_nm()+"\", ");
		json.append("\"remark\" : \""+masterInsert.getRemark()+"\", ");
		json.append("\"result\" : \"ok\" ");
		json.append("}");
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
	@RequestMapping("/delete_Mcode.cd")
	public ModelAndView CodeMasterDelete(
			@RequestParam(value="cd_m") String cd_m,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException{
		String comp_cd = (String) session.getAttribute("company");
		masterDAO.deleteCodeMaster(comp_cd, cd_m);
		response.sendRedirect("CodeMgr.cd");
		return null;
	}
	@RequestMapping("/Check_Mcode.cd")
	public ModelAndView Check_Mcode(
			@RequestParam(value="i_cd_m") String cd_m,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		String company = (String)session.getAttribute("company");
		boolean result =masterDAO.getCd_mName(company, cd_m);
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"result\" : ");
		json.append(result);
		json.append("}");
		
		PrintWriter out;
		out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
}
