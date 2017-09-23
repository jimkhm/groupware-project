package spring.mybatis.gw.share.dto;

import org.springframework.stereotype.Repository;

@Repository
public class FileMasterDTO {
	
	private String comp_cd;
	private int file_m_id;
	private String file_gubun;
	private String insert_id;
	private String insert_dt;
	
	public String getComp_cd() {
		return comp_cd;
	}
	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}
	public int getFile_m_id() {
		return file_m_id;
	}
	public void setFile_m_id(int file_m_id) {
		this.file_m_id = file_m_id;
	}
	public String getFile_gubun() {
		return file_gubun;
	}
	public void setFile_gubun(String file_gubun) {
		this.file_gubun = file_gubun;
	}
	public String getInsert_id() {
		return insert_id;
	}
	public void setInsert_id(String insert_id) {
		this.insert_id = insert_id;
	}
	public String getInsert_dt() {
		return insert_dt;
	}
	public void setInsert_dt(String insert_dt) {
		this.insert_dt = insert_dt;
	}
	
	
	
	
	

}
