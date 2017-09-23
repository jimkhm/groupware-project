package spring.mybatis.gw.apprdoc.dto;

import java.sql.Date;

import org.springframework.stereotype.Repository;

@Repository
public class ApprListDTO {
	private String comp_cd;
	private int appr_no;
	private String emp_no;
	private String appr_title;
	private String	subApprTitle;//잘려진 제목
	private String appr_content;
	private Date appr_date_up;
	private Date appr_date_appr;
	private Date appr_date_final;
	private int file_m_id;
	private String insert_id;
	private Date insert_dt;
	private String update_id;
	private Date update_dt;
	private String emp_nm;
	private int startrow;
	private int endrow;
	
	public String getComp_cd() {
		return comp_cd;
	}
	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}
	public int getAppr_no() {
		return appr_no;
	}
	public void setAppr_no(int appr_no) {
		this.appr_no = appr_no;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getAppr_title() {
		return appr_title;
	}
	public void setAppr_title(String appr_title) {
		this.appr_title = appr_title;
	}
	public String getSubApprTitle() {
		return subApprTitle;
	}
	public void setSubApprTitle(String subApprTitle) {
		this.subApprTitle = subApprTitle;
	}
	public String getAppr_content() {
		return appr_content;
	}
	public void setAppr_content(String appr_content) {
		this.appr_content = appr_content;
	}
	public Date getAppr_date_up() {
		return appr_date_up;
	}
	public void setAppr_date_up(Date appr_date_up) {
		this.appr_date_up = appr_date_up;
	}
	public Date getAppr_date_appr() {
		return appr_date_appr;
	}
	public void setAppr_date_appr(Date appr_date_appr) {
		this.appr_date_appr = appr_date_appr;
	}
	public Date getAppr_date_final() {
		return appr_date_final;
	}
	public void setAppr_date_final(Date appr_date_final) {
		this.appr_date_final = appr_date_final;
	}
	public int getFile_m_id() {
		return file_m_id;
	}
	public void setFile_m_id(int file_m_id) {
		this.file_m_id = file_m_id;
	}
	public String getInsert_id() {
		return insert_id;
	}
	public void setInsert_id(String insert_id) {
		this.insert_id = insert_id;
	}
	public Date getInsert_dt() {
		return insert_dt;
	}
	public void setInsert_dt(Date insert_dt) {
		this.insert_dt = insert_dt;
	}
	public String getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}
	public Date getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(Date update_dt) {
		this.update_dt = update_dt;
	}
	public String getEmp_nm() {
		return emp_nm;
	}
	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}
	public int getStartrow() {
		return startrow;
	}
	public void setStartrow(int startrow) {
		this.startrow = startrow;
	}
	public int getEndrow() {
		return endrow;
	}
	public void setEndrow(int endrow) {
		this.endrow = endrow;
	}
	
	
	
}

