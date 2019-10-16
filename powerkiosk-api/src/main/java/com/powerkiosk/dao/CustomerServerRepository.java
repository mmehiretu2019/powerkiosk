package com.powerkiosk.dao;

import com.powerkiosk.model.persist.CustomerServer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerServerRepository extends JpaRepository<CustomerServer, String> {

    List<CustomerServer> findByServiceProvider(String providerId);
}
