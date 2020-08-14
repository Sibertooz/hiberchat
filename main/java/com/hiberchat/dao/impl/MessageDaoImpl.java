package com.hiberchat.dao.impl;

import com.hiberchat.dao.MessageDao;
import com.hiberchat.models.Message;
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
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public void addMessage(Message message) {
        openSession();
        session.save(message);
        closeSession();
    }

    @Override
    public Message getMessageById(Long messageId) {
        openSession();
        Message msg = session.get(Message.class, messageId);
        closeSession();
        return msg;
    }

    @Override
    public List<Message> getAllMessages(Long fromUserId, Long toUserId) {
        openSession();
        TypedQuery<Message> query = session
                .createQuery(GET_ALL_MESSAGES_BY_USERS_IDS).setParameter(QueryParameters.FROM_USER_ID, fromUserId)
                .setParameter(QueryParameters.TO_USER_ID, toUserId);
        List<Message> msgs = query.getResultList();
        closeSession();
        return msgs;
    }

    @Override
    public List<Message> list() {
        TypedQuery<Message> query = sessionFactory.getCurrentSession().createQuery("from Message");
        return query.getResultList();
    }

    @Override
    public void deleteHistory(Long fromUserId, Long toUserId) {
        openSession();
        session.createQuery(DELETE_HISTORY).setParameter(QueryParameters.FROM_USER_ID, fromUserId).setParameter(QueryParameters.TO_USER_ID, toUserId).executeUpdate();
        session.createQuery(DELETE_HISTORY).setParameter(QueryParameters.FROM_USER_ID, toUserId).setParameter(QueryParameters.TO_USER_ID, fromUserId).executeUpdate();
        closeSession();
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
