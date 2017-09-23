package spring.mybatis.gw.admin.dto;

import java.sql.Blob;
import java.sql.Date;

public class EmployeeDTO {
	
	private String	comp_cd;	//	회사 코드
	private String	emp_no;		//	사번
	private String	emp_id;		//	아이디
	private String	emp_pw;		//	비밀번호
	private String	emp_nm;		//	이름
	private String	dept_no;	//	부서코드
	private String	emp_role_cd;//	직급코드
	private String	emp_phone;	//	전화번호
	private String	emp_email;	//	이메일
	private String	emp_addr;	//	주소
	private String	insert_id;	//	insert user
	private Date	insert_dt;	//	insert time
	private String	update_id;	//	최종 update user
	private Date	update_dt;	//	최종 update time
	private String	dept_nm;	// 부서명**
	private String	emp_role_nm;// 직급명**
	private String	check_yn;	// 삭제여부**
	private String	search_input;// 검색어**
	private String	img_file_nm;	// 이미지 파일명
	private String	img_file_rnm;	// 실제 이미지 파일명
	
	public String getComp_cd() {
		return comp_cd;
	}
	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_pw() {
		return emp_pw;
	}
	public void setEmp_pw(String emp_pw) {
		this.emp_pw = emp_pw;
	}
	public String getEmp_nm() {
		return emp_nm;
	}
	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getEmp_role_cd() {
		return emp_role_cd;
	}
	public void setEmp_role_cd(String emp_role_cd) {
		this.emp_role_cd = emp_role_cd;
	}
	public String getEmp_phone() {
		return emp_phone;
	}
	public void setEmp_phone(String emp_phone) {
		this.emp_phone = emp_phone;
	}
	public String getEmp_email() {
		return emp_email;
	}
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	public String getEmp_addr() {
		return emp_addr;
	}
	public void setEmp_addr(String emp_addr) {
		this.emp_addr = emp_addr;
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
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getEmp_role_nm() {
		return emp_role_nm;
	}
	public void setEmp_role_nm(String emp_role_nm) {
		this.emp_role_nm = emp_role_nm;
	}
	public String getCheck_yn() {
		return check_yn;
	}
	public void setCheck_yn(String check_yn) {
		this.check_yn = check_yn;
	}
	public String getSearch_input() {
		return search_input;
	}
	public void setSearch_input(String search_input) {
		this.search_input = search_input;
	}
	public String getImg_file_nm() {
		return img_file_nm;
	}
	public void setImg_file_nm(String img_file_nm) {
		this.img_file_nm = img_file_nm;
	}
	public String getImg_file_rnm() {
		return img_file_rnm;
	}
	public void setImg_file_rnm(String img_file_rnm) {
		this.img_file_rnm = img_file_rnm;
	}
	
}
