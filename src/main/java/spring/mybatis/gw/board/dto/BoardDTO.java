package spring.mybatis.gw.board.dto;

import java.util.Date;

public class BoardDTO {
	//GW_BOARD 테이블	
	private int NO;
	private String M_YN;
	private String SUBJECT;
	private String GUBUN;
	private String CONTENT;
	private int READCOUNT;
	private int FILE_M_ID;
	private String SUB_SUBJECT; // 잘라진 제목
		
	//EMPLOYEE_INFO 사원 테이블
	private String EMP_NO;
	private String EMP_PW;
	private String EMP_NM;
	private String DEPT_NO;
	private String EMP_ROLE_CD;
	private String EMP_PHONE;
	private String EMP_EMAIL;
	private String EMP_ADDR;
	private String INSERT_ID;	
	private String UPDATE_ID;
	
	
	//GW_BOARD + EMPLOYEE_INFO  
	private String COMP_CD;
	private Date INSERT_DT;
	private Date UPDATE_DT;
	
	//페이지 선택 관련 추가
	private int startrow;
	private int endrow;
	
	
	public int getNO() {
		return NO;
	}
	public void setNO(int nO) {
		NO = nO;
	}
	public String getM_YN() {
		return M_YN;
	}
	public void setM_YN(String m_YN) {
		M_YN = m_YN;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}
	public String getGUBUN() {
		return GUBUN;
	}
	public void setGUBUN(String gUBUN) {
		GUBUN = gUBUN;
	}
	public String getCONTENT() {
		return CONTENT;
	}
	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}
	public int getREADCOUNT() {
		return READCOUNT;
	}
	public void setREADCOUNT(int rEADCOUNT) {
		READCOUNT = rEADCOUNT;
	}
	public int getFILE_M_ID() {
		return FILE_M_ID;
	}
	public void setFILE_M_ID(int fILE_M_ID) {
		FILE_M_ID = fILE_M_ID;
	}
	public String getSUB_SUBJECT() {
		return SUB_SUBJECT;
	}
	public void setSUB_SUBJECT(String sUB_SUBJECT) {
		SUB_SUBJECT = sUB_SUBJECT;
	}
	public String getEMP_NO() {
		return EMP_NO;
	}
	public void setEMP_NO(String eMP_NO) {
		EMP_NO = eMP_NO;
	}
	public String getEMP_PW() {
		return EMP_PW;
	}
	public void setEMP_PW(String eMP_PW) {
		EMP_PW = eMP_PW;
	}
	public String getEMP_NM() {
		return EMP_NM;
	}
	public void setEMP_NM(String eMP_NM) {
		EMP_NM = eMP_NM;
	}
	public String getDEPT_NO() {
		return DEPT_NO;
	}
	public void setDEPT_NO(String dEPT_NO) {
		DEPT_NO = dEPT_NO;
	}
	public String getEMP_ROLE_CD() {
		return EMP_ROLE_CD;
	}
	public void setEMP_ROLE_CD(String eMP_ROLE_CD) {
		EMP_ROLE_CD = eMP_ROLE_CD;
	}
	public String getEMP_PHONE() {
		return EMP_PHONE;
	}
	public void setEMP_PHONE(String eMP_PHONE) {
		EMP_PHONE = eMP_PHONE;
	}
	public String getEMP_EMAIL() {
		return EMP_EMAIL;
	}
	public void setEMP_EMAIL(String eMP_EMAIL) {
		EMP_EMAIL = eMP_EMAIL;
	}
	public String getEMP_ADDR() {
		return EMP_ADDR;
	}
	public void setEMP_ADDR(String eMP_ADDR) {
		EMP_ADDR = eMP_ADDR;
	}
	public String getINSERT_ID() {
		return INSERT_ID;
	}
	public void setINSERT_ID(String iNSERT_ID) {
		INSERT_ID = iNSERT_ID;
	}
	public String getUPDATE_ID() {
		return UPDATE_ID;
	}
	public void setUPDATE_ID(String uPDATE_ID) {
		UPDATE_ID = uPDATE_ID;
	}
	public String getCOMP_CD() {
		return COMP_CD;
	}
	public void setCOMP_CD(String cOMP_CD) {
		COMP_CD = cOMP_CD;
	}
	public Date getINSERT_DT() {
		return INSERT_DT;
	}
	public void setINSERT_DT(Date iNSERT_DT) {
		INSERT_DT = iNSERT_DT;
	}
	public Date getUPDATE_DT() {
		return UPDATE_DT;
	}
	public void setUPDATE_DT(Date uPDATE_DT) {
		UPDATE_DT = uPDATE_DT;
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