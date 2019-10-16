package com.powerkiosk.dao;

import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.ServiceProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CustomerRepositoryTest extends AbstractRepositoryTest{


    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void findCustomerById() {

        //setup test data
        ServiceProvider provider = new ServiceProvider();
        provider.setName("BusinessA");
        providerRepository.save(provider);

        int total = 10;
        for(int i = 0; i < total; i++){
            Customer customer = new Customer(UUID.randomUUID().toString(), i);
            customer.setServiceProvider(provider);
            customerRepository.save(customer);
        }

        //execute and verify
        List<Customer> all = customerRepository.findAll();

        assertEquals(total, all.size());
        Customer first = all.get(0);
        Customer actual = customerRepository.findById(first.getId()).get();
        assertEquals(first, actual);
    }

}