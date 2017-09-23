/******************************************************************************
 *   프로그램ID : DocDeptEx.java
 *   프로그램명 : 부서 xml 생성 예제
 *   관련 DB 테이블 : 
 *   기타 DB 테이블 : 
 *   작  성  자 : 조 수 정 (Sujung Jo)
 *   작  성  일 : 2016.10.18.
 ******************************************************************************/
package spring.mybatis.gw.share.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.StringWriter;

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

public class DocDeptEx {
	
	public static void main(String[] args) {
		
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
		
		depts.appendChild(setDept(doc, "D01", "총무팀", "M"));
		depts.appendChild(setDept(doc, "D05", "개발팀", "M"));
		depts.appendChild(setDept(doc, "D02", "고객지원팀", "D01"));
		depts.appendChild(setDept(doc, "D06", "고객지원팀2", "D01"));
		depts.appendChild(setDept(doc, "D03", "영업팀", "D02"));
		depts.appendChild(setDept(doc, "D04", "영업팀2", "D02"));
		
		doc.appendChild(depts);
		
//		NodeList nList = doc.getChildNodes();		
//		System.out.println(nList.getLength());		
//		for(int i=0, iend = nList.getLength(); i<iend; i++){
//			
//			System.out.println("First===>" + nList.item(i).getNodeName());			
//			NodeList childNodeList = nList.item(i).getChildNodes();
//			
//			System.out.println(childNodeList.getLength());
//			for(int j=0, jend=childNodeList.getLength(); j<jend;j++){
//				
//				System.out.println("Child===>" + childNodeList.item(j).getNodeName());
//				System.out.println("Child===>" + childNodeList.item(j).getFirstChild().getNodeValue());				
//				NodeList childNodeList2 = childNodeList.item(j).getChildNodes();
//				
//				System.out.println(childNodeList2.getLength());
//				for(int k=0, kend=childNodeList2.getLength(); k<kend;k++){
//					System.out.println("Child===>" + childNodeList2.item(k).getNodeName());
//					System.out.println("Child===>" + childNodeList2.item(k).getTextContent());
//				}
//			}
//		}
		
		TransformerFactory trFactory = TransformerFactory.newInstance();
		String xmlStr = "";
		
		StringWriter sw = new StringWriter();

		try {
			Transformer transformer = trFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			xmlStr = sw.getBuffer().toString();
			
			System.out.println(xmlStr);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Element setDept(Document doc, String sDeptNo, String sDeptNm, String sMDeptNo) {
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

}
