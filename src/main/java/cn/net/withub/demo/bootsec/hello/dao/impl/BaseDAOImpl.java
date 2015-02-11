/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.dao.impl;

import cn.net.withub.demo.bootsec.hello.dao.BaseDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class BaseDAOImpl<E> extends HibernateDaoSupport implements BaseDAO<E> {

    protected Class<?> entityClass = (Class<?>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];

    @Autowired
    public void setSessionFactoryBean(SessionFactory factory) {
        this.setSessionFactory(factory);
    }

    @Override
    public void save(E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(E entity) {
        currentSession().delete(entity);
    }

    @Override
    public List<E> findAll() {
        return currentSession().createCriteria(entityClass).list();
    }

    @Override
    public int count(DetachedCriteria criteria) {
        Session session = currentSession();
        return ((Number) criteria.getExecutableCriteria(session).setProjection(Projections.rowCount())).intValue();
    }

    @Override
    public List<E> search(DetachedCriteria criteria, int start, int limit) {
        Session session = currentSession();
        return criteria.getExecutableCriteria(session).setFirstResult(start).setMaxResults(limit).list();
    }

    @Override
    public void deleteByKey(Serializable id) {
        Session session = currentSession();
        session.delete(session.load(entityClass, id));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    public E findOne(Serializable id) {
        Session session = currentSession();
        return (E) session.load(entityClass, id);
    }

}
