/**
 * 
 */
package com.mycompany.page;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.mycompany.entity.Employee;
import com.mycompany.entity.Employee_;
import com.mycompany.entity.EntityBase_;
import com.mycompany.service.IEmployeeService;
import com.mycompany.util.FormatUtil;

/**
 * @author jabaraster
 */
public class HomePage extends WebPage {
    private static final long              serialVersionUID = 3588561381855276997L;

    /**
     * 
     */
    @Inject
    protected IEmployeeService             employeeService;

    AjaxFallbackDefaultDataTable<Employee> employees;

    Link<?>                                goNewEmployeePage;

    /**
     * 
     */
    public HomePage() {
        this.add(getTenants());
        this.add(getGoNewEmployeePage());
    }

    private List<IColumn<Employee>> createColumns() {
        final List<IColumn<Employee>> ret = new ArrayList<IColumn<Employee>>();
        ret.add(new LabelColumn<Employee>("ID", EntityBase_.id.getName()));
        ret.add(new LabelColumn<Employee>("No", Employee_.no.getName()));
        ret.add(new LabelColumn<Employee>("名称", Employee_.name.getName()));
        ret.add(new LabelColumn<Employee>("作成日時", EntityBase_.created.getName()) {
            private static final long serialVersionUID = -4453243779783219681L;

            @Override
            protected IModel<?> createLabelModel(final IModel<Employee> pRowModel) {
                return new Model<String>(FormatUtil.formatDatetime(pRowModel.getObject().getCreated()));
            }
        });
        ret.add(new LabelColumn<Employee>("更新日時", EntityBase_.updated.getName()) {
            private static final long serialVersionUID = 691942650983762278L;

            @Override
            protected IModel<?> createLabelModel(final IModel<Employee> pRowModel) {
                return new Model<String>(FormatUtil.formatDatetime(pRowModel.getObject().getUpdated()));
            }
        });
        return ret;
    }

    private Link<?> getGoNewEmployeePage() {
        if (this.goNewEmployeePage == null) {
            this.goNewEmployeePage = new Link<Object>("goNewEmployeePage") {
                private static final long serialVersionUID = 1638117217205508742L;

                @Override
                public void onClick() {
                    setResponsePage(new EmployeePage());
                }
            };
        }
        return this.goNewEmployeePage;
    }

    private DataTable<Employee> getTenants() {
        if (this.employees == null) {
            final List<IColumn<Employee>> columns = createColumns();
            final ISortableDataProvider<Employee> provider = new EmployeesProvider();
            this.employees = new AjaxFallbackDefaultDataTable<Employee>("employees", columns, provider, 100);
        }
        return this.employees;
    }

    private class EmployeesProvider extends SortableDataProvider<Employee> {
        private static final long serialVersionUID = -8883945448078919593L;

        EmployeesProvider() {
            this.setSort("no", SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends Employee> iterator(final int pFirst, final int pCount) {
            return HomePage.this.employeeService.find(pFirst, pCount, getSort()).iterator();
        }

        @Override
        public IModel<Employee> model(final Employee pObject) {
            return new AbstractReadOnlyModel<Employee>() {
                private static final long serialVersionUID = 4717362410922741671L;

                @Override
                public Employee getObject() {
                    return pObject;
                }
            };
        }

        @Override
        public int size() {
            return (int) HomePage.this.employeeService.countAll();
        }
    }

    private static class LabelColumn<E> extends PropertyColumn<E> {
        private static final long serialVersionUID = -1848496469025584338L;

        LabelColumn(final String pLabel, final String pPropertyExpression) {
            super(new Model<String>(pLabel), pPropertyExpression, pPropertyExpression);
        }
    }
}