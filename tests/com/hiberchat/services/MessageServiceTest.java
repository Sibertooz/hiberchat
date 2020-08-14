package com.hiberchat.services;

import com.hiberchat.configs.WebMvcConfig;
import com.hiberchat.exceptions.MessageException;
import com.hiberchat.models.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebMvcConfig.class)
@Transactional("txManager")
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test(expected = MessageException.class)
    public void addMessageNull() {
        messageService.addMessage(null);
    }

    @Test(expected = MessageException.class)
    public void addMessageEmpty() {
        messageService.addMessage(new Message().new Builder().setMessageId(1L).setFromUserId(1L).setToUserId(2L)
                .setMessageText("").build());
    }

    @Test(expected = MessageException.class)
    public void usersIdsEquals() {
        messageService.addMessage(new Message().new Builder().setMessageId(1L).setFromUserId(1L).setToUserId(1L)
                .setMessageText("hello").build());
    }

    @Test(expected = MessageException.class)
    public void getMessageByIdNull() {
        messageService.getMessageById(null);
    }
}
