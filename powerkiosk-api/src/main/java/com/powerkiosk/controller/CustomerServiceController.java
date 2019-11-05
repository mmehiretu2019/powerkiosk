package com.powerkiosk.controller;

import com.powerkiosk.model.*;
import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.persist.ServiceProvider;
import com.powerkiosk.service.CustomerService;
import com.powerkiosk.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerServiceController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private ServiceProviderService providerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Logger log = LoggerFactory.getLogger(getClass());


    @MessageMapping("/customerService/{providerId}/serve")
    @SendTo("/topic/servingInfo/{providerId}")
    public void startServing(@DestinationVariable String providerId, CustomerServer server){
        customerService.addCustomerServer(server);
        ServingInfo servingInfo = customerService.getCurrentServingInfo(providerId);
        messagingTemplate.convertAndSend("/topic/servingInfo/" + providerId, new ResponseEntity<>(servingInfo, HttpStatus.OK));

    }

    @GetMapping("/currentServingInfo")
    public ResponseEntity getCurrentServingInfo() {
        ServingInfo currentServingInfo = customerService.getCurrentServingInfo(null);

        if(currentServingInfo != null){
            return new ResponseEntity(currentServingInfo, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/info/{providerId}")
    @SendTo("/topic/servingInfo/{providerId}")
    public ResponseEntity getNextCustomer(@DestinationVariable String providerId, ServingRequest request){
        Optional<Customer> nextCustomer = customerService.getNextCustomer(providerId, String.valueOf(request.getServerId()));

        if(nextCustomer.isPresent()){
            ServingInfo servingInfo = customerService.getCurrentServingInfo(providerId);

            //send summary message
            ServingSummary summary = customerService.getServingSummary(providerId);
            messagingTemplate.convertAndSend("/topic/servingSummary/" + providerId, new ResponseEntity<>(summary, HttpStatus.OK));

            return new ResponseEntity(servingInfo, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/summary/{providerId}")
    @SendTo("/topic/servingSummary/{providerId}")
    public Message getNextNumberInLine(@DestinationVariable String providerId){

        Optional<ServiceProvider> provider = providerService.findProviderById(providerId);

        if(provider.isPresent()){
            Customer customer = new Customer();
            customer.setServiceProvider(provider.get());

            customerService.addCustomer(customer);
            ServingSummary summary = customerService.getServingSummary(providerId);
            if(summary != null){

                return new GenericMessage(summary);
            }
        }

        return new ErrorMessage(new EntityNotFoundException());
    }

    @PostConstruct
    public void setUpTest(){
        ServiceProvider provider = new ServiceProvider();
        provider.setFirstName("John");
        provider.setLastName("Doe");
        String providerId = providerService.registerProvider(provider);
        log.info("====== Started with test provider: {}", providerId);

    }
}
