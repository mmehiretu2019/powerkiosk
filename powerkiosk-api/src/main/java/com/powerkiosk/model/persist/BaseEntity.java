package com.powerkiosk.model.persist;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class BaseEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "service_provider_id", referencedColumnName = "id", nullable = false)
    private ServiceProvider serviceProvider;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
