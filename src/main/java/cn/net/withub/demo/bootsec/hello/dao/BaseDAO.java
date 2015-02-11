/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;

/**
 *
 * @author Diluka
 * @param <E>
 * @param <K>
 */
public interface BaseDAO<E> {

    void save(E entity);

    void delete(E entity);

    void deleteByKey(Serializable id);

    List<E> findAll();

    E findOne(Serializable id);

    int count(DetachedCriteria criteria);

    List<E> search(DetachedCriteria criteria, int start, int limit);

}
