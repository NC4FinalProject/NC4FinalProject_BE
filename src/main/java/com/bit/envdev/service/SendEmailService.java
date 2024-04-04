package com.bit.envdev.service;

import com.bit.envdev.dto.MessageDTO;

public interface SendEmailService {

    public void mailSend(MessageDTO messageDTO);

}
