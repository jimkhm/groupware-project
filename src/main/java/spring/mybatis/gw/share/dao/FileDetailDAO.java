package spring.mybatis.gw.share.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.share.dto.FileDetailDTO;
import spring.mybatis.gw.share.dto.FileMasterDTO;

public class FileDetailDAO {
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}	

	public int getFileDetailId(){   //파일 detail id 가져오기
		int detailId = sqlSession.selectOne("getFileDetailId");
		
		return detailId;
	}
		
	public int FileDetailInsert(FileDetailDTO fileDetail) {
		
		int rtn = sqlSession.insert("insertFileDetail", fileDetail);
		
		return rtn;
	}
	
	// 파일 디테일 테이블에서 삭제 
	public int FileDetailDelete(String company, int file_m_id ) {
		
		FileDetailDTO fileDetail = new FileDetailDTO();
		fileDetail.setComp_cd(company);
		fileDetail.setFile_m_id(file_m_id);
		
		int rtn = sqlSession.delete("deleteFileDetail", fileDetail);
		
		return rtn;
	}
	
	// 특정 파일 삭제
	public int FileDetailDelete(String company, int file_m_id, int file_d_id ) {
		
		
		System.out.println("company:"+company);
		System.out.println("file_m_id: "+file_m_id);
		System.out.println("file_d_id: "+file_d_id);
		
		FileDetailDTO fileDetail = new FileDetailDTO();
		fileDetail.setComp_cd(company);
		fileDetail.setFile_m_id(file_m_id);
		fileDetail.setFile_d_id(file_d_id);
		
		
		
		
		int rtn = sqlSession.delete("deleteSomeFileDetail", fileDetail);
		
		return rtn;
	}
	
	//상세보기에서 파일 가져오는 메서드
	public List<FileDetailDTO> getFileDetailList(String company, int file_m_id) {
		
		FileDetailDTO fileDetail = new FileDetailDTO();
		fileDetail.setComp_cd(company);
		fileDetail.setFile_m_id(file_m_id);
		
		List<FileDetailDTO> list = sqlSession.selectList("selectAllDetailFile", fileDetail);
		
		return list;
	}
	
	public FileDetailDTO getFileDetail(String company, int file_m_id, int file_d_id) {
		
		FileDetailDTO fileDetail = new FileDetailDTO();
		fileDetail.setComp_cd(company);
		fileDetail.setFile_m_id(file_m_id);
		fileDetail.setFile_d_id(file_d_id);
		
		fileDetail = sqlSession.selectOne("selectDetailFile", fileDetail);
		
		return fileDetail;
	}

	public int getCountFileDetail(String company, int file_m_id){
		
		FileDetailDTO fileDetail = new FileDetailDTO();
		fileDetail.setComp_cd(company);
		fileDetail.setFile_m_id(file_m_id);
		
		int count = sqlSession.selectOne("selectCountFileDetail", fileDetail);
		
		return count;
	}
	
	public int getSameFileCnt(String selectSameFileCnt){
		System.out.println("selectSameFileCnt: "+ selectSameFileCnt);
		
		int count = sqlSession.selectOne("getCountFileDetail", selectSameFileCnt);
		System.out.println("selectSameFileCnt: "+ selectSameFileCnt);
		return count;
	}
}
