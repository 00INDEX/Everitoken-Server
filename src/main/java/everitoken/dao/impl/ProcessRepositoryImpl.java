package everitoken.dao.impl;

import everitoken.dao.ProcessRepository;
import everitoken.entity.ProcessEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ProcessRepositoryImpl implements ProcessRepository {
    private Configuration cfg;
    @Override
    public ProcessEntity load(Integer id) {
        return null;
    }

    @Override
    public ProcessEntity get(Integer id) {
        return null;
    }

    @Override
    public List<ProcessEntity> findAll() {
        return null;
    }

    @Override
    public void persist(ProcessEntity entity) {

    }

    @Override
    public int add(ProcessEntity entity) throws Exception {
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        int uid = -1;
        try {
            session.save(entity);
            uid = entity.getProcessorUid();
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
    public void update(ProcessEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }

    public List getProcess(Integer id){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ProcessEntity process where process.applicantUid =:id");
        List result = query.getResultList();
        return result;
    }

    public List getPassedProcesses(Integer id){
        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ProcessEntity process where process.applicantUid = " + id + " and process.value = 1");


        List result = query.getResultList();
        return result;
    }

    public List getByPId(Integer id) {

        cfg = new Configuration();
        cfg.configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ProcessEntity process where process.processorUid = :id");
        query.setParameter("id", id);
        List applicationEntities = query.getResultList();
        return applicationEntities;
    }
}
