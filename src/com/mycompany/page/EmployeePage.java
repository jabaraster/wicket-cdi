/**
 * 
 */
package com.mycompany.page;

import static com.mycompany.page.ComponentUtil.createTextField;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.mycompany.entity.Employee;
import com.mycompany.entity.Employee_;
import com.mycompany.service.IEmployeeService;

/**
 * @author jabaraster
 */
public class EmployeePage extends WebPage {
    private static final long serialVersionUID = -8511294355100339291L;

    private final Employee    target;

    private Link<?>           goHome;

    Form<?>                   form;
    TextField<String>         noText;
    TextField<String>         nameText;
    Button                    submitter;

    FeedbackPanel             messages;

    @Inject
    IEmployeeService          employeeService;

    /**
     * 
     */
    public EmployeePage() {
        this(new Employee());
    }

    /**
     * @param pTarget
     */
    public EmployeePage(final Employee pTarget) {
        this.target = pTarget;
        this.add(getGoHome());
        this.add(getForm());
        this.add(getMessages());
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new Form<Object>("form");
            this.form.add(getNoText());
            this.form.add(getNameText());
            this.form.add(getSubmitter());
        }
        return this.form;
    }

    private Link<?> getGoHome() {
        if (this.goHome == null) {
            this.goHome = new Link<Object>("goHome") {
                private static final long serialVersionUID = -4951547438918297246L;

                @Override
                public void onClick() {
                    this.setResponsePage(getApplication().getHomePage());
                }
            };
        }
        return this.goHome;
    }

    private FeedbackPanel getMessages() {
        if (this.messages == null) {
            this.messages = new FeedbackPanel("messages");
        }
        return this.messages;
    }

    private TextField<String> getNameText() {
        if (this.nameText == null) {
            this.nameText = createTextField(this.target, Employee_.name);
        }
        return this.nameText;
    }

    private TextField<String> getNoText() {
        if (this.noText == null) {
            this.noText = createTextField(this.target, Employee_.no);
        }
        return this.noText;
    }

    private Button getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new Button("submitter") {
                private static final long serialVersionUID = 6261185085275193986L;

                public void onSubmit() {
                    on_doButton_onSubmit(this);
                }
            };
        }
        return this.submitter;
    }

    private void on_doButton_onSubmit(final Button pButton) {
        this.employeeService.insertOrUpdate(this.target);
        pButton.setResponsePage(HomePage.class);
    }

}
