package com.alver.api.controller;

import com.alver.core.model.Address;
import com.alver.app.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
public class AddressController extends EntityController<Address> {

    @Autowired
    public AddressController(IService<Address> service) {
        super(service);
    }
}
