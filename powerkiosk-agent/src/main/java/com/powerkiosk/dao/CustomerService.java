package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;
import com.powerkiosk.model.ServingInfo;

public interface CustomerService {

    Customer getNextCustomer(int serverId);

    Customer addCustomer(Customer customer);

    ServingInfo getCurrentServingInfo();
}
