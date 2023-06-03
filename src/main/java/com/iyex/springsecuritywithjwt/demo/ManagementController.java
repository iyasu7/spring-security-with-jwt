package com.iyex.springsecuritywithjwt.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
public class ManagementController {
    @GetMapping
    public String getAdminMsg(){
        return "get all list for management";
    }
    @PostMapping
    public String postAdminMsg(){
        return "post/create all list for management";
    }
    @PutMapping
    public String putAdminMsg(){
        return "put/update all list for management";
    }
    @DeleteMapping
    public String deleteAdminMsg(){
        return "delete all list for management";
    }
}
