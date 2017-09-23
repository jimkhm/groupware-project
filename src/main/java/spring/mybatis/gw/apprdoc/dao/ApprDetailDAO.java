package spring.mybatis.gw.apprdoc.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mybatis.gw.apprdoc.dto.ApprDetailDTO1;
import spring.mybatis.gw.apprdoc.dto.ApprDetailDTO2;


public class ApprDetailDAO {

	
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//결재 서류 내용 가져오기
	public ApprDetailDTO1 getApprDoc(int appr_no){
		
		ApprDetailDTO1 apprDetail = null; 
		
		apprDetail=  (ApprDetailDTO1) sqlSession.selectOne("getApprDoc", appr_no);
		
		if(apprDetail != null){
			
			System.out.println("getApprDoc(결재 서류 내용 가져오기)"+apprDetail.getAppr_title());			
			
		}	
		
		return apprDetail;
	}
	
	//결재 스텝들 가져오기 
	public List<ApprDetailDTO2> getApprStep(int appr_no) throws Exception{						
		
	   	List<ApprDetailDTO2> list = new ArrayList<ApprDetailDTO2>();
		
	   	list = sqlSession.selectList("getApprStep", appr_no);
	
	   	System.out.println("getApprStep(결재자 스텝들 가져오기)"+list.get(0).getEmp_nm());
	
	   	return list;
	
	}
	

}
