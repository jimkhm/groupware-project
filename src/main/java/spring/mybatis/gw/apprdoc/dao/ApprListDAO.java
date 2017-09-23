package spring.mybatis.gw.apprdoc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.mybatis.gw.apprdoc.dto.ApprListDTO;


public class ApprListDAO {
	
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	/*결재서류 리스트 개수 구하기*/
	public int getApprDocListCount(String comp_cd, String emp_no) {
		
		int cnt= 0;
		
		System.out.println("getApprDocListCount() 진입");
		
		ApprListDTO alDto = new ApprListDTO();
		
		alDto.setComp_cd(comp_cd);
		alDto.setEmp_no(emp_no);
		
		System.out.println(alDto.getComp_cd());
		System.out.println(alDto.getEmp_no());
		
		cnt = sqlSession.selectOne("selectApprListCnt1", alDto);
		System.out.println("cnt:"+cnt);
		
		return cnt;
	}
	
	/*결재서류 리스트 개수 구하기2*/
	public int getApprDocListCount2(String comp_cd, String emp_no) {//총 개시판의 개수를 반환하는 메소드
		/*String comp_cd, String emp_no*/
		
		int cnt2= 0;	
		
		System.out.println("getApprDocListCount2() 진입");
		
		ApprListDTO alDto = new ApprListDTO();
		
		alDto.setComp_cd(comp_cd);
		alDto.setEmp_no(emp_no);		
					
		cnt2 = sqlSession.selectOne("selectApprListCnt2", alDto);
					
		System.out.println("cnt2:"+cnt2);
		
		return cnt2;
		
	}
	
	public List<ApprListDTO> getApprDocList(int page,int limit, String comp_cd, String emp_no){
		
				 
		ApprListDTO alDto = new ApprListDTO();
				
		
				
		//페이지 단위로 들어갈 페이지 수를 계산
		//1(n)페이지에는 몇 번부터 몇 번까지의 글을 가지고 올 것이냐? top 쿼리문
		int startrow=(page-1)*5+1; //읽기 시작할 row 번호.  //1
		int endrow=startrow+limit-1; //읽을 마지막 row 번호.	//10
		
		alDto.setStartrow(startrow);
		alDto.setEndrow(endrow);
		alDto.setComp_cd(comp_cd);
		alDto.setEmp_no(emp_no);
		
		List<ApprListDTO> list =  sqlSession.selectList("apprDoclist", alDto);
						
		
		//if(list != null){
			for(int i=0; i < list.size() ; i++ ){
				
				String appr_title = list.get(i).getAppr_title();
				
				String subApprTitle  = subTitle(appr_title, 20);
				
				list.get(i).setSubApprTitle(subApprTitle);			
				
			//}
			
			//if(list != null){
				//System.out.println("리스트 출력:"+list.get(0).getAppr_title());
			//}
		}
							
		return list;		
		
	}
	
	public List<ApprListDTO> getApprDocList2(int page,int limit, String comp_cd, String emp_no){
			 
		ApprListDTO alDto = new ApprListDTO();
				
		//페이지 단위로 들어갈 페이지 수를 계산
		//1(n)페이지에는 몇 번부터 몇 번까지의 글을 가지고 올 것이냐? top 쿼리문
		int startrow=(page-1)*5+1; //읽기 시작할 row 번호.  //1
		int endrow=startrow+limit-1; //읽을 마지막 row 번호.	//10
		
		alDto.setStartrow(startrow);
		alDto.setEndrow(endrow);
		alDto.setComp_cd(comp_cd);
		alDto.setEmp_no(emp_no);
		
		List<ApprListDTO> list =  sqlSession.selectList("apprDoclist2", alDto);
		//if(list != null){
			for(int i=0; i < list.size() ; i++ ){
				
				String appr_title = list.get(i).getAppr_title();
				
				String subApprTitle  = subTitle(appr_title, 20);
				
				list.get(i).setSubApprTitle(subApprTitle);
			}
			
			
			//System.out.println("리스트 출력22:"+list.get(0).getAppr_title());
			
		//}	
									
		return list;	
		
	}
		
	
	public String stripTags(String str) {    // 프로그래밍에서 '\' 등 명령어로 인식할 때가 많다. 그렇기 때문에 지정을 해줘야한다. 
		
		String rStr = "";
		
		Matcher mat;
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		mat = tag.matcher(str);
		rStr = mat.replaceAll("");
		
		return rStr;
	}
	
	public String subTitle(String str, int size) {         //제목을 자르는 기능(제목의 크기)을 하는 메서드
		int han=0;
		int eng=0;
		str=stripTags(str);										// html 태그 빼기
		if(str.length()>size){										// 자를 길이보다 문자길이가 큰경우만 자르기
			String temp=str.substring(0,size);				// 문자를 잘른다
			for(int i=0;i<temp.length();i++){				// 잘린문자 길이대로 반복
				if((int)(temp.charAt(i))>127) han++;	// 한글이면 han 변수값 증가
				else eng++;											// 한글이 아니면 영어나 문자니까 eng 변수값 증가
			}
			if(han%2!=0) han++;								// 한글은 2바이트니까 만약 한글이 짝수가 아니면 1 증가시켜준다
			int total_size=eng+han;							// 자를 총 길이를 계산
			str=str.substring(0,total_size);					// 문자를 자른다
		}
		return str;
	}
		
	

}
