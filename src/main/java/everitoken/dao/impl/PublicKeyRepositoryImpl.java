package everitoken.dao.impl;

import everitoken.dao.PublicKeyRepository;
import everitoken.entity.PublicKeyEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class PublicKeyRepositoryImpl implements PublicKeyRepository {
    private Configuration cfg;
    @Override
    public PublicKeyEntity load(Integer id) {
        return null;
    }

    @Override
    public PublicKeyEntity get(Integer id) {
        return null;
    }

    @Override
    public List<PublicKeyEntity> findAll() {
        return null;
    }

    @Override
    public void persist(PublicKeyEntity entity) {

    }

    @Override
    public int add(PublicKeyEntity entity) throws Exception {
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
    public void update(PublicKeyEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }

    public PublicKeyEntity getByPK(String id) {

        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from PublicKeyEntity public_key where public_key.publicKey = :id");
        query.setParameter("id", id);
        List<PublicKeyEntity> applicationEntities = query.getResultList();
        int i=0;
        if (applicationEntities.size()>0){
            return applicationEntities.get(0);
        }
        return null;
    }
}
