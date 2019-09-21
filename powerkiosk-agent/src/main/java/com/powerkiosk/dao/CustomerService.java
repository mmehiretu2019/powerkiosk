package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;

public interface CustomerService {

    Customer getNextCustomer(int serverId);

    Customer addCustomer(Customer customer);
}
