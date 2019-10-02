package com.powerkiosk.controller;


import com.powerkiosk.dao.ServiceProviderService;
import com.powerkiosk.model.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/providers")
public class ServiceProviderController {


    @Autowired
    private ServiceProviderService providerService;

    @PostMapping
    public ResponseEntity registerServiceProvider(@RequestBody ServiceProvider provider) {

        String id = providerService.registerProvider(provider);
        provider.setId(id);
        return new ResponseEntity(provider, HttpStatus.CREATED);
    }
}
