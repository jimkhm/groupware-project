package spring.mybatis.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.model.UserBean;

public class UserDAOImpl {
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<UserBean> getUserList() {		
		List<UserBean> list = sqlSession.selectList("selectAllUsers");		
		return list;
	}
	
	public UserBean getUser(String user_id){		
		UserBean user = sqlSession.selectOne("selectUserById", user_id);		
		return user;
	}
	
	public int addUser(UserBean user){
		int rtn = sqlSession.insert("insertUser", user);
		return rtn;
	}
	
	public int updUser(UserBean user){
		int rtn = sqlSession.update("updateUser", user);
		return rtn;
	}
	
	public int delUser(String user_id){
		int rtn = sqlSession.delete("deleteUser", user_id);
		return rtn;
	}
	
}
