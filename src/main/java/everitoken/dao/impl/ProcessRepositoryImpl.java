package everitoken.dao.impl;

import everitoken.dao.ProcessRepository;
import everitoken.entity.ProcessEntity;

import java.util.List;

public class ProcessRepositoryImpl implements ProcessRepository {
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
        return 0;
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
}