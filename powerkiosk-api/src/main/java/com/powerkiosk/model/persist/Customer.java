package com.powerkiosk.model.persist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
public class Customer extends BaseEntity{

    @GeneratedValue
    private long lineNumber;
    @OneToOne(mappedBy = "currentCustomer")
    private CustomerServer customerServer;

    private OffsetDateTime arrivalDate;
    private OffsetDateTime serviceCompleteDate;

    //for hibernate
    public Customer(){

    }

    public Customer(long lineNumber){
        this.lineNumber = lineNumber;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public CustomerServer getCustomerServer() {
        return customerServer;
    }

    public void setCustomerServer(CustomerServer customerServer) {
        this.customerServer = customerServer;
    }

    public OffsetDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(OffsetDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public OffsetDateTime getServiceCompleteDate() {
        return serviceCompleteDate;
    }

    public void setServiceCompleteDate(OffsetDateTime serviceCompleteDate) {
        this.serviceCompleteDate = serviceCompleteDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return lineNumber == customer.lineNumber &&
                Objects.equals(arrivalDate, customer.arrivalDate) &&
                Objects.equals(serviceCompleteDate, customer.serviceCompleteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, arrivalDate, serviceCompleteDate);
    }
}
