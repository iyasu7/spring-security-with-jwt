package com.iyex.springsecuritywithjwt.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String getAdminMsg(){
        return "get all list for admin";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String postAdminMsg(){
        return "post/create all list for admin";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String putAdminMsg(){
        return "put/update all list for admin";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String deleteAdminMsg(){
        return "delete all list for admin";
    }
}
