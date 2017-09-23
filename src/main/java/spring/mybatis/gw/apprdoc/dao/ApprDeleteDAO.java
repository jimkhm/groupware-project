package spring.mybatis.gw.apprdoc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public class ApprDeleteDAO {

	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int deleteApprStep(int apprNo){
		
		int ret = 0;
		
		ret = sqlSession.delete("deleteApprStep", apprNo);
		
		return ret;
	}
	
	public int deleteApprDoc(int apprNo){
	
		int ret = 0;
		
		ret = sqlSession.delete("deleteApprDoc", apprNo);
		
		return ret;
	
	}
	
	public int getFileMidExist(int apprNo){
		int fileMId = 0;
		
		if(sqlSession.selectOne("getFileMidExist", apprNo) != null){
		fileMId = sqlSession.selectOne("getFileMidExist", apprNo);
		}
		return fileMId;
	}
	
}
