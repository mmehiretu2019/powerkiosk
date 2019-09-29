package com.powerkiosk.controller;

import com.powerkiosk.dao.CustomerService;
import com.powerkiosk.model.Customer;
import com.powerkiosk.model.ServingInfo;
import com.powerkiosk.model.ServingRequest;
import com.powerkiosk.model.ServingSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;

@Controller
public class CustomerServiceController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/currentServingInfo")
    public ResponseEntity getCurrentServingInfo() {
        ServingInfo currentServingInfo = customerService.getCurrentServingInfo();

        if(currentServingInfo != null){
            return new ResponseEntity(currentServingInfo, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/info")
    @SendTo("/topic/servingInfo")
    public ResponseEntity getNextCustomer(ServingRequest request){
        Customer nextCustomer = customerService.getNextCustomer(request.getServerId());

        if(nextCustomer != null){
            ServingInfo servingInfo = customerService.getCurrentServingInfo();

            //send summary message
            ServingSummary summary = customerService.getServingSummary();
            messagingTemplate.convertAndSend("/topic/servingSummary", new ResponseEntity<>(summary, HttpStatus.OK));

            return new ResponseEntity(servingInfo, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @MessageMapping("/summary")
    @SendTo("/topic/servingSummary")
    public ResponseEntity getNextNumberInLine(){

        customerService.addCustomer(new Customer(-1, -1));
        ServingSummary summary = customerService.getServingSummary();
        if(summary != null){

            return new ResponseEntity(summary, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    //set up in memory test database
    @PostConstruct
    public void init(){
        for(int i = 1; i < 100; i++){
            customerService.addCustomer(new Customer(i, -1));
        }
    }
}
