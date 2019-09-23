package com.powerkiosk.model;

public class CustomerServer {

    private int id;
    private Customer currentCustomer;

    public CustomerServer(int id, Customer customer){
        this.id = id;
        this.currentCustomer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }
}
