package everitoken.dao.impl;

import everitoken.dao.CustomerRepository;
import everitoken.entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
    public void add(CustomerEntity entity) /**throws Exception*/{
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(entity);
        }catch (Exception e){
            e.printStackTrace();
            Exception exception = new Exception("数据库异常");
            //throw exception;
        }finally {
            transaction.commit();
            session.close();
            sessionFactory.close();
        }

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
