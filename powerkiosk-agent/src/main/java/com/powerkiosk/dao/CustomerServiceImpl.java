package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;
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

    public ServingInfo getCurrentServingInfo() {
        return customerServiceDao.getCurrentServingInfo();
    }

    @Override
    public ServingSummary getServingSummary() {
        return customerServiceDao.getServingSummary();
    }
}
