package com.bit.envdev.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bit.envdev.dto.MemberDTO;
import com.bit.envdev.dto.MessageDTO;
import com.bit.envdev.service.SendEmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class SendEmailServiceImpl implements SendEmailService {
    
    private final JavaMailSender mailSend;

    @Value("${FROM_ADDRESS}")
    private String FROM_ADDRESS ;

    public MessageDTO createMail(MemberDTO memberDTO) {
        String code  = getTempPassword();
        MessageDTO messageDTO = new MessageDTO();
        String username = memberDTO.getUsername();
        String userNickname = memberDTO.getUserNickname();


        messageDTO.setUsername(username);
        messageDTO.setSubject(userNickname+"님의 인증번호 안내 이메일입니다.");
        messageDTO.setContents("안녕하세요. 임시비밀번호 안내 관련 이메일 입니다." + "[" + userNickname + "]" +"님의 임시 비밀번호는 "
                                + code + " 입니다.");
        messageDTO.setCode(code);

        mailSend(messageDTO);
        return messageDTO;
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String code = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            code += charSet[idx];
        }
        return code;
    }

    @Override
    public void mailSend(MessageDTO messageDTO) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(messageDTO.getUsername());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getContents());

        mailSend.send(message);
    }
}
