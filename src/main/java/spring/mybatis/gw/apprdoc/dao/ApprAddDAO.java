package spring.mybatis.gw.apprdoc.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mybatis.gw.apprdoc.dto.ApprDocDTO;
import spring.mybatis.gw.apprdoc.dto.ApprListDTO;
import spring.mybatis.gw.apprdoc.dto.ApprStepDTO;


public class ApprAddDAO {
	
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//appr_doc 테이블 저장
	public int apprDocInsert(ApprDocDTO apprDocData){
		int result = 0;	
		
		System.out.println("getEmp_no 테스트: "+apprDocData.getEmp_no());
		
		result= sqlSession.insert("apprDocInsert", apprDocData);
	
		return result;
	}
	
	public int updateApprDocFileMId(String comp_cd, int appr_no, int file_m_id){
		int result = 0;	
		
		ApprListDTO apprListDTO = new ApprListDTO();
		apprListDTO.setComp_cd(comp_cd);
		apprListDTO.setAppr_no(appr_no);
		apprListDTO.setFile_m_id(file_m_id);
		
		result = sqlSession.update("updateApprDocFileMId", apprListDTO);
		
		return result;
	}
	
	//결재자들 저장
	public int apprStepInsert(List<ApprStepDTO> apprStepList){
				
		int result=0;
			
		for(int i=0; i< apprStepList.size(); i++){
			
			System.out.println("apprStepInsert for문 안 test"+apprStepList.get(i).getAppr_line()+"+cnt"+i);
			System.out.println("apprStepInsert for문 안 test"+apprStepList.get(i).getComp_cd()+"+cnt"+i);
			System.out.println("apprStepInsert for문 안 test"+apprStepList.get(i).getEmp_no()+"+cnt"+i);
			System.out.println("apprStepInsert for문 안 test"+apprStepList.get(i).getAppr_priority()+"+cnt"+i);
			
			result = sqlSession.insert("apprStepInsert", (ApprStepDTO)apprStepList.get(i));		
		}
		
		return result;
	}
		
	public int getApprNo(){   //appr_no 가져오기
		int apprNo = sqlSession.selectOne("getApprNo");
			
		return apprNo;
	}

}
