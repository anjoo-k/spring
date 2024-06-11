package com.kh.mail;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// 직접 도구를 생성해서 이메일을 보내보자 

/*
 * Email Protocol
 * SMTP
 * 이메일을 전송할 때 사용하는 프로토콜
 * 
 * POP
 * 이메일 서버에 도착한 메일을 클라이언트로 가져올 때 사용하는 프로토콜
 * 
 * IMAP
 * 이메일 서버에 직접 접속하여 이메일을 확인할 때 사용하는 프로토콜
 * (gmail의 SMTP를 이용하기 위해서는 IMAP를 사용으로 해줘야한다.)
 */
public class Test1 {
	public static void main(String[] args) {
	
		// MIME 형식의 메일 보내기 위해서는 JavaMailSender 인터페이스를 사용한다.
		// 계정설정
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl(); // 연결이 안됐는데
		sender.setHost("smtp.gmail.com");
		sender.setPort(587); // gmail에서 쓰라는데로 정해져 있음
		sender.setUsername("knjoo92@gmail.com");
		sender.setPassword("xcpj eimf ilgi gvio"); // 여기에 google에서 받은 앱비밀번호
		
		//옵션셜정
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", true);
		
		sender.setJavaMailProperties(prop);
		
		//메세지 생성
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("이메일 전송 테스트");
		message.setText("이메일을 테스트하도록 하겠습니다");
		
		String[] to = {"knjoo92@gmail.com", "knjou92@naver.com"};
		message.setTo(to);
		
		String[] cc = {"knjoo92@gmail.com", "knjou92@naver.com"};
		message.setTo(cc);
		
		sender.send(message);
	}
}
