package com.powerkiosk.model.persist;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CustomerServer extends BaseEntity {

    private String stationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_customer", referencedColumnName = "id")
    private Customer currentCustomer;

    //for hibernate
    public CustomerServer(){

    }

    public CustomerServer(UUID id, Customer customer){
        setId(id);
        this.currentCustomer = customer;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerServer server = (CustomerServer) o;
        return Objects.equals(stationId, server.stationId) &&
                Objects.equals(currentCustomer, server.currentCustomer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, currentCustomer);
    }
}
