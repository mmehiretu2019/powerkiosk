package com.powerkiosk.dao;

import com.powerkiosk.model.Customer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class InMemoryCustomerServiceDao {

    private static volatile long lineNumber = 0;
    private Map<Integer, Customer> serverToCustomerMap = new HashMap();
    private Queue<Customer> customerLine = new LinkedList<Customer>();

    public Customer getNextCustomer(int serverId) {
        //first remove current customer
        serverToCustomerMap.remove(serverId);

        //then get next customer to serve
        Customer next = customerLine.remove();

        //Put serverId and customer to map
        serverToCustomerMap.put(serverId, next);
        return next;
    }

    public synchronized Customer addCustomer(Customer customer) {
        //get next number in the line and assign
        long nextNumber = ++lineNumber;
        customer.setLineNumber(nextNumber);
        customer.setId(nextNumber);
        //add to the queue
        customerLine.add(customer);
        return customer;
    }
}
