package com.powerkiosk.dao;

import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;
import com.powerkiosk.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private InMemoryCustomerServiceDao customerServiceDao = new InMemoryCustomerServiceDao();

    public Customer getNextCustomer(int serverId) {
        return customerServiceDao.getNextCustomer(serverId);
    }

    public Customer addCustomer(Customer customer) {
        return customerServiceDao.addCustomer(customer);
    }

    public ServingInfo getCurrentServingInfo() {
        return customerServiceDao.getCurrentServingInfo();
    }

    @Override
    public ServingSummary getServingSummary() {
        return customerServiceDao.getServingSummary();
    }

    @Override
    public String addCustomerServer(CustomerServer server) {
        String id = UUID.randomUUID().toString();
        return id;
    }
}
