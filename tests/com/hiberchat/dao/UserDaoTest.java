package com.hiberchat.dao;

import com.hiberchat.AssertUtils;
import com.hiberchat.configs.WebMvcConfig;
import com.hiberchat.models.User;
import com.hiberchat.models.enums.UserStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebMvcConfig.class)
@Transactional("txManager")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    private User expectedUserFirst;
    private User expectedUserSecond;
    private User createdExpectedUser;
    private User actualUser;

    private List<User> expectedList;
    private List<User> actualList;

    @Before
    public void initialize() {
        createdExpectedUser = new User().new Builder().setNickname("Fred").setEmail("fredi@mail.ru").setRegistrationDate(LocalDate.now())
                .setUserStatus(UserStatus.NO).setPassword("12345").build();
        expectedUserFirst = new User().new Builder().setId(3L).setEmail("sabertooth@mail.ru").setNickname("Sabertooth")
                .setRegistrationDate(LocalDate.of(2020, 3, 13))
                .setUserStatus(UserStatus.NO).setPassword("12345").build();
        expectedUserSecond = new User().new Builder().setId(2L).setEmail("angel@mail.ru").setNickname("Angelina")
                .setRegistrationDate(LocalDate.of(2020, 3, 13))
                .setUserStatus(UserStatus.NO).setPassword("12345").build();

        expectedList = new ArrayList<>();
        actualList = new ArrayList<>();
    }

    @Test
    public void createUser() {
        userDao.addUser(createdExpectedUser);
        actualUser = userDao.getUserByEmail("fredi@mail.ru");
        createdExpectedUser.new Builder().setId(actualUser.getId()).build();
        AssertUtils.assertUsers(createdExpectedUser, actualUser);
    }

    @Test
    public void getAllUsersList() {
        expectedList.add(expectedUserFirst);
        expectedList.add(expectedUserSecond);
        actualList = userDao.list();
        AssertUtils.assertUsersList(expectedList, actualList);
    }

    @Test
    public void getUserByNickname() {
        actualUser = userDao.getUserByNickname("Angelina");
        AssertUtils.assertUsers(expectedUserSecond, actualUser);
    }

}