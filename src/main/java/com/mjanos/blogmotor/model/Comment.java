package com.mjanos.blogmotor.model;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Post post;

    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date commentDate;

    @ManyToOne
    private BlogUser owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public BlogUser getOwner() {
        return owner;
    }

    public void setOwner(BlogUser owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
