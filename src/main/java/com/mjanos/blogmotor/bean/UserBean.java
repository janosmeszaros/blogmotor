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
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.BlogUser;

@Component("userBean")
public class UserBean {
    private final Logger LOG = LoggerFactory.getLogger(UserBean.class);

    @Autowired
    @Qualifier("userGenericDAO")
    private GenericDAO<BlogUser> dao;

    private BlogUser user = new BlogUser();

    public void register() {
        LOG.debug(getUser().toString());
        dao.persist(user);
        invalidate();
    }

    private void invalidate() {
        user = new BlogUser();
    }

    public void signIn() {
        SimpleExpression email = Restrictions.eq("email", user.getEmail());
        SimpleExpression pass = Restrictions.eq("password", user.getPassword());

        List<BlogUser> list = dao.getByCriteria(Restrictions.and(email, pass));
        LOG.debug(list.toString());
    }

    public BlogUser getUser() {
        return user;
    }

    public void validatePassword(FacesContext context, UIComponent toValidate, Object value) {
        String confirmPassword = (String) value;

        UIInput passwordField = (UIInput) context.getViewRoot().findComponent("registerForm:pass");
        if (passwordField == null)
            throw new IllegalArgumentException(String.format("Unable to find component."));
        String password = (String) passwordField.getValue();

        if (!confirmPassword.equals(password)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match!", "Passwords do not match!");
            throw new ValidatorException(message);
        }
    }
}
