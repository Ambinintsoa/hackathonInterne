package com.apollo.art.chat.service;

import com.apollo.art.chat.model.Message;
import com.apollo.art.chat.model.ResponseMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;

    public WSService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyUser(final String id, final Message message) {
        ResponseMessage response = new ResponseMessage(message.getContent(), message.getSenderId(),
                message.getSenderName(), message.getSenderPicturePath());

        // eto ndray ilay mi specifier hoe alefa any amin'iza ilay msg (id)
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);
    }
}
