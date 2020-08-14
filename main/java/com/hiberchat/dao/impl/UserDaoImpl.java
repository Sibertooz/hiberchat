package com.hiberchat.dao.impl;

import com.hiberchat.dao.UserDao;
import com.hiberchat.models.User;
import com.hiberchat.utils.QueryParameters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
@EnableTransactionManagement
@Transactional("txManager")
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public User getUserByEmail(String email) {
        openSession();
        TypedQuery<User> query = session.createQuery(GET_USER_BY_EMAIL);
        query.setParameter(QueryParameters.EMAIL, email);
        User user = query.getSingleResult();
        closeSession();
        return user;
    }

    @Override
    public User getUserById(Long id) {
        openSession();
        User user = session.get(User.class, id);
        closeSession();
        return user;
    }

    @Override
    public User getUserByNickname(String nickname) {
        openSession();
        TypedQuery<User> query = session.createQuery(GET_USER_BY_NICKNAME);
        query.setParameter(QueryParameters.NICKNAME, nickname);
        User user = query.getSingleResult();
        closeSession();
        return user;
    }

    @Override
    public void addUser(User user) {
        openSession();
        session.save(user);
        closeSession();
    }

    @Override
    public List<User> list() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
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
