package spring.mybatis.gw.apprdoc.dao;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.apprdoc.dto.ApprDocDTO;
import spring.mybatis.gw.board.dto.BoardDTO;



public class ApprUpdateDAO {
	
private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int apprDocUpdate(ApprDocDTO apprDocData){
		int ret = 0;
				
		ret = sqlSession.update("apprDocUpdate", apprDocData);
		
		return ret;
	}
	
	public int setApprFileMIDNull(String company, int appr_no) {
		
		ApprDocDTO apprDocDTO = new ApprDocDTO();
		apprDocDTO.setComp_cd(company);
		apprDocDTO.setAppr_no(appr_no);;
		
		int rtn = sqlSession.update("setApprFileMIDNull", apprDocDTO);
		
		return rtn;
	}
	
	

}
