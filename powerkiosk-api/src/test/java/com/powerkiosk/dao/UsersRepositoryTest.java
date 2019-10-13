package com.powerkiosk.dao;

import com.powerkiosk.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersRepositoryTest {

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

        //verify
        assertEquals(total, users.size());

        users.stream().forEach(u -> System.out.println(u.getId()));
    }

}