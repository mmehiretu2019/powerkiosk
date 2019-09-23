package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;
import com.powerkiosk.model.CustomerServer;
import com.powerkiosk.model.ServingInfo;

import java.time.OffsetDateTime;
import java.util.*;

import static java.time.ZoneOffset.UTC;

public class InMemoryCustomerServiceDao {

    private static volatile long lineNumber = 0;
    private Map<Integer, List<Customer>> serverToCustomerMap = new HashMap();
    private Queue<Customer> customerLine = new LinkedList<>();

    public Customer getNextCustomer(int serverId) {
        //first set completion time of the current customer
        List<Customer> serverCustomers = serverToCustomerMap.get(serverId);
        if(serverCustomers != null) {
            Customer currCustomer = serverCustomers
                    .stream()
                    .filter(c -> c.getServiceCompleteDate() == null)
                    .findAny()
                    .get();

            currCustomer.setServiceCompleteDate(OffsetDateTime.now(UTC));
        }else {
            serverCustomers = new ArrayList<>();
            serverToCustomerMap.put(serverId, serverCustomers);
        }

        //then get next customer to serve
        Customer next = customerLine.remove();

        //Put serverId and customer to map
        serverCustomers.add(next);
        return next;
    }

    public synchronized Customer addCustomer(Customer customer) {
        //get next number in the line and assign
        long nextNumber = ++lineNumber;
        customer.setLineNumber(nextNumber);
        customer.setId(nextNumber);
        customer.setArrivalDate(OffsetDateTime.now(UTC));
        //add to the queue
        customerLine.add(customer);
        return customer;
    }

    public ServingInfo getCurrentServingInfo(){
        ServingInfo servingInfo = new ServingInfo();
        List<CustomerServer> servers = new ArrayList<>();
        for(int serverId: serverToCustomerMap.keySet()){

            Customer currCustomer = serverToCustomerMap.get(serverId)
                    .stream()
                    .filter(c -> c.getServiceCompleteDate() == null)
                    .findAny()
                    .get();
            servers.add(new CustomerServer(serverId, currCustomer));
        }
        //sort servers by id
        servers.stream().sorted();
        servingInfo.setCustomerServers(servers);

        return servingInfo;
    }
}
