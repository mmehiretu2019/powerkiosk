package com.powerkiosk.service;

import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getNextCustomer(String providerId, String serverId);

    Customer addCustomer(Customer customer);

    ServingInfo getCurrentServingInfo(String providerId);

    ServingSummary getServingSummary(String providerId);

    CustomerServer addCustomerServer(CustomerServer server);
}
