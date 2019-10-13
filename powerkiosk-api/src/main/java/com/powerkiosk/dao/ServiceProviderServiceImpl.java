package com.powerkiosk.dao;

import com.powerkiosk.model.ServiceProvider;
import com.powerkiosk.service.ServiceProviderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
    @Override
    public String registerProvider(ServiceProvider provider) {

        return UUID.randomUUID().toString();
    }
}
