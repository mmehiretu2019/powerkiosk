package com.powerkiosk.admin.dao;

import com.powerkiosk.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InMemoryUserDao {

    private Map<String, User> userMap = new HashMap<>();

    public User add(User user){
        String id = UUID.randomUUID().toString();
        user.setId(id);

        userMap.put(id, user);
        return user;
    }
}
