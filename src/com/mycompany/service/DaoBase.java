/**
 * 
 */
package com.mycompany.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ejb.TransactionAttribute;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.commons.beanutils.PropertyUtils;

import com.mycompany.entity.EntityBase;
import com.mycompany.util.ArgUtil;
import com.mycompany.util.ExceptionUtil;

/**
 * @author jabaraster
 * @param <E>
 */
public abstract class DaoBase<E extends EntityBase<E>> {

    /**
     * 
     */
    protected final Class<E> entityType;

    /**
     * 
     */
    @PersistenceContext(unitName = "pu")
    protected EntityManager  em;

    /**
     * 
     */
    public DaoBase() {
        this.entityType = findType();
    }

    /**
     * @param pEntity
     * @throws EntityExistsException pEntityが既に永続化されていた場合.
     */
    @TransactionAttribute
    public void insert(final E pEntity) throws EntityExistsException {
        ArgUtil.checkNull(pEntity, "pEntity");

        if (this.em.contains(pEntity)) {
            throw new EntityExistsException();
        }

        this.em.persist(pEntity);
    }

    /**
     * @param pEntity
     */
    @TransactionAttribute
    public void insertOrUpdate(final E pEntity) {
        ArgUtil.checkNull(pEntity, "pEntity");
        if (this.em.contains(pEntity)) {
            this.update(pEntity);
        } else {
            this.insert(pEntity);
        }
    }

    /**
     * @param pEntity
     */
    @TransactionAttribute
    public void update(final E pEntity) {
        ArgUtil.checkNull(pEntity, "pEntity");
        if (!this.em.contains(pEntity)) {
            final E forUpdate = this.em.merge(pEntity);
            try {
                PropertyUtils.copyProperties(forUpdate, pEntity);
            } catch (final Exception e) {
                throw ExceptionUtil.rethrow(e);
            }
        }
    }

    /**
     * @param <T>
     * @param pResultType
     * @return
     */
    protected <T> CriteriaQuery<T> createCriteria(final Class<T> pResultType) {
        ArgUtil.checkNull(pResultType, "pResultType");
        return this.em.getCriteriaBuilder().createQuery(pResultType);
    }

    /**
     * @return
     */
    protected CriteriaBuilder getCriteriaBuilder() {
        return this.em.getCriteriaBuilder();
    }

    @SuppressWarnings("unchecked")
    private Class<E> findType() {
        for (Class<?> c = this.getClass(); c != Object.class; c = c.getSuperclass()) {
            final Type t = c.getGenericSuperclass();
            if (ParameterizedType.class.isInstance(t)) {
                final Type[] typeArguments = ParameterizedType.class.cast(t).getActualTypeArguments();
                if (typeArguments.length >= 1 && Class.class.isInstance(typeArguments[0])) {
                    return Class.class.cast(typeArguments[0]);
                }
            }
        }
        throw new IllegalStateException();
    }
}
