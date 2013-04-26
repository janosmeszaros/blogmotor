package com.mjanos.blogmotor.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Taxonomy {
    @Id
    @GeneratedValue
    private long id;

    private String Name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taxonomy")
    private final Set<Post> posts = new HashSet<Post>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
