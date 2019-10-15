package com.powerkiosk.service;

import com.powerkiosk.dao.UsersRepository;
import com.powerkiosk.model.CustomUserDetails;
import com.powerkiosk.model.SignUpUser;
import com.powerkiosk.model.persist.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void testSetUp(){
        User user = new User();
        user.setEmail("mmehiretu@gmail.com");
        user.setPassword("password");

        usersRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByEmail(username);
        user.orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user.get());
    }

    @Override
    public User add(User user) {

        return usersRepository.save(user);
    }

    @Override
    public String add(SignUpUser user) {
        User basicUser = new User();
        basicUser.setEmail(user.getEmail());
        basicUser.setPassword(user.getPassword());

        return add(basicUser).getId().toString();
    }
}
