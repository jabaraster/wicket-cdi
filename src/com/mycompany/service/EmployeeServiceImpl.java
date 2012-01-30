/**
 * 
 */
package com.mycompany.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import com.mycompany.entity.Employee;

/**
 * @author jabaraster
 */
@Stateless
public class EmployeeServiceImpl extends DaoBase<Employee> implements IEmployeeService {

    /**
     * @see com.mycompany.service.IEmployeeService#countAll()
     */
    @Override
    public long countAll() {
        final CriteriaBuilder builder = getCriteriaBuilder();
        final CriteriaQuery<Long> query = this.createCriteria(Long.class);
        final Root<Employee> root = query.from(Employee.class);
        query.select(builder.count(root));
        final TypedQuery<Long> q = this.em.createQuery(query);
        return q.getSingleResult().longValue();
    }

    @Override
    public List<Employee> find(final int pFirst, final int pCount, final SortParam pSort) {
        final CriteriaBuilder builder = getCriteriaBuilder();
        final CriteriaQuery<Employee> query = this.createCriteria(Employee.class);
        final Root<Employee> root = query.from(Employee.class);

        Order ord;
        if (pSort.isAscending()) {
            ord = builder.asc(root.get(pSort.getProperty()));
        } else {
            ord = builder.desc(root.get(pSort.getProperty()));
        }
        query.orderBy(ord);
        return this.em.createQuery(query).setFirstResult(pFirst).setMaxResults(pCount).getResultList();
    }

    /**
     * @return
     */
    @TransactionAttribute
    public List<Employee> getAll() {
        final CriteriaQuery<Employee> query = this.createCriteria(Employee.class);
        query.from(Employee.class);
        return this.em.createQuery(query).getResultList();
    }
}
