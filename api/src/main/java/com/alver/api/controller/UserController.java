package com.alver.api.controller;

import com.alver.core.model.User;
import com.alver.app.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController extends EntityController<User> {

    @Autowired
    public UserController(IService<User> userService) {
        super(userService);
    }
}
