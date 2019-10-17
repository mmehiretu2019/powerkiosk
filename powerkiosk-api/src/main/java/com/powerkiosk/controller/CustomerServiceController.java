package com.powerkiosk.controller;

import com.powerkiosk.model.*;
import com.powerkiosk.model.persist.Customer;
import com.powerkiosk.model.persist.CustomerServer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class CustomerServiceController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


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
    public ResponseEntity getNextNumberInLine(){

        customerService.addCustomer(new Customer(null, -1));
        ServingSummary summary = customerService.getServingSummary(null);
        if(summary != null){

            return new ResponseEntity(summary, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
