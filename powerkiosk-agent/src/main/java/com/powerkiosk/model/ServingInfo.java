package com.powerkiosk.model;

import java.util.List;

public class ServingInfo {

    private List<CustomerServer> customerServers;

    public List<CustomerServer> getCustomerServers() {
        return customerServers;
    }

    public void setCustomerServers(List<CustomerServer> customerServers) {
        this.customerServers = customerServers;
    }
}
