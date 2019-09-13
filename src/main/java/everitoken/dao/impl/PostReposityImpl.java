package everitoken.dao.impl;

import everitoken.dao.PostRepository;
import everitoken.entity.PostEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class PostReposityImpl implements PostRepository {
    private Configuration cfg;
    @Override
    public PostEntity load(Integer id) {
        return null;
    }

    @Override
    public PostEntity get(Integer id) {
        return null;
    }

    @Override
    public List<PostEntity> findAll() {
        return null;
    }

    @Override
    public void persist(PostEntity entity) {

    }

    @Override
    public int add(PostEntity entity) throws Exception {
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
    public void update(PostEntity entity) {

    }

    @Override
    public void delete(Integer id) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            PostEntity c = session.get(PostEntity.class, id);
            session.delete(c);
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
    public void flush() {

    }

    public PostEntity getById(int uid){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        PostEntity postEntity = session.get(PostEntity.class, uid);
        session.close();
        sessionFactory.close();
        return postEntity;
    }

    public List<PostEntity> getByCId(Integer id){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PostEntity postEntity where postEntity.customerId = :id");
        query.setParameter("id", id);
        List<PostEntity> postEntities = query.list();
        if (postEntities.size() > 0) return postEntities;
        else return null;
    }

    public List<PostEntity> getByTime(){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PostEntity postEntity order by postEntity.date desc");
        List<PostEntity> postEntities = query.list();
        if (postEntities.size() > 0) return postEntities;
        else return null;
    }
}
