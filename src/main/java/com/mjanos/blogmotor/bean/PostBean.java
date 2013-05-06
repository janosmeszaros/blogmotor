package com.mjanos.blogmotor.bean;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Post;

/**
 * Post bean.
 * @author Janos_Gyula_Meszaros
 */
@Scope("session")
@Component("postBean")
public class PostBean {
    private static final Logger LOG = LoggerFactory.getLogger(PostBean.class);

    @Autowired
    @Qualifier("postGenericDAO")
    private GenericDAO<Post> dao;

    @Autowired
    private UserBean userBean;
    private Post newPost;
    private Post actual;

    /**
     * Save the new post to db.
     */
    public void saveNewPost() {
        persistPost();
        invalidatePost();
    }

    /**
     * Set the actually selected post to the edit.
     */
    public void edit() {
        newPost = actual;
    }

    /**
     * Delete the selected post.
     * @return to home screen.
     */
    public String delete() {
        LOG.debug("Delete post with id: " + actual.getId());
        dao.delete(actual);
        return "home";
    }

    /**
     * Updates the specified {@link Post}
     * @param post
     *            {@link Post} to update.
     */
    public void update(final Post post) {
        dao.update(post);
    }

    /**
     * Show actual post in detailed window.
     * @param id
     *            actual post's id.
     * @return view
     */
    public String showActual(final long id) {
        actual = dao.getById(id);
        return "post";
    }

    private void persistPost() {
        newPost.setPostDate(new Date());
        newPost.setOwner(userBean.getLoggedInUser());
        dao.persist(newPost);
        LOG.debug("Save post: " + newPost);
    }

    /**
     * Invalidate post.
     */
    public void invalidatePost() {
        newPost = null;
    }

    /**
     * Get all posts.
     * @return list of {@link Post}
     */
    public List<Post> getPosts() {
        final List<Post> list = dao.getByCriteria();
        return list;
    }

    /**
     * Return new post.
     * @return {@link Post}
     */
    public Post getNewPost() {
        if (newPost == null) {
            newPost = new Post();
        }
        return newPost;
    }

    public Post getActual() {
        return actual;
    }

    public void setActual(final Post actual) {
        this.actual = actual;
    }

}
