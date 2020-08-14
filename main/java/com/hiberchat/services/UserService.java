package com.hiberchat.services;

import com.hiberchat.models.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserById(Long id);
    User getUserByNickname(String nickname);
    void addUser(User user);
}
