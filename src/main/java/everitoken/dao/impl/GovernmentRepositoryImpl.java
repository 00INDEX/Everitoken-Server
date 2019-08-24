package everitoken.dao.impl;

import everitoken.dao.GovernmentRepository;
import everitoken.entity.GovernmentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class GovernmentRepositoryImpl implements GovernmentRepository {
    private Configuration cfg;

    @Override
    public GovernmentEntity load(Integer id) {
        return null;
    }

    @Override
    public GovernmentEntity get(Integer id) {
        return null;
    }

    @Override
    public List<GovernmentEntity> findAll() {
        return null;
    }

    @Override
    public void persist(GovernmentEntity entity) {

    }

    @Override
    public int add(GovernmentEntity entity) throws Exception {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getGovernmentUid();
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
    public GovernmentEntity getById(int uid){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        GovernmentEntity governmentEntity = session.get(GovernmentEntity.class, uid);
        session.close();
        sessionFactory.close();
        return governmentEntity;
    }

    @Override
    public void update(GovernmentEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
}
