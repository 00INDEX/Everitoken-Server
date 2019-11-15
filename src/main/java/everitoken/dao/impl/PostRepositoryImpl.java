package everitoken.dao.impl;

import everitoken.dao.PostRepository;
import everitoken.entity.PostEntity;
import everitoken.entity.RecyclingStationEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Collections;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
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
        return 0;
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
            transaction.rollback();
            Exception exception = new Exception("数据库异常");
        }finally {
            transaction.commit();
            session.close();
            sessionFactory.close();
        }
    }

    public List RandomGet(){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PostEntity post");
        List postEntities = query.getResultList();
        Collections.shuffle(postEntities);
        postEntities = postEntities.subList(0, postEntities.size() < 10 ? postEntities.size() : 10);
        return postEntities;
    }

    public List GetByUseID(Integer id){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PostEntity post where customer_id =:id");
        query.setParameter("id",id);
        List postEntities = query.getResultList();
        Collections.shuffle(postEntities);
        postEntities = postEntities.subList(0, postEntities.size() < 10 ? postEntities.size() : 10);
        return postEntities;
    }

    @Override
    public void flush() {

    }
}
