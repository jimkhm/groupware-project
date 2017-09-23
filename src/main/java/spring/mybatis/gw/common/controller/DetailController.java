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

import spring.mybatis.gw.common.dao.CodeDetailDAO;
import spring.mybatis.gw.common.dto.CodeDetailDTO;

@Controller
public class DetailController {
	@Autowired
	private CodeDetailDAO detailDAO;
	
	@RequestMapping("/CodeDetail.cd")
	public ModelAndView CodeMasterList(
			@RequestParam(value="val") String cd_m,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		System.out.println("CodeDetail.cd");
		
		ArrayList<CodeDetailDTO> detailList = null;
		String comp_cd = (String) session.getAttribute("company");
		detailList = detailDAO.getCodeDetailList(comp_cd, cd_m);
				
		request.setAttribute("masterList", detailList);
		
		StringBuffer json = new StringBuffer();
		json.append("[");
		if(detailList.size() > 0){
			for(int i = 0 ; i < detailList.size() ; i++){
				/*if(detailList.get(i))*/
			json.append("{\"comp_cd\" : \""+detailList.get(i).getComp_cd()+"\", ");
			json.append("\"cd_m\" : \""+detailList.get(i).getCd_m()+"\", ");
			json.append("\"cd_d\" : \""+detailList.get(i).getCd_d()+"\", ");
			json.append("\"cd_d_nm\" : \""+detailList.get(i).getCd_d_nm()+"\", ");
			json.append("\"q_ord\" : \""+detailList.get(i).getQ_ord()+"\", ");
			json.append("\"remark\" : \""+detailList.get(i).getRemark()+"\", ");
			json.append("\"insert_id\" : \""+detailList.get(i).getInsert_id()+"\", ");
			json.append("\"insert_dt\" : \""+detailList.get(i).getInsert_dt()+"\", ");
			json.append("\"update_id\" : \""+detailList.get(i).getUpdate_id()+"\", ");
			json.append("\"update_dt\" : \""+detailList.get(i).getUpdate_dt()+"\"}");
				if(i < detailList.size()-1){
					json.append(", ");
				}
			}
		}else{
			json.append("{\"comp_cd\" : \""+comp_cd+"\", ");
			json.append("\"cd_m\" : \""+cd_m+"\", ");
			json.append("\"result\" : \"null\"}");
		}
		json.append("]");
		
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
	
	@RequestMapping("/insert_Icode.cd")
	public ModelAndView CodeDetailInsert(
			@RequestParam(value="cd_m") String cd_m,
			@RequestParam(value="cd_d") String cd_d,
			@RequestParam(value="cd_d_nm") String cd_d_nm,
			@RequestParam(value="q_ord") String q_ord,
			@RequestParam(value="remark") String remark,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		
		CodeDetailDTO detailInsert = new CodeDetailDTO();
		detailInsert.setComp_cd((String) session.getAttribute("company"));
		detailInsert.setCd_m(cd_m);
		detailInsert.setCd_d(cd_d);
		detailInsert.setCd_d_nm(cd_d_nm);
		detailInsert.setQ_ord(Integer.parseInt(q_ord));
		detailInsert.setRemark(remark);
		detailInsert.setInsert_id((String)session.getAttribute("id"));
		detailInsert.setUpdate_id((String)session.getAttribute("id"));
		detailDAO.insertCodeDetail(detailInsert);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"cd_m\" : \""+detailInsert.getCd_m()+"\", ");
		json.append("\"cd_d\" : \""+detailInsert.getCd_d()+"\", ");
		json.append("\"cd_d_nm\" : \""+detailInsert.getCd_d_nm()+"\", ");
		json.append("\"q_ord\" : \""+detailInsert.getQ_ord()+"\", ");
		json.append("\"remark\" : \""+detailInsert.getRemark()+"\", ");
		json.append("\"result\" : \"ok\" ");
		json.append("}");
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
	@RequestMapping("/update_Dcode.cd")
	public ModelAndView CodeDetailUpdate(
			@RequestParam(value="originCd_m") String ocd_m,
			@RequestParam(value="originCd_d") String ocd_d,
			@RequestParam(value="upCd_m") String upCd_m,
			@RequestParam(value="upCd_d") String upCd_d,
			@RequestParam(value="upCd_d_nm") String upCd_d_nm,
			@RequestParam(value="upQ_ord") String upQ_ord,
			@RequestParam(value="upRemark") String upRemark,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		
		CodeDetailDTO detailUpdate = new CodeDetailDTO();
		detailUpdate.setComp_cd((String) session.getAttribute("company"));
		detailUpdate.setCd_m(upCd_m);
		detailUpdate.setCd_d(upCd_d);
		detailUpdate.setCd_d_nm(upCd_d_nm);
		detailUpdate.setQ_ord(Integer.parseInt(upQ_ord));
		detailUpdate.setRemark(upRemark);
		detailUpdate.setUpdate_id((String)session.getAttribute("id"));
		detailDAO.updateCodeMaster(detailUpdate, ocd_m, ocd_d);
		
		//리스트 수정해줄 데이터 셋팅	
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"cd_m\" : \""+detailUpdate.getCd_m()+"\", ");
		json.append("\"cd_d\" : \""+detailUpdate.getCd_d()+"\", ");
		json.append("\"cd_d_nm\" : \""+detailUpdate.getCd_d_nm()+"\", ");
		json.append("\"q_ord\" : \""+detailUpdate.getQ_ord()+"\", ");
		json.append("\"remark\" : \""+detailUpdate.getRemark()+"\", ");
		json.append("\"result\" : \"ok\" ");
		json.append("}");
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	@RequestMapping("/delete_Dcode.cd")
	public ModelAndView CodeDetailDelete(
			@RequestParam(value="delCd_m") String cd_m,
			@RequestParam(value="delCd_d") String cd_d,
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		String comp_cd = (String) session.getAttribute("company");
		detailDAO.deleteCodeDetail(comp_cd, cd_m, cd_d);
		
		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"result\" : \"ok\" ");
		json.append("}");
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		
		return null;
	}
	@RequestMapping("/Check_Dcode.cd")
	public ModelAndView Check_Mcode(
			@RequestParam(value="i_cd_m") String cd_m,
			/*@RequestParam(value="i_cd_d") String cd_d,
			@RequestParam(value="i_q_ord") String q_ord,*/
			HttpServletRequest request,  HttpServletResponse response, HttpSession session) throws IOException{
		String company = (String)session.getAttribute("company");
		String cd_d = request.getParameter("i_cd_d");
		String q_ord = request.getParameter("i_q_ord");
		boolean result;
		StringBuffer json = new StringBuffer();
		
		if((cd_d != null) && (q_ord == null)){
			result = detailDAO.getCd_dName(company, cd_m, cd_d);
			json.append("{");
			json.append("\"result\" : ");
			json.append(result);
			json.append("}");
		}else if((cd_d == null) && (q_ord != null)){
			result = detailDAO.getQ_ord(company, cd_m, q_ord);
			json.append("{");
			json.append("\"result\" : ");
			json.append(result);
			json.append("}");
		}
		
		PrintWriter out;
		out = response.getWriter();
		out.print(json.toString());
		out.flush();
		out.close();
		return null;
	}
}
