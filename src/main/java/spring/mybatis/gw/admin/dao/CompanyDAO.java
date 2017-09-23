package spring.mybatis.gw.admin.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import spring.mybatis.gw.admin.dto.CompanyDTO;
import spring.mybatis.gw.security.util.SecurityUtil;

public class CompanyDAO {
	
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public String getCompanyCd(){
		String sResult = "";
		int ret = 0;
		
		HashMap <String, Object> param = new HashMap<String, Object>();
		
		param.put("sq_cd", "COMP_CD");
		param.put("sq_nm", "C");
		param.put("ret", 0);
		param.put("dbcode", 0);
		param.put("dberrtext", "");
		
		sqlSession.selectOne("selectSeqNo", param);
		
		ret = (Integer) param.get("ret");
		
//		System.out.println("getCompanyCd() ret : " + ret);
		
		if (ret > 0){
			sResult = "C" + String.format("%03d", ret);				
		}
		
		return sResult;
	}
	
	public int insertCompany(CompanyDTO company){
		
		String pw = securityUtil.encryptSHA256(company.getAdmin_pw()); // 관리자 비밀번호
		company.setAdmin_pw(pw);
		
		int ret = sqlSession.insert("insertCompany", company);
		
		return ret;
	}
	
	public int updateCompany(CompanyDTO company) {
		int ret = 0;
		boolean changPassword = false;
		String pw = null;
		
		if(null != company.getAdmin_pw() && !company.getAdmin_pw().equals("")){
			pw = securityUtil.encryptSHA256(company.getAdmin_pw()); // 관리자 비밀번호
			company.setAdmin_pw(pw);
			changPassword = true;
		}
		
		if(changPassword) {
			ret = sqlSession.update("updateCompanyWithPW", company);
		} else {
			ret = sqlSession.update("updateCompany", company);
		}
		
		return ret;
	}
	
	public int deleteCompany(String comp_cd) {
		int ret = sqlSession.delete("deleteCompany", comp_cd);
		return ret;
	}
	
	public int deleteCompany(CompanyDTO company) {
		int ret = sqlSession.delete("deleteCompany", company.getComp_cd());
		return ret;
	}
	
	// ID 체크
	public boolean isAdminId(String comp_cd, String admin_id){
		boolean rOK = false;
		
		CompanyDTO company = new CompanyDTO();
		company.setComp_cd(comp_cd);
		company.setAdmin_id(admin_id);
		
		int ret = sqlSession.selectOne("selectCountId", company);
		
		if(ret > 0){
			rOK = false; // 이미 존재함
		} else {
			rOK = true; // OK
		}
		
		return rOK;
	}
	
	public CompanyDTO getCompany(String comp_cd){
		CompanyDTO company = sqlSession.selectOne("selectCompany", comp_cd);
		
		company.setAdmin_pw(""); // 관리자 비밀번호
		
		return company;
	}
	
	public List<CompanyDTO> getCompanyList() {		
		List<CompanyDTO> list = sqlSession.selectList("selectAllCompany");
		
		for(CompanyDTO company : list){
			company.setAdmin_pw(""); // 관리자 비밀번호
		}
		
		if(list.size()==0){
			CompanyDTO company = new CompanyDTO();
			list.add(company);
		}
		
		return list;
	}

}
