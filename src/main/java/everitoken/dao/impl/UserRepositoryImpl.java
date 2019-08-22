package everitoken.dao.impl;

import everitoken.dao.UserRepository;
import everitoken.entity.CustomerEntity;
import everitoken.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private Configuration cfg;

    @Override
    public UserEntity load(Integer id) {
        return null;
    }

    @Override
    public UserEntity get(Integer id) {
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public void persist(UserEntity entity) {

    }

    @Override
    public int add(UserEntity entity) throws Exception {
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

    /**
     * 根据邮箱查询数据
     * @param email 用户邮箱
     * @return
     */
    public UserEntity getByEmail(String email) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from UserEntity user where user.email = :email");
        query.setParameter("email", email);
        UserEntity userEntity = (UserEntity) query.getSingleResult();
        transaction.commit();
        session.close();
        sessionFactory.close();
        return userEntity;
    }

    /**
     * 根据id查询数据
     * @param uid 用户id
     * @return
     */
    public UserEntity getById(int uid){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        UserEntity userEntity = session.get(UserEntity.class, uid);
        session.close();
        sessionFactory.close();
        return userEntity;
    }

    /**
     * 根据用户名查询数据
     * @param username 用户邮箱
     * @return
     */
    public UserEntity getByUsername(String username) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from UserEntity user where user.username = :username");
        query.setParameter("username", username);
        List list = query.getResultList();
        transaction.commit();
        session.close();
        sessionFactory.close();
        if (list.size() > 0) return (UserEntity)list.get(0);
        return null;
    }

    @Override
    public void update(UserEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
}
