package com.mjanos.blogmotor.bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.BlogUser;

@Scope("session")
@Component("userBean")
public class UserBean {
    private final Logger LOG = LoggerFactory.getLogger(UserBean.class);

    @Autowired
    @Qualifier("userGenericDAO")
    private GenericDAO<BlogUser> dao;

    private BlogUser user = new BlogUser();
    private BlogUser loggedInUser;

    public void register() {
        LOG.debug(getUser().toString());
        dao.persist(user);
        invalidate();
    }

    private void invalidate() {
        user = new BlogUser();
    }

    public void signIn() {
        final List<BlogUser> list = getUserFromDb();
        storeUser(list);
        invalidate();
    }

    private void storeUser(final List<BlogUser> list) {
        if (list.size() == 1) {
            loggedInUser = list.get(0);
        } else {
            final FacesContext context = FacesContext.getCurrentInstance();
            final FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wrong email or password!", "Wrong email or password!");
            context.addMessage("loginForm:signin", message);
        }
    }

    private List<BlogUser> getUserFromDb() {
        final SimpleExpression email = Restrictions.eq("email", user.getEmail());
        final SimpleExpression pass = Restrictions.eq("password", user.getPassword());

        final List<BlogUser> list = dao.getByCriteria(Restrictions.and(email, pass));
        LOG.debug(list.toString());
        return list;
    }

    public void signOut() {
        LOG.debug("Logging out " + loggedInUser.getName());
        loggedInUser = null;
    }

    public void validatePassword(final FacesContext context, final UIComponent toValidate, final Object value) {
        final String confirmPassword = (String) value;

        final UIInput passwordField = (UIInput) context.getViewRoot().findComponent("registerForm:pass");
        if (passwordField == null) {
            throw new IllegalArgumentException(String.format("Unable to find component."));
        }
        final String password = (String) passwordField.getValue();

        if (!confirmPassword.equals(password)) {
            final FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!", "Passwords do not match!");
            throw new ValidatorException(message);
        }
    }

    public BlogUser getUser() {
        return user;
    }

    public BlogUser getLoggedInUser() {
        return loggedInUser;
    }
}
