package com.powerkiosk.model;

import java.time.OffsetDateTime;

public class Customer {

    private long id;
    private long lineNumber;
    private OffsetDateTime arrivalDate;
    private OffsetDateTime serviceCompleteDate;

    public Customer(long id, long lineNumber){
        this.id = id;
        this.lineNumber = lineNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
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
}
