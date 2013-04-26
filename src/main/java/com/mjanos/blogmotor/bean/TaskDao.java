package com.mjanos.blogmotor.bean;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mjanos.blogmotor.model.Task;

@Component
public class TaskDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(Task task) {
        sessionFactory.getCurrentSession().persist(task);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Task> list() {
        return sessionFactory.getCurrentSession().createCriteria(Task.class).list();
    }

}
