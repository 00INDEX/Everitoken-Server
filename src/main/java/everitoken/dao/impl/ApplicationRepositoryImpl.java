package everitoken.dao.impl;

import everitoken.dao.ApplicationRepository;
import everitoken.entity.ApplicationEntity;

import java.util.List;

public class ApplicationRepositoryImpl implements ApplicationRepository {
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
        return null;
    }

    @Override
    public void persist(ApplicationEntity entity) {

    }

    @Override
    public int add(ApplicationEntity entity) throws Exception {
        return 0;
    }

    @Override
    public void update(ApplicationEntity entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void flush() {

    }
    @Override
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
}
