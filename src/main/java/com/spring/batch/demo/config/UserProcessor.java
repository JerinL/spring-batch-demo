package com.spring.batch.demo.config;

import com.spring.batch.demo.entity.User;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<User,User> {
    @Override
    public User process(User item) throws Exception {
        if(item.getEmail().equals("amanda522@gmail.com")){
            return item;
        }else {
            return null;
        }

    }
}
