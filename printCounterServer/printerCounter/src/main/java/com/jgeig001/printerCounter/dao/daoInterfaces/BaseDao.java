package com.jgeig001.printerCounter.dao.daoInterfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    @Transactional
    Optional<T> get(int id);

    @Transactional
    List<T> getAll();

    @Transactional
    void save(T t);

    @Transactional
    void update(T t);

    @Transactional
    void delete(T t);

}
