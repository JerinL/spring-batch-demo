package com.spring.batch.demo.config;

import com.spring.batch.demo.entity.User;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User item) throws Exception {
        return item;
    }
}
