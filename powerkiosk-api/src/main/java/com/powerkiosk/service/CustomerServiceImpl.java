package com.powerkiosk.service;

import com.powerkiosk.dao.CustomerRepository;
import com.powerkiosk.dao.CustomerServerRepository;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingSummary;
import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServerRepository serverRepository;

    public Optional<Customer> getNextCustomer(String providerId, String serverId) {

        Optional<Customer> nextCustomer = Optional.empty();

        //Get the current customer being served by this server and complete the service
        Optional<CustomerServer> server = serverRepository.findById(providerId);

        if(server.isPresent()) {
            Customer currentCustomer = server.get().getCurrentCustomer();

            if(currentCustomer != null) {
                currentCustomer.setServiceCompleteDate(OffsetDateTime.now(UTC));
                serverRepository.save(server.get());
            }

            //Get next customer
            nextCustomer = customerRepository.findTopByOrderByArrivalDateAsc();
            if (nextCustomer.isPresent()) {
                Customer customer = nextCustomer.get();
                customer.setCustomerServer(server.get());
                customerRepository.save(customer);
            }
        }

        return nextCustomer;
    }

    public Customer addCustomer(Customer customer) {
        Customer saved = customerRepository.save(customer);
        return saved;
    }

    public ServingInfo getCurrentServingInfo(String providerId) {

        ServingInfo servingInfo = new ServingInfo();
        List<CustomerServer> servers = serverRepository.findByServiceProvider(providerId);
        servingInfo.setCustomerServers(servers);

        return servingInfo;
    }

    @Override
    public ServingSummary getServingSummary(String providerId) {

        int waitingCount = customerRepository.countWaitingCustomers(providerId);
        int completedCount = customerRepository.countServiceCompletedCustomers(providerId);

        ServingSummary summary = new ServingSummary();
        summary.setWaitingCount(waitingCount);
        summary.setServedCount(completedCount);

        return summary;
    }

    @Override
    public CustomerServer addCustomerServer(CustomerServer server) {

        CustomerServer saved = serverRepository.save(server);
        return saved;
    }
}
