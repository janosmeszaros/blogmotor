package com.mjanos.blogmotor.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

public class Post {
    @Id
    @GeneratedValue
    private long id;

    private String post;

    @ManyToOne
    private Taxonomy taxonomy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date postDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    private final Set<Comment> comments = new HashSet<Comment>();

    @ManyToOne
    private BlogUser owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public BlogUser getOwner() {
        return owner;
    }

    public void setOwner(BlogUser owner) {
        this.owner = owner;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
