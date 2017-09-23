package spring.mybatis.gw.admin.dto;

public class SeqDTO {
	
	private String sq_cd;
	private String sq_nm;
	private int ret;
	private int dbcode;
	private String dberrtext;
	
	public String getSq_cd() {
		return sq_cd;
	}
	public void setSq_cd(String sq_cd) {
		this.sq_cd = sq_cd;
	}
	public String getSq_nm() {
		return sq_nm;
	}
	public void setSq_nm(String sq_nm) {
		this.sq_nm = sq_nm;
	}
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public int getDbcode() {
		return dbcode;
	}
	public void setDbcode(int dbcode) {
		this.dbcode = dbcode;
	}
	public String getDberrtext() {
		return dberrtext;
	}
	public void setDberrtext(String dberrtext) {
		this.dberrtext = dberrtext;
	}
	
}
