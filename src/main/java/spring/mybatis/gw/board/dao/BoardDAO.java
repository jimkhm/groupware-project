package spring.mybatis.gw.board.dao;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import spring.mybatis.gw.admin.dto.CompanyDTO;
import spring.mybatis.gw.board.dto.BoardDTO;
import spring.mybatis.gw.share.dto.FileDetailDTO;



public class BoardDAO {
	
	private SqlSessionTemplate sqlSession;
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}	
	
	// 글 개수 
		public int getListCount(String company, String deptno) {
			
			BoardDTO boardDTO = new BoardDTO();
			boardDTO.setCOMP_CD(company);
			boardDTO.setDEPT_NO(deptno);
			
			//System.out.println("company, deptno :  "+company+deptno); 
			
			int count = sqlSession.selectOne("selectBoardListCount", boardDTO);
			//return count; 
			//System.out.println("count 출력 : "+count);
			return count;
		}
		
	
	// 게시판 목록 (리스트)
	public List<BoardDTO> getBoardList(int page, int limit, String company, String deptno) {
		
		//아규먼트(?) : dto에 저장될 값, 파라메터에 넣을 줄 값 
		BoardDTO boardDTO = new BoardDTO();
		String subject =  null;
		String sub_subject = null;
		
		//page 1 limit 10
		int startrow = (page - 1) * 10 + 1; // 읽기 시작할 row 번호.
		int endrow = startrow + limit - 1; // 읽을 마지막 row 번호.
	
		boardDTO.setCOMP_CD(company);
		boardDTO.setDEPT_NO(deptno);	
		boardDTO.setStartrow(startrow);
		boardDTO.setEndrow(endrow);		
		
		List<BoardDTO> list =  sqlSession.selectList("selectBoardList", boardDTO);	
		
		//sub_  를 for문으로...
		for(int i= 0, size = list.size() ; i < size;  i++){
			
			subject =  list.get(i).getSUBJECT();			
			sub_subject = subTitle(subject, 20);	
			list.get(i).setSUB_SUBJECT(sub_subject);

		}
		
//		for(BoardDTO board : list){
//			subject = board.getSUBJECT();
//			sub_subject = subTitle(subject, 20);	
//			board.setSUB_SUBJECT(sub_subject);
//		}
		
		return list;
	}
	
	//글 내용(상세 보기)  	
	public BoardDTO getDetail(String company, int num) {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCOMP_CD(company);
		boardDTO.setNO(num);
		
		BoardDTO board = sqlSession.selectOne("getDetail", boardDTO);
		
		return board;
	}
	
	
	//상세보기에서 파일 가져오는 메서드
	// FileDetailDTO getSelectFile(int file_m_id) throws Exception {
	// List<FileDetailDTO>  FileDetailDAO.getSelectFile(String company, int file_m_id) {
		
	//글 번호 (NO)
	public int getBoardNo()
	{
		int boardNo = sqlSession.selectOne("getBoardNo");
		
		return boardNo;		
	}
	
	// GW_BOARD 테이블 저장 (글 등록)
	public int gwBoardInsert(BoardDTO board) {
		
		int rtn = sqlSession.insert("gwBoardInsert", board);
		
		return rtn;
	}
	
	// 파일 부분 업데이트
//			public boolean updateFileMId(String company, int no, int file_m_id) {
	public int updateBoardFileMId(String company, int no, Integer file_m_id) {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCOMP_CD(company);
		boardDTO.setNO(no);
		boardDTO.setFILE_M_ID(file_m_id);
		
		int rtn = sqlSession.update("updateBoardFileMId", boardDTO);
		
		// m_id를 null로 업데이트
		//boardDTO.setFILE_M_ID();
		
		return rtn;
	}
	
	//글 수정
	public int boardModify(BoardDTO board) {

		int rtn = sqlSession.update("boardModify", board);
		return rtn;
	}
	
	// 글 삭제
	public int boardDelete(String company, int num) {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCOMP_CD(company);
		boardDTO.setNO(num);
		
		int rtn = sqlSession.delete("boardDelete", boardDTO);
		return rtn;
	}
	
	// 조회수 업데이트
	public int setReadCountUpdate(String company, int num) {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCOMP_CD(company);
		boardDTO.setNO(num);
		
		int rtn = sqlSession.update("setReadCountUpdate", boardDTO);
		
		return rtn;
	}
	
	public int setBoardFileMIDNull(String company, int num) {
		
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCOMP_CD(company);
		boardDTO.setNO(num);
		
		int rtn = sqlSession.update("setBoardFileMIDNull", boardDTO);
		
		return rtn;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public String stripTags(String str) {    // 프로그래밍에서 '\' 등 명령어로 인식할 때가 많다. 그렇기 때문에 지정을 해줘야한다. 
		
		String rStr = "";
		
		Matcher mat;
		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		mat = tag.matcher(str);
		rStr = mat.replaceAll("");
		
		//System.out.println("stripTags : "+rStr+"   "+mat);
		
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
		//System.out.println("subTitle"+"  "+str);
		return str;
	}
	

}
