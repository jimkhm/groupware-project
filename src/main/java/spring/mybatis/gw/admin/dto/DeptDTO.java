/******************************************************************************
 *   프로그램ID : DeptDTO.java
 *   프로그램명 : 부서 정보 DTO
 *   관련 DB 테이블 : DEPT_INFO
 *   기타 DB 테이블 : 
 *   작  성  자 : 조 수 정 (Sujung Jo)
 *   작  성  일 : 2016.10.04.
 *   수  정  자 : 조 수 정 (Sujung Jo)
 *   수  정  일 : 2016.10.28.
 *   변경 내용 : 상위 부서 코드 (m_dept_no) 추가
 ******************************************************************************/
package spring.mybatis.gw.admin.dto;

import java.sql.Date;

public class DeptDTO {

	private String	comp_cd;			// 회사 코드
	private String	dept_no;			// 부서 코드
	private String	dept_nm;			// 부서명
	private String	dept_manager;// 부서장(사번)
	private String	dept_manager_nm;// 부서장(이름)	
	private String	m_dept_no;		// 상위 부서 코드
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
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getDept_manager() {
		return dept_manager;
	}
	public void setDept_manager(String dept_manager) {
		this.dept_manager = dept_manager;
	}
	public String getDept_manager_nm() {
		return dept_manager_nm;
	}
	public void setDept_manager_nm(String dept_manager_nm) {
		this.dept_manager_nm = dept_manager_nm;
	}
	public String getM_dept_no() {
		return m_dept_no;
	}
	public void setM_dept_no(String m_dept_no) {
		this.m_dept_no = m_dept_no;
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
