package com.powerkiosk.dao;

import com.powerkiosk.model.persist.CustomerServer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerServerRepository extends JpaRepository<CustomerServer, UUID> {
}
