package spring.mybatis.gw.admin.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.admin.dto.CompanyDTO;
import spring.mybatis.gw.admin.dto.DeptDTO;
import spring.mybatis.gw.admin.dto.SelDTO;
import spring.mybatis.gw.admin.dto.SelDeptDTO;

public class DeptDAO {
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public String getDeptNo(){
		String sResult = "";
		int ret = 0;
		
		HashMap <String, Object> param = new HashMap<String, Object>();
		
		param.put("sq_cd", "DEPT_NO");
		param.put("sq_nm", "D");
		param.put("ret", 0);
		param.put("dbcode", 0);
		param.put("dberrtext", "");
		
		sqlSession.selectOne("selectSeqNo", param);
		
		ret = (Integer) param.get("ret");
		
		System.out.println("getDeptNo() ret : " + ret);
		
		if (ret > 0){
			sResult = "D" + String.format("%03d", ret);				
		}
		
		return sResult;
	}
	
	public int insertDept(DeptDTO dept) {
		
		int ret = sqlSession.insert("insertDept", dept);
		
		return ret;
	}
	
	public int updateDeptTree(DeptDTO dept) {
		
		int ret = sqlSession.update("updateDeptTree", dept);
		
		return ret;
	}
	
	public int updateDept(DeptDTO dept) {
		
		int ret = sqlSession.update("updateDept", dept);
		
		return ret;
	}
	
	public int deleteSubDept(String company, String deptNo) {
		
		DeptDTO dept = new DeptDTO();
		
		dept.setComp_cd(company);
		dept.setM_dept_no(deptNo);
		
		int ret = sqlSession.delete("deleteSubDept", dept);
		
		return ret;
	}
	
	public int deleteDept(String comp_cd, String dept_no) {
		
		DeptDTO dept = new DeptDTO();
		
		dept.setComp_cd(comp_cd);
		dept.setDept_no(dept_no);
		
		int ret = sqlSession.delete("deleteDept", dept);
		
		return ret;
	}
	
	public int deleteDept(String comp_cd, String[] dept_no) {
		int ret = -1;
		
		for(String dept : dept_no){
			ret = deleteDept(comp_cd, dept);
			if(ret <= 0){
				break;
			}
		}
		
		return ret;
	}
	
	public int deleteDept(DeptDTO dept) {
		
		int ret = sqlSession.delete("deleteDept", dept);
		
		return ret;
	}
	
	// 부서 코드 체크
	public boolean isDeptNo(String comp_cd, String dept_no){
		boolean rOK = false;
		
		DeptDTO dept = new DeptDTO();
		
		dept.setComp_cd(comp_cd);
		dept.setDept_no(dept_no);
		
		int ret = sqlSession.selectOne("selectCountDept", dept);
		
		if(ret > 0){
			rOK = false; // 이미 존재함
		} else {
			rOK = true; // OK
		}
		
		return rOK;
	}
	
	public List<DeptDTO> getDeptList(String comp_cd){
		
		List<DeptDTO> list = sqlSession.selectList("selectAllDept", comp_cd);		

		return list;
	}
	
	public ArrayList<SelDeptDTO> getSelDeptList(String comp_cd){
		
		ArrayList<SelDeptDTO> selDeptList = new ArrayList<SelDeptDTO>();		
		
		List<SelDTO> list = sqlSession.selectList("selDeptList", comp_cd);
		
		for(SelDTO sel : list){
//			System.out.printf("%s %s \n", sel.getCd(), sel.getNm());
			SelDeptDTO selDept = new SelDeptDTO();
			selDept.setCd(sel.getCd());
			selDept.setNm(sel.getNm());
			selDeptList.add(selDept);
		}
		
		return selDeptList;
	}
	
}
