package com.powerkiosk.admin.controller;


import com.powerkiosk.admin.dao.UserService;
import com.powerkiosk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user) {

        User addedUser = userService.add(user);
        return new ResponseEntity(addedUser, HttpStatus.CREATED);
    }
}
