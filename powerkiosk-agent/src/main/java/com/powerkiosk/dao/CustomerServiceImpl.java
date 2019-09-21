package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private InMemoryCustomerServiceDao customerServiceDao = new InMemoryCustomerServiceDao();

    public Customer getNextCustomer(int serverId) {
        return customerServiceDao.getNextCustomer(serverId);
    }

    public Customer addCustomer(Customer customer) {
        return customerServiceDao.addCustomer(customer);
    }
}
