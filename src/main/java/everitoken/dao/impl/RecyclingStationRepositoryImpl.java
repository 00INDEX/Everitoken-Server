package everitoken.dao.impl;

import everitoken.dao.RecyclingStationRepository;
import everitoken.entity.RecyclingStationEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class RecyclingStationRepositoryImpl implements RecyclingStationRepository {
    private Configuration cfg;
    @Override
    public RecyclingStationEntity load(Integer id) {
        return null;
    }

    @Override
    public RecyclingStationEntity get(Integer id) {
        return null;
    }

    @Override
    public List<RecyclingStationEntity> findAll() {
        return null;
    }

    @Override
    public void persist(RecyclingStationEntity entity) {

    }

    public RecyclingStationEntity getById(Integer id) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        RecyclingStationEntity rs = null;
        try {
            rs = session.get(RecyclingStationEntity.class, id);
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            Exception exception = new Exception("数据库异常");
            //throw exception;
        }finally {
            transaction.commit();
            session.close();
            sessionFactory.close();
        }
        return rs;
    }

    public RecyclingStationEntity getByName(String name) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from RecyclingStationEntity recycling_station where recycling_station.rsName = :name");
        query.setParameter("name", name);
        RecyclingStationEntity recyclingStationEntity = (RecyclingStationEntity)query.getSingleResult();
        return recyclingStationEntity;
    }

    @Override
    public int add(RecyclingStationEntity entity) throws Exception {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getRsUid();
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

    @Override
    public void update(RecyclingStationEntity entity) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.saveOrUpdate(entity);
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
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
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            RecyclingStationEntity c = session.get(RecyclingStationEntity.class, id);
            session.delete(c);
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            Exception exception = new Exception("数据库异常");
            //throw exception;
        }finally {
            transaction.commit();
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public void flush() {

    }
}
