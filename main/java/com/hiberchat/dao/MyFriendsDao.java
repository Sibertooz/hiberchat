package com.hiberchat.dao;

import com.hiberchat.models.MyFriends;

import java.util.List;

public interface MyFriendsDao {
    void addMyFriend(MyFriends myFriend);
    void deleteMyFriend(Long myId, Long friendId);
    List<MyFriends> getAllMyFriends(Long myId);

    String GET_ALL_MY_FRIENDS = "from MyFriends where my_id = :my_id";
    String DELETE_MY_FRIENDS = "delete MyFriends where my_id = :my_id and friend_id = :friend_id";
}
