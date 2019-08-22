package everitoken.dao.impl;

import everitoken.dao.CustomerRepository;
import everitoken.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    private Configuration cfg;

    @Override
    public CustomerEntity load(Integer id) {
        return null;
    }

    @Override
    public CustomerEntity get(Integer id) {
        return null;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return null;
    }

    @Override
    public void persist(CustomerEntity entity) {

    }

    @Override
    public int add(CustomerEntity entity) throws Exception{
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getCustomerUid();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
        return uid;
    }

    /**
     * 根据邮箱查询数据
     * @param email 用户邮箱
     * @return
     */
    public CustomerEntity getByEmail(String email) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from CustomerEntity customer where customer.customerEmail = :email");
        query.setParameter("email", email);
        CustomerEntity customerEntity = (CustomerEntity)query.getSingleResult();
        return customerEntity;
    }

    @Override
    public void update(CustomerEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
}
