package everitoken.dao.impl;

import everitoken.dao.BatteryRepository;
import everitoken.entity.BatteryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class BatteryRepositoryImpl implements BatteryRepository {

    private Configuration cfg;

    @Override
    public int add(BatteryEntity entity) throws Exception {
        return 0;
    }

    @Override
    public BatteryEntity load(Integer id) {
        return null;
    }

    @Override
    public BatteryEntity get(Integer id) {
        return null;
    }

    @Override
    public List<BatteryEntity> findAll() {
        return null;
    }

    @Override
    public void persist(BatteryEntity entity) {

    }


    public String save(BatteryEntity entity) {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String name = "";
        try {
            session.save(entity);
            name = entity.getBatteryName();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
        return name;
    }

    @Override
    public void update(BatteryEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
}
