package com.powerkiosk.dao;

import com.powerkiosk.model.SignUpUser;
import com.powerkiosk.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InMemoryUserDao {

    private Map<String, User> userMap = new HashMap<>();

    public User add(User user){
        user.setId(UUID.randomUUID());

        userMap.put(user.getId().toString(), user);
        return user;
    }

    public User add(SignUpUser user){
        User basicUser = new User();
        basicUser.setEmail(user.getEmail());
        basicUser.setPassword(user.getPassword());

        return add(basicUser);
    }
}
