package com.hiberchat.dao.impl;

import com.hiberchat.dao.MyFriendsDao;
import com.hiberchat.models.MyFriends;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
@EnableTransactionManagement
@Transactional("txManager")
public class MyFriendsDaoImpl implements MyFriendsDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public void addMyFriend(MyFriends myFriend) {
        openSession();
        session.save(myFriend);
        closeSession();
    }

    @Override
    public void deleteMyFriend(Long myId, Long friendId) {
        openSession();
        session.createQuery(DELETE_MY_FRIENDS).setParameter("my_id", myId).setParameter("friend_id", friendId).executeUpdate();
        closeSession();
    }

    @Override
    public List<MyFriends> getAllMyFriends(Long myId) {
        openSession();
        TypedQuery<MyFriends> query = session.createQuery(GET_ALL_MY_FRIENDS);
        query.setParameter("my_id", myId);
        List<MyFriends> myFriends = new ArrayList<>(query.getResultList());
        closeSession();
        return myFriends;
    }

    private void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    private void closeSession() {
        session.getTransaction().commit();
        session.close();
    }
}
