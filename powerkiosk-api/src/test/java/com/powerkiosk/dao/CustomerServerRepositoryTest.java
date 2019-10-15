package com.powerkiosk.dao;

import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.persist.ServiceProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CustomerServerRepositoryTest extends AbstractRepositoryTest{


    @Autowired
    private CustomerServerRepository serverRepository;

    @Test
    public void findCustomerById() {

        //setup test data
        ServiceProvider provider = new ServiceProvider();
        provider.setName("BusinessA");
        providerRepository.save(provider);

        int total = 10;
        for(int i = 0; i < total; i++){
            Customer customer = new Customer(UUID.randomUUID(), i);
            customer.setServiceProvider(provider);

            CustomerServer server = new CustomerServer(UUID.randomUUID(), customer);
            server.setServiceProvider(provider);
            serverRepository.save(server);
        }

        //execute and verify
        List<CustomerServer> all = serverRepository.findAll();

        assertEquals(total, all.size());
        CustomerServer first = all.get(0);
        CustomerServer actual = serverRepository.findById(first.getId()).get();
        assertEquals(first, actual);
    }

}