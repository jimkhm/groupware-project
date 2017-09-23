package spring.mybatis.gw.admin.dto;

import java.sql.Date;

public class CompanyDTO {
	
	private String	comp_cd;			// 회사 코드
	private String	comp_nm;		// 회사명
	private String	comp_no1;		// 사업자 등록 번호
	private String	comp_no2;		// 법인 등록 번호
	private String	comp_ceo_nm;// 대표자(성명)
	private String	comp_addr;		// 소재지(회사 주소)
	private String	comp_phone;	// 회사 대표 전화
	private String	admin_id;			// 관리자 아이디
	private String	admin_phone;	// 관리자 연락처
	private String	admin_pw;		// 관리자 비밀번호
	private String	insert_id;			// insert user
	private Date		insert_dt;			// insert time
	private String	update_id;		// 최종 update user
	private Date		update_dt;		// 최종 update time
	
	public String getComp_cd() {
		return comp_cd;
	}
	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}
	public String getComp_nm() {
		return comp_nm;
	}
	public void setComp_nm(String comp_nm) {
		this.comp_nm = comp_nm;
	}
	public String getComp_no1() {
		return comp_no1;
	}
	public void setComp_no1(String comp_no1) {
		this.comp_no1 = comp_no1;
	}
	public String getComp_no2() {
		return comp_no2;
	}
	public void setComp_no2(String comp_no2) {
		this.comp_no2 = comp_no2;
	}
	public String getComp_ceo_nm() {
		return comp_ceo_nm;
	}
	public void setComp_ceo_nm(String comp_ceo_nm) {
		this.comp_ceo_nm = comp_ceo_nm;
	}
	public String getComp_addr() {
		return comp_addr;
	}
	public void setComp_addr(String comp_addr) {
		this.comp_addr = comp_addr;
	}
	public String getComp_phone() {
		return comp_phone;
	}
	public void setComp_phone(String comp_phone) {
		this.comp_phone = comp_phone;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_phone() {
		return admin_phone;
	}
	public void setAdmin_phone(String admin_phone) {
		this.admin_phone = admin_phone;
	}
	public String getAdmin_pw() {
		return admin_pw;
	}
	public void setAdmin_pw(String admin_pw) {
		this.admin_pw = admin_pw;
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
	
}
