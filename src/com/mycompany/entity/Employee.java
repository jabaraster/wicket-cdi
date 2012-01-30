/**
 * 
 */
package com.mycompany.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author jabaraster
 */
@Entity
public class Employee extends EntityBase<Employee> {
    private static final long serialVersionUID = 8748033290035691356L;

    private static final int  NO_DIGIT         = 50;
    private static final int  NAME_DIGIT       = 100;

    /**
     * 
     */
    @Column(nullable = false, unique = true, length = NO_DIGIT * 3)
    protected String          no;

    /**
     * 
     */
    @Column(nullable = false, length = NAME_DIGIT * 3)
    protected String          name;

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     */
    /**
     * @return the no
     */
    public String getNo() {
        return this.no;
    }

    /**
     * @param pName the name to set
     */
    public void setName(final String pName) {
        this.name = pName;
    }

    /**
     * @param pNo the no to set
     */
    public void setNo(final String pNo) {
        this.no = pNo;
    }

}
