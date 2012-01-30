/**
 * 
 */
package com.mycompany.service;

import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;

import com.mycompany.entity.Employee;

/**
 * @author jabaraster
 */
public interface IEmployeeService {

    /**
     * @return
     */
    long countAll();

    /**
     * @param pFirst
     * @param pCount
     * @param pSort
     * @return
     */
    List<Employee> find(int pFirst, int pCount, SortParam pSort);

    /**
     * @return
     */
    List<Employee> getAll();

    /**
     * @param pEntity
     */
    void insertOrUpdate(Employee pEntity);
}
