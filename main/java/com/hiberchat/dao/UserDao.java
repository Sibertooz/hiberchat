package com.hiberchat.dao;

import com.hiberchat.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User getUserByEmail(String email);
    User getUserById(Long id);
    User getUserByNickname(String nickname);
    void addUser(User user);
    List<User> list();

    String GET_USER_BY_EMAIL = "from User where email = :email";
    String GET_USER_BY_NICKNAME = "from User where nickname = :nickname";
}
