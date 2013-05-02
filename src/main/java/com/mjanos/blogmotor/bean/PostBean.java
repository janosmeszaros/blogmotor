package com.mjanos.blogmotor.bean;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Post;

@Scope("session")
@Component("postBean")
public class PostBean {

    @Autowired
    @Qualifier("postGenericDAO")
    private GenericDAO<Post> dao;

    @Autowired
    private UserBean userBean;
    private Post newPost;

    public void saveNewPost() {
        persistPost();
        invalidate();
    }

    private void persistPost() {
        newPost.setPostDate(new Date());
        newPost.setOwner(userBean.getLoggedInUser());
        dao.persist(newPost);
    }

    private void invalidate() {
        newPost = null;
    }

    public List<Post> getPosts() {
        return dao.getByCriteria();
    }

    public Post getNewPost() {
        if (newPost == null) {
            newPost = new Post();
        }
        return newPost;
    }

}
