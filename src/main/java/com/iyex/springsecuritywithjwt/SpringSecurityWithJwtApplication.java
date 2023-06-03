package com.iyex.springsecuritywithjwt;

import com.iyex.springsecuritywithjwt.auth.AuthenticationService;
import com.iyex.springsecuritywithjwt.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.iyex.springsecuritywithjwt.entity.Role.ADMIN;
import static com.iyex.springsecuritywithjwt.entity.Role.MANAGER;

@SpringBootApplication
public class SpringSecurityWithJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityWithJwtApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service){
        return args -> {
            var admin = RegisterRequest.builder()

                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@gmail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: "+ service.register(admin).getToken());

            var manager = RegisterRequest.builder()

                    .firstname("Manger")
                    .lastname("Manager")
                    .email("manger@gmail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();
            System.out.println("Manager token: "+ service.register(manager).getToken());
        };
    }
}
