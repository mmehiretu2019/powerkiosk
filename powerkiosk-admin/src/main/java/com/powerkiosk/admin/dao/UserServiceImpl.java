package com.powerkiosk.admin.dao;

import com.powerkiosk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private InMemoryUserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }
}
