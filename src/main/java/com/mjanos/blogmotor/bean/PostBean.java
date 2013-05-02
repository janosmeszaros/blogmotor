package com.mjanos.blogmotor.bean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.dao.GenericDAO;
import com.mjanos.blogmotor.model.Post;

@Component("postBean")
public class PostBean {

    @Autowired
    @Qualifier("postGenericDAO")
    private GenericDAO<Post> dao;

    private List<Post> posts;

    public List<Post> getPosts() {
        if (posts == null) {
            posts = dao.getByCriteria();
        }
        return posts;
    }

}
