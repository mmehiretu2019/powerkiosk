package com.powerkiosk.service;

import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;

public interface CustomerService {

    Customer getNextCustomer(int serverId);

    Customer addCustomer(Customer customer);

    ServingInfo getCurrentServingInfo();

    ServingSummary getServingSummary();

    String addCustomerServer(CustomerServer server);
}
