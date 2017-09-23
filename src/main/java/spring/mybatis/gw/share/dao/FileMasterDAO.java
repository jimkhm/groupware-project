package spring.mybatis.gw.share.dao;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.share.dto.FileMasterDTO;

public class FileMasterDAO {
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int getFileMasterId(){   //파일 마스터 id 가져오기
		int masterId = sqlSession.selectOne("getFileMasterId");
		
		return masterId;
	}
	
	public int FileMasterInsert(FileMasterDTO fileMaster) {
		
		int rtn = sqlSession.insert("insertFileMaster", fileMaster);
		
		return rtn;
	}
	
    // 파일 마스터 테이블에서 삭제 
	public int FileMasterDelete(String company, int file_m_id) {
		FileMasterDTO fileMaster = new FileMasterDTO();
		fileMaster.setComp_cd(company);
		fileMaster.setFile_m_id(file_m_id);
		
		int rtn = sqlSession.delete("deleteFileMaster", fileMaster);
		
		return rtn;
	}

	public FileMasterDTO getFileMaster(String company, int file_m_id){
		
		FileMasterDTO fileMaster = new FileMasterDTO();
		fileMaster.setComp_cd(company);
		fileMaster.setFile_m_id(file_m_id);
		
		fileMaster = sqlSession.selectOne("selectFileMaster", fileMaster);
		
		return fileMaster;
	}
	
}
