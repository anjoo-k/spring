package com.kh.mail;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class Text2 {
	
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("send")
	public String mail() {
		//메세지 생성
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("이메일 전송 테스트2");
		message.setText("이메일을 테스트하도록 하겠습니다");
		
		String[] to = {"knjoo92@gmail.com", "knjou92@naver.com"};
		message.setTo(to);
		
		String[] cc = {"knjoo92@gmail.com", "knjou92@naver.com"};
		message.setTo(cc);
		
		sender.send(message);
		
		return "redirect:/";
		
	}
	
	// 첨부파일 등 보낼 때 hypermail 써야함
	@GetMapping("hypermail")
	public String hyperMail() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
		
		String[] to = {"knjoo92@gmail.com", "knjou92@naver.com"};
		helper.setTo(to);
		
		String[] cc = {"knjoo92@gmail.com", "knjou92@naver.com"};
		helper.setTo(cc);
		
		helper.setSubject("이메일 전송 테스트3");
		
		String url = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.port(8899).path("/user")
				.queryParam("user_id", "knjoo92")
				.queryParam("uuid", "sdfsd") //구글그거?
				.toUriString();
		
		//MimeMessageHelper의 두번째 인자를 true로 보낼 시 html을 사용하겠다는 의미
		helper.setText("<a href='localhost:8899/springETC'>웹사이트로 이동</a>", true);
		
		sender.send(message);
		
		return "redirect:/";
		
	}
	
	
	@GetMapping("sendfile")
	public String sendFile() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		String[] to = {"knjoo92@gmail.com", "knjou92@naver.com"};
		helper.setTo(to);
		
		String[] cc = {"knjoo92@gmail.com", "knjou92@naver.com"};
		helper.setTo(cc);
		
		helper.setSubject("이미지 전송 이메일 테스트");
		
		helper.setText("파일 전송 테스트 입니다", true);
		
		// 첨부파일추가
		DataSource source = new FileDataSource("C:\\Users\\user1\\Desktop\\무대.png");
		helper.addAttachment(source.getName(), source);
		
		sender.send(message);
		
		return "redirect:/";
		
	}
	
	
	

}
