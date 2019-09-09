package everitoken.dao.impl;

import everitoken.dao.ApplicationRepository;
import everitoken.entity.ApplicationEntity;
import everitoken.entity.ProcessEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ApplicationRepositoryImpl implements ApplicationRepository {
    private Configuration cfg;
    @Override
    public ApplicationEntity load(Integer id) {
        return null;
    }

    @Override
    public ApplicationEntity get(Integer id) {
        return null;
    }

    @Override
    public List<ApplicationEntity> findAll() {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ApplicationEntity application");
        List applicationEntities = query.getResultList();
        return applicationEntities;
    }



    @Override
    public void persist(ApplicationEntity entity) {

    }

    @Override
    public int add(ApplicationEntity entity) throws Exception {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getUid();
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
    public void update(ApplicationEntity entity) {
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
    public List RandomGet(){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select top 10 from ApplicationEntity application order by newid()");
        List applicationEntities = query.getResultList();
        return applicationEntities;
    }

    public List getByAId(Integer id) {

        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ApplicationEntity application where application.applicantUid = :id");
        query.setParameter("id", id);
        List applicationEntities = query.getResultList();
        return applicationEntities;
    }

    public ApplicationEntity getById(Integer id) {
        ApplicationEntity applicationEntity = null;
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            applicationEntity = session.get(ApplicationEntity.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        session.close();
        sessionFactory.close();
        return applicationEntity;
    }
}
