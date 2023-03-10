package com.myomi.common.advice;

import com.myomi.exception.AddException;
import com.myomi.exception.FindException;
import com.myomi.exception.RemoveException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler(FindException.class)
	@ResponseBody
	public ResponseEntity<?> findExceptionHandler(Exception e) {
		System.out.println("---------------findControllerAdvice----------------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset-UTF-8"); //한글깨짐방지를 위해 HttpHeader설정
		e.printStackTrace();
		Map<String, String> map = new HashMap<>();
		map.put("msg", e.getMessage());
		System.out.println("---------------findControllerAdvice----------------");
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(RemoveException.class)
	@ResponseBody
	public ResponseEntity<?> RemoveExceptionHandler(Exception e) {
		System.out.println("---------------RemoveControllerAdvice----------------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(e.getMessage(),headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AddException.class)
	@ResponseBody
	public ResponseEntity<?> AddExceptionHandler(Exception e) {
		System.out.println("---------------AddControllerAdvice----------------");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		return new ResponseEntity<>(e.getMessage(),headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public ResponseEntity<?> exceptMaxUploadSize(MaxUploadSizeExceededException e){
		System.out.println("---------------File AttachControllerAdvice----------------");
		
		//전달하고자 하는 메시지가 한글깨짐! header 설정 추가
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8"); //단순 text하려면 text/html;charset=UTF-*
		
		//xml의 cors설정은 컨트롤러관련된 설정!즉 컨트롤러만 적용! Advice는 적용이 안됨
		//advice에서 설정은 추가해줘야 함.
		headers.add("Access-Control-Allow-Origin", "http://192.168.0.18:5500"); //본인 IP적어줘야함
		headers.add("Access-Control-Allow-Credentials", "true");
		return new ResponseEntity<>("파일크기가 초과되었습니다", headers, HttpStatus.BAD_REQUEST);
	}
}



