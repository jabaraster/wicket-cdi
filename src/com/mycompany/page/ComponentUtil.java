/**
 * 
 */
package com.mycompany.page;

import javax.persistence.metamodel.Attribute;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import com.mycompany.util.ArgUtil;

/**
 * @author jabaraster
 */
public final class ComponentUtil {

    private ComponentUtil() {
        //
    }

    /**
     * @param <E>
     * @param <V>
     * @param pEntity
     * @param pAttribute
     * @return
     */
    public static <E, V> TextField<V> createTextField(final E pEntity, final Attribute<E, V> pAttribute) {
        ArgUtil.checkNull(pEntity, "pEntity");
        ArgUtil.checkNull(pAttribute, "pAttribute");
        return new TextField<V>(pAttribute.getName(), new PropertyModel<V>(pEntity, pAttribute.getName()));
    }
}
