package com.myomi.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myomi.board.dao.BoardDAO;
import com.myomi.board.dao.BoardDAOOracle;
import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;

public class BoardService {
    
    //확인
	public List<BoardVo> findAllBoard() throws FindException{
		BoardDAO dao = new BoardDAOOracle(); //다형성 
		return dao.selectAll();
	}
	
	//확인
    public List<Map<String, Object>> findByTitle(String title) throws FindException{
    	BoardDAO dao = new BoardDAOOracle();
    	return dao.selectByTitle(title);
    }
    
    //확인
    public List<Map<String, Object>> findByCategory(String category, String title) throws FindException{
    	BoardDAO dao = new BoardDAOOracle();
    	 return dao.selectByCategory(category, title);
    }
    
    //보류
    public List<Map<String, Object>> findDetail(int num) throws FindException{
        BoardDAO dao = new BoardDAOOracle();
        return dao.selectDetail(num);
    }
    
    //확인
    public void addBoard(BoardVo bVo) throws FindException {
    	BoardDAO dao = new BoardDAOOracle();
        dao.insertBoard(bVo);
    }
    
    //확인
    public void modifyBoard(BoardVo bVo) throws FindException{
    	BoardDAO dao = new BoardDAOOracle();
    	dao.updateBoard(bVo);
    }
    
    //확인
    public void removeBoard (int num)throws FindException {
    	BoardDAO dao = new BoardDAOOracle();
    	dao.deleteBoard(num);
    	
    }
    
    public static void main(String[] args) throws FindException{
		BoardDAOOracle dao = new BoardDAOOracle();
		//dao.deleteBoard(10);	
		//dao.updateBoard("베스트 조합","수정테스트","수정테스트",9);
		//dao.selectDetail(1);
		//dao.insertBoard(0, "user1", "잡담", "글작성 테스트","어이없어@@@@@2",new Date(),0);
		//dao.selectByCategory("잡담","%더미%");
		//dao.selectAll();
	  // dao.selectByTitle("%최종%");

	}
}
