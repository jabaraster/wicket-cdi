package com.mycompany.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.mycompany.util.ExceptionUtil;

/**
 * @param <E>
 */
@MappedSuperclass
public abstract class EntityBase<E extends EntityBase<E>> implements Serializable {
    private static final long   serialVersionUID = 8859623522184687965L;

    private static final Method CLONE_METHOD     = getCloneMethod();

    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long              id;

    /**
     * 
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date              updated          = new Date();

    /**
     * 
     */
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date              created          = new Date();

    /**
     * @return the created
     */
    public Date getCreated() {
        return this.created;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the updated
     */
    public Date getUpdated() {
        return this.updated;
    }

    /**
     * @param pCreated the created to set
     */
    public void setCreated(final Date pCreated) {
        this.created = copy(pCreated);
    }

    /**
     * @param pUpdated the updated to set
     */
    public void setUpdated(final Date pUpdated) {
        this.updated = pUpdated;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * @param <V>
     * @param pValue
     * @return
     */
    @SuppressWarnings("unchecked")
    protected static <V> V copy(final V pValue) {
        if (pValue == null) {
            return null;
        }
        if (pValue instanceof Cloneable) {
            return (V) cloneValue(pValue);
        }

        if (pValue instanceof Serializable) {
            return (V) cloneSerializable(pValue);
        }

        return pValue;
    }

    private static Object cloneSerializable(final Object pValue) {
        try {
            ByteArrayOutputStream memory = new ByteArrayOutputStream();
            final ObjectOutputStream objOut = new ObjectOutputStream(memory);

            objOut.writeObject(pValue);

            final byte[] data = memory.toByteArray();
            memory = null;

            final ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data));
            return objIn.readObject();

        } catch (final IOException e) {
            throw ExceptionUtil.rethrow(e);
        } catch (final ClassNotFoundException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }

    private static Object cloneValue(final Object pValue) {
        try {
            return CLONE_METHOD.invoke(pValue);
        } catch (final IllegalAccessException e) {
            throw ExceptionUtil.rethrow(e);
        } catch (final InvocationTargetException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }

    private static Method getCloneMethod() {
        try {
            final Method cloneMethod = Object.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
            return cloneMethod;
        } catch (final NoSuchMethodException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }
}
