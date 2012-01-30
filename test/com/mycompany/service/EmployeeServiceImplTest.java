/**
 * 
 */
package com.mycompany.service;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mycompany.entity.Employee;

/**
 * @author jabaraster
 */
public class EmployeeServiceImplTest {

    EntityManagerFactory emf;
    EntityManager        em;
    EmployeeServiceImpl  service;

    /**
     * 
     */
    @Test
    public void _countAll() {
        assertEquals(0, this.service.countAll());
    }

    /**
     * 
     */
    @Test
    public void _find_int_int_SortParam() {
        for (final Employee e : this.service.find(0, 10, new SortParam("no", false))) {
            System.out.println(e);
        }
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
        this.service = new EmployeeServiceImpl();
        this.service.em = this.em;
        this.em.getTransaction().begin();
    }

}
