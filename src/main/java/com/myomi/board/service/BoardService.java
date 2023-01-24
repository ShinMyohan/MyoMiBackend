package com.myomi.board.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.myomi.board.dao.BoardDAO;
import com.myomi.board.dao.BoardDAOOracle;
import com.myomi.board.vo.BoardVo;
import com.myomi.exception.FindException;
import com.myomi.user.vo.UserVo;

public class BoardService {
	BoardDAO dao = new BoardDAOOracle();
	BoardVo bVo = new BoardVo();
	UserVo uVo = new UserVo();
	
    //확인
	public List<Map<String, Object>> findAllBoard() throws FindException{
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
    public void addBoard(JSONObject jsonObject) throws FindException {
    	BoardDAO dao = new BoardDAOOracle();
    	uVo.setId(jsonObject.get("id").toString());
    	bVo.setUser(uVo);
    	bVo.setCategory(jsonObject.get("category").toString());
    	bVo.setTitle(jsonObject.get("title").toString());
    	bVo.setContent(jsonObject.get("content").toString());
    	
        dao.insertBoard(bVo);
    }
    
    //확인
    public void modifyBoard(JSONObject jsonObject) throws FindException{
    	BoardDAO dao = new BoardDAOOracle();
    	bVo.setCategory(jsonObject.get("category").toString());
    	bVo.setTitle(jsonObject.get("title").toString());
    	bVo.setContent(jsonObject.get("content").toString());
      	bVo.setNum(Integer.parseInt(jsonObject.get("num").toString()));
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
	dao.selectAll();
	  // dao.selectByTitle("%최종%");
//		BoardVo bVo = new BoardVo();
//		UserVo uVo = new UserVo();
//		bVo.setNum(0);
//	    bVo.setUser(uVo);
//	    uVo.setId("user3");
//		bVo.setCategory("잡담");
//		bVo.setTitle("하이");
//     	bVo.setContent("제이슨");
//        bVo.setCreatedDate(new Date());
//        bVo.setHits(0);
//        dao.insertBoard(bVo);

	}
}
