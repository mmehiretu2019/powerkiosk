package com.powerkiosk.controller;


import com.powerkiosk.service.UserService;
import com.powerkiosk.model.SignUpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {


//    @Autowired
//    private ServiceProviderService providerService;

    @Autowired
    private UserService userService;


//    @PostMapping
//    public ResponseEntity registerServiceProvider(@RequestBody ServiceProvider provider) {
//
//        String id = providerService.registerProvider(provider);
//        provider.setId(null);
//        return new ResponseEntity(provider, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody SignUpUser user){

        String id = userService.add(user);

        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable String id){
        return new ResponseEntity("fakeID", HttpStatus.OK);
    }

    @RequestMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
