package com.powerkiosk.service;

import com.powerkiosk.model.SignUpUser;
import com.powerkiosk.model.User;

public interface UserService {

    User add(User user);

    String add(SignUpUser user);
}
