package com.hostelmart.dao;

import com.hostelmart.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int save(User user) {
        return (Integer) getSession().save(user);
    }

    public User findByEmailAndPassword(String email, String password) {
        return getSession().createQuery("FROM User WHERE email = :email AND password = :password", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .uniqueResult();
    }

    public User findById(int id) {
        return getSession().get(User.class, id);
    }
}
