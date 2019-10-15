package com.powerkiosk.dao;

import com.powerkiosk.model.persist.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, String> {
}
