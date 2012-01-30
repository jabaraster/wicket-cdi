/**
 * 
 */
package com.mycompany;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.protocol.http.WebApplication;

import com.mycompany.page.EmployeePage;
import com.mycompany.page.HomePage;
import com.mycompany.util.ExceptionUtil;

/**
 * @author jabaraster
 */
public class WicketApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();

        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
        getMarkupSettings().setDefaultMarkupEncoding(getRequestCycleSettings().getResponseRequestEncoding());

        this.mountPage("newEmployee", EmployeePage.class);

        try {
            final BeanManager manager = InitialContext.doLookup("java:comp/BeanManager");
            getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
                @Override
                public void onInstantiation(final Component pComponent) {
                    inject(manager, pComponent);
                }
            });
        } catch (final NamingException e) {
            throw ExceptionUtil.rethrow(e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void inject(final BeanManager pBeanManager, final Component pTargetObject) {
        final Class pType = pTargetObject.getClass();
        final Bean<Object> bean = (Bean<Object>) pBeanManager.resolve(pBeanManager.getBeans(pType));
        final CreationalContext<Object> cc = pBeanManager.createCreationalContext(bean);
        final AnnotatedType<Object> at = pBeanManager.createAnnotatedType(pType);
        final InjectionTarget<Object> it = pBeanManager.createInjectionTarget(at);
        it.inject(pTargetObject, cc);
    }
}
