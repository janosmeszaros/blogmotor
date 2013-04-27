package com.mjanos.blogmotor.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mjanos.blogmotor.model.BlogUser;

@Component("userBean")
public class UserBean {
    private final Logger LOG = LoggerFactory.getLogger(UserBean.class);

    private final BlogUser user = new BlogUser();

    public void register() {
        LOG.debug(getUser().toString());
    }

    public BlogUser getUser() {
        return user;
    }
}
