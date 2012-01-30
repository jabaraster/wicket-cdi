/**
 * 
 */
package com.mycompany.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.entity.Employee;

/**
 * @author jabaraster
 */
public class DaoBaseTest {

    EntityManagerFactory emf;
    EntityManager        em;
    DaoBase<Employee>    dao;

    /**
     * 
     */
    @Test
    public void _constructor() {
        final DaoBase<Employee> service = new DaoBase<Employee>() {
            //
        };
        assertEquals(Employee.class, service.entityType);
    }

    /**
     * 
     */
    @Test
    public void _insert() {
        assertEquals(0, getAll().size());

        final Employee employee = new Employee();
        employee.setNo("00001");
        employee.setName("AAAA");

        this.dao.insert(employee);

        assertNotNull(employee.getId());
        assertEquals(1, getAll().size());
    }

    /**
     * 
     */
    @Test(expected = PersistenceException.class)
    public void _insert_一意制約違反() {
        final Employee emp1 = new Employee();
        emp1.setNo("0001");
        emp1.setName("AAAA");
        this.dao.insert(emp1);

        final Employee emp2 = new Employee();
        emp2.setNo("0001");
        emp2.setName("AAAA+");
        this.dao.insert(emp2);

        this.em.flush();
    }

    /**
     * 
     */
    @Test(expected = EntityExistsException.class)
    public void _insert_二重登録() {
        final Employee employee = new Employee();
        employee.setNo("00001");
        employee.setName("AAAA");
        this.dao.insert(employee);

        this.em.getTransaction().commit();
        this.em.getTransaction().begin();

        this.dao.insert(employee);
    }

    /**
     * 
     */
    @Test
    public void _insertOrUpdate_insert() {
        assertEquals(0, getAll().size());

        final Employee employee = new Employee();
        employee.setNo("00001");
        employee.setName("AAAA");

        this.dao.insertOrUpdate(employee);

        assertNotNull(employee.getId());
        assertEquals(1, getAll().size());
    }

    /**
     * 
     */
    @Test
    public void _insertOrUpdate_update() {
        assertEquals(0, getAll().size());

        final Employee employee = new Employee();
        employee.setNo("00001");
        employee.setName("AAAA");

        this.dao.insertOrUpdate(employee);

        employee.setName("AAAA+");
        this.dao.insertOrUpdate(employee);
        assertEquals(1, getAll().size());
        assertEquals(employee.getName(), getAll().get(0).getName());
    }

    /**
     * 
     */
    @Test
    public void _update() {
        final Employee employee = new Employee();
        employee.setNo("0001");
        employee.setName("AAAA");
        this.dao.insert(employee);
        assertEquals(1, getAll().size());

        this.em.detach(employee);
        assertFalse(this.dao.em.contains(employee));

        employee.setName("AAAA+");
        this.dao.update(employee);
        assertEquals(1, getAll().size());

        this.em.getTransaction().commit();
        assertEquals(1, getAll().size());
        assertEquals(employee.getName(), getAll().get(0).getName());
    }

    /**
     * 
     */
    @After
    public void after() {
        try {
            if (this.em != null) {
                final EntityTransaction tx = this.em.getTransaction();
                if (tx.isActive()) {
                    tx.rollback();
                }
            }
        } catch (final Throwable e) {
            e.printStackTrace();
        }
        if (this.emf != null) {
            this.emf.close();
        }
    }

    /**
     * 
     */
    @Before
    public void before() {
        this.emf = Persistence.createEntityManagerFactory("test");
        this.em = this.emf.createEntityManager();
        this.dao = new DaoBase<Employee>() {
            //
        };
        this.dao.em = this.em;
        this.dao.em.getTransaction().begin();
    }

    private List<Employee> getAll() {
        return this.em.createQuery("select e from Employee e", Employee.class).getResultList();
    }
}
