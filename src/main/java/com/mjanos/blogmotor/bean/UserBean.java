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

/**
 * Bean for handling users.
 * @author Janos_Gyula_Meszaros
 */
@Scope("session")
@Component("userBean")
public class UserBean {
    private static final String PASSWORDS_DO_NOT_MATCH = "Passwords do not match!";
    private static final String WRONG_EMAIL_OR_PASSWORD = "Wrong email or password!";
    private static final Logger LOG = LoggerFactory.getLogger(UserBean.class);

    @Autowired
    @Qualifier("userGenericDAO")
    private GenericDAO<BlogUser> dao;

    private BlogUser user = new BlogUser();
    private BlogUser loggedInUser;
    private List<BlogUser> users;
    private BlogUser userToEdit;

    /**
     * Register user.
     */
    public void register() {
        LOG.debug(getUser().toString());
        dao.persist(user);
        invalidate();
    }

    private void invalidate() {
        user = new BlogUser();
    }

    /**
     * Prepare the {@link BlogUser} edit to get the actual user from db.
     * @param id
     *            id of {@link BlogUser}
     */
    public void edit(final long id) {
        userToEdit = dao.getById(id);
    }

    /**
     * Save the edited user.
     */
    public void saveEditedUser() {
        dao.update(userToEdit);
        invalidateUserEdit();
        getUsersFromDb();
    }

    /**
     * Invalidate edit user.
     */
    public void invalidateUserEdit() {
        userToEdit = null;
    }

    /**
     * List the existing users.
     * @return view name.
     */
    public String listUsers() {
        LOG.debug("List users.");
        getUsersFromDb();
        return "userlist";
    }

    private void getUsersFromDb() {
        users = dao.getByCriteria();
    }

    /**
     * Login user.
     */
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
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, WRONG_EMAIL_OR_PASSWORD, WRONG_EMAIL_OR_PASSWORD);
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

    /**
     * Sign out signed in user.
     */
    public void signOut() {
        LOG.debug("Logging out " + loggedInUser.getName());
        loggedInUser = null;
    }

    /**
     * Delete the selected user.
     * @param id
     *            id of user to delete.
     */
    public void delete(final long id) {
        final BlogUser userToDelete = dao.getById(id);
        dao.delete(userToDelete);
        getUsersFromDb();
    }

    /**
     * Validate the password in registration.
     * @param context
     *            context
     * @param toValidate
     *            toValidate
     * @param value
     *            value
     */
    public void validatePassword(final FacesContext context, final UIComponent toValidate, final Object value) {
        final String confirmPassword = (String) value;

        final UIInput passwordField = (UIInput) context.getViewRoot().findComponent("registerForm:pass");
        if (passwordField == null) {
            throw new IllegalArgumentException(String.format("Unable to find component."));
        }
        final String password = (String) passwordField.getValue();

        if (!confirmPassword.equals(password)) {
            final FacesMessage message =
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, PASSWORDS_DO_NOT_MATCH, PASSWORDS_DO_NOT_MATCH);
            throw new ValidatorException(message);
        }
    }

    public List<BlogUser> getUsers() {
        return users;
    }

    public void setUsers(final List<BlogUser> users) {
        this.users = users;
    }

    public BlogUser getUser() {
        return user;
    }

    public BlogUser getLoggedInUser() {
        return loggedInUser;
    }

    public BlogUser getUserToEdit() {
        return userToEdit;
    }

    public void setUserToEdit(final BlogUser userToEdit) {
        this.userToEdit = userToEdit;
    }

}
