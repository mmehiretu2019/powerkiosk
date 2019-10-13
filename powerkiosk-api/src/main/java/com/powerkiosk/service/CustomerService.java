package com.powerkiosk.service;

import com.powerkiosk.model.Customer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;

public interface CustomerService {

    Customer getNextCustomer(int serverId);

    Customer addCustomer(Customer customer);

    ServingInfo getCurrentServingInfo();

    ServingSummary getServingSummary();
}
