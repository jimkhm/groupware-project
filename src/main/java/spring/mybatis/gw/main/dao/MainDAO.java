package spring.mybatis.gw.main.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import spring.mybatis.gw.admin.dto.CompanyDTO;
import spring.mybatis.gw.admin.dto.EmployeeDTO;
import spring.mybatis.gw.admin.dto.SelDTO;
import spring.mybatis.gw.main.dto.MainDTO;
import spring.mybatis.gw.security.util.SecurityUtil;

public class MainDAO {
	
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	// 회사명, 회사코드
	public ArrayList<SelDTO> getCompanyList() {
		
		ArrayList<SelDTO> selList = new ArrayList<SelDTO>();
		
		List<SelDTO>list = sqlSession.selectList("selCompanyList");
		
		
		for(SelDTO sel : list){
			SelDTO selDto = new SelDTO();
			
			selDto.setCd(sel.getCd());
			selDto.setNm(sel.getNm());
			
			selList.add(selDto);
		}
		
		return selList;
	}
	
	// 회사명 (타이틀)
	public CompanyDTO getCompnayNm(String company){

		CompanyDTO companyDto = sqlSession.selectOne("selectCompany", company);
		
		return companyDto;
	}

	// 사원 로그인 체크
	public boolean empCheck(String company, String id, String pw) {
		boolean bReturn = false;
		String selectedPw = null;
		
		MainDTO main = new MainDTO();
		main.setComp_cd(company);
		main.setEmp_id(id);
		
		String sPw = securityUtil.encryptSHA256(pw);
		
		main.setEmp_pw(sPw);
		
		selectedPw = sqlSession.selectOne("selectEmpChk", main);
		
		if(selectedPw!=null && !selectedPw.equals("")){ // 찾음
			if(securityUtil.isStringEqual(pw, selectedPw)){ // 같음
				bReturn = true;
			} else { // 다름
				bReturn = false;
			}
		} else { // 없다
			bReturn = false;
		}
		
		return bReturn;
	}
	
	// 관리자 로그인 체크
	public boolean adminCheck(String company, String id, String pw) {
		boolean cReturn = false;
		String selectedPw = null;
		
		CompanyDTO companyDto = new CompanyDTO();
		
		companyDto.setComp_cd(company);
		companyDto.setAdmin_id(id);
		
		String sPw = securityUtil.encryptSHA256(pw);
		
		companyDto.setAdmin_pw(sPw);
		
		selectedPw = sqlSession.selectOne("selectAdminChk", companyDto);
		
		if(selectedPw != null && !selectedPw.equals("")){ //찾음
			if(securityUtil.isStringEqual(pw, selectedPw)){
				cReturn = true;
			}else { // 다음
				cReturn = false;
			}
		}else { // 없다.
			cReturn = false;
		}
		
		return cReturn;
	}
	
	// 사원번호, 로그인이름, 부서번호, 부서명
	public EmployeeDTO getEmployee(String company, String id){
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(company);
		employee.setEmp_id(id);
		
		employee = sqlSession.selectOne("selectEmployeeInfo", employee);
		
		employee.setEmp_no(employee.getEmp_no());
		employee.setEmp_nm(employee.getEmp_nm());
		employee.setDept_no(employee.getDept_no());
		employee.setDept_nm(employee.getDept_nm());
		
		return employee;
	}
	
	
	// 사원 PW 체크
	public boolean isEmpPw(String company, String id, String pw) {
		boolean rOK = false;
		
		MainDTO mainDto = new MainDTO();		
		mainDto.setComp_cd(company);
		mainDto.setEmp_id(id);
		
		mainDto = sqlSession.selectOne("selectEmpPwChk", mainDto);
		
		if(securityUtil.isStringEqual(pw, mainDto.getEmp_pw())){			
			rOK = true; // 이미존재(기존비번이다.)
		}else{
			rOK = false; 
		}
		return rOK;
	}
	
	// 관리자 PW 체크
	public boolean isAdminPw(String company, String id, String pw){
		boolean rOK = false;
		
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setComp_cd(company);
		companyDto.setAdmin_id(id);
		
		companyDto = sqlSession.selectOne("selectAdminPwChk", companyDto);
		
		if(securityUtil.isStringEqual(pw, companyDto.getAdmin_pw())){
			rOK = true; // 이미 존재(기존비번)
		}else {
			rOK = false;
		}
		return rOK;
	}
	
	// 비밀번호 수정
	public int pwUpdate(MainDTO mainDto){
		boolean changePassword = false;
		String sPw = null;
		int ret= 0;
		
		if(null != mainDto.getEmp_pw() && !mainDto.getEmp_pw().equals("")){
			sPw = securityUtil.encryptSHA256(mainDto.getEmp_pw());
			mainDto.setEmp_pw(sPw);
			changePassword = true;
		}
		
		if(changePassword){
			ret = sqlSession.update("updateEmployeeWithPw", mainDto);
		} else {
			ret = sqlSession.update("updateEmployee", mainDto);
		}	
		return ret;
	}
	
	// 정보수정
	public int infoUpdate(MainDTO mainDto){
		
		int ret = sqlSession.update("updateInfoChange", mainDto);
		
		return ret;
	}
}	

