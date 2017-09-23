package spring.mybatis.gw.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.common.dto.CodeDetailDTO;

public class CodeDetailDAO {
	private SqlSessionTemplate sqlSession;
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public ArrayList<CodeDetailDTO> getCodeDetailList(String comp_cd, String cd_m) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", comp_cd);
		map.put("cd_m", cd_m);
		ArrayList<CodeDetailDTO> detailList = new ArrayList<CodeDetailDTO>();
		List<CodeDetailDTO>list = sqlSession.selectList("selectDetailList", map);
			
			for(CodeDetailDTO dto : list){
				CodeDetailDTO ddto = new CodeDetailDTO();
				ddto.setComp_cd(dto.getComp_cd());
				ddto.setCd_m(dto.getCd_m());
				ddto.setCd_d(dto.getCd_d());
				ddto.setCd_d_nm(dto.getCd_d_nm());
				ddto.setQ_ord(dto.getQ_ord());
				ddto.setRemark(dto.getRemark());
				ddto.setInsert_id(dto.getInsert_id());
				ddto.setInsert_dt(dto.getInsert_dt());
				ddto.setUpdate_id(dto.getUpdate_id());
				ddto.setUpdate_dt(dto.getUpdate_dt());
				detailList.add(ddto);
			}
			return detailList;
	}
	public void insertCodeDetail(CodeDetailDTO detailInsert) {
		int result = sqlSession.insert("insertCodeDetail", detailInsert);
		if(result != 0){
			System.out.println("detail insert 성공");
		}
	}
	public void deleteCodeDetail(String comp_cd, String cd_m, String cd_d) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", comp_cd);
		map.put("cd_m", cd_m);
		map.put("cd_d", cd_d);
		int result = sqlSession.delete("deleteCodeDetail", map);
		if(result != 0){
			System.out.println("detail delete 성공");
		}
	}
	public void updateCodeMaster(CodeDetailDTO detailUpdate, String ocd_m, String ocd_d) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("detailUpdate", detailUpdate);
		map.put("ocd_m", ocd_m);
		map.put("ocd_d", ocd_d);
		int result = sqlSession.update("updateCodeDetail", map);
		if(result != 0){
			System.out.println("detail update 성공");
		}
	}
	public boolean getCd_dName(String company, String cd_m, String cd_d) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", company);
		map.put("cd_m", cd_m);
		map.put("cd_d", cd_d);
		int result = sqlSession.selectOne("checkCd_d", map);
		if(result != 0){
			return false;
		}else{
			return true;
		}	
	}
	public boolean getQ_ord(String company, String cd_m, String q_ord) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("comp_cd", company);
		map.put("cd_m", cd_m);
		map.put("q_ord", q_ord);
		int result = sqlSession.selectOne("checkQ_ord", map);
		if(result != 0){
			return false;
		}else{
			return true;
		}	
	}
}
