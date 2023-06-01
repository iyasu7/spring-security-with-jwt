package com.iyex.springsecuritywithjwt.token;

import com.iyex.springsecuritywithjwt.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
