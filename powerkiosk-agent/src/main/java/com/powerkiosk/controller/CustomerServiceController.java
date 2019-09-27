package com.powerkiosk.controller;

import com.powerkiosk.dao.CustomerService;
import com.powerkiosk.model.Customer;
import com.powerkiosk.model.ServingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/customerService")
public class CustomerServiceController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/currentServingInfo")
    public ResponseEntity getCurrentServingInfo() {
        ServingInfo currentServingInfo = customerService.getCurrentServingInfo();

        if(currentServingInfo != null){
            return new ResponseEntity(currentServingInfo, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{serverId}")
    @MessageMapping("/customerService")
    @SendTo("/topic/servingInfo")
    public ResponseEntity getNextCustomer(@PathVariable("serverId") int serverId){
        Customer nextCustomer = customerService.getNextCustomer(serverId);

        if(nextCustomer != null){
            return new ResponseEntity(nextCustomer, HttpStatus.OK);
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
