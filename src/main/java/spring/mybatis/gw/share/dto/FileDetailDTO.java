package spring.mybatis.gw.share.dto;

import org.springframework.stereotype.Repository;

@Repository
public class FileDetailDTO {
	private String comp_cd;
	private int file_m_id;
	private int file_d_id;
	private String file_path; //C:\workspace\jsp\gwt\WebContent\boardUpload
	private String file_nm; //ShoppingMall13.war
	private String file_rnm; //ShoppingMall.war
	private String file_size;
	private String file_type;
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
	public int getFile_d_id() {
		return file_d_id;
	}
	public void setFile_d_id(int file_d_id) {
		this.file_d_id = file_d_id;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public String getFile_rnm() {
		return file_rnm;
	}
	public void setFile_rnm(String file_rnm) {
		this.file_rnm = file_rnm;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_type() {
		return file_type;
	}
	public void setFile_type(String file_type) {
		this.file_type = file_type;
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
