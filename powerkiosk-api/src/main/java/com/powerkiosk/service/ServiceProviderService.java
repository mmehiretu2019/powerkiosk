package com.powerkiosk.service;

import com.powerkiosk.model.persist.ServiceProvider;

import java.util.Optional;

public interface ServiceProviderService {

    String registerProvider(ServiceProvider provider);

    Optional<ServiceProvider> findProviderById(String providerId);
}
