package com.powerkiosk.service;

import com.powerkiosk.dao.ServiceProviderRepository;
import com.powerkiosk.model.persist.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {

    @Autowired
    private ServiceProviderRepository providerRepository;


    @Override
    public String registerProvider(ServiceProvider provider) {
        return providerRepository.save(provider).getId().toString();
    }

    @Override
    public Optional<ServiceProvider> findProviderById(String providerId) {
        return providerRepository.findById(providerId);
    }
}
