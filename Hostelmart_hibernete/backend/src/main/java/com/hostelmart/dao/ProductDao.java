package com.hostelmart.dao;

import com.hostelmart.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ProductDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public int save(Product product) {
        return (Integer) getSession().save(product);
    }

    public int update(Product product) {
        return getSession().createQuery("UPDATE Product SET name=:name, price=:price, quantity=:quantity WHERE id=:id AND sellerId=:sellerId")
                .setParameter("name", product.getName())
                .setParameter("price", product.getPrice())
                .setParameter("quantity", product.getQuantity())
                .setParameter("id", product.getId())
                .setParameter("sellerId", product.getSellerId())
                .executeUpdate();
    }

    public int delete(int id, int sellerId) {
        return getSession().createQuery("DELETE FROM Product WHERE id=:id AND sellerId=:sellerId")
                .setParameter("id", id)
                .setParameter("sellerId", sellerId)
                .executeUpdate();
    }

    public Product findById(int id) {
        String hql = "SELECT p, u.name as sellerName FROM Product p LEFT JOIN User u ON p.sellerId = u.id WHERE p.id = :id";
        Object[] result = (Object[]) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
        if (result != null) {
            return mapResultToProduct(result);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        String hql = "SELECT p, u.name as sellerName FROM Product p LEFT JOIN User u ON p.sellerId = u.id ORDER BY p.createdAt DESC";
        List<Object[]> results = getSession().createQuery(hql).list();
        return results.stream().map(this::mapResultToProduct).collect(java.util.stream.Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<Product> findBySellerId(int sellerId) {
        String hql = "SELECT p, u.name as sellerName FROM Product p LEFT JOIN User u ON p.sellerId = u.id WHERE p.sellerId = :sellerId ORDER BY p.createdAt DESC";
        List<Object[]> results = getSession().createQuery(hql).setParameter("sellerId", sellerId).list();
        return results.stream().map(this::mapResultToProduct).collect(java.util.stream.Collectors.toList());
    }

    public int reduceQuantity(int id, int qty) {
        return getSession().createQuery("UPDATE Product SET quantity = quantity - :qty WHERE id = :id AND quantity >= :qty")
                .setParameter("qty", qty)
                .setParameter("id", id)
                .executeUpdate();
    }

    private Product mapResultToProduct(Object[] result) {
        Product p = (Product) result[0];
        p.setSellerName((String) result[1]);
        return p;
    }
}
