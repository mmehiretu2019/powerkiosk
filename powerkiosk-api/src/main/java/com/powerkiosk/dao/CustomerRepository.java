package com.powerkiosk.dao;

import com.powerkiosk.model.persist.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findTopByOrderByArrivalDateAsc();

    @Query("SELECT count(*) FROM Customer c WHERE c.serviceProvider = :providerId AND c.serviceCompleteDate is null")
    int countWaitingCustomers(String providerId);

    @Query("SELECT count(*) FROM Customer c WHERE c.serviceProvider = :providerId AND c.serviceCompleteDate is not null")
    int countServiceCompletedCustomers(String providerId);
}
