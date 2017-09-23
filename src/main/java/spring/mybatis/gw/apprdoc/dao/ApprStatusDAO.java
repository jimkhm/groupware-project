package spring.mybatis.gw.apprdoc.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import spring.mybatis.gw.apprdoc.dto.ApprHistoryDTO;
import spring.mybatis.gw.apprdoc.dto.ApprListDTO;
import spring.mybatis.gw.apprdoc.dto.HistoryPopDTO;

//결재 최고 순위 구하기 O
//사용자 결재 순서 가져오기 O
//사용자 결재 라인 가져오기 O
//결재 이력 거부, 반려 확인하기
//결재 이력 최고 순위 확인하기 O
//내 결재 이력 확인 O
//결재 이력에서 내 상태를 확인 O




public class ApprStatusDAO {
	
	//@Autowired
	private SqlSessionTemplate sqlSession;
			
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}
		
	
	
	//결재 라인 번호 가져오기 
	public int getApprLineNo(int appr_no, String emp_no)  {
		int appr_line_no=0;
		
		ApprListDTO alDto = new ApprListDTO() ;
		
		alDto.setAppr_no(appr_no);
		alDto.setEmp_no(emp_no);		
		
		
		appr_line_no = sqlSession.selectOne("getApprLineNo", alDto);
		
		return appr_line_no;
	}
	
	//결재 이력 입력하기 
	public int apprHistoryInsert(ApprHistoryDTO apprhistoryDTO){
		
		int ret = sqlSession.insert("apprHistoryInsert", apprhistoryDTO);
		
		return ret;
		
	}
	
	/*결재 최고 순위 구하기*/
	public int getApprMax(int appr_no) {
		int x= 0;		
		
		
		
		x = sqlSession.selectOne("getApprMax", appr_no);
		
		System.out.println("getApprMax(결재 최고 순위 구하기) :"  +x);
					
		return x;
	}
	
	/*사용자 결재 순서 가져오기 */
	public int getApprMe(String emp_no, int appr_no) {
		int x= 0;
		
		ApprListDTO alDto = new ApprListDTO() ;
		
		System.out.println("emp_no  "+emp_no);
		System.out.println("appr_no"+appr_no);
		
		alDto.setEmp_no(emp_no);
		alDto.setAppr_no(appr_no);
		
		
		if(sqlSession.selectOne("getApprMe", alDto) != null){		
		 x = sqlSession.selectOne("getApprMe", alDto);
		}
		System.out.println("getApprMe(사용자 결재 순서 가져오기) :"  +x);
		
		return x;
	}
	
	/*사용자 결재 라인 가져오기 */
	public String getApprMyLine(String emp_no, int appr_no) {
		String m= null;	
		
		ApprListDTO alDto = new ApprListDTO() ;
		List<String> apprLine = new ArrayList();
		
		alDto.setEmp_no(emp_no);
		alDto.setAppr_no(appr_no);
		
		
		apprLine = sqlSession.selectList("getApprMyLine", alDto);
		
		//결재 라인이 두 개인 경우를 대비한 조건식 ex)결재자이면서 시행자
		//쿼리문에서 ordery by appr_line desc 로 오름차순으로 정렬했음
		//결재자와 합의자는 등록과정에서 겹칠 수 없도록 제약조건이 걸려 있음
		for(int i = 0; i<apprLine.size(); i++){
			if(apprLine.get(i).equals("D") ){
				m = "D";
			}else if(apprLine.get(i).equals("C")){
				m= "C";
			}else if(apprLine.get(i).equals("B")){
				m= "B";
			}else if(apprLine.get(i).equals("A")){
				m= "A";
			}
		}
		
		System.out.println("getApprMyLine(사용자 결재 라인 가져오기) :"  +m);
		
		return m;
	}
	
	/*결재 이력 거부, 반려 확인하기*/
	public int getApprYN(int appr_no) {
		int x= 0;
						
		x = sqlSession.selectOne("getApprYN", appr_no);
		
		System.out.println("getApprYN(결재 이력 거부, 반려 확인하기) :"  +x);
		
		return x;
	}
	
	/*결재 이력 최고 순위 확인*/
	public int getApprHistoryMax(int appr_no) {
		int x= 0;		
		
		if(sqlSession.selectOne("getApprHistoryMax", appr_no) != null){		
			x = sqlSession.selectOne("getApprHistoryMax", appr_no);
		}
				
		System.out.println("getApprHistoryMax(결재 이력 최고 순위 확인) :"  +x);	
	
		return x;
	}
	
	//내 결재이력 확인
	public int getMyApprHistory(String emp_no, String comp_cd, int appr_no) {//총 개시판의 개수를 반환하는 메소드
		int x= 0;	
		
		ApprListDTO alDto = new ApprListDTO() ;
		
		alDto.setEmp_no(emp_no);
		alDto.setComp_cd(comp_cd);
		alDto.setAppr_no(appr_no);
				
		x = sqlSession.selectOne("getMyApprHistory", alDto);
		
		System.out.println("getMyApprHistory(내 결재이력 확인) :"  +x);
				
		return x;
	}
	
	 //결재 이력에 내 상태를 확인
		public String getMyHistoryStatus(String emp_no, String comp_cd, int appr_no) {//총 개시판의 개수를 반환하는 메소드
			String x= null;	
			
			ApprListDTO alDto = new ApprListDTO() ;
			
			alDto.setEmp_no(emp_no);
			alDto.setComp_cd(comp_cd);
			alDto.setAppr_no(appr_no);
					
			x = sqlSession.selectOne("getMyHistoryStatus", alDto);
			
			System.out.println("getMyHistoryStatus(결재 이력에 내 상태를 확인) :"  +x);
			
			return x;
		}
		
		//결재자 리스트 구하기
		public List<HistoryPopDTO> getHistory(int apprNo, String campany) {
			List<HistoryPopDTO> list = new ArrayList<HistoryPopDTO>();
			System.out.println("getHistory() 시작");
			
			ApprListDTO alDto = new ApprListDTO();
			
			alDto.setComp_cd(campany);
			alDto.setAppr_no(apprNo);			
					
			
			list =  sqlSession.selectList("getHistory", alDto);
			
			System.out.println("appr_no check:" +list.get(0).getAppr_no());
			System.out.println("이름 check:" +list.get(0).getEmp_nm());
			
			return list;			
		}
			

}
