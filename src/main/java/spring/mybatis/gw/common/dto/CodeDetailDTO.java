package spring.mybatis.gw.common.dto;

import java.sql.Date;

public class CodeDetailDTO {
	private String comp_cd;
	private String cd_m;
	private String cd_d;
	private String cd_d_nm;
	private int q_ord;
	private String remark;
	private String insert_id;
	private Date insert_dt;
	private String update_id;
	private Date update_dt;
	public String getComp_cd() {
		return comp_cd;
	}
	public void setComp_cd(String comp_cd) {
		this.comp_cd = comp_cd;
	}
	public String getCd_m() {
		return cd_m;
	}
	public void setCd_m(String cd_m) {
		this.cd_m = cd_m;
	}
	public String getCd_d() {
		return cd_d;
	}
	public void setCd_d(String cd_d) {
		this.cd_d = cd_d;
	}
	public String getCd_d_nm() {
		return cd_d_nm;
	}
	public void setCd_d_nm(String cd_d_nm) {
		this.cd_d_nm = cd_d_nm;
	}
	public int getQ_ord() {
		return q_ord;
	}
	public void setQ_ord(int q_ord) {
		this.q_ord = q_ord;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
