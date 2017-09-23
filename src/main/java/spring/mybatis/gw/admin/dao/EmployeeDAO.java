package spring.mybatis.gw.admin.dao;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import spring.mybatis.gw.admin.dto.EmployeeDTO;
import spring.mybatis.gw.admin.dto.SelDTO;
import spring.mybatis.gw.admin.dto.SelRoleDTO;
import spring.mybatis.gw.security.util.SecurityUtil;

public class EmployeeDAO {
	
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insertEmployee(EmployeeDTO employee) {
		
		String pw = securityUtil.encryptSHA256(employee.getEmp_pw()); // 비밀번호
		employee.setEmp_pw(pw);
	
		System.out.println("COMP_CD : " + employee.getComp_cd());
		System.out.println("EMP_NO : " + employee.getEmp_no());
		System.out.println("EMP_ID : " + employee.getEmp_id());
		System.out.println("EMP_PW : " + employee.getEmp_pw());
		System.out.println("EMP_NM : " + employee.getEmp_nm());
		
		int rtn = sqlSession.insert("insertEmployee", employee);
		
		return rtn;
	}
	
	public int updateEmployee(EmployeeDTO employee) {
		int rtn = 0;
		boolean changPassword = false;
		String pw = null;
		
		if(null != employee.getEmp_pw() && !employee.getEmp_pw().equals("")){
			pw = securityUtil.encryptSHA256(employee.getEmp_pw()); // 비밀번호
			employee.setEmp_pw(pw);
			changPassword = true;
		}
		
		if(changPassword) {
			rtn = sqlSession.update("updateEmployeeWithPW", employee);
		} else {
			rtn = sqlSession.update("updateEmployee", employee);
		}
		
		return rtn;
	}
	
	public int deleteEmployee(String comp_cd, String emp_no) {
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		
		int rtn = sqlSession.delete("deleteEmployee", employee);
		
		return rtn;
	}
	
	public int deleteEmployee(String comp_cd, String[] emp_no, String[] check_yn) {
		int rtn = -1;
		int len = emp_no.length;
		
		for(int i = 0; i < len; i++) {
			if(check_yn[i] != null && check_yn[i].equals("Y")){
				rtn = deleteEmployee(comp_cd, emp_no[i]);
				if(rtn <= 0){
					break;
				}
			}
		}
		
		return rtn;
	}
	
	public int deleteEmployee(EmployeeDTO employee) {
		int rtn = sqlSession.delete("deleteEmployee", employee);
		
		return rtn;
	}
	
	// 사번 체크
	public boolean isEmpNo(String comp_cd, String emp_no){
		boolean rOK = false;
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		
		int cnt = sqlSession.selectOne("selectCountEmpNo", employee);
		if(cnt > 0) {
			rOK = false; // 이미 존재함
		} else {
			rOK = true; // OK
		}
		return rOK;
	}
	
	// ID 체크
	public boolean isEmpId(String comp_cd, String emp_id){
		boolean rOK = false;
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_id(emp_id);

		int cnt = sqlSession.selectOne("selectCountEmpId", employee);
		if(cnt > 0) {
			rOK = false; // 이미 존재함
		} else {
			rOK = true; // OK
		}
		
		return rOK;		
	}
	
	// 사원 정보 테이블에 사원 수 return
	public int getEmployeeCount(String comp_cd){
		int rtn = sqlSession.selectOne("selectCountEmployee", comp_cd);
		
		return rtn;
	}
	
	// 로그인 화면에서 사용함!!
	public EmployeeDTO getEmployee(String comp_cd, String emp_id){
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_id(emp_id);
		
		employee = sqlSession.selectOne("selectEmployeeInfo", employee);
		
		return employee;
	}

	public List<EmployeeDTO> getEmployeeList(String comp_cd){
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		
		List<EmployeeDTO> listSelect = sqlSession.selectList("selectAllEmployee", comp_cd);
		
		for(EmployeeDTO employeeDTO : listSelect){
			employeeDTO.setEmp_pw("");
			list.add(employeeDTO);
		}
		
		return list;
	}

	public ArrayList<EmployeeDTO> getEmployeeList(String comp_cd, String searchGubun, String searchInput){
		ArrayList<EmployeeDTO> list = new ArrayList<EmployeeDTO>();
		List<EmployeeDTO> listSelect = null;
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setSearch_input(searchInput);
		
		switch (searchGubun) {
			case "empName":
				listSelect = sqlSession.selectList("selectEmployeeSearchEmpName", employee);
				break;
			case "deptName":
				listSelect = sqlSession.selectList("selectEmployeeSearchDeptName", employee);
				break;
			case "roleName":
				listSelect = sqlSession.selectList("selectEmployeeSearchRoleName", employee);
				break;
			default:
				listSelect = sqlSession.selectList("selectEmployeeSearchAll", employee);
				break;
		}
		
		for(EmployeeDTO employeeDTO : listSelect){
			employeeDTO.setEmp_pw("");
			list.add(employeeDTO);
		}
		
		return list;
	}
	
	public ArrayList<SelRoleDTO> getSelRoleList(String comp_cd) {
		ArrayList<SelRoleDTO> selRoleList = new ArrayList<SelRoleDTO>();		
		
		List<SelDTO> list = sqlSession.selectList("selRoleList", comp_cd);
		
		for(SelDTO sel : list){
//			System.out.printf("%s %s \n", sel.getCd(), sel.getNm());
			SelRoleDTO selRole = new SelRoleDTO();
			selRole.setCd(sel.getCd());
			selRole.setNm(sel.getNm());
			selRoleList.add(selRole);
		}
		
		return selRoleList;
	}

	public Map<String, Object> getEmployeeImg(String comp_cd, String emp_no) {
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		
		Map<String, Object> map = sqlSession.selectOne("selectEmployeeImg", employee);
		
		return map;
	}

	public String getEmployeeImgFileRNM(String comp_cd, String emp_no) {
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		
		String img_file_rnm = sqlSession.selectOne("selectEmployeeImgFileRNM", employee);
		
		return img_file_rnm;
	}
	
	public boolean isEmployeeImgFileRNM(String comp_cd, String emp_no, String img_file_rnm) {
		boolean bReturn = false;
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		employee.setImg_file_rnm(img_file_rnm);
		
		int count = sqlSession.selectOne("selectEmployeeImgFileRNMCount", employee);
		if(count > 0){
			bReturn = true; // 존재한다!!
		}
		
		return bReturn;
	}
	
	public int setEmployeeImg(Map<String, Object> map) {
		
		int rtn = sqlSession.update("updateEmployeeImg", map);
		
		return rtn;
	}
	
	public int setEmployeeImgEmpty(String comp_cd, String emp_no) {
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setComp_cd(comp_cd);
		employee.setEmp_no(emp_no);
		
		int rtn = sqlSession.update("updateEmployeeImgEmpty", employee);
		
		return rtn;
	}
	
}
