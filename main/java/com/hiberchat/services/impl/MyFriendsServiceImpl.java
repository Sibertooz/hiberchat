package com.hiberchat.services.impl;

import com.hiberchat.dao.MyFriendsDao;
import com.hiberchat.exceptions.NullObjectException;
import com.hiberchat.exceptions.UserException;
import com.hiberchat.models.MyFriends;
import com.hiberchat.models.User;
import com.hiberchat.services.MyFriendsService;
import com.hiberchat.services.UserService;
import com.hiberchat.utils.ErrorCodes;
import com.hiberchat.utils.ExceptionMessages;
import com.hiberchat.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyFriendsServiceImpl implements MyFriendsService {
    @Autowired
    private MyFriendsDao myFriendsDao;

    @Autowired
    private UserService userService;

    @Override
    public void addMyFriend(MyFriends myFriend) {
        try {
            ExceptionUtil.isNotNull(myFriend);
        } catch (NullObjectException ex) {
            throw new UserException(ExceptionMessages.USER_EMPTY, ErrorCodes.NOT_FOUND);
        }

        if (myFriend.getMyId().equals(myFriend.getFriendId())) throw new UserException(ExceptionMessages.USERS_IDS_EQUALS, ErrorCodes.BAD_REQUEST);

        List<User> myFriends = this.getAllMyFriends(myFriend.getMyId());
        for (User user : myFriends) if (user.getId().equals(myFriend.getFriendId())) throw new UserException(ExceptionMessages.USER_IS_FRIEND, ErrorCodes.CONFLICT);

        myFriendsDao.addMyFriend(myFriend);
    }

    @Override
    public void deleteMyFriend(Long myId, Long friendId) {
        try {
            ExceptionUtil.isNotNull(myId, friendId);
        } catch (NullObjectException ex) {
            throw new UserException(ExceptionMessages.USER_EMPTY);
        }

        myFriendsDao.deleteMyFriend(myId, friendId);
        myFriendsDao.deleteMyFriend(friendId, myId);
    }

    @Override
    public List<User> getAllMyFriends(Long myId) {
        try {
            ExceptionUtil.isNotNull(myId);
        } catch (NullObjectException ex) {
            throw new UserException(ExceptionMessages.USER_EMPTY);
        }

        List<MyFriends> myFriendsList = new ArrayList<>(myFriendsDao.getAllMyFriends(myId));
        List<User> myUsers = new ArrayList<>();

        if (!CollectionUtils.isEmpty(myFriendsList)) {
            for (MyFriends myFriends : myFriendsList) {
                User user = userService.getUserById(myFriends.getFriendId());
                myUsers.add(user);
            }
        }

        return myUsers;
    }
}
