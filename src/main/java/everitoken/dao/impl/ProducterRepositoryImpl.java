package everitoken.dao.impl;

import everitoken.dao.ProducterRepository;
import everitoken.entity.ProducerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProducterRepositoryImpl implements ProducterRepository {
    private Configuration cfg;

    @Override
    public ProducerEntity load(Integer id) {
        return null;
    }

    @Override
    public ProducerEntity get(Integer id) {
        ProducerEntity c = new ProducerEntity();
        c.setProducerName("test");
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            c = session.get(ProducerEntity.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
        return c;
    }

    @Override
    public List<ProducerEntity> findAll() {
        return null;
    }

    @Override
    public void persist(ProducerEntity entity) {

    }

    @Override
    public int add(ProducerEntity entity) throws Exception {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getProducerUid();
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
     * 根据id查询数据
     * @param uid 用户id
     * @return
     */
    public ProducerEntity getById(int uid){
        ProducerEntity producerEntity = null;
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            producerEntity = session.get(ProducerEntity.class, uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        session.close();
        sessionFactory.close();
        return producerEntity;
    }

    @Override
    public void update(ProducerEntity entity) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(entity);
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
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
}
