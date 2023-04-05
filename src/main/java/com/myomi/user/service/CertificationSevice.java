package com.myomi.user.service;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Slf4j
@Service
public class CertificationSevice {
	@Value("${api_key}")
    private String apiKey;

    @Value("${api_secret}")
    private String apiSecret;
    
	@Transactional
	public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
		Message coolsms = new Message(apiKey, apiSecret);

        // to, from, type, text은 필수 필요한 값입니다!
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "{보내는사람번호넣어주기}");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[묘미] 휴대폰인증 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "myomi web 1.0.0"); // application 이름이랑 version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

	@Transactional
	public void certifiedSeller(String phoneNumber) {
		Message coolsms = new Message(apiKey, apiSecret);

        // to, from, type, text은 필수 필요한 값입니다!
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01077428168");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "판매자 승인 심사가 완료되었습니다."+ "\n" + "마이페이지>파트너에서 승인상태를 확인해 주세요.");
        params.put("app_version", "myomi web 1.0.0"); // application 이름이랑 version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
