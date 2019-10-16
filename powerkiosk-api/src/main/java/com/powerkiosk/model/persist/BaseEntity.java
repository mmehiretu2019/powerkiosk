package com.powerkiosk.model.persist;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BaseEntity {

    @Id
    private String id;

    public BaseEntity(){
        this.id = UUID.randomUUID().toString();
    }

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id", nullable = false)
    private ServiceProvider serviceProvider;

    public String getId() {
        return UUID.randomUUID().toString();
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
