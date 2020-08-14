package com.hiberchat.services;

import com.hiberchat.configs.WebMvcConfig;
import com.hiberchat.exceptions.UserException;
import com.hiberchat.models.User;
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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test(expected = UserException.class)
    public void addUserNull() {
        userService.addUser(null);
    }

    @Test(expected = UserException.class)
    public void addUserEmpty() {
        userService.addUser(new User().new Builder().setId(1L).setEmail("").setNickname("Ang").build());
    }

    @Test(expected = UserException.class)
    public void getUserByNicknameNull() {
        userService.getUserByNickname(null);
    }

    @Test(expected = UserException.class)
    public void getUserByNicknameEmpty() {
        userService.getUserByNickname("");
    }

    @Test(expected = UserException.class)
    public void getUserByEmailNull() {
        userService.getUserByEmail(null);
    }

    @Test(expected = UserException.class)
    public void getUserByEmailEmpty() {
        userService.getUserByEmail("");
    }

    @Test(expected = UserException.class)
    public void getUserByIdNull() {
        userService.getUserById(null);
    }

}
