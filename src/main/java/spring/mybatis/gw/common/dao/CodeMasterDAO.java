package spring.mybatis.gw.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.common.dto.CodeMasterDTO;

public class CodeMasterDAO {
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public ArrayList<CodeMasterDTO> getCodeMasterList(String comp_cd){
		ArrayList<CodeMasterDTO> masterList = new ArrayList<CodeMasterDTO>();
		List<CodeMasterDTO>list = sqlSession.selectList("selectMasterList", comp_cd);
			
			for(CodeMasterDTO dto : list){
				CodeMasterDTO mdto = new CodeMasterDTO();
				mdto.setComp_cd(dto.getComp_cd());
				mdto.setCd_m(dto.getCd_m());
				mdto.setCd_nm(dto.getCd_nm());
				mdto.setRemark(dto.getRemark());
				mdto.setInsert_id(dto.getInsert_id());
				mdto.setInsert_dt(dto.getInsert_dt());
				mdto.setUpdate_id(dto.getUpdate_id());
				mdto.setUpdate_dt(dto.getUpdate_dt());
				masterList.add(mdto);
			}
			return masterList;
	}
	
	public void insertCodeMaster(CodeMasterDTO masterInsert){
		int result = sqlSession.insert("insertCodeMaster", masterInsert);
		if (result > 0) {
			System.out.println("insertCodeMaster 성공");
		} else {
			System.out.println("insertCodeMaster 실패");
		}
	}

	public void deleteCodeMaster(String comp_cd, String cd_m) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", comp_cd);
		map.put("cd_m", cd_m);
		int result = sqlSession.delete("deleteCodeMaster", map);
		int result2 = sqlSession.delete("deleteCodeMD", map);
		if(result != 0){
			System.out.println("delete 성공");
		}
		if(result2 != 0){
			System.out.println("delete2 성공");
		}
	}
	public void updateCodeMaster(CodeMasterDTO masterUpdate, String ocd_m){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("masterUpdate", masterUpdate);
		map.put("ocd_m", ocd_m);
		int result = sqlSession.update("updateCodeMaster", map);
		int result2 = sqlSession.update("updateCodeMD", map);
		if(result != 0 && result2 != 0){
			System.out.println("update 성공");
		}
	}

	public boolean getCd_mName(String company, String cd_m) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", company);
		map.put("cd_m", cd_m);
		int result = sqlSession.selectOne("checkCd_m", map);
		if(result != 0){
			return false;
		}else{
			return true;
		}	
	}
}
