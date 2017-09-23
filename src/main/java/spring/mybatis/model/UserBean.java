package spring.mybatis.model;

public class UserBean {
	
	private String user_id;		// VARCHAR2(20 BYTE) NOT NULL 
	private String user_pwd;	// VARCHAR2(20 BYTE) 
	private String user_name;	// VARCHAR2(20 BYTE)
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
		
}
