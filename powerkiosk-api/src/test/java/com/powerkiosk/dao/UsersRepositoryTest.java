package com.powerkiosk.dao;

import com.powerkiosk.model.persist.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UsersRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testRepo(){
        //add records
        int total = 10;
        for(int i =0; i < total; i++){
            User user = new User();
            user.setEmail("user" + i + "@gmail.com");
            user.setFirstName("Merry" + i);
            user.setLastName("Cooper" + i);

            usersRepository.save(user);
        }
        //get records
        List<User> users = usersRepository.findAll();

        users.stream().forEach(u -> System.out.println(u.getId()));
    }

}