/******************************************************************************
 *   프로그램ID : DocDept.java
 *   프로그램명 : 부서 xml 생성 class
 *   관련 DB 테이블 : DEPT_INFO
 *   기타 DB 테이블 : 
 *   작  성  자 : 조 수 정 (Sujung Jo)
 *   작  성  일 : 2016.10.28.
 *   -----------------------------------------
 *   수  정  자 : 조 수 정 (Sujung Jo)
 *   수  정  일 : 2016.11.16.
 *   변경 내역 : getDeptWithManagerXml() 추가
 *                   public Element setDept(Document doc, String sDeptNo, String sDeptNm, String sMDeptNo, String sDeptManager, String sDeptManagerNm) 추가
 ******************************************************************************/
package spring.mybatis.gw.share.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import spring.mybatis.gw.admin.dto.DeptDTO;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DocDept {
	
	public String getDeptXml(List<DeptDTO> list) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc = builder.newDocument();
		
		Element depts = doc.createElement("depts");
		
		if(list != null){
			for(int i=0, len=list.size(); i<len; i++){
				DeptDTO dept = new DeptDTO();
				dept = list.get(i);
				// depts.appendChild(setDept(doc, "D01", "총무팀", "M"));
				depts.appendChild(setDept(doc, dept.getDept_no(), dept.getDept_nm(), dept.getM_dept_no()));
			}
		}
		
		doc.appendChild(depts);
		
		TransformerFactory trFactory = TransformerFactory.newInstance();
		String xmlStr = "";
		
		StringWriter sw = new StringWriter();

		try {
			Transformer transformer = trFactory.newTransformer();
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			xmlStr = sw.getBuffer().toString();
			
//			System.out.println(xmlStr);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//xmlStr = xmlStr.replace("\"", "'");
		
		return xmlStr;
	}

	public Element setDept(Document doc, String sDeptNo, String sDeptNm, String sMDeptNo) {
		Element dept = doc.createElement("dept");
		
		Element dept_no = doc.createElement("dept_no");
		Element dept_nm = doc.createElement("dept_nm");
		Element m_dept_no = doc.createElement("m_dept_no");
		
		dept_no.setTextContent(sDeptNo);
		dept_nm.setTextContent(sDeptNm);
		m_dept_no.setTextContent(sMDeptNo);
		
		dept.appendChild(dept_no);
		dept.appendChild(dept_nm);
		dept.appendChild(m_dept_no);
		
		return dept;		
	}
	
	public String getDeptWithManagerXml(List<DeptDTO> list) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc = builder.newDocument();
		
		Element depts = doc.createElement("depts");
		
		if(list != null){
			for(int i=0, len=list.size(); i<len; i++){
				DeptDTO dept = new DeptDTO();
				dept = list.get(i);
				// depts.appendChild(setDept(doc, "D01", "총무팀", "M"));
				depts.appendChild(setDept(doc, dept.getDept_no(), dept.getDept_nm(), dept.getM_dept_no(), dept.getDept_manager(), dept.getDept_manager_nm()));
			}
		}
		
		doc.appendChild(depts);
		
		TransformerFactory trFactory = TransformerFactory.newInstance();
		String xmlStr = "";
		
		StringWriter sw = new StringWriter();

		try {
			Transformer transformer = trFactory.newTransformer();
			//transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			xmlStr = sw.getBuffer().toString();
			
//			System.out.println(xmlStr);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//xmlStr = xmlStr.replace("\"", "'");
		
		return xmlStr;
	}
	
	public Element setDept(Document doc, String sDeptNo, String sDeptNm, String sMDeptNo, String sDeptManager, String sDeptManagerNm) {
		Element dept = doc.createElement("dept");
		
		Element dept_no = doc.createElement("dept_no");
		Element dept_nm = doc.createElement("dept_nm");
		Element m_dept_no = doc.createElement("m_dept_no");
		Element dept_manager = doc.createElement("dept_manager");
		Element dept_manager_nm = doc.createElement("dept_manager_nm");
		
		dept_no.setTextContent(sDeptNo);
		dept_nm.setTextContent(sDeptNm);
		m_dept_no.setTextContent(sMDeptNo);
		dept_manager.setTextContent(sDeptManager);
		dept_manager_nm.setTextContent(sDeptManagerNm);
		
		dept.appendChild(dept_no);
		dept.appendChild(dept_nm);
		dept.appendChild(m_dept_no);
		dept.appendChild(dept_manager);
		dept.appendChild(dept_manager_nm);
		
		return dept;		
	}

}
