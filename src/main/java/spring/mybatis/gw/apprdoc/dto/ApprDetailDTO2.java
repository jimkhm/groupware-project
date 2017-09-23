package spring.mybatis.gw.apprdoc.dto;

import org.springframework.stereotype.Repository;

@Repository
public class ApprDetailDTO2 {
	
	private int 	appr_line_no;
	private int 	appr_no;
	private String 	appr_line;
	private String 	emp_no; //결재자 emp_no을 구분하기 위해서 appr을 붙였음	
	private int 	appr_priority;
	private String 	emp_nm;
	
	public int getAppr_line_no() {
		return appr_line_no;
	}
	public void setAppr_line_no(int appr_line_no) {
		this.appr_line_no = appr_line_no;
	}
	public int getAppr_no() {
		return appr_no;
	}
	public void setAppr_no(int appr_no) {
		this.appr_no = appr_no;
	}
	public String getAppr_line() {
		return appr_line;
	}
	public void setAppr_line(String appr_line) {
		this.appr_line = appr_line;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public int getAppr_priority() {
		return appr_priority;
	}
	public void setAppr_priority(int appr_priority) {
		this.appr_priority = appr_priority;
	}
	public String getEmp_nm() {
		return emp_nm;
	}
	public void setEmp_nm(String emp_nm) {
		this.emp_nm = emp_nm;
	}
	

}
