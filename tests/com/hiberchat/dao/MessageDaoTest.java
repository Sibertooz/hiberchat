package com.hiberchat.dao;

import com.hiberchat.AssertUtils;
import com.hiberchat.configs.WebMvcConfig;
import com.hiberchat.models.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebMvcConfig.class)
@Transactional("txManager")
public class MessageDaoTest {
    @Autowired
    private MessageDao messageDao;

    private Message expectedMessageFirst;
    private Message expectedMessageSecond;
    private Message actualMessage;
    private List<Message> expectedList;
    private List<Message> actualList;

    @Before
    public void initialize() {
        expectedMessageFirst = new Message().new Builder().setMessageId(3L).setFromUserId(1L).setToUserId(2L)
                .setSendingDate(LocalDateTime.of(2020, 3, 13, 20, 11, 40))
                .setMessageText("Hello, world!").build();

        expectedMessageSecond = new Message().new Builder().setMessageId(4L).setFromUserId(2L).setToUserId(1L)
                .setSendingDate(LocalDateTime.of(2020, 3, 13, 20, 12, 43))
                .setMessageText("Nu privet, yopta").build();

        actualMessage = new Message().new Builder().setFromUserId(1L).setToUserId(2L)
                .setSendingDate(LocalDateTime.now()).setMessageText("Davno ne videlis, urod").build();

        expectedList = new ArrayList<>();
    }

    @Test
    public void getAllMessages() {
        expectedList.add(expectedMessageFirst);
        expectedList.add(expectedMessageSecond);
        actualList = messageDao.list();
        AssertUtils.assertMessagesList(expectedList, actualList);
    }

    @Test
    public void getAllMessagesFirst() {
        expectedList.add(expectedMessageFirst);
        actualList = messageDao.getAllMessages(1L, 2L);
        AssertUtils.assertMessagesList(expectedList, actualList);
    }

    @Test
    public void addMessage() {
        messageDao.addMessage(actualMessage);
        expectedList.add(expectedMessageFirst);
        expectedList.add(expectedMessageSecond);
        expectedList.add(actualMessage);
        actualList = messageDao.list();
        AssertUtils.assertMessagesList(expectedList, actualList);
    }
}
