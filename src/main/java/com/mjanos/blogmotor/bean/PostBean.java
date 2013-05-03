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
import com.mjanos.blogmotor.model.Comment;
import com.mjanos.blogmotor.model.Post;

@Scope("session")
@Component("postBean")
public class PostBean {
    private static final Logger LOG = LoggerFactory.getLogger(PostBean.class);

    @Autowired
    @Qualifier("postGenericDAO")
    private GenericDAO<Post> dao;
    @Autowired
    @Qualifier("commentGenericDAO")
    private GenericDAO<Comment> commentDAO;

    @Autowired
    private UserBean userBean;
    private Post newPost;
    private Post actual;
    private Comment newComment;

    public void saveNewPost() {
        persistPost();
        invalidatePost();
    }

    public void edit() {
        newPost = actual;
    }

    public String delete() {
        LOG.debug("Delete post with id: " + actual.getId());
        dao.delete(actual);
        return "home";
    }

    public void saveNewComment() {
        addPostToNewMessage();
        addOwnerToNewMessage();
        addDateToNewComment();
        persistNewComment();
        invalidateComment();
    }

    private void invalidateComment() {
        newComment = null;
    }

    private void persistNewComment() {
        actual.getComments().add(newComment);
        commentDAO.persist(newComment);
        dao.update(actual);
    }

    private void addDateToNewComment() {
        newComment.setCommentDate(new Date());
    }

    private void addOwnerToNewMessage() {
        newComment.setOwner(userBean.getLoggedInUser());
    }

    private void addPostToNewMessage() {
        newComment.setPost(actual);
    }

    public Comment getNewComment() {
        if (newComment == null) {
            newComment = new Comment();
        }
        return newComment;
    }

    public void setNewComment(final Comment newComment) {
        this.newComment = newComment;
    }

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

    public void invalidatePost() {
        newPost = null;
    }

    public List<Post> getPosts() {
        final List<Post> list = dao.getByCriteria();
        return list;
    }

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
