package com.mjanos.blogmotor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String post;

    @NotEmpty
    private String title;

    @ManyToOne
    private Taxonomy taxonomy;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date postDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Comment> comments = new ArrayList<Comment>();

    @ManyToOne
    private BlogUser owner;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(final String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(final Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(final Date postDate) {
        this.postDate = postDate;
    }

    public BlogUser getOwner() {
        return owner;
    }

    public void setOwner(final BlogUser owner) {
        this.owner = owner;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(final List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
