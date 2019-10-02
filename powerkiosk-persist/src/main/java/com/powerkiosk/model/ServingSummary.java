package com.powerkiosk.model;

public class ServingSummary {

    private long servedCount;
    private long waitingCount;

    public long getServedCount() {
        return servedCount;
    }

    public void setServedCount(long servedCount) {
        this.servedCount = servedCount;
    }

    public long getWaitingCount() {
        return waitingCount;
    }

    public void setWaitingCount(long waitingCount) {
        this.waitingCount = waitingCount;
    }
}
