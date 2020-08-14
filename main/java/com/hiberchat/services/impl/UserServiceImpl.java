package com.hiberchat.services.impl;

import com.hiberchat.dao.UserDao;
import com.hiberchat.exceptions.NullObjectException;
import com.hiberchat.exceptions.UserException;
import com.hiberchat.models.User;
import com.hiberchat.services.UserService;
import com.hiberchat.utils.ErrorCodes;
import com.hiberchat.utils.ExceptionMessages;
import com.hiberchat.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        try {
            ExceptionUtil.isNotNull(email);
            ExceptionUtil.isNotEmptyString(email);
        } catch (NullObjectException | IllegalArgumentException ex) {
            throw new UserException(ExceptionMessages.EMPTY_FIELD);
        }

        try {
            userDao.getUserByEmail(email);
        } catch (NoResultException ex) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND, ErrorCodes.NOT_FOUND);
        }

        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        try {
            ExceptionUtil.isNotNull(id);
        } catch (NullObjectException ex) {
            throw new UserException(ExceptionMessages.NULL_OBJECT);
        }

        try {
            userDao.getUserById(id);
        } catch (NoResultException ex) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND, ErrorCodes.NOT_FOUND);
        }

        return userDao.getUserById(id);
    }

    @Override
    public User getUserByNickname(String nickname) {
        try {
            ExceptionUtil.isNotNull(nickname);
            ExceptionUtil.isNotEmptyString(nickname);
        } catch (NullObjectException | IllegalArgumentException ex) {
            throw new UserException(ExceptionMessages.EMPTY_FIELD, ErrorCodes.BAD_REQUEST);
        }

        try {
            userDao.getUserByNickname(nickname);
        } catch (NoResultException ex) {
            throw new UserException(ExceptionMessages.USER_NOT_FOUND, ErrorCodes.NOT_FOUND);
        }

        return userDao.getUserByNickname(nickname);
    }

    @Override
    public void addUser(User user) {
        try {
            ExceptionUtil.userIsNotEmpty(user);
        } catch (NullObjectException | IllegalArgumentException ex) {
            throw new UserException(ExceptionMessages.USER_EMPTY, ErrorCodes.NO_CONTENT);
        }

        try {
            userDao.getUserByNickname(user.getNickname());
            userDao.getUserByEmail(user.getEmail());
        } catch (NoResultException ex) {
            throw new UserException(ExceptionMessages.USER_EXIST, ErrorCodes.CONFLICT);
        }

        userDao.addUser(user);
    }

}
