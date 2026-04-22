package com.hostelmart.dao;

import com.hostelmart.model.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class OrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int save(Order order) {
        return (Integer) getSession().save(order);
    }

    @SuppressWarnings("unchecked")
    public List<Order> findByBuyerId(int buyerId) {
        String hql = "SELECT o, p.name as productName, u.name as sellerName " +
                     "FROM Order o " +
                     "JOIN Product p ON o.productId = p.id " +
                     "JOIN User u ON o.sellerId = u.id " +
                     "WHERE o.buyerId = :buyerId ORDER BY o.createdAt DESC";
        List<Object[]> results = getSession().createQuery(hql).setParameter("buyerId", buyerId).list();
        return results.stream().map(res -> {
            Order o = (Order) res[0];
            o.setProductName((String) res[1]);
            o.setSellerName((String) res[2]);
            return o;
        }).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Order> findBySellerId(int sellerId) {
        String hql = "SELECT o, p.name as productName, u.name as buyerName " +
                     "FROM Order o " +
                     "JOIN Product p ON o.productId = p.id " +
                     "JOIN User u ON o.buyerId = u.id " +
                     "WHERE o.sellerId = :sellerId ORDER BY o.createdAt DESC";
        List<Object[]> results = getSession().createQuery(hql).setParameter("sellerId", sellerId).list();
        return results.stream().map(res -> {
            Order o = (Order) res[0];
            o.setProductName((String) res[1]);
            o.setBuyerName((String) res[2]);
            return o;
        }).collect(Collectors.toList());
    }
}
